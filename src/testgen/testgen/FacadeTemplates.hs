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
    equalityTemplate,
    emailTemplate,
    getActivitiesFromUserTemplate
  ) where

import Java (assertEquals, assertTrue, runJava, toJavaExpression, toJavaExpressionList, JavaData (toJavaExpression))
import TestTemplate (TestTemplate(..), genToTestTemplate)
import Test.QuickCheck (Gen, arbitrary, elements, generate, listOf, Arbitrary (arbitrary))
import Generators
import Data.List (intercalate)
import GHC.Generics (Associativity)

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

genEmail :: Gen String
genEmail = do
  before <- listOf $ elements (['a'..'z'] ++ ['A'..'Z'] ++ ['0'..'9'])
  after <- listOf $ elements (['a'..'z'] ++ ['A'..'Z'] ++ ['0'..'9'])
  return $ before ++ "@" ++ after ++ ".com"

emailTestGenerator :: IO [String]
emailTestGenerator = do
  email <- generate $ genEmail
  resultLines <- runJava
    [
      "import MakeItFit.utils.EmailValidator;"
    , "System.out.println(EmailValidator.isValidEmail(" ++ toJavaExpression email ++ "));"
    ]
  return
    [
      assertEquals (toJavaExpression email) (last resultLines)
    ]

emailTemplate :: TestTemplate
emailTemplate = TestTemplate "validEmail" emailTestGenerator 1

-- getActivitiesFromUser
userEmail :: User -> String
userEmail (Amateur _ _ _ _ _ _ _ _ _ email _)         = email
userEmail (Occasional _ _ _ _ _ _ _ _ _ email _ _)    = email
userEmail (Professional _ _ _ _ _ _ _ _ _ email _ _)  = email

userActivities :: User -> [Activity]
userActivities (Amateur _ _ _ _ _ _ _ _ _ _ acts)         = acts
userActivities (Occasional _ _ _ _ _ _ _ _ _ _ _ acts)    = acts
userActivities (Professional _ _ _ _ _ _ _ _ _ _ _ acts)  = acts

userCodeActivity :: String -> Activity -> Activity
userCodeActivity userCode (PushUp _ a b c d e f) = PushUp userCode a b c d e f
userCodeActivity userCode (Running _ a b c d e f) = Running userCode a b c d e f
userCodeActivity userCode (Trail _ a b c d e f g h) = Trail userCode a b c d e f g h
userCodeActivity userCode (WeightSquat _ a b c d e f g) = WeightSquat userCode a b c d e f g

getActivitiesFromUserTestGenerator :: Gen [String]
getActivitiesFromUserTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  let activitiesLen = length (userActivities user)
  let activities = map (userCodeActivity "userCode") (userActivities user)
  let emailNoSpaces = filter (/= ' ') email
  let userVarName = "user"
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.create(" ++ toJavaExpression user ++ ")"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let activitiesVar = "activities"
  let activitiesDecl = "List<Activity> " ++ activitiesVar ++ " = Arrays.asList(" ++ intercalate ", " (map toJavaExpression (activities)) ++ ");"
  let forLine = "for (Activity a : " ++ activitiesVar ++ ") model.addActivityToUser(" ++ toJavaExpression emailNoSpaces ++ ", a);"
  let testLine = assertEquals ("getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\")") (toJavaExpression activitiesLen)
  return ([setupLine] ++ [userLine] ++ [";"] ++ [userCodeLine, activitiesDecl, forLine] ++ [testLine])


getActivitiesFromUserTemplate :: TestTemplate
getActivitiesFromUserTemplate = genToTestTemplate "getActivitiesFromUser" getActivitiesFromUserTestGenerator 3

-- addActivityToUser
{-- addActivityToUserTestGenerator :: Gen [String]
addActivityToUserTestGenerator = do
  user <- arbitrary :: Gen User
  activity <- genActivity (userCode user)
  let email = userEmail user
  let activitiesLen = length (userActivities user)
  let emailNoSpaces = filter (/= ' ') email
  let userVarName = "user"
  let activityVarName = "activity"
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.create(" ++ toJavaExpression user ++ ")"
  let activityLine = "Activity " ++ activityVarName ++ " = " ++ toJavaExpression activity ++ ";"
  let beforeLine = assertEquals
        ("getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\").size()")
        (toJavaExpression activitiesLen)
  let addLine = "addActivityToUser(\"" ++ emailNoSpaces ++ "\", " ++ activityVarName ++ ");"
  let afterLine = assertEquals
        ("getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\").size()")
        (toJavaExpression (activitiesLen + 1))
  return [setupLine, userLine, activityLine, beforeLine, addLine, afterLine]

addActivityToUserTemplate :: TestTemplate
addActivityToUserTemplate = genToTestTemplate "addActivityToUser" addActivityToUserTestGenerator 3
--}