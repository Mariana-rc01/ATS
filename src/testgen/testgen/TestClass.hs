module TestClass (generateTestClass) where

import Data.List (intercalate)
import Java (indent)
import TestTemplate (TestTemplate(..), generateTestsFromTemplate)

templates =
  [
    TestTemplate "BooleanTest" (return ["assertTrue(true)"]) 5
  , TestTemplate "IntegerTest" (return ["assertEquals(5, 5)"]) 5
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

-- | Generates the test class for the applications facade
generateTestClass :: IO String
generateTestClass = do
  lines <- generateUnformattedTestClass
  return $ unlines lines
