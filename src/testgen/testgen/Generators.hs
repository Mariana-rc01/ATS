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

module Generators (User(..)) where

import Test.QuickCheck (Arbitrary(..), elements, choose, oneof, vectorOf, Gen, listOf, frequency)
import Java (JavaData(..), toJavaExpression)

-- | MakeItFit user
data User =
  Amateur
    UUID -- ^ User's identifier
    String -- ^ User's name
    Int -- ^ User's age
    Gender -- ^ User's gender
    Float -- ^ User's weight
    Int -- ^ User's height
    Int -- ^ User's BPM
    Int -- ^ User's level
    String -- ^ User's address
    String -- ^ User's phone
    String -- ^ User's email
    Float  -- ^ User's index
    [Activity] -- ^ User's activities
  |
  Occasional
    UUID -- ^ User's identifier
    String -- ^ User's name
    Int -- ^ User's age
    Gender -- ^ User's gender
    Float -- ^ User's weight
    Int -- ^ User's height
    Int -- ^ User's BPM
    Int -- ^ User's level
    String -- ^ User's address
    String -- ^ User's phone
    String -- ^ User's email
    Int -- ^ User's frequency
    Float  -- ^ User's index
    [Activity] -- ^ User's activities
  |
  Professional
    UUID -- ^ User's identifier
    String -- ^ User's name
    Int -- ^ User's age
    Gender -- ^ User's gender
    Float -- ^ User's weight
    Int -- ^ User's height
    Int -- ^ User's BPM
    Int -- ^ User's level
    String -- ^ User's address
    String -- ^ User's phone
    String -- ^ User's email
    Int -- ^ User's frequency
    String -- ^ User's specialization
    Float  -- ^ User's index
    [Activity] -- ^ User's activities
  deriving (Show, Eq)

data Gender = Male | Female deriving (Show, Eq)

instance JavaData Gender where
  javaTypeName _ = "Gender"
  toJavaExpression Male = "Gender.MALE"
  toJavaExpression Female = "Gender.FEMALE"

genUUID :: Gen UUID
genUUID = do
  part1 <- vectorOf 8 (elements (['a'..'f'] ++ ['0'..'9']))
  part2 <- vectorOf 4 (elements (['a'..'f'] ++ ['0'..'9']))
  part3 <- vectorOf 4 (elements (['a'..'f'] ++ ['0'..'9']))
  part4 <- vectorOf 4 (elements (['a'..'f'] ++ ['0'..'9']))
  part5 <- vectorOf 12 (elements (['a'..'f'] ++ ['0'..'9']))
  return $ part1 ++ "-" ++ part2 ++ "-" ++ part3 ++ "-" ++ part4 ++ "-" ++ part5

genName :: Gen String
genName = elements
  [ "Humberto" , "José" , "Mariana" , "André" , "Adriana" , "Bruno" , "Beatriz" , "Carlota"
  , "Carlos" , "Dalila" , "Dinis" , "Eva", "Filipe" , "Hugo" , "Gabriel" , "Sofia"
  , "Olívia" , "Henrique" , "Margarida" , "Elisabete"
  ]

genPhone :: Gen String
genPhone = do
  secondDigit <- elements ['1', '2', '3', '6']
  rest <- sequence $ replicate 7 (elements ['0'..'9'])
  return $ '9' : secondDigit : rest

genEmail :: Gen String
genEmail = do
  localPart <- listOf $ elements (['a'..'z'] ++ ['0'..'9'])
  domain <- frequency
    [ (32, return "gmail.com"), (30, return "yahoo.com"), (28, return "hotmail.com")
    , (5,  return "aol.com"), (2,  return "hotmail.co.uk"), (2,  return "hotmail.fr")
    , (1,  return "msn.com")
    ]
  return $ localPart ++ "@" ++ domain

genAddress :: Gen String
genAddress = do
  street <- genName
  number <- elements [1..100]
  city <- elements ["Braga", "Lisboa", "Porto", "Coimbra", "Leiria"]
  return $ "Rua" ++ street ++ " " ++ show number ++ ", " ++ city

genActivityName :: Gen String
genActivityName = elements
  [ "Yoga", "Corrida", "Trail", "Musculação", "Caminhada", "Bicicleta", "Natação"
  , "Pilates", "Boxe", "Zumba", "Body Pump", "Cycling"
  ]

genSpecialization :: Gen String
genSpecialization = elements
  [ "Ballet", "Musculação", "Cardio", "Pilates", "Yoga", "Body Pump", "Localizada"
  , "Zumba", "Natação", "Cycling", "Boxe"
  ]

instance Arbitrary User where
  arbitrary = do
    code <- genUUID
    tag <- elements ["Amateur", "Occasional", "Professional"]
    name <- genName
    age <- elements [18..80]
    gender <- elements [Male, Female]
    weight <- elements [50.0..100.00]
    height <- elements [150..195]
    bpm <- elements [60..100]
    level <- arbitrary
    address <- genAddress
    phone <- genPhone
    email <- genEmail
    index <- choose (0.0, 10.0)
    n <- choose (1, 30)
    activities <- vectorOf n arbitrary
    case tag of
      "Amateur" ->
        return $ Amateur code name age gender weight height bpm level address phone
            email index activities
      "Occasional" -> do
        freq <- elements [1..14]
        return $ Occasional code name age gender weight height bpm level address phone email
            freq index activities
      "Professional" -> do
        freq <- elements [1..21]
        spec <- genSpecialization
        return $ Professional code name age gender weight height bpm level address phone email freq
            spec index activities

instance JavaData User where
  javaTypeName _ = "User"
  toJavaExpression u = case u of
    Amateur uuid name age gender weight height bpm lvl addr phone email idx acts ->
      "new Amateur(" ++ intercalate ", " (map toJavaExpression
        [uuid, name, age, gender, weight, height, bpm, lvl, addr, phone, email, idx]) ++
        ", " ++ toJavaExpression acts ++ ")"

    Occasional uuid name age gender weight height bpm lvl addr phone email freq idx acts ->
      "new Occasional(" ++ intercalate ", " (map toJavaExpression
        [uuid, name, age, gender, weight, height, bpm, lvl, addr, phone, email, freq, idx]) ++
        ", " ++ toJavaExpression acts ++ ")"

    Professional uuid name age gender weight height bpm lvl addr phone email freq spec idx acts ->
      "new Professional(" ++ intercalate ", " (map toJavaExpression
        [uuid, name, age, gender, weight, height, bpm, lvl, addr, phone, email, freq, spec, idx]) ++
        ", " ++ toJavaExpression acts ++ ")"

-- | MakeItFit Date
data MakeItFitDate = MakeItFitDate (Int, Int, Int) deriving (Show, Eq, Ord)

type UUID = String

instance Arbitrary MakeItFitDate where
  arbitrary = do
    year <- elements [2001..2025]
    month <- elements [1..12]
    let daysInMonth m y
          | m == 2 = if y `mod` 4 == 0 && (y `mod` 100 /= 0 || y `mod` 400 == 0) then 29 else 28
          | m `elem` [4, 6, 9, 11] = 30
          | otherwise = 31
    day <- elements [1..daysInMonth month year]
    return $ MakeItFitDate (year, month, day)

instance JavaData MakeItFitDate where
  javaTypeName _ = "MakeItFitDate"
  toJavaExpression (MakeItFitDate (y, m, d)) =
    "new MakeItFitDate(" ++ show y ++ ", " ++ show m ++ ", " ++ show d ++ ")"

-- | MakeItFit activity
data Activity
  = PushUp
      UUID          -- ^ Activity's user code identifier
      UUID          -- ^ Activity's code
      MakeItFitDate -- ^ Date when the activity was performed
      Int           -- ^ Expected duration of the activity (in minutes)
      String        -- ^ Designation for the activity
      String        -- ^ Name of the activity
      String        -- ^ Specialization of the activity
      Int           -- ^ Activity's duration
      Int           -- ^ Calories wasted during the activity
      Int           -- ^ Number of repetitions
      Int           -- ^ Number of series
  | Running
      UUID          -- ^ Activity's user code identifier
      UUID          -- ^ Activity's code
      MakeItFitDate -- ^ Date when the activity was performed
      Int           -- ^ Expected duration of the activity (in minutes)
      String        -- ^ Designation for the activity
      String        -- ^ Name of the activity
      String        -- ^ Specialization of the activity
      Int           -- ^ Activity's duration
      Int           -- ^ Calories wasted during the activity
      Double        -- ^ Distance covered
      Double        -- ^ Average speed
  | Trail
      UUID          -- ^ Activity's user code identifier
      UUID          -- ^ Activity's code
      MakeItFitDate -- ^ Date when the activity was performed
      Int           -- ^ Expected duration of the activity (in minutes)
      String        -- ^ Designation for the activity
      String        -- ^ Name of the activity
      String        -- ^ Specialization of the activity
      Int           -- ^ Activity's duration
      Int           -- ^ Calories wasted during the activity
      Double        -- ^ Distance covered
      Double        -- ^ Elevation gain
      Double        -- ^ Elevation loss
      TrailType     -- ^ Type of trail (Easy, Medium, Hard)
  | WeightSquat
      UUID          -- ^ Activity's user code identifier
      UUID          -- ^ Activity's code
      MakeItFitDate -- ^ Date when the activity was performed
      Int           -- ^ Expected duration of the activity (in minutes)
      String        -- ^ Designation for the activity
      String        -- ^ Name of the activity
      String        -- ^ Specialization of the activity
      Int           -- ^ Activity's duration
      Int           -- ^ Calories wasted during the activity
      Int           -- ^ Number of repetitions
      Int           -- ^ Number of series
      Double        -- ^ Weight used in the repetitions
  deriving (Show, Eq)

data TrailType = TrailEasy | TrailMedium | TrailHard
  deriving (Show, Eq)

genDesignation :: Gen String
genDesignation = elements
  [ "Treino das 9h", "Treino do 12h", "Treino das 15h", "Treino das 17h", "Treino das 19h"
  , "Treino para emagrecer", "Passeio"]

instance JavaData TrailType where
  javaTypeName _ = "TrailType"
  toJavaExpression TrailEasy = "TrailType.EASY"
  toJavaExpression TrailMedium = "TrailType.MEDIUM"
  toJavaExpression TrailHard = "TrailType.HARD"

instance Arbitrary Activity where
  arbitrary = do
    tag <- elements ["PushUp", "Running", "Trail", "WeightSquat"]
    userCode <- genUUID
    activityCode <- genUUID
    date <- arbitrary
    expDur <- elements [10,15,20,30,45,60]
    designation <- genDesignation
    name <- genActivityName
    let spec = tag
    dur <- elements [5,10,15,20,30,45,60]
    cal <- elements [20,50,100,150,172,200,300]
    case tag of
      "PushUp" -> do
         reps <- elements [5..100]
         series <- elements [1..10]
         return $ PushUp userCode activityCode date expDur designation name spec dur cal reps series
      "Running" -> do
         dist <- choose (1.0, 45.0)
         speed <- choose (6.0, 15.0)
         return $ Running userCode activityCode date expDur designation name spec dur cal dist speed
      "Trail" -> do
         dist <- frequency[(5, choose(2.0, 10.0)),(3, choose(10.0, 25.0)),(2, choose(25.0, 50.0))]
         elevGain <- choose (0.0, 20.0)
         elevLoss <- choose (0.0, 20.0)
         trailtype <- arbitrary
         return $ Trail userCode activityCode date expDur designation name spec dur cal dist
            elevGain elevLoss trailtype
      "WeightSquat" -> do
         reps <- elements [5..30]
         series <- elements [1..8]
         weight <- choose (10.0, 200.0)
         return $ WeightSquat userCode activityCode date expDur designation name spec dur cal reps
            series weight

instance JavaData Activity where
  javaTypeName _ = "Activity"
  toJavaExpression a = case a of
    PushUp uc ac date ed des name spec dur cal reps series ->
      "new PushUp(" ++ intercalate ", " (map toJavaExpression
        [uc, ac]) ++ ", " ++
        toJavaExpression date ++ ", " ++
        intercalate ", " (map toJavaExpression
          [ed, des, name, spec, dur, cal, reps, series]) ++ ")"

    Running uc ac date ed des name spec dur cal dist speed ->
      "new Running(" ++ intercalate ", " (map toJavaExpression
        [uc, ac]) ++ ", " ++
        toJavaExpression date ++ ", " ++
        intercalate ", " (map toJavaExpression
          [ed, des, name, spec, dur, cal]) ++ ", " ++
        toJavaExpression dist ++ ", " ++ toJavaExpression speed ++ ")"

    Trail uc ac date ed des name spec dur cal dist eg el tt ->
      "new Trail(" ++ intercalate ", " (map toJavaExpression
        [uc, ac]) ++ ", " ++
        toJavaExpression date ++ ", " ++
        intercalate ", " (map toJavaExpression
          [ed, des, name, spec, dur, cal]) ++ ", " ++
        toJavaExpression dist ++ ", " ++
        toJavaExpression eg ++ ", " ++
        toJavaExpression el ++ ", " ++
        toJavaExpression tt ++ ")"

    WeightSquat uc ac date ed des name spec dur cal reps series weight ->
      "new WeightSquat(" ++ intercalate ", " (map toJavaExpression
        [uc, ac]) ++ ", " ++
        toJavaExpression date ++ ", " ++
        intercalate ", " (map toJavaExpression
          [ed, des, name, spec, dur, cal, reps, series, weight]) ++ ")"

-- | MakeItFit training plan
data TrainingPlan = TrainingPlan
  UUID                   -- ^ user code
  UUID                   -- ^ training plan code
  String                 -- ^ training plan name
  [MyTuple Int Activity] -- ^ activities (list of (Integer, Activity) tuples)
  MakeItFitDate          -- ^ date when the training plan was created
  deriving (Show, Eq)

data MyTuple a b = MyTuple a b
  deriving (Eq, Show)

instance Arbitrary TrainingPlan where
  arbitrary = do
    userCode <- genUUID
    planCode <- genUUID
    letter <- elements ['A'..'Z']
    let name = "Plano " ++ [letter]
    n <- choose (1, 7)
    activities <- vectorOf n arbitrary
    date <- arbitrary
    return $ TrainingPlan userCode planCode name (zip [1..n] activities) date

instance JavaData TrainingPlan where
  toJava (TrainingPlan name duration activities) =
    "new TrainingPlan(" ++
      toJava name ++ ", " ++
      show duration ++ ", " ++
      "List.of(" ++ intercalate ", " (map toJava activities) ++ ")" ++
    ")"

