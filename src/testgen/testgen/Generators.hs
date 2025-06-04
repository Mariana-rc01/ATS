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

{-# LANGUAGE FlexibleInstances #-}

module Generators
  (
    Gender(..)
  , User(..)
  , MakeItFitDate(..)
  , Activity(..)
  , TrainingPlan(..)
  , genActivity
  , genTrainingPlan
  , toJavaCreateUserArgs
  , genAddress
  , genPhone
  , genUserName
  ) where

import Data.List (intercalate)
import Test.QuickCheck (Arbitrary(..), Gen, choose, elements, frequency, listOf1, oneof, vectorOf)
import Java (JavaData(..), toJavaExpressionList)

-- | A MakeItFit gender
data Gender = Male | Female | Other deriving (Show, Eq)

instance Arbitrary Gender where
  arbitrary = elements [Male, Female, Other]

instance JavaData Gender where
  javaTypeName = const "Gender"
  toJavaExpression Male = "Gender.Male"
  toJavaExpression Female = "Gender.Female"
  toJavaExpression Other = "Gender.Other"

-- | A MakeItFit user
data User =
  Amateur
    String     -- ^ User's name
    Int        -- ^ User's age
    Gender     -- ^ User's gender
    Int        -- ^ User's weight
    Int        -- ^ User's height
    Int        -- ^ User's BPM
    Int        -- ^ User's level
    String     -- ^ User's address
    String     -- ^ User's phone
    String     -- ^ User's email
    [Activity] -- ^ User's activities
  |
  Occasional
    String     -- ^ User's name
    Int        -- ^ User's age
    Gender     -- ^ User's gender
    Int        -- ^ User's weight
    Int        -- ^ User's height
    Int        -- ^ User's BPM
    Int        -- ^ User's level
    String     -- ^ User's address
    String     -- ^ User's phone
    String     -- ^ User's email
    Int        -- ^ User's frequency
    [Activity] -- ^ User's activities
  |
  Professional
    String     -- ^ User's name
    Int        -- ^ User's age
    Gender     -- ^ User's gender
    Int        -- ^ User's weight
    Int        -- ^ User's height
    Int        -- ^ User's BPM
    Int        -- ^ User's level
    String     -- ^ User's address
    String     -- ^ User's phone
    String     -- ^ User's email
    Int        -- ^ User's frequency
    [Activity] -- ^ User's activities
  deriving (Show, Eq)

genUserName :: Gen String
genUserName = do
  name <- elements
    [ "Humberto" , "José" , "Mariana" , "André" , "Adriana" , "Bruno" , "Beatriz" , "Carlota"
    , "Carlos" , "Dalila" , "Dinis" , "Eva", "Filipe" , "Hugo" , "Gabriel" , "Sofia" , "Olívia"
    , "Henrique" , "Margarida" , "Elisabete"
    ]
  surname <- elements
    [
      "Gomes", "Lopes", "Rocha", "Campos", "Silva", "Pereira", "Coelho", "Gonçalves", "Dias"
    ]
  return $ name ++ " " ++ surname

genPhone :: Gen String
genPhone = oneof [genMobile, genLandline]
  where
    genMobile :: Gen String
    genMobile = do
      secondDigit <- elements ['1', '2', '3', '6']
      rest        <- vectorOf 7 (elements ['0'..'9'])
      return $ '9' : secondDigit : rest

    genLandline :: Gen String
    genLandline = do
      rest <- vectorOf 8 (elements ['0'..'9'])
      return $ '2' : rest

genEmail :: Gen String
genEmail = do
  localPart <- listOf1 $ elements (['a'..'z'] ++ ['0'..'9'])
  domain    <- frequency
    [ (32, return "gmail.com"), (30, return "yahoo.com"), (28, return "hotmail.com")
    , (5,  return "aol.com"), (2,  return "hotmail.co.uk"), (2,  return "hotmail.fr")
    , (1,  return "msn.com")
    ]
  return $ localPart ++ "@" ++ domain

genAddress :: Gen String
genAddress = do
  street <- genUserName
  number <- elements [1..100]
  city   <- frequency
    [(40, return "Lisboa"), (30, return "Porto"), (20, return "Braga"), (10, return "Coimbra")]
  apartment <- oneof [return "", genApartment >>= return . (", " ++)]
  return $ "Rua " ++ street ++ " " ++ show number ++ apartment ++ ", " ++ city
  where
    genApartment :: Gen String
    genApartment = do
      floor <- choose (1, 10) :: Gen Int
      side <- elements ["esq.", "dto."]
      return $ (show floor) ++ "º " ++ side

instance Arbitrary User where
  arbitrary = do
    tag        <- elements ["Amateur", "Occasional", "Professional"]
    name       <- genUserName
    age        <- choose (18, 80)
    gender     <- arbitrary :: Gen Gender
    weight     <- elements [50..100] -- NOTE: elements is used to avoid fractional numbers
    height     <- choose (150, 195)
    bpm        <- choose (60, 100)
    level      <- choose (1, 10)
    address    <- genAddress
    phone      <- genPhone
    email      <- genEmail
    n          <- choose (1, 30)
    activities <- vectorOf n (genActivity "ERROR")
    case tag of
      "Amateur" -> return $
        Amateur name age gender weight height bpm level address phone email activities
      "Occasional" -> do
        freq <- elements [1..14]
        return $
          Occasional name age gender weight height bpm level address phone email freq activities
      "Professional" -> do
        freq <- elements [7..21]
        return $
          Professional name age gender weight height bpm level address phone email freq activities

instance JavaData User where
  javaTypeName = const "User"

  -- NOTE: this won't have the activities set
  toJavaExpression u = case u of
    Amateur name age gender weight height bpm level address phone email _ ->
      "new Amateur(" ++ intercalate ", " [
        toJavaExpression name
      , toJavaExpression age
      , toJavaExpression gender
      , toJavaExpression weight
      , toJavaExpression height
      , toJavaExpression bpm
      , toJavaExpression level
      , toJavaExpression address
      , toJavaExpression phone
      , toJavaExpression email
      ] ++ ")"

    Occasional name age gender weight height bpm level address phone email frequency _ ->
      "new Occasional(" ++ intercalate ", " [
        toJavaExpression name
      , toJavaExpression age
      , toJavaExpression gender
      , toJavaExpression weight
      , toJavaExpression height
      , toJavaExpression bpm
      , toJavaExpression level
      , toJavaExpression address
      , toJavaExpression phone
      , toJavaExpression email
      , toJavaExpression frequency
      ] ++ ")"

    Professional name age gender weight height bpm level address phone email frequency _ ->
      "new Professional(" ++ intercalate ", " [
        toJavaExpression name
      , toJavaExpression age
      , toJavaExpression gender
      , toJavaExpression weight
      , toJavaExpression height
      , toJavaExpression bpm
      , toJavaExpression level
      , toJavaExpression address
      , toJavaExpression phone
      , toJavaExpression email
      , toJavaExpression frequency
      ] ++ ")"

  toJavaVariable name user =
    [
      javaTypeName user ++ " " ++ name ++ " = " ++ toJavaExpression user ++ ";"
    , name ++ ".addActivities(" ++ toJavaExpression
        (
          map (changeActivityUserUUID (name ++ ".getCode()")) activities
        ) ++ ");"
    ]
    where
      activities = case user of
        Amateur      _ _ _ _ _ _ _ _ _ _   activities -> activities
        Occasional   _ _ _ _ _ _ _ _ _ _ _ activities -> activities
        Professional _ _ _ _ _ _ _ _ _ _ _ activities -> activities

toJavaCreateUserArgs :: User -> String
toJavaCreateUserArgs (Amateur name age gender weight height bpm level address phone email _) =
  intercalate ", " [ toJavaExpression name
                   , toJavaExpression age
                   , toJavaExpression gender
                   , toJavaExpression weight
                   , toJavaExpression height
                   , toJavaExpression bpm
                   , toJavaExpression level
                   , toJavaExpression address
                   , toJavaExpression phone
                   , toJavaExpression email
                   , "0"  -- valor default para frequency
                   , "\"Amateur\""
                   ]
toJavaCreateUserArgs (Occasional name age gender weight height bpm level address phone email
    freq _) =
  intercalate ", " [ toJavaExpression name
                   , toJavaExpression age
                   , toJavaExpression gender
                   , toJavaExpression weight
                   , toJavaExpression height
                   , toJavaExpression bpm
                   , toJavaExpression level
                   , toJavaExpression address
                   , toJavaExpression phone
                   , toJavaExpression email
                   , toJavaExpression freq
                   , "\"Occasional\""
                   ]
toJavaCreateUserArgs (Professional name age gender weight height bpm level address phone email
    freq _) =
  intercalate ", " [ toJavaExpression name
                   , toJavaExpression age
                   , toJavaExpression gender
                   , toJavaExpression weight
                   , toJavaExpression height
                   , toJavaExpression bpm
                   , toJavaExpression level
                   , toJavaExpression address
                   , toJavaExpression phone
                   , toJavaExpression email
                   , toJavaExpression freq
                   , "\"Professional\""
                   ]

-- | A MakeItFit date (YYYY/MM/DD)
data MakeItFitDate = MakeItFitDate Int Int Int deriving (Show, Eq, Ord)

instance Arbitrary MakeItFitDate where
  arbitrary = do
    year  <- elements [2001..2025]
    month <- elements [1..12]
    day   <- elements [1..daysInMonth month year]
    return $ MakeItFitDate year month day
    where
      daysInMonth m y
        | m == 2 = if y `mod` 4 == 0 && (y `mod` 100 /= 0 || y `mod` 400 == 0) then 29 else 28
        | m `elem` [4, 6, 9, 11] = 30
        | otherwise = 31

instance JavaData MakeItFitDate where
  javaTypeName = const "MakeItFitDate"
  toJavaExpression (MakeItFitDate y m d) =
    "MakeItFitDate.of(" ++ intercalate ", " (map toJavaExpression [y, m, d])  ++ ")"

-- | MakeItFit TrailType
data TrailType = TrailEasy | TrailMedium | TrailHard deriving (Show, Eq)

instance JavaData TrailType where
  javaTypeName _ = "TrailType"
  toJavaExpression TrailEasy = "Trail.TRAIL_TYPE_EASY"
  toJavaExpression TrailMedium = "Trail.TRAIL_TYPE_MEDIUM"
  toJavaExpression TrailHard = "Trail.TRAIL_TYPE_HARD"

instance Arbitrary TrailType where
  arbitrary = elements [TrailEasy, TrailMedium, TrailHard]

-- | A MakeItFit activity
data Activity =
  PushUp
    String        -- ^ Java expression for the user's UUID
    MakeItFitDate -- ^ Date when the activity was performed
    Int           -- ^ Expected duration (in minutes)
    String        -- ^ Activity's designation
    String        -- ^ Activity's name
    Int           -- ^ Number of repetitions
    Int           -- ^ Number of series
  |
  Running
    String        -- ^ Java expression for the user's UUID
    MakeItFitDate -- ^ Date when the activity was performed
    Int           -- ^ Expected duration (in minutes)
    String        -- ^ Activity's designation
    String        -- ^ Activity's name
    Double        -- ^ Distance ran
    Double        -- ^ Running velocity
  |
  Trail
    String        -- ^ Java expression for the user's UUID
    MakeItFitDate -- ^ Date when the activity was performed
    Int           -- ^ Expected duration (in minutes)
    String        -- ^ Activity's designation
    String        -- ^ Activity's name
    Double        -- ^ Distance walked
    Double        -- ^ Elevation gain
    Double        -- ^ Elevation loss
    TrailType     -- ^ Type of trail
  |
  WeightSquat
    String        -- ^ Java expression for the user's UUID
    MakeItFitDate -- ^ Date when the activity was performed
    Int           -- ^ Expected duration of the activity (in minutes)
    String        -- ^ Designation for the activity
    String        -- ^ Name of the activity
    Int           -- ^ Number of repetitions
    Int           -- ^ Number of series
    Double        -- ^ Weight used in the repetitions
  deriving (Show, Eq)

changeActivityUserUUID :: String -> Activity -> Activity
changeActivityUserUUID userCode (PushUp _ date duration designation name repetitions series) =
  PushUp userCode date duration designation name repetitions series
changeActivityUserUUID userCode (Running _ date duration designation name distance speed) =
  Running userCode date duration designation name distance speed
changeActivityUserUUID
  userCode (Trail _ date duration designation name distance elevationGain elevationLoss trailType) =
    Trail userCode date duration designation name distance elevationGain elevationLoss trailType
changeActivityUserUUID
  userCode (WeightSquat _ date duration designation name repetitions series weight) =
    WeightSquat userCode date duration designation name repetitions series weight

genActivityName :: Gen String
genActivityName = elements
  [ "Yoga", "Corrida", "Trail", "Musculação", "Caminhada", "Bicicleta", "Natação" , "Pilates"
  , "Boxe", "Zumba", "Body Pump", "Cycling"
  ]

genActivityDesignation :: Gen String
genActivityDesignation = elements
  [ "Treino das 9h", "Treino do 12h", "Treino das 15h", "Treino das 17h", "Treino das 19h"
  , "Treino para emagrecer", "Passeio"
  ]

-- | Generates a MakeItFit activity
genActivity :: String       -- ^ Java expression with the activity's user UUID
            -> Gen Activity -- ^ Generated activity
genActivity userCode = do
  tag         <- elements ["PushUp", "Running", "Trail", "WeightSquat"]
  date        <- arbitrary
  duration    <- elements [10, 15, 20, 30, 45, 60]
  designation <- genActivityDesignation
  name        <- genActivityName
  case tag of
    "PushUp" -> do
       repetitions <- choose (5, 100)
       series      <- choose (1, 10)
       return $ PushUp userCode date duration designation name repetitions series
    "Running" -> do
       distance <- choose (1.0, 20.0)
       speed    <- choose (6.0, 15.0)
       return $ Running userCode date duration designation name distance speed
    "Trail" -> do
       distance      <- choose (1.0, 45.0)
       elevationGain <- choose (0.0, 20.0)
       elevationLoss <- choose (0.0, 20.0)
       trailType     <- arbitrary
       return $ Trail
         userCode date duration designation name distance elevationGain elevationLoss trailType
    "WeightSquat" -> do
       repetitions <- choose (5, 100)
       series      <- choose (1, 10)
       weight      <- choose (10.0, 20.0)
       return $ WeightSquat userCode date duration designation name repetitions series weight

instance JavaData Activity where
  javaTypeName _ = "Activity"

  toJavaExpression a = case a of
    PushUp userCode date duration designation name repetitions series ->
      "new PushUp(" ++ intercalate ", " [
        userCode
      , toJavaExpression date
      , toJavaExpression duration
      , toJavaExpression designation
      , toJavaExpression name
      , toJavaExpression repetitions
      , toJavaExpression series
      ] ++ ")"

    Running userCode date duration designation name distance speed ->
      "new Running(" ++ intercalate ", " [
        userCode
      , toJavaExpression date
      , toJavaExpression duration
      , toJavaExpression designation
      , toJavaExpression name
      , toJavaExpression distance
      , toJavaExpression speed
      ] ++ ")"

    Trail userCode date duration designation name distance elevationGain elevationLoss trailType ->
      "new Trail(" ++ intercalate ", " [
        userCode
      , toJavaExpression date
      , toJavaExpression duration
      , toJavaExpression designation
      , toJavaExpression name
      , toJavaExpression distance
      , toJavaExpression elevationGain
      , toJavaExpression elevationLoss
      , toJavaExpression trailType
      ] ++ ")"

    WeightSquat userCode date duration designation name repetitions series weight ->
      "new WeightSquat(" ++ intercalate ", " [
        userCode
      , toJavaExpression date
      , toJavaExpression duration
      , toJavaExpression designation
      , toJavaExpression name
      , toJavaExpression repetitions
      , toJavaExpression series
      , toJavaExpression weight
      ] ++ ")"

instance JavaData [Activity] where
  javaTypeName = const "List<Activity>"
  toJavaExpression = toJavaExpressionList

-- | MakeItFit training plan
data TrainingPlan = TrainingPlan
  String            -- ^ Java expression for the user's UUID
  [(Int, Activity)] -- ^ Activities [(Repetitions, Activity)]
  MakeItFitDate     -- ^ Training plan Start date
  deriving (Show, Eq)

genTrainingPlanActivity :: String -> Gen (Int, Activity)
genTrainingPlanActivity userCode = do
  repetitions <- choose (1, 10)
  activity <- genActivity userCode
  return (repetitions, activity)

-- | Generates a MakeItFit activity
genTrainingPlan :: String           -- ^ Java expression with the activity's user UUID
                -> Gen TrainingPlan -- ^ Generated training plan
genTrainingPlan userCode = do
  activityCount <- choose (1, 7)
  activities <- vectorOf activityCount (genTrainingPlanActivity userCode)
  date <- arbitrary
  return $ TrainingPlan userCode activities date

instance JavaData (Int, Activity) where
  javaTypeName = const "MyTuple<Integer, Activity>"
  toJavaExpression (i, a) =
    "new MyTuple(" ++ toJavaExpression i ++ ", " ++ toJavaExpression a ++ ")"

instance JavaData [(Int, Activity)] where
  javaTypeName = const "List<MyTuple<Integer, Activity>>"
  toJavaExpression = toJavaExpressionList

instance JavaData TrainingPlan where
  javaTypeName = const "TrainingPlan"

  -- NOTE: this won't have the activities set
  toJavaExpression (TrainingPlan userCode _ date) =
    "new TrainingPlan(" ++ userCode ++ ", " ++ toJavaExpression date ++ ")"

  toJavaVariable name plan@(TrainingPlan userCode activities _) =
    [
      javaTypeName plan ++ " " ++ name ++ " = " ++ toJavaExpression plan ++ ";"
    , name ++ ".addActivities(" ++ toJavaExpression
        (
          map (\(i, a) -> (i, changeActivityUserUUID (userCode) a)) activities
        ) ++ ");"
    ]
