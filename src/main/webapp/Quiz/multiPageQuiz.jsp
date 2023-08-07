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
    int questionIndex = Integer.valueOf(request.getParameter("questionIndex"));

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
        <div class = "question-container multi-page-container">
            <form method = "post" action = "/gradeQuizServlet">
                <input type = "hidden" name = "questionIndex" value = <%= questionIndex%> />
                <%= questions.get(questionIndex).renderQuestionHTML(questionIndex+1) %>
                <div class = "divider centered" >
                    <%if(questionIndex < questions.size() - 1){%>
                        <button type="submit">Next</button>
                    <%}else{%>
                        <button type="submit">Finish</button>
                    <%}%>
                </div>
            </form>
            <%if(quiz.getImmediateCorrection()){
                int correct = (Integer) request.getSession().getAttribute("correct");%>
                <div class="result">
                    <p><%= correct%>/<%= questions.size()%> correct</p>
                </div>
            <%}%>
        </div>
    </div>
</body>
</html>
