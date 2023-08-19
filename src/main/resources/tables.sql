CREATE DATABASE IF NOT EXISTS quizDB;
USE quizDB;

-- Drop tables if they exist to avoid errors during re-creation
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS friend_requests;
DROP TABLE IF EXISTS challenge_requests;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS quiz_history;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS users;

-- Users Table
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quizzes Table
CREATE TABLE quizzes (
  quiz_id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_name VARCHAR(100) NOT NULL,
  description TEXT,
  creator_id INT NOT NULL,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  randomized_order BOOLEAN DEFAULT FALSE,
  single_page_questions BOOLEAN DEFAULT FALSE,
  immediate_correction BOOLEAN DEFAULT FALSE,
  time_limit_minutes INT default 60,
  times_taken INT default 0,
  FOREIGN KEY (creator_id) REFERENCES users(user_id)
);

-- Questions Table
CREATE TABLE questions (
  question_id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_id INT NOT NULL,
  question_type ENUM('Question-Response', 'Fill in the Blank', 'Multiple Choice', 'Picture-Response') NOT NULL,
  question_text TEXT NOT NULL,
  picture_url VARCHAR(255),
  FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);

-- Answers Table
CREATE TABLE answers (
  answer_id INT AUTO_INCREMENT PRIMARY KEY,
  question_id INT NOT NULL,
  answer_text TEXT NOT NULL,
  is_correct BOOLEAN NOT NULL,
  FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

-- Quiz History Table
CREATE TABLE quiz_history (
  history_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  quiz_id INT NOT NULL,
  score INT,
  completion_time INT,
  completion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);

-- Friends Table
CREATE TABLE friends (
    friendship_id INT AUTO_INCREMENT PRIMARY KEY,
    username_1 VARCHAR(50) NOT NULL,
    username_2 VARCHAR(50) NOT NULL,
    FOREIGN KEY (username_1) REFERENCES users(username),
    FOREIGN KEY (username_2) REFERENCES users(username)
);

-- Friend Requests Table
CREATE TABLE friend_requests (
  request_id INT AUTO_INCREMENT PRIMARY KEY,
  sender VARCHAR(50) NOT NULL,
  receiver VARCHAR(50) NOT NULL,
  request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender) REFERENCES users(username),
  FOREIGN KEY (receiver) REFERENCES users(username)
);

-- Challenge Requests Table
CREATE TABLE challenge_requests (
    challenge_id INT AUTO_INCREMENT PRIMARY KEY,
    sender VARCHAR(50) NOT NULL,
    receiver VARCHAR(50) NOT NULL,
    quiz_id INT NOT NULL,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender) REFERENCES  users(username),
    FOREIGN KEY (receiver) REFERENCES  users(username),
    FOREIGN KEY (quiz_id) REFERENCES  quizzes(quiz_id)
);

-- Notes Table
CREATE TABLE notes (
  message_id INT AUTO_INCREMENT PRIMARY KEY,
  sender VARCHAR(50) NOT NULL,
  receiver VARCHAR(50) NOT NULL,
  note TEXT NULL,
  send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender) REFERENCES users(username),
  FOREIGN KEY (receiver) REFERENCES users(username)
);
