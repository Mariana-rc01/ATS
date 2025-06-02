-- | Test template definition
module TestTemplate (TestTemplate(..), genToTestTemplate, generateTestsFromTemplate) where

import Data.List (intercalate)
import Java (decorateTest)
import Test.QuickCheck.Gen (Gen, generate)

-- Test template data structure and helpers

-- | A template used for test generation
data TestTemplate =
  TestTemplate
    String        -- ^ Test name
    (IO [String]) -- ^ Generator function of the lines in the test's body
    Int           -- ^ Number of tests of this type to generate

-- | Creates a 'TestTemplate' from a QuickCheck generator
genToTestTemplate :: String       -- ^ Test name
                  -> Gen [String] -- ^ QuickCheck generator of the lines of the test's body
                  -> Int          -- ^ Number of tests
                  -> TestTemplate -- ^ Output test template
genToTestTemplate name gen n = TestTemplate name (generate gen) n

-- Test generation

getTestNumber :: Int -> Int -> String
getTestNumber 1 _ = ""
getTestNumber _ i = show i

generateTestFromTemplate :: TestTemplate -> Int -> IO [String]
generateTestFromTemplate (TestTemplate name sampler n) i = do
  body <- sampler
  return $ decorateTest (name ++ getTestNumber n i) body

-- | Generates all tests from a template
generateTestsFromTemplate :: TestTemplate -- ^ Test template to use for generation
                          -> IO [String]  -- ^ Lines of the generated tests
generateTestsFromTemplate t@(TestTemplate _ _ n) = do
  tests <- sequence $ zipWith generateTestFromTemplate (repeat t) [1 .. n]
  return $ intercalate [""] tests
