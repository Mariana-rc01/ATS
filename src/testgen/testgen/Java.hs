{-# LANGUAGE FlexibleInstances #-}

-- | Java code generation utilities
module Java
  (
    JavaData(..)
  , indent
  , decorateTest
  , assertTrue
  , assertEquals
  , assertSame
  , assertThrows) where

import Data.Char (isAscii, ord)
import Data.List (intercalate)
import Numeric (showHex)

-- JavaData definition
class JavaData a where
  -- | Gets the equivalent Java name for this Haskell type
  javaTypeName :: a      -- ^ Input value
               -> String -- ^ Java type name

  -- |
  -- Creates a Java expression that evaluates to the given value.
  --
  -- Example output: @Arrays.asList(1, 2, 3)@
  toJavaExpression :: a      -- ^ Input value
                   -> String -- ^ Java expression

  -- |
  -- Creates a Java variable declaration, where the variable is initialized to the given value
  --
  -- Example output: @List<Integer> list = Arrays.asList(1, 2, 3)@
  toJavaVariable :: String -- ^ Variable name
                 -> a      -- ^ Input value
                 -> String -- ^ Java variable declation
  toJavaVariable name obj = javaTypeName obj ++ " " ++ name ++ " = " ++ toJavaExpression obj ++ ";"

-- Basic scalar types

instance JavaData Bool where
  javaTypeName = const "bool"
  toJavaExpression False = "false"
  toJavaExpression True = "true"

instance JavaData Int where
  javaTypeName = const "int"
  toJavaExpression = show

instance JavaData Integer where
  javaTypeName = const "int"
  toJavaExpression = show

instance JavaData Float where
  javaTypeName = const "float"
  toJavaExpression = show

instance JavaData Double where
  javaTypeName = const "double"
  toJavaExpression = show

pad :: Int -> a -> [a] -> [a]
pad n f xs = replicate (n - length xs) f ++ xs

toJavaCharacter '"' = "\\\""
toJavaCharacter '\'' = "\\'"
toJavaCharacter '\\' = "\\\\"
toJavaCharacter '\t' = "\\t"
toJavaCharacter '\b' = "\\b"
toJavaCharacter '\n' = "\\n"
toJavaCharacter '\r' = "\\r"
toJavaCharacter '\f' = "\\f"
toJavaCharacter c
  | isAscii c = [c]
  | otherwise = "\\u" ++ pad 4 '0' (showHex (ord c) "")

instance JavaData Char where
  javaTypeName = const "char"
  toJavaExpression c = "\'" ++ toJavaCharacter c ++ "\'"

instance JavaData String where
  javaTypeName = const "String"
  toJavaExpression s = "\"" ++ concat (map toJavaCharacter s) ++ "\""

-- Lists

toJavaExpressionList :: JavaData a => [a] -> String
toJavaExpressionList xs = "Arrays.asList(" ++ intercalate ", " (map toJavaExpression xs) ++ ")"

instance JavaData [Bool] where
  javaTypeName = const "List<Boolean>"
  toJavaExpression = toJavaExpressionList

instance JavaData [Int] where
  javaTypeName = const "List<Integer>"
  toJavaExpression = toJavaExpressionList

instance JavaData [Integer] where
  javaTypeName = const "List<Integer>"
  toJavaExpression = toJavaExpressionList

instance JavaData [Float] where
  javaTypeName = const "List<Float>"
  toJavaExpression = toJavaExpressionList

instance JavaData [Double] where
  javaTypeName = const "List<Double>"
  toJavaExpression = toJavaExpressionList

instance JavaData [String] where
  javaTypeName = const "List<String>"
  toJavaExpression = toJavaExpressionList

-- Utilities

-- | Indents a sequence of lines with 4 spaces
indent :: [String] -> [String]
indent = map ("    " ++)

-- |
-- Puts the body of a test in a method declaration
--
-- Example output:
--
-- @
-- \@Test
-- void testName() {
--     testContents
-- }
-- @
decorateTest :: String   -- ^ Name of the test method
             -> [String] -- ^ Lines constituting the body of the test method
             -> [String] -- ^ Lines of the test method
decorateTest name test = concat [["@Test", "void " ++ name ++ "() {"], indent test, ["}"]]

-- Assertions

-- | @assertTrue@ statement generator
assertTrue :: String -- ^ A Java boolean expression
           -> String -- ^ A Java assertion statement
assertTrue c = "assertTrue(" ++ c ++ ");"

-- | @assertEquals@ statement generator
assertEquals :: String -- ^ A Java expression for the expected value
             -> String -- ^ A Java expression for the obtained value
             -> String -- ^ A Java assertion statement
assertEquals e g = "assertEquals(" ++ e ++ ", " ++ g ++ ");"

-- | @assertSame@ statement generator
assertSame :: String -- ^ A Java expression for the expected value
           -> String -- ^ A Java expression for the obtained value
           -> String -- ^ A Java assertion statement
assertSame e g = "assertSame(" ++ e ++ ", " ++ g ++ ");"

-- | @assertThrows@ statement generator
assertThrows :: String -- ^ Name of the expected exception class
             -> [String] -- ^ Lines where the exception should occur
             -> [String] -- ^ A Java assertion statement
assertThrows e b =
  [
    "assertThrows("
  , ("    " ++ e ++ ".class,")
  , "    () -> {"
  ] ++ (indent (indent b)) ++
  [
    "    }"
  , ");"
  ]
