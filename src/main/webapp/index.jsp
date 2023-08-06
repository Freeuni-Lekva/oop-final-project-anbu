<!DOCTYPE html>
<html>
<head>
    <title>Quiz Homepage</title>
</head>
<body>
    <h1>Welcome to the Quiz Homepage</h1>
    <form action="takeQuizServlet" method="post">
        <label for="quizIndex">Enter the index of the quiz:</label>
        <input type="number" id="quizIndex" name="quizIndex" required>
        <button type="submit">Start Quiz</button>
    </form>
</body>
</html>
