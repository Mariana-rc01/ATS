-- Copyright 2025 Humberto Gomes, José Lopes, Mariana Rocha
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

module TestClass (generateTestClass) where

import Data.List (intercalate)
import Java (indent, toJavaExpressionList)
import TestTemplate (generateTestsFromTemplate)

import System.Process (StdStream(CreatePipe), createProcess, proc, std_in, std_out, waitForProcess)
import System.IO (hClose, hGetContents, hPutStr)
import Control.Exception (bracket, evaluate)

import FacadeTemplates

templates =
  [
    getActivitiesFromUserTemplate,
    addActivityToUserTemplate,
    removeActivityFromUserTemplate,
    createTrainingPlanTemplate,
    createTrainingPlanExceptionTemplate,
    constructTrainingPlanTemplate,
    constructTrainingPlanExceptionTemplate
  ]

generateTests :: IO [String]
generateTests = do
  tests <- sequence $ map generateTestsFromTemplate templates
  return $ intercalate [""] tests

generateUnformattedTestClass :: IO [String]
generateUnformattedTestClass = do
  tests <- generateTests
  return
    $ concat
        [ [ "package MakeItFit;"
          , ""
          , "import java.util.Arrays;"
          , "import java.util.List;"
          , "import org.junit.jupiter.api.Test;"
          , "import static org.junit.jupiter.api.Assertions.*;"
          , ""
          , "import MakeItFit.users.User;"
          , ""
          , "public class MakeItFitTest {"
          ]
        , indent tests
        , ["}"]
        ]

format :: String -> IO String
format source = do
  (Just stdin, Just stdout, _, process) <- createProcess
    (proc "clang-format" ["--assume-filename=MakeItFitTest.java"])
    { std_in = CreatePipe, std_out = CreatePipe }

  bracket
    (return ())
    (\_ -> hClose stdin >> hClose stdout)
    (\_ -> do
      hPutStr stdin source
      hClose stdin

      output <- hGetContents stdout
      evaluate (length output) -- Force deep strict evaluation

      waitForProcess process
      return output
    )

-- | Generates the test class for the applications facade
generateTestClass :: IO String
generateTestClass = do
  lines <- generateUnformattedTestClass
  format $ unlines lines
