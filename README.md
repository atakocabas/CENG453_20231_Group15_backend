render deployment url: https://ceng453-catan.onrender.com/

# CATAN app

## About

Catan is an interactive multiplayer video game.

## Features

  1. Register player
  2. Login player
  3. Reset password
  4. Get leaderboard for week, month and all times

## Prerequisites

  1. Catan app installation (will be added later)
  2. Internet connection for register, login, reset password and leaderboard update

## Installation

  1. 

## Entities

  1. Leaderboard: Includes leaderboard id, player id, score and date of the score 
  2. LeaderboardEntry: Includes username and score
  3. User: Includes id, username, password, email and salt data
     
## Endpoints

  1. "api/v1/player"
  2. "api/v1/player/register"
  3. "api/v1/player/login"
  4. "api/v1/player/resetPassword"
  5. "api/v1/leaderboard/add"
  6. "api/v1/leaderboard/weekly"
  7. "api/v1/leaderboard/monthly"
  8. "api/v1/leaderboard/all-time"

## Usage

  1. Go to website: https://ceng453-catan.onrender.com/swagger-ui/index.html#/
  2. Go to endpoint 2 for register player
  3. Go to endpoint 3 for login player
  4. Go to endpoint 6 to see weekly leaderboard
  5. Go to endpoint 7 to see monthly leaderboard
  6. Go to endpoint 8 to see all-time leaderboard
  7. api/v1/leaderboard/add is used by the game to add entry to leaderboard. For date, use YYYY-MM-DDTHH:MM:SS format. For example: 2021-05-01T00:00:00