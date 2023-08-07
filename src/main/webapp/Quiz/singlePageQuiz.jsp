<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Collections" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.questions.QuestionType" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%@ page import="quizapp.models.domain.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    List<Question> questions = quiz.getQuestions();

%>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Page</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1><%= quiz.getQuizName()%></h1>
    <p><%= quiz.getDescription()%></p>
        <div class="centered">
            <form method = "post" action = "/gradeQuizServlet">
            <% for (int i = 0; i < questions.size(); i++){ %>
            <div class = "question-container single-page-container">
                     <%= questions.get(i).renderQuestionHTML(i+1) %>
            </div>
            <div class = "divider"></div>
            <% } %>
             <button type="submit">Finish</button>
            </form>
        </div>
</body>
</html>
