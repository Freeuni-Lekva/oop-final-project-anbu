<%@ page import = "java.util.List" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.questions.QuestionType" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
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
     <div class = "info">
        <h1 class = " centered"><%= quiz.getQuizName()%></h1>
        <p class = " centered"><%= quiz.getDescription()%></p>
    </div>
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
                int correctCounter = (Integer) request.getSession().getAttribute("correctCounter");%>
                <div class="result">
                    <p><%= correctCounter%>/<%= questions.size()%> correct</p>
                </div>
            <%}%>
        </div>
    </div>
</body>
</html>
