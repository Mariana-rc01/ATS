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

import Test.QuickCheck (Arbitrary(..), elements, listOf)

-- | MakeItFit user
data User =
  Beginner
    String -- ^ User's name
  |
  Intermediate
    String -- ^ User's name
  deriving (Show, Eq)

instance Arbitrary User where
  arbitrary = do
    constructor <- elements [Beginner, Intermediate]
    name <- listOf $ elements (['A'..'Z'] ++ ['a'..'z'] ++ [' '])
    return $ constructor name
