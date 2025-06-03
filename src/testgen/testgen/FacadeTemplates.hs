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

getActivitiesFromUserTestGenerator :: Gen [String]
getActivitiesFromUserTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  let activitiesLen = length (userActivities user)
  let emailNoSpaces = filter (/= ' ') email
  let userVarName = "user"
  let userLine = "User " ++ userVarName ++ " = " ++ toJavaExpression user
  let testLine = assertEquals ("getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\")") (toJavaExpression activitiesLen)
  return ([userLine] ++ [";"] ++ [testLine])

getActivitiesFromUserTemplate :: TestTemplate
getActivitiesFromUserTemplate = genToTestTemplate "getActivitiesFromUser" getActivitiesFromUserTestGenerator 3