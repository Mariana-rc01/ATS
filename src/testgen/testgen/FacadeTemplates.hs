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

module FacadeTemplates (
    equalityTemplate
  , executeQueryHowManyKMsDoneTemplate
  , executeQueryHowManyKMsDoneDatesTemplate
  , executeQueryHowManyAltimetryDoneTemplate
  , executeQueryHowManyAltimetryDoneDatesTemplate
  ) where

import Generators (User(..), MakeItFitDate(..))
import Java (JavaData(..), assertEquals, javaImports, runJava, toJavaExpression)
import TestTemplate (TestTemplate(..), genToTestTemplate)
import Test.QuickCheck (Gen, arbitrary, elements, generate, listOf)

equalityTestGenerator :: Gen [String]
equalityTestGenerator = do
  elem <- arbitrary :: Gen Int
  return [assertEquals (toJavaExpression elem) (toJavaExpression elem)]

  -- Other usage examples
  -- return [assertTrue (toJavaExpression elem ++ " == " ++ toJavaExpression elem)]
  -- return [assertSame (toJavaExpression elem) (toJavaExpression elem)]
  -- return $ assertThrows "Exception" [assertEquals "1" "1"]

equalityTemplate :: TestTemplate
equalityTemplate = genToTestTemplate "equals" equalityTestGenerator 3

executeQueryHowManyKMsDoneGenerator :: IO [String]
executeQueryHowManyKMsDoneGenerator = do
  user <- generate $ (arbitrary :: Gen User)
  resultLines <- runJava $ concat
    [
      javaImports
    , toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , "System.out.println(makeItFit.executeQueryHowManyKMsDone(user.getEmail()))"
      ]
    ]
  return $ concat
    [
      toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , assertEquals (last resultLines) "makeItFit.executeQueryHowManyKMsDone(user.getEmail())"
      ]
    ]

executeQueryHowManyKMsDoneTemplate :: TestTemplate
executeQueryHowManyKMsDoneTemplate =
  TestTemplate "testExecuteQueryHowManyKMsDone" executeQueryHowManyKMsDoneGenerator 1

executeQueryHowManyKMsDoneDatesGenerator :: IO [String]
executeQueryHowManyKMsDoneDatesGenerator = do
  user                        <- generate $ (arbitrary :: Gen User)
  date1@(MakeItFitDate y m d) <- generate $ (arbitrary :: Gen MakeItFitDate)
  date2                       <- return $ MakeItFitDate (y + 100) m d
  expr <- return $ concat
    [
      "makeItFit.executeQueryHowManyKMsDone(user.getEmail(), "
    , toJavaExpression date1
    , ", "
    , toJavaExpression date2
    , ")"
    ]
  resultLines <- runJava $ concat
    [
      javaImports
    , toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , "System.out.println(" ++ expr ++ ")"
      ]
    ]
  return $ concat
    [
      toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , assertEquals (last resultLines) expr
      ]
    ]

executeQueryHowManyKMsDoneDatesTemplate :: TestTemplate
executeQueryHowManyKMsDoneDatesTemplate =
  TestTemplate "testExecuteQueryHowManyKMsDoneDates" executeQueryHowManyKMsDoneDatesGenerator 1

executeQueryHowManyAltimetryDoneGenerator :: IO [String]
executeQueryHowManyAltimetryDoneGenerator = do
  user <- generate $ (arbitrary :: Gen User)
  resultLines <- runJava $ concat
    [
      javaImports
    , toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , "System.out.println(makeItFit.executeQueryHowManyAltimetryDone(user.getEmail()))"
      ]
    ]
  return $ concat
    [
      toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , assertEquals (last resultLines)
          "makeItFit.executeQueryHowManyAltimetryDone(user.getEmail())"
      ]
    ]

executeQueryHowManyAltimetryDoneTemplate :: TestTemplate
executeQueryHowManyAltimetryDoneTemplate =
  TestTemplate "testExecuteQueryHowManyAltimetryDone" executeQueryHowManyAltimetryDoneGenerator 1

executeQueryHowManyAltimetryDoneDatesGenerator :: IO [String]
executeQueryHowManyAltimetryDoneDatesGenerator = do
  user                        <- generate $ (arbitrary :: Gen User)
  date1@(MakeItFitDate y m d) <- generate $ (arbitrary :: Gen MakeItFitDate)
  date2                       <- return $ MakeItFitDate (y + 100) m d
  expr <- return $ concat
    [
      "makeItFit.executeQueryHowManyAltimetryDone(user.getEmail(), "
    , toJavaExpression date1
    , ", "
    , toJavaExpression date2
    , ")"
    ]
  resultLines <- runJava $ concat
    [
      javaImports
    , toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , "System.out.println(" ++ expr ++ ")"
      ]
    ]
  return $ concat
    [
      toJavaVariable "user" user
    , [
        "MakeItFit makeItFit = new MakeItFit();"
      , "makeItFit.addUser(user);"
      , assertEquals (last resultLines) expr
      ]
    ]

executeQueryHowManyAltimetryDoneDatesTemplate :: TestTemplate
executeQueryHowManyAltimetryDoneDatesTemplate =
  TestTemplate
    "testExecuteQueryHowManyAltimetryDoneDates"
    executeQueryHowManyAltimetryDoneDatesGenerator
    1
