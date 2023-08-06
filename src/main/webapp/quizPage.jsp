<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Collections" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    List<Question> questions = quiz.getQuestions();
    if(quiz.getRandomizedOrder()) Collections.shuffle(questions);
    int totalQuestions = questions.size();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Page</title>
    <style>
            .question {
                display: none;
            }
            .question.visible {
                display: block;
            }
        </style>
</head>
<body>
    <h1><%= quiz.getQuizName()%></h1>
    <p><%= quiz.getDescription()%></p>
    <p>total number of questions: <%= quiz.getQuestions().size()%></p>
    <div id="question-container">
        <% for (int i = 0; i < questions.size(); i++){ %>
            <%String questionHtml = questions.get(i).renderQuestionHTML(i+1); %>
             <div class="question <%= (i == 0) || quiz.getSinglePageQuestions() ? "visible" : "" %>">
                     <%= questionHtml %>
             <%if(quiz.getImmediateCorrection()){%>
                    <button onclick="answerQuestion()">Answer</button>
                    <div class="feedback" style="display: none;"></div>
             <%}%>
             </div>
        <% } %>
    </div>
    <%if (!quiz.getSinglePageQuestions()){ %>
        <button onclick="showPreviousQuestion()">Previous</button>
        <button onclick="showNextQuestion()">Next</button>
    <%} %>
    <button onclick="finishQuiz()">Finish</button>

    <script>
        var questionIndex = 0;
        var questions = document.getElementsByClassName("question");
        function showQuestion(index) {
            // Hide all questions
            for (var i = 0; i < questions.length; i++) {
                questions[i].classList.remove("visible");
            }
            // Show the question at the specified index
            questions[index].classList.add("visible");
        }
        function showPreviousQuestion() {
            if (questionIndex > 0) {
                questionIndex--;
                showQuestion(questionIndex);
            }
        }
        function showNextQuestion() {
            if (questionIndex < questions.length - 1) {
                questionIndex++;
                showQuestion(questionIndex);
            }
        }
        function finishQuiz() {
            // Your logic to finish the quiz (e.g., submit the answers)
            alert("Quiz finished!");
        }
        function answerQuestion(){
        }
    </script>
</body>
</html>
