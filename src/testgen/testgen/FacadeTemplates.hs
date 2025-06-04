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
    createAndGetUserTemplate,
    removeUserByEmailTemplate,
    removeUserByUUIDTemplate,
    removeUserInvalidTypeTemplate,
    getUserInvalidTypeTemplate,
    removeNonExistentUserTemplate,
    updateUserTemplate,
    updateEmailTemplate,
    duplicateUserTemplate,
    getAllUsersTemplate,
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
import Test.QuickCheck (Gen, arbitrary, elements, choose, generate, listOf, Arbitrary (arbitrary), suchThat)
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

-- getActivitiesFromUserdate
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

userName :: User -> String
userName (Amateur     name _ _ _ _ _ _ _ _ _ _)       = name
userName (Occasional  name _ _ _ _ _ _ _ _ _ _ _)  = name
userName (Professional name _ _ _ _ _ _ _ _ _ _ _)     = name

userAge :: User -> Int
userAge (Amateur     _ age _ _ _ _ _ _ _ _ _)       = age
userAge (Occasional  _ age _ _ _ _ _ _ _ _ _ _)    = age
userAge (Professional _ age _ _ _ _ _ _ _ _ _ _)     = age

userGender :: User -> Gender
userGender (Amateur _ _ gender _ _ _ _ _ _ _ _) = gender
userGender (Occasional _ _ gender _ _ _ _ _ _ _ _ _) = gender
userGender (Professional _ _ gender _ _ _ _ _ _ _ _ _) = gender

userWeight :: User -> Int
userWeight (Amateur _ _ _ weight _ _ _ _ _ _ _) = weight
userWeight (Occasional _ _ _ weight _ _ _ _ _ _ _ _) = weight
userWeight (Professional _ _ _ weight _ _ _ _ _ _ _ _) = weight

userHeight :: User -> Int
userHeight (Amateur _ _ _ _ height _ _ _ _ _ _) = height
userHeight (Occasional _ _ _ _ height _ _ _ _ _ _ _) = height
userHeight (Professional _ _ _ _ height _ _ _ _ _ _ _) = height

userBpm :: User -> Int
userBpm (Amateur _ _ _ _ _ bpm _ _ _ _ _) = bpm
userBpm (Occasional _ _ _ _ _ bpm _ _ _ _ _ _) = bpm
userBpm (Professional _ _ _ _ _ bpm _ _ _ _ _ _) = bpm

userLevel :: User -> Int
userLevel (Amateur _ _ _ _ _ _ level _ _ _ _) = level
userLevel (Occasional _ _ _ _ _ _ level _ _ _ _ _) = level
userLevel (Professional _ _ _ _ _ _ level _ _ _ _ _) = level

userAddress :: User -> String
userAddress (Amateur _ _ _ _ _ _ _ address _ _ _) = address
userAddress (Occasional _ _ _ _ _ _ _ address _ _ _ _) = address
userAddress (Professional _ _ _ _ _ _ _ address _ _ _ _) = address

userPhone :: User -> String
userPhone (Amateur _ _ _ _ _ _ _ _ phone _ _) = phone
userPhone (Occasional _ _ _ _ _ _ _ _ phone _ _ _) = phone
userPhone (Professional _ _ _ _ _ _ _ _ phone _ _ _) = phone

-- testCreateAndGetUser
testCreateAndGetUserGenerator :: Gen [String]
testCreateAndGetUserGenerator = do
  user <- arbitrary :: Gen User
  let name       = userName user
      age        = userAge user
      email      = userEmail user
      setupLine       = "MakeItFit model = new MakeItFit();"
      createUserLine  = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      existsLine      = "assertTrue(model.existsUserWithEmail(" ++ toJavaExpression email ++ "));"
      getUserLine     = "User user = model.getUser(" ++ toJavaExpression email ++ ");"
      assertNameLine  = "assertEquals(" ++ toJavaExpression name ++ ", user.getName());"
      assertAgeLine   = "assertEquals(" ++ show age ++ ", user.getAge());"
  return
    [ setupLine
    , createUserLine
    , existsLine
    , getUserLine
    , assertNameLine
    , assertAgeLine
    ]

createAndGetUserTemplate :: TestTemplate
createAndGetUserTemplate =
  genToTestTemplate "testCreateAndGetUser" testCreateAndGetUserGenerator 1

-- testRemoveUserByEmail
testRemoveUserByEmailGenerator :: Gen [String]
testRemoveUserByEmailGenerator = do
  user <- arbitrary :: Gen User
  let email      = userEmail user
      setupLine    = "MakeItFit model = new MakeItFit();"
      createLine   = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      removeLine   = "model.removeUser(" ++ toJavaExpression email ++ ");"
      assertLine   = "assertFalse(model.existsUserWithEmail(" ++ toJavaExpression email ++ "));"
  return [ setupLine, createLine, removeLine, assertLine ]

removeUserByEmailTemplate :: TestTemplate
removeUserByEmailTemplate =
  genToTestTemplate "testRemoveUserByEmail" testRemoveUserByEmailGenerator 1

-- testRemoveUserByUUID
testRemoveUserByUUIDGenerator :: Gen [String]
testRemoveUserByUUIDGenerator = do
  user <- arbitrary :: Gen User
  let email      = userEmail user
      createArgs = toJavaCreateUserArgs user
      setupLine    = "MakeItFit model = new MakeItFit();"
      createLine   = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      getIdLine    = "UUID id = model.getUser(" ++ toJavaExpression email ++ ").getCode();"
      removeLine   = "model.removeUser(id);"
      assertLine   = "assertFalse(model.existsUserWithEmail(" ++ toJavaExpression email ++ "));"
  return [ setupLine, createLine, getIdLine, removeLine, assertLine ]

removeUserByUUIDTemplate :: TestTemplate
removeUserByUUIDTemplate =
  genToTestTemplate "testRemoveUserByUUID" testRemoveUserByUUIDGenerator 1

-- testRemoveUserInvalidTypeGenerator
testRemoveUserInvalidTypeGenerator :: Gen [String]
testRemoveUserInvalidTypeGenerator = do
  let setupLine  = "MakeItFit model = new MakeItFit();"
      assertLine = "assertThrows(InvalidTypeException.class, () -> model.removeUser(123));"
  return [ setupLine, assertLine ]

removeUserInvalidTypeTemplate :: TestTemplate
removeUserInvalidTypeTemplate =
  genToTestTemplate "testRemoveUserInvalidType" testRemoveUserInvalidTypeGenerator 1

-- testGetUserInvalidTypeGenerator
testGetUserInvalidTypeGenerator :: Gen [String]
testGetUserInvalidTypeGenerator = do
  let setupLine  = "MakeItFit model = new MakeItFit();"
      assertLine = "assertThrows(InvalidTypeException.class, () -> model.getUser(123));"
  return [ setupLine, assertLine ]

getUserInvalidTypeTemplate :: TestTemplate
getUserInvalidTypeTemplate =
  genToTestTemplate "testGetUserInvalidType" testGetUserInvalidTypeGenerator 1

-- testRemoveNonExistentUserGenerator
testRemoveNonExistentUserGenerator :: Gen [String]
testRemoveNonExistentUserGenerator = do
  email <- genEmail
  let setupLine  = "MakeItFit model = new MakeItFit();"
      assertLine = "assertThrows(EntityDoesNotExistException.class, () -> model.removeUser(" ++ toJavaExpression email ++ "));"
  return [ setupLine, assertLine ]

removeNonExistentUserTemplate :: TestTemplate
removeNonExistentUserTemplate =
  genToTestTemplate "testRemoveNonExistentUser" testRemoveNonExistentUserGenerator 1

-- testUpdateUser
testUpdateUserGenerator :: Gen [String]
testUpdateUserGenerator = do
  user <- arbitrary :: Gen User
  let email     = userEmail user

  newName    <- genUserName `suchThat` (/= userName user)
  newAge <- (choose (18, 80) :: Gen Int) `suchThat` (/= userAge user)
  newGender  <- (arbitrary :: Gen Gender) `suchThat` (/= userGender user)
  newWeight  <- (elements [50..100] :: Gen Int) `suchThat` (/= userWeight user)
  newHeight  <- (choose (150, 195) :: Gen Int) `suchThat` (/= userHeight user)
  newBpm <- (choose (60, 100) :: Gen Int) `suchThat` (/= userBpm user)
  newLevel   <- (choose (1, 10) :: Gen Int) `suchThat` (/= userLevel user)
  newAddress <- (genAddress) `suchThat` (/= userAddress user)
  newPhone   <- genPhone `suchThat` (/= userPhone user)

  let setupLine     = "MakeItFit model = new MakeItFit();"
      createLine    = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      updNameLine   = "model.updateUserName("   ++ toJavaExpression newName   ++ ", " ++ toJavaExpression email ++ ");"
      updAgeLine    = "model.updateUserAge("    ++ show newAge       ++ ", " ++ toJavaExpression email ++ ");"
      updGenderLine = "model.updateUserGender(" ++ toJavaExpression newGender ++ ", " ++ toJavaExpression email ++ ");"
      updWeightLine = "model.updateUserWeight(" ++ show newWeight    ++ "f, " ++ toJavaExpression email ++ ");"
      updHeightLine = "model.updateUserHeight(" ++ show newHeight    ++ ", " ++ toJavaExpression email ++ ");"
      updBpmLine    = "model.updateUserBpm("    ++ show newBpm       ++ ", " ++ toJavaExpression email ++ ");"
      updLevelLine  = "model.updateUserLevel("  ++ show newLevel     ++ ", " ++ toJavaExpression email ++ ");"
      updAddrLine   = "model.updateUserAddress("++ toJavaExpression newAddress ++ ", " ++ toJavaExpression email ++ ");"
      updPhoneLine  = "model.updateUserPhone("  ++ toJavaExpression newPhone ++ ", " ++ toJavaExpression email ++ ");"
      getUpdatedByEmailLine = "User updated = model.getUser(" ++ toJavaExpression email ++ ");"
      getCodeLine         = "UUID id = updated.getCode();"
      getByIdLine         = "User u = model.getUser(id);"
      assertName1        = "assertEquals(" ++ toJavaExpression newName ++ ", u.getName());"
      assertName2        = "assertEquals(" ++ toJavaExpression newName ++ ", updated.getName());"
      assertAge          = "assertEquals(" ++ show newAge ++ ", updated.getAge());"
      assertGender       = "assertEquals(" ++ toJavaExpression newGender ++ ", updated.getGender());"
      assertWeight       = "assertEquals(" ++ show newWeight ++ "f, updated.getWeight());"
      assertHeight       = "assertEquals(" ++ show newHeight ++ ", updated.getHeight());"
      assertBpm          = "assertEquals(" ++ show newBpm ++ ", updated.getBpm());"
      assertLevel        = "assertEquals(" ++ show newLevel ++ ", updated.getLevel());"
      assertAddress      = "assertEquals(" ++ toJavaExpression newAddress ++ ", updated.getAddress());"
      assertPhone        = "assertEquals(" ++ toJavaExpression newPhone ++ ", updated.getPhone());"

  return
    [ setupLine
    , createLine
    , updNameLine
    , updAgeLine
    , updGenderLine
    , updWeightLine
    , updHeightLine
    , updBpmLine
    , updLevelLine
    , updAddrLine
    , updPhoneLine
    , getUpdatedByEmailLine
    , getCodeLine
    , getByIdLine
    , assertName1
    , assertName2
    , assertAge
    , assertGender
    , assertWeight
    , assertHeight
    , assertBpm
    , assertLevel
    , assertAddress
    , assertPhone
    ]

updateUserTemplate :: TestTemplate
updateUserTemplate =
  genToTestTemplate "testUpdateUser" testUpdateUserGenerator 1

-- testUpdateEmail
testUpdateEmailGenerator :: Gen [String]
testUpdateEmailGenerator = do
  user <- arbitrary :: Gen User
  let oldEmail   = userEmail user
      oldNoSp    = filter (/= ' ') oldEmail
  newEmail <- genEmail `suchThat` (/= oldEmail)
  let newNoSp = filter (/= ' ') newEmail
      setupLine      = "MakeItFit model = new MakeItFit();"
      createLine     = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      updEmailLine   = "model.updateUserEmail(" ++ toJavaExpression oldNoSp ++ ", " ++ toJavaExpression newNoSp ++ ");"
      assertOldGone  = "assertFalse(model.existsUserWithEmail(" ++ toJavaExpression oldNoSp ++ "));"
      assertNewThere = "assertTrue(model.existsUserWithEmail(" ++ toJavaExpression newNoSp ++ "));"
      getUserLine    = "User user = model.getUser(" ++ toJavaExpression newNoSp ++ ");"
      assertNameLine = "assertEquals(" ++ toJavaExpression (userName user) ++ ", user.getName());"

  return
    [ setupLine
    , createLine
    , updEmailLine
    , assertOldGone
    , assertNewThere
    , getUserLine
    , assertNameLine
    ]

updateEmailTemplate :: TestTemplate
updateEmailTemplate =
  genToTestTemplate "testUpdateEmail" testUpdateEmailGenerator 1

-- testDuplicateUser
testDuplicateUserGenerator :: Gen [String]
testDuplicateUserGenerator = do
  user <- arbitrary :: Gen User
  let setupLine    = "MakeItFit model = new MakeItFit();"
      firstCreate  = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      assertLine   = "assertThrows(ExistingEntityConflictException.class, () -> model.createUser(" ++ toJavaCreateUserArgs user ++ "));"
  return [ setupLine, firstCreate, assertLine ]

duplicateUserTemplate :: TestTemplate
duplicateUserTemplate =
  genToTestTemplate "testDuplicateUser" testDuplicateUserGenerator 1

-- testGetAllUsers
testGetAllUsersGenerator :: Gen [String]
testGetAllUsersGenerator = do
  user <- arbitrary :: Gen User
  let email      = userEmail user
      createArgs = toJavaCreateUserArgs user

      setupLine     = "MakeItFit model = new MakeItFit();"
      getAll1       = "List<User> allUsers = model.getAllUsers();"
      assertNotNull = "assertNotNull(allUsers);"
      assertEmpty   = "assertTrue(allUsers.isEmpty());"
      createLine    = "model.createUser(" ++ toJavaCreateUserArgs user ++ ");"
      getAll2       = "allUsers = model.getAllUsers();"
      collectEmails = "List<String> emails = allUsers.stream().map(User::getEmail).collect(Collectors.toList());"
      assertContains = "assertTrue(emails.contains(" ++ toJavaExpression email ++ "));"

  return
    [ setupLine
    , getAll1
    , assertNotNull
    , assertEmpty
    , createLine
    , getAll2
    , collectEmails
    , assertContains
    ]

getAllUsersTemplate :: TestTemplate
getAllUsersTemplate =
  genToTestTemplate "testGetAllUsers" testGetAllUsersGenerator 1

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

-- addActivityToUserdate
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

-- removeActivityFromUserdate
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

-- createTrainingPlandate
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

-- ConstructTrainingPlandate
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
  let assertLine = assertThrows "EntityDoesNotExistException" ["model.getTrainingPlan(planCode);"]
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

-- getTrainingPlandate
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
  let assertLine = assertThrows "EntityDoesNotExistException" ["model.getTrainingPlan(invalidCode);"]
  return
   ([ setupLine
    ,invalidCodeLine] ++ assertLine)

getTrainingPlanExceptionTemplate :: TestTemplate
getTrainingPlanExceptionTemplate =
  genToTestTemplate "getTrainingPlanExceptionTest" getTrainingPlanExceptionTestGenerator 1

-- updateTrainingPlandate
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

-- getAllTrainingPlansdate
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

