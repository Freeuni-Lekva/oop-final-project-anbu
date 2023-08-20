<%@ page import = "java.util.List" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.questions.QuestionType" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="quizapp.settings.Endpoints" %>
<%
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    List<Question> questions = quiz.getQuestions();
    long timeTookMinutes = (Long) request.getSession().getAttribute("timeTookMinutes");
    long timeTookSeconds = (Long) request.getSession().getAttribute("timeTookSeconds");
    int correctCounter = (Integer) request.getSession().getAttribute("correctCounter");
    HashMap<Question,String> answers = (HashMap<Question,String>) request.getSession().getAttribute("answers");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Results Page</title>
     <title>Quiz Welcome Page</title>
        <style>
        body{
            font-family: Sans-Serif;
        }
        .flash-card{
            background-color: #f1f1f1;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
        button {
            font-size: 1.5rem;
            padding: 10px 20px;
            background-color: #3e73b3;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #2a43b0;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 10px;
        }
        .result p{
            font-size: 1.5rem;
            line-height: 1.5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
        }
        </style>
</head>
<body>
        <h1>Your Result</h1>
        <p><b>final score:</b> <%= correctCounter%>/<%=questions.size()%> </p>
        <p><b>time took:</b> <%= timeTookMinutes%> minutes and <%= timeTookSeconds%> seconds</p>
        <div class = "flash-card">
            <%for(int i = 0; i < questions.size();i++){%>
                <div>
                    <%String answer = answers.get(questions.get(i));%>
                    <p><%=i+1%>. <%= questions.get(i).getQuestionText()%></p>
                    <%if(questions.get(i).isAnswerCorrect(answer)){%>
                        <span style="color: green;">&#10004; <%=answer%></span>
                    <%}else{%>
                        <span style="color: red;">&#10008; <%=answer%></span>
                    <%}%>
                </div>
            <%}%>

        </div>
        <form action="<%=Endpoints.HOMEPAGE%>" method="get">
            <button type="submit">go back to homepage</button>
        </form>
</body>
</html>
