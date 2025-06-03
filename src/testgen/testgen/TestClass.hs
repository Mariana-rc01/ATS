module TestClass (generateTestClass) where

import Data.List (intercalate)
import Java (indent)
import TestTemplate (generateTestsFromTemplate)

import System.Process (StdStream(CreatePipe), createProcess, proc, std_in, std_out, waitForProcess)
import System.IO (hClose, hGetContents, hPutStr)
import Control.Exception (bracket, evaluate)

import FacadeTemplates

templates =
  [
    equalityTemplate -- TODO: remove this simple template only to show the desired architecture
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
        [ [ "import java.util.Arrays;"
          , "import java.util.List;"
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
