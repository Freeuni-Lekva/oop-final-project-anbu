<%@ page import = "java.util.List" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.questions.QuestionType" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%
    boolean takingQuiz = (boolean) request.getSession().getAttribute("takingQuiz");
    if(!takingQuiz){return;}
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    List<Question> questions = quiz.getQuestions();
    long startTimeMillis = (long) session.getAttribute("startTimeMillis");
    long currentTimeMillis = System.currentTimeMillis();
    long timeSpendSeconds = (currentTimeMillis - startTimeMillis) / 1000;
    long timeLeftSeconds = quiz.getTimeLimitMinutes()*60 - timeSpendSeconds;
    String minutes = timeLeftSeconds/60 < 10 ? "0" + timeLeftSeconds/60:timeLeftSeconds/60;
    String seconds = timeLeftSeconds%60 < 10 ? "0" + timeLeftSeconds%60:timeLeftSeconds%60;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Page</title>
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
    .multi-page-container{
        width: 50%;
        max-width: 50%;
    }
    .single-page-container{
        width: 100%;
        max-width: 100%;
    }
    .question {
        text-align: center;
    }

    .question p {
        font-size: 1.5rem;
        line-height: 1.5;
    }

    .question {
        margin-top: 20px;
        text-align: center;
    }

    .answer-input{
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 1.5rem;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-right: 10px;
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

    input[type="text"] {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        width: 300px;
    }

    input[type="text"]:focus {
        border-color: #3e73b3;
        box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.5);
    }

    input[type="text"]:hover {
        border-color: #2a43b0;
    }

    input[type="radio"] {
        display: none;
    }

    input[type="radio"] + label::before {
        display: inline-block;
        width: 20px;
        height: 20px;
        border: 2px solid #3e73b3;
        border-radius: 50%;
        margin-right: 5px;
    }

    .radio-style {
        display: inline-block;
        width: 20px;
        height: 20px;
        border: 2px solid #2a43b0;
        border-radius: 50%;
        margin-right: 5px;
        position: relative;
    }

    .radio-option {
        flex: 1;
        width: 48%;
        padding: 10px;
        margin-bottom: 10px;
        border-radius: 5px;
        box-sizing: border-box;
    }

    .radio-option:hover {
        background-color: #3e73b3;
        color: #fff;
    }

    input[type="radio"]:checked + .radio-style::after {
        content: "";
        position: absolute;
        top: 4px;
        left: 4px;
        width: 12px;
        height: 12px;
        background-color: #2a43b0;
        border-radius: 50%;
    }

    .result p{
        font-size: 1.5rem;
        line-height: 1.5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }
    .divider {
        margin-top: 20px;
        padding: 10px;
    }
     .centered {
        display: flex;
        justify-content: center;
        align-items: center;
    }
     #timer {
        font-size: 24px;
        margin-bottom: 10px;
     }
    </style>
</head>
<body>
        <h1 class = "centered"><%= quiz.getQuizName()%></h1>
        <div class="centered">
            <form method = "post" action = "/secured/gradeQuizServlet" id = "quizForm">
            <% for (int i = 0; i < questions.size(); i++){ %>
            <div class = "flash-card single-page-container">
                     <%= questions.get(i).renderQuestionHTML(i+1) %>
            </div>
            <div class = "divider"></div>
            <% } %>
            <div class = "centered">
            <button class = "centered" type="submit" >Finish</button>
            </div>
            <span class = "centered" id="timer"><%= minutes%>:<%= seconds%></span>
            </form>
        </div>
    <script>
        const quizForm = document.getElementById("quizForm");
        const timerElement = document.getElementById('timer');
        var timeRemaining  = <%= timeLeftSeconds%>;
        let timerInterval;
        function submitForm() {
        quizForm.submit();
        }

        function updateTimerDisplay() {
            const minutes = Math.floor(timeRemaining / 60);
            const seconds = timeRemaining % 60;
            const formattedTime = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            timerElement.textContent = formattedTime;
        }

        function startTimer() {
            if (!timerInterval) {
                timerInterval = setInterval(function() {
                    if (timeRemaining > 0) {
                        timeRemaining--;
                        updateTimerDisplay();
                    } else {
                        clearInterval(timerInterval);
                        timerInterval = null;
                        submitForm();
                    }
                }, 1000);
            }
        }
        startTimer();
    </script>
</body>
</html>
