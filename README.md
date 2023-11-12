render deployment url: https://ceng453-catan.onrender.com/

# CATAN app

## About

Catan is an interactive multiplayer video game.

## Features

  1. Register user
  2. Login user
  3. Reset password
  4. Get leaderboard for week, month and all times

## Prerequisites

  1. Catan app installation (will be added later)
  2. Internet connection for register, login, reset password and leaderboard update

## Installation

  1. 

## Entities

  1. Leaderboard: Includes leaderboard id, user id, score and date of the score 
  2. LeaderboardEntry: Includes username and score
  3. User: Includes id, username, password, email and salt data
     
## Endpoints

  1. "api/v1/user"
  2. "api/v1/user/register"
  3. "api/v1/user/login"
  4. "api/v1/user/resetPassword"
  5. "api/v1/leaderboard"
  6. "api/v1/leaderboard/weekly"
  7. "api/v1/leaderboard/monthly"
  8. "api/v1/leaderboard/all-time"

## Usage

  1. Go to website: https://ceng453-catan.onrender.com/
  2. Go to endpoint 2 for register user
  3. Go to endpoint 3 for login user
  4. Go to endpoint 6 to see weekly leaderboard
  5. Go to endpoint 7 to see monthly leaderboard
  6. Go to endpoint 8 to see all-time leaderboard
