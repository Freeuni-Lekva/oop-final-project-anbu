<!DOCTYPE html>
<html>
<head>
    <title>Quiz Page</title>
    <!-- Add any necessary CSS or other headers here -->
    <style>
        .question {
            display: none; /* Hide all questions initially */
        }
        .question.visible {
            display: block; /* Show only the question with 'visible' class */
        }
    </style>
</head>
<body>

<!-- Include your quiz object from the backend -->
<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    List<Question> questions = quiz.getQuestions();
    int totalQuestions = questions.size();
%>

<!-- Create a container to hold the questions -->
<div id="question-container">
    <%
        int questionIndex = 0;
        for (Question question : questions) {
            // Render the HTML for each question using the renderQuestionHtml() method
            String questionHtml = question.renderQuestionHtml();
    %>
    <div class="question <%= (questionIndex == 0) ? "visible" : "" %>">
        <%= questionHtml %>
    </div>
    <%
            questionIndex++;
        }
    %>
</div>

<!-- Display the current question number and total number of questions -->
<p>Question <%= questionIndex %> of <%= totalQuestions %></p>

<!-- Previous and Next buttons -->
<button onclick="showPreviousQuestion()">Previous</button>
<button onclick="showNextQuestion()">Next</button>
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
</script>

</body>
</html>
