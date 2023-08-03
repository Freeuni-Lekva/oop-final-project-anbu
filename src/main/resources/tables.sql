CREATE DATABASE IF NOT EXISTS quizDB;
USE quizDB;

-- Drop tables if they exist to avoid errors during re-creation
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS friends;
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
  email VARCHAR(100) NOT NULL,
  registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quizzes Table
CREATE TABLE quizzes (
  quiz_id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_name VARCHAR(100) NOT NULL,
  description TEXT,
  creator_id INT NOT NULL,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (creator_id) REFERENCES users(user_id)
);

-- Questions Table
CREATE TABLE questions (
  question_id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_id INT NOT NULL,
  question_type ENUM('Question-Response', 'Fill in the Blank', 'Multiple Choice', 'Picture-Response') NOT NULL,
  question_text TEXT NOT NULL,
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
  user_id_1 INT NOT NULL,
  user_id_2 INT NOT NULL,
  friendship_status ENUM('Pending', 'Accepted') DEFAULT 'Pending',
  request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id_1) REFERENCES users(user_id),
  FOREIGN KEY (user_id_2) REFERENCES users(user_id)
);

-- Messages Table
CREATE TABLE messages (
  message_id INT AUTO_INCREMENT PRIMARY KEY,
  sender_id INT NOT NULL,
  receiver_id INT NOT NULL,
  message_text TEXT NOT NULL,
  send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender_id) REFERENCES users(user_id),
  FOREIGN KEY (receiver_id) REFERENCES users(user_id)
);
