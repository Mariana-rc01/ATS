{-# LANGUAGE FlexibleInstances #-}

module JavaData
  ( JavaData(..)
  ) where

import Data.Char (isAscii, ord)
import Data.List (intercalate)
import Numeric (showHex)

-- JavaData definition
class JavaData a where
  javaTypeName :: a -> String
  toJavaExpression :: a -> String
  toJavaVariable :: String -> a -> String
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
