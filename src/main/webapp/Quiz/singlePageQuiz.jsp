<%@ page import = "java.util.List" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.questions.QuestionType" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
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
    <div class = "info">
        <h1 class = " centered"><%= quiz.getQuizName()%></h1>
        <p class = " centered"><%= quiz.getDescription()%></p>
    </div>
        <div class="centered">
            <form method = "post" action = "/gradeQuizServlet">
            <% for (int i = 0; i < questions.size(); i++){ %>
            <div class = "question-container single-page-container">
                     <%= questions.get(i).renderQuestionHTML(i+1) %>
            </div>
            <div class = "divider"></div>
            <% } %>
            <div class = "centered">
                <button type="submit" >Finish</button>
            </div>
            </form>
        </div>
</body>
</html>
