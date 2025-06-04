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
    getActivitiesFromUserTemplate,
    addActivityToUserTemplate,
    removeActivityFromUserTemplate,
    createTrainingPlanTemplate,
    createTrainingPlanExceptionTemplate,
    constructTrainingPlanTemplate,
    constructTrainingPlanExceptionTemplate,
    removeTrainingPlanTemplate,
    getTrainingPlanTemplate,
    getTrainingPlanExceptionTemplate,
    updateTrainingPlanTemplate,
    updateTrainingPlanExceptionTemplate,
    getAllTrainingPlansTemplate,
    addActivityToTrainingPlanTemplate,
    removeActivityFromTrainingPlanTemplate,
    getTrainingPlansFromUserTemplate
  ) where

import Java (assertEquals, assertTrue, runJava, toJavaExpression, toJavaExpressionList,
  JavaData (toJavaExpression), assertThrows)
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

-- getActivitiesFromUser -> needs a throws Exception
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
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let activitiesVar = "activities"
  let activitiesDecl = "List<Activity> " ++ activitiesVar ++ " = Arrays.asList(" ++
        intercalate ", " (map toJavaExpression activities) ++ ");"
  let forLine = "for (Activity a : " ++ activitiesVar ++ ") model.addActivityToUser(" ++
        toJavaExpression emailNoSpaces ++ ", a);"
  let testLine = assertEquals ("model.getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\").size()")
        (toJavaExpression activitiesLen)
  return ([setupLine] ++ [userLine] ++ [userCodeLine, activitiesDecl, forLine] ++
        [testLine])

getActivitiesFromUserTemplate :: TestTemplate
getActivitiesFromUserTemplate = genToTestTemplate "getActivitiesFromUserTest"
    getActivitiesFromUserTestGenerator 1

-- addActivityToUser -> needs a throws Exception
addActivityToUserTestGenerator :: Gen [String]
addActivityToUserTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  let activitiesLen = length (userActivities user)
  let activities = map (userCodeActivity "userCode") (userActivities user)
  let emailNoSpaces = filter (/= ' ') email
  let userVarName = "user"
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let activitiesVar = "activities"
  let activitiesDecl = "List<Activity> " ++ activitiesVar ++ " = Arrays.asList(" ++
        intercalate ", " (map toJavaExpression activities) ++ ");"
  let forLine = "for (Activity a : " ++ activitiesVar ++ ") model.addActivityToUser(" ++
        toJavaExpression emailNoSpaces ++ ", a);"
  let testLineBefore = assertEquals
        ("model.getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\").size()")
        (toJavaExpression activitiesLen)
  activityToAdd <- genActivity "userCode"
  let activityAdd = "model.addActivityToUser(" ++ toJavaExpression emailNoSpaces ++ ", " ++
        toJavaExpression activityToAdd ++ ");"
  let testLineAfter = assertEquals
        ("model.getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\").size()")
        (toJavaExpression (activitiesLen + 1))
  return ([setupLine, userLine] ++ [";"] ++ [userCodeLine, activitiesDecl, forLine,
        testLineBefore, activityAdd, testLineAfter])

addActivityToUserTemplate :: TestTemplate
addActivityToUserTemplate = genToTestTemplate "addActivityToUserTest"
      addActivityToUserTestGenerator 1

-- removeActivityFromUser -> needs a throws Exception
removeActivityFromUserTestGenerator :: Gen [String]
removeActivityFromUserTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  let activitiesLen = length (userActivities user)
  let activities = map (userCodeActivity "userCode") (userActivities user)
  let emailNoSpaces = filter (/= ' ') email
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let activitiesVar = "activities"
  let activitiesDecl = "List<Activity> " ++ activitiesVar ++ " = Arrays.asList(" ++
        intercalate ", " (map toJavaExpression activities) ++ ");"
  let forAddLine = "for (Activity a : " ++ activitiesVar ++ ") model.addActivityToUser(" ++
        toJavaExpression emailNoSpaces ++ ", a);"
  let activityIdsVar = "activityIds"
  let activityIdsDecl = "List<UUID> " ++ activityIdsVar ++ " = new java.util.ArrayList<>();"
  let forGetIdLine = "for (Activity a : " ++ activitiesVar ++ ") " ++ activityIdsVar ++
        ".add(a.getCode());"
  let forRemoveLine = "for (UUID activityId : " ++ activityIdsVar ++
        ") model.removeActivityFromUser(" ++ toJavaExpression emailNoSpaces ++ ", activityId);"
  let testLine = assertEquals ("model.getActivitiesFromUser(\"" ++ emailNoSpaces ++ "\").size()")
        "0"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , activitiesDecl
    , forAddLine
    , activityIdsDecl
    , forGetIdLine
    , forRemoveLine
    , testLine
    ]

removeActivityFromUserTemplate :: TestTemplate
removeActivityFromUserTemplate = genToTestTemplate "removeActivityFromUserTest"
      removeActivityFromUserTestGenerator 1

-- createTrainingPlan -> needs a throws Exception
createTrainingPlanTestGenerator :: Gen [String]
createTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let assertLine = "assertNotNull(planCode);"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine
    , planCodeLine
    , assertLine
    ]

createTrainingPlanTemplate :: TestTemplate
createTrainingPlanTemplate = genToTestTemplate "createTrainingPlanTest"
      createTrainingPlanTestGenerator 1

createTrainingPlanExceptionTestGenerator :: Gen [String]
createTrainingPlanExceptionTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let assertThrowsLines = assertThrows "IllegalArgumentException"
        ["model.createTrainingPlan(userCode, null);"]
  return
    ([ setupLine
     , userLine
     , userCodeLine
     ] ++ assertThrowsLines)

createTrainingPlanExceptionTemplate :: TestTemplate
createTrainingPlanExceptionTemplate = genToTestTemplate "createTrainingPlanExceptionTest"
      createTrainingPlanExceptionTestGenerator 1

-- ConstructTrainingPlan -> needs a throws Exception
constructTrainingPlanTestGenerator :: Gen [String]
constructTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let planLine = "TrainingPlan plan = model.getTrainingPlan(planCode);"
  let assertLine = "assertDoesNotThrow(() -> { model.constructTrainingPlanByObjectives(plan," ++
        " true, 2, 3, 4, 500); });"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine
    , planCodeLine
    , planLine
    , assertLine
    ]

constructTrainingPlanTemplate :: TestTemplate
constructTrainingPlanTemplate = genToTestTemplate "constructTrainingPlanTest"
      constructTrainingPlanTestGenerator 1

constructTrainingPlanExceptionTestGenerator :: Gen [String]
constructTrainingPlanExceptionTestGenerator = do
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let fakeUserCodeLine = "UUID fakeUserCode = UUID.randomUUID();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let fakePlanLine = "TrainingPlan fakePlan = new TrainingPlan(fakeUserCode, startDate);"
  let assertLine = assertThrows "EntityDoesNotExistException"
        ["model.constructTrainingPlanByObjectives(fakePlan, true, 2, 3, 4, 500);"]
  return
    ([setupLine
    , fakeUserCodeLine
    , dateLine
    , fakePlanLine
    ] ++ assertLine)

constructTrainingPlanExceptionTemplate :: TestTemplate
constructTrainingPlanExceptionTemplate = genToTestTemplate "constructTrainingPlanExceptionTest"
      constructTrainingPlanExceptionTestGenerator 1

-- removeTrainingPlan
removeTrainingPlanTestGenerator :: Gen [String]
removeTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let removeLine = "model.removeTrainingPlan(planCode);"
  let assertLine = assertThrows "IllegalArgumentException" ["model.getTrainingPlan(planCode);"]
  return
    ([setupLine
    , userLine
    , userCodeLine
    , dateLine
    , planCodeLine
    , removeLine
    ] ++ assertLine)

removeTrainingPlanTemplate :: TestTemplate
removeTrainingPlanTemplate =
  genToTestTemplate "removeTrainingPlanTest" removeTrainingPlanTestGenerator 1

-- getTrainingPlan -> needs a throws Exception
getTrainingPlanTestGenerator :: Gen [String]
getTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let retrievedLine = "TrainingPlan retrievedPlan = model.getTrainingPlan(planCode);"
  let assertNotNullLine = "assertNotNull(retrievedPlan);"
  let assertEqualsLine = assertEquals "planCode" "retrievedPlan.getCode()"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine
    , planCodeLine
    , retrievedLine
    , assertNotNullLine
    , assertEqualsLine
    ]

getTrainingPlanTemplate :: TestTemplate
getTrainingPlanTemplate =
  genToTestTemplate "getTrainingPlanTest" getTrainingPlanTestGenerator 1

getTrainingPlanExceptionTestGenerator :: Gen [String]
getTrainingPlanExceptionTestGenerator = do
  let setupLine = "MakeItFit model = new MakeItFit();"
  let invalidCodeLine = "UUID invalidCode = UUID.randomUUID();"
  let assertLine = assertThrows "IllegalArgumentException" ["model.getTrainingPlan(invalidCode);"]
  return
   ([ setupLine
    ,invalidCodeLine] ++ assertLine)

getTrainingPlanExceptionTemplate :: TestTemplate
getTrainingPlanExceptionTemplate =
  genToTestTemplate "getTrainingPlanExceptionTest" getTrainingPlanExceptionTestGenerator 1

-- updateTrainingPlan -> needs a throws Exception
updateTrainingPlanTestGenerator :: Gen [String]
updateTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date1 <- arbitrary :: Gen MakeItFitDate
  date2 <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine1 = "MakeItFitDate startDate = " ++ toJavaExpression date1 ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let planLine = "TrainingPlan plan = model.getTrainingPlan(planCode);"
  let setDateLine = "plan.setStartDate(" ++ toJavaExpression date2 ++ ");"
  let assertDoesNotThrowLine = "assertDoesNotThrow(() -> model.updateTrainingPlan(plan));"
  let updatedLine = "TrainingPlan updated = model.getTrainingPlan(planCode);"
  let assertEqualsLine = assertEquals (toJavaExpression date2) "updated.getStartDate()"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine1
    , planCodeLine
    , planLine
    , setDateLine
    , assertDoesNotThrowLine
    , updatedLine
    , assertEqualsLine
    ]

updateTrainingPlanTemplate :: TestTemplate
updateTrainingPlanTemplate =
  genToTestTemplate "updateTrainingPlanTest" updateTrainingPlanTestGenerator 1

updateTrainingPlanExceptionTestGenerator :: Gen [String]
updateTrainingPlanExceptionTestGenerator = do
  date <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let fakePlanLine = "TrainingPlan fakePlan = new TrainingPlan(UUID.randomUUID(), startDate);"
  let assertLine = assertThrows "EntityDoesNotExistException"
        ["model.updateTrainingPlan(fakePlan);"]
  return
    ([setupLine
    , dateLine
    , fakePlanLine
    ] ++ assertLine)

updateTrainingPlanExceptionTemplate :: TestTemplate
updateTrainingPlanExceptionTemplate =
  genToTestTemplate "updateTrainingPlanExceptionTest" updateTrainingPlanExceptionTestGenerator 1

-- getAllTrainingPlans -> needs a throws Exception
getAllTrainingPlansTestGenerator :: Gen [String]
getAllTrainingPlansTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date1 <- arbitrary :: Gen MakeItFitDate
  date2 <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let date2Line = "MakeItFitDate startDate2 =" ++ toJavaExpression date2 ++ ";"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine1 = "MakeItFitDate startDate = " ++ toJavaExpression date1 ++ ";"
  let createPlan1 = "model.createTrainingPlan(userCode, startDate);"
  let createPlan2 = "model.createTrainingPlan(userCode, startDate.plusDays(7));"
  let plansLine = "List<TrainingPlan> plans = model.getAllTrainingPlans();"
  let assertLine = assertEquals "2" "plans.size()"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine1
    , createPlan1
    , createPlan2
    , plansLine
    , assertLine
    ]

getAllTrainingPlansTemplate :: TestTemplate
getAllTrainingPlansTemplate =
  genToTestTemplate "getAllTrainingPlansTest" getAllTrainingPlansTestGenerator 1

-- addActivityToTrainingPlan
addActivityToTrainingPlanTestGenerator :: Gen [String]
addActivityToTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  activity <- genActivity "userCode"
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let activityLine = "Activity activity = " ++ toJavaExpression activity ++ ";"
  let assertDoesNotThrowLine = "assertDoesNotThrow(() -> {" ++
        " model.addActivityToTrainingPlan(planCode, activity, 3); });"
  let planLine = "TrainingPlan plan = model.getTrainingPlan(planCode);"
  let assertTrueLine = assertTrue
        "plan.getActivities().stream().anyMatch(a -> a.getItem2().equals(activity))"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine
    , planCodeLine
    , activityLine
    , assertDoesNotThrowLine
    , planLine
    , assertTrueLine
    ]

addActivityToTrainingPlanTemplate :: TestTemplate
addActivityToTrainingPlanTemplate =
  genToTestTemplate "addActivityToTrainingPlanTest" addActivityToTrainingPlanTestGenerator 1

-- removeActivityFromTrainingPlan
removeActivityFromTrainingPlanTestGenerator :: Gen [String]
removeActivityFromTrainingPlanTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date <- arbitrary :: Gen MakeItFitDate
  activity <- genActivity "userCode"
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine = "MakeItFitDate startDate = " ++ toJavaExpression date ++ ";"
  let planCodeLine = "UUID planCode = model.createTrainingPlan(userCode, startDate);"
  let activityLine = "Activity activity = " ++ toJavaExpression activity ++ ";"
  let addActivityLine = "model.addActivityToTrainingPlan(planCode, activity, 2);"
  let planLine = "TrainingPlan plan = model.getTrainingPlan(planCode);"
  let activityCodeLine = "UUID activityCode = plan.getActivities().get(0).getItem2().getCode();"
  let removeActivityLine = "model.removeActivityFromTrainingPlan(planCode, activityCode);"
  let updatedLine = "TrainingPlan updated = model.getTrainingPlan(planCode);"
  let assertTrueLine = assertTrue ("updated.getActivities().stream().noneMatch(a ->" ++
        " a.getItem2().getCode().equals(activityCode))")
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine
    , planCodeLine
    , activityLine
    , addActivityLine
    , planLine
    , activityCodeLine
    , removeActivityLine
    , updatedLine
    , assertTrueLine
    ]

removeActivityFromTrainingPlanTemplate :: TestTemplate
removeActivityFromTrainingPlanTemplate = genToTestTemplate "removeActivityFromTrainingPlanTest"
      removeActivityFromTrainingPlanTestGenerator 1

-- getTrainingPlansFromUser
getTrainingPlansFromUserTestGenerator :: Gen [String]
getTrainingPlansFromUserTestGenerator = do
  user <- arbitrary :: Gen User
  let email = userEmail user
  date1 <- arbitrary :: Gen MakeItFitDate
  date2 <- arbitrary :: Gen MakeItFitDate
  let setupLine = "MakeItFit model = new MakeItFit();"
  let userLine = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
  let userCodeLine = "UUID userCode = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
  let dateLine1 = "MakeItFitDate startDate = " ++ toJavaExpression date1 ++ ";"
  let createPlan1 = "model.createTrainingPlan(userCode, startDate);"
  let createPlan2 = "model.createTrainingPlan(userCode," ++ toJavaExpression date2 ++ ");"
  let userPlansLine = "List<TrainingPlan> userPlans = model.getTrainingPlansFromUser(userCode);"
  let assertSizeLine = assertEquals "2" "userPlans.size()"
  let forLine = "for (TrainingPlan plan : userPlans) {" ++
        "assertEquals(userCode, plan.getUserCode()); }"
  return
    [ setupLine
    , userLine
    , userCodeLine
    , dateLine1
    , createPlan1
    , createPlan2
    , userPlansLine
    , assertSizeLine
    , forLine
    ]

getTrainingPlansFromUserTemplate :: TestTemplate
getTrainingPlansFromUserTemplate =
  genToTestTemplate "getTrainingPlansFromUserTest" getTrainingPlansFromUserTestGenerator 1

