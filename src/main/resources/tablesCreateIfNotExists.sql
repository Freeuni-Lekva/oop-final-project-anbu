CREATE DATABASE IF NOT EXISTS quizDB;
USE quizDB;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quizzes Table
CREATE TABLE IF NOT EXISTS quizzes (
  quiz_id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_name VARCHAR(100) NOT NULL,
  description TEXT,
  creator_id INT NOT NULL,
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  randomized_order BOOLEAN DEFAULT FALSE,
  single_page_questions BOOLEAN DEFAULT FALSE,
  immediate_correction BOOLEAN DEFAULT FALSE,
  time_limit_minutes INT DEFAULT 60,
  times_taken INT default 0,	
  FOREIGN KEY (creator_id) REFERENCES users(user_id)
);

-- Questions Table
CREATE TABLE IF NOT EXISTS questions (
  question_id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_id INT NOT NULL,
  question_type ENUM('Question-Response', 'Fill in the Blank', 'Multiple Choice', 'Picture-Response') NOT NULL,
  question_text TEXT NOT NULL,
  picture_url VARCHAR(255),
  FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);

-- Answers Table
CREATE TABLE IF NOT EXISTS answers (
  answer_id INT AUTO_INCREMENT PRIMARY KEY,
  question_id INT NOT NULL,
  answer_text TEXT NOT NULL,
  is_correct BOOLEAN NOT NULL,
  FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

-- Quiz History Table
CREATE TABLE IF NOT EXISTS quiz_history (
  history_id INT AUTO_INCREMENT PRIMARY KEY,
  username INT NOT NULL,
  quiz_id INT NOT NULL,
  score INT,
  completion_time_seconds INT,
  completion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);

-- Friends Table
CREATE TABLE IF NOT EXISTS friends (
  friendship_id INT AUTO_INCREMENT PRIMARY KEY,
  username_1 VARCHAR(50) NOT NULL,
  username_2 VARCHAR(50) NOT NULL,
  FOREIGN KEY (username_1) REFERENCES users(username),
  FOREIGN KEY (username_2) REFERENCES users(username)
);

-- Friend Requests Table
CREATE TABLE IF NOT EXISTS friend_requests (
  request_id INT AUTO_INCREMENT PRIMARY KEY,
  sender VARCHAR(50) NOT NULL,
  receiver VARCHAR(50) NOT NULL,
  request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender) REFERENCES users(username),
  FOREIGN KEY (receiver) REFERENCES users(username)
);

-- Challenge Requests Table
CREATE TABLE IF NOT EXISTS challenge_requests (
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
CREATE TABLE IF NOT EXISTS notes (
  message_id INT AUTO_INCREMENT PRIMARY KEY,
  sender VARCHAR(50) NOT NULL,
  receiver VARCHAR(50) NOT NULL,
  note TEXT NULL,
  send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender) REFERENCES users(username),
  FOREIGN KEY (receiver) REFERENCES users(username)
);
