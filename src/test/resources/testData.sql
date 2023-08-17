USE quizDB;
-- Insert user into users table
INSERT INTO users (username, password_hash) VALUES ('example_user', 'hashed_password');

-- Insert quiz into quizzes table
INSERT INTO quizzes (quiz_name, description, creator_id, time_limit_minutes) VALUES ('Example Quiz', 'This is a sample quiz', 1,5);

INSERT INTO questions (quiz_id, question_type, question_text) VALUES (1, 'Question-Response', 'What is the capital of France?');
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (1, 'Paris', true);

INSERT INTO questions (quiz_id, question_type, question_text) VALUES (1, 'Fill in the Blank', 'The Great Wall of __________ is located in China.');
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (2, 'China', true);

INSERT INTO questions (quiz_id, question_type, question_text) VALUES (1, 'Multiple Choice', 'What is the largest planet in our solar system?');

INSERT INTO answers (question_id, answer_text, is_correct) VALUES (3, 'Jupiter', true);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (3, 'Mars', false);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (3, 'Earth', false);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (3, 'Saturn', false);

INSERT INTO questions (quiz_id, question_type, question_text, picture_url) VALUES (1, 'Picture-Response', 'What breed is this dog?', 'https://www.akc.org/wp-content/uploads/2017/11/Labrador-Retrievers-three-colors.jpg');
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (4, 'Labrador Retriever', true);

INSERT INTO questions (quiz_id, question_type, question_text) VALUES (1, 'Multiple Choice', 'What is the capital of Georgia?');

INSERT INTO answers (question_id, answer_text, is_correct) VALUES (5, 'Tbilisi', true);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (5, 'Rustavi', false);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (5, 'Batumi', false);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (5, 'Sokhumi', false);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (5, 'Kutaisi', false);
INSERT INTO answers (question_id, answer_text, is_correct) VALUES (5, 'Mckheta', false);
