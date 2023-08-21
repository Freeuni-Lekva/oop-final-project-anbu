<%@ page import="quizapp.settings.Endpoints" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Quiz</title>
</head>
<body>
    <h1>Create Quiz</h1>
    <form class = flash-card  method = "post" action = "<%= Endpoints.MAKE_QUIZ%>"  onsubmit="return validateForm()">
        <h2>Quiz:</h2>
       <div id = "quiz-info">
            <div class = "info-input">
                <label for="quizName" class="bold">Quiz Name:</label>
                <input type="text" id="quizName" name="quizName" required>
            </div>


            <div class="info-input">
                <label class="textarea-label bold" for="description">Description:</label>
                <textarea   id="description" name="description" required></textarea>
            </div>

            <div class = "info-input">
                <label for="timeLimit" class="bold">Time Limit (minutes):</label>
                <input type="number" id="timeLimit" name="timeLimit" min="1" required>
            </div>

           <div class="preferences">
                <p class="bold">Choose Your Preferences:</p>
                <label class="checkbox-label">
                    <input type="checkbox" class="custom-checkbox" name="randomizedQuestionsOption">
                    randomized order for questions
                </label>

                <label class="checkbox-label">
                    <input type="checkbox" class="custom-checkbox" name="multiplePageQuestionsOption">
                    display one question per page
                </label>

                <label class="checkbox-label">
                    <input type="checkbox" class="custom-checkbox" name="immediateCorrectionOption">
                    immediate result for answers (only for one question per page)
                </label>
           </div>
       </div>
       <div id="questions-info">
            <h2>Questions:</h2>
            <div class = "single-line-container">
                <p class="bold">Select a question type:</p>
                <div  class="dropdown">
                    <select id = "questionTypeDropDown"class="dropdown-content">
                        <option value="Question-Response">Question-Response</option>
                        <option value="Fill in the Blank">Fill in the Blank</option>
                        <option value="Multiple Choice">Multiple Choice</option>
                        <option value="Picture-Response">Picture-Response</option>
                    </select>
                </div>
                <button type="button" id="add-question" onclick="addQuestionForm()">Add Question</button>
            </div>

                <div id ="questionsDiv"></div>
        </div>
        <button type="submit">Create Quiz</button>
    </form>
    <script>

    var questionIndex = 0;
    const questionTypeDropdown = document.getElementById('questionTypeDropDown');
    const questionsDiv = document.getElementById('questionsDiv');
    function addQuestionForm(){
        const questionType = questionTypeDropdown.value;

        if (questionType === "Question-Response") {
            generateQuestionResponse()
        } else if (questionType === "Fill in the Blank") {
            generateFillInTheBlank()
        } else if (questionType === "Multiple Choice") {
            generateMultipleChoice()
        } else if (questionType === "Picture-Response") {
            generatePictureResponse()
        }else{
            alert("Please select");
        }
        questionIndex++;
    }

    function generateQuestionResponse(){
        const questionDiv = document.createElement("div");
        questionDiv.classList.add("question", "main-content");

        const label = document.createElement('label');
        label.textContent = `${questionIndex+1}. Question response:`;

        const questionText = document.createElement("input");
        questionText.type = "text";
        questionText.name = `questionText:q=${questionIndex}`;
        questionText.placeholder = "Enter question text...";
        questionText.required = true;

        const inputInfo = document.createElement("div");
        inputInfo.className = "input-info";
        inputInfo.appendChild(questionText);

        const addCorrectAnswerInputButton = document.createElement("button");
        addCorrectAnswerInputButton.type = "button";
        addCorrectAnswerInputButton.textContent = "Add Answer";
        addCorrectAnswerInputButton.addEventListener("click", addCorrectAnswerInput);

        const answersDiv = document.createElement("div");
        answersDiv.classList.add("answers", "sub-content");
        answersDiv.setAttribute('data-index', questionIndex);
        answersDiv.style.display = 'block';

        const answersDropDown = document.createElement('span');
        answersDropDown.className = 'dropdown-icon';
        answersDropDown.innerHTML = '&#9650;';
        answersDropDown.addEventListener('click', function(event) {
            toggleSubelements(answersDropDown);
            event.stopPropagation();
        });

        const questionType = document.createElement("input");
        questionType.type = "hidden";
        questionType.name = `questionType:q=${questionIndex}`;
        questionType.value = "Question-Response";

        questionDiv.appendChild(questionType);
        questionDiv.appendChild(label);
        questionDiv.appendChild(inputInfo);
        questionDiv.appendChild(addCorrectAnswerInputButton);
        questionDiv.appendChild(answersDropDown);
        questionDiv.appendChild(answersDiv);
        questionsDiv.insertBefore(questionDiv, questionsDiv.firstChild);
        addCorrectAnswerInputButton.click();
    }

    function generateFillInTheBlank(){
        const questionDiv = document.createElement("div");
        questionDiv.classList.add("question", "main-content");

        const label = document.createElement('label');
        label.textContent = `${questionIndex+1}. Fill In The Blank`;

        const questionText = document.createElement("input");
        questionText.type = "text";
        questionText.name = `questionText:q=${questionIndex}`;
        questionText.placeholder = "write '\\?' for blank word";
        questionText.required = true;
        questionText.className = "fillInTheBlank";

        const inputInfo = document.createElement("div");
        inputInfo.className = "input-info";
        inputInfo.appendChild(questionText);

        const addCorrectAnswerInputButton = document.createElement("button");
        addCorrectAnswerInputButton.type = "button";
        addCorrectAnswerInputButton.textContent = "Add Answer";
        addCorrectAnswerInputButton.addEventListener("click", addCorrectAnswerInput);

        const answersDiv = document.createElement("div");
        answersDiv.classList.add("answers", "sub-content");
        answersDiv.setAttribute('data-index', questionIndex);
        answersDiv.style.display = 'block';

        const answersDropDown = document.createElement('span');
        answersDropDown.className = 'dropdown-icon';
        answersDropDown.innerHTML = '&#9650;';
        answersDropDown.addEventListener('click', function(event) {
            toggleSubelements(answersDropDown);
            event.stopPropagation();
        });

        const questionType = document.createElement("input");
        questionType.type = "hidden";
        questionType.name = `questionType:q=${questionIndex}`;
        questionType.value = "Fill in the Blank";

        questionDiv.appendChild(questionType);
        questionDiv.appendChild(label);
        questionDiv.appendChild(inputInfo);
        questionDiv.appendChild(addCorrectAnswerInputButton);
        questionDiv.appendChild(answersDropDown);
        questionDiv.appendChild(answersDiv);
        questionsDiv.insertBefore(questionDiv, questionsDiv.firstChild);
        addCorrectAnswerInputButton.click();
    }

    function validateFillInTheBlanks() {
         var inputFields = document.querySelectorAll(".fillInTheBlank");
         for (var i = 0; i < inputFields.length; i++) {
            var inputValue = inputFields[i].value;

            if (inputValue.indexOf("\\?") === -1) {
                alert("fill in the blank must include the substring '\\?'");
                return false; // Prevent form submission
            }
         }
        return true;
    }

    function validateForm(){
        var questions = document.querySelectorAll(".question");
        if(questions.length === 0){
            alert("add at least 1 question");
            return false;
        }

        return validateFillInTheBlanks()
    }

    function generateMultipleChoice(){
        const questionDiv = document.createElement("div");
        questionDiv.classList.add("question", "main-content");

        const label = document.createElement('label');
        label.textContent = `${questionIndex+1}. Multiple Choice`;

        const questionText = document.createElement("input");
        questionText.type = "text";
        questionText.name = `questionText:q=${questionIndex}`;
        questionText.placeholder = "Enter question text...";
        questionText.required = true;

        const inputInfo = document.createElement("div");
        inputInfo.className = "input-info";
        inputInfo.appendChild(questionText);

        const addCorrectAnswerInputButton = document.createElement("button");
        addCorrectAnswerInputButton.type = "button";
        addCorrectAnswerInputButton.textContent = "Add Answer";
        addCorrectAnswerInputButton.addEventListener("click", addChoiceAnswerInput);

        const answersDiv = document.createElement("div");
        answersDiv.classList.add("answers", "sub-content");
        answersDiv.setAttribute('data-index', questionIndex);
        answersDiv.style.display = 'block';

        const answersDropDown = document.createElement('span');
        answersDropDown.className = 'dropdown-icon';
        answersDropDown.innerHTML = '&#9650;';
        answersDropDown.addEventListener('click', function(event) {
            toggleSubelements(answersDropDown);
            event.stopPropagation();
        });

        const questionType = document.createElement("input");
        questionType.type = "hidden";
        questionType.name = `questionType:q=${questionIndex}`;
        questionType.value = "Multiple Choice";

        questionDiv.appendChild(questionType);
        questionDiv.appendChild(label);
        questionDiv.appendChild(inputInfo);
        questionDiv.appendChild(addCorrectAnswerInputButton);
        questionDiv.appendChild(answersDropDown);
        questionDiv.appendChild(answersDiv);
        questionsDiv.insertBefore(questionDiv, questionsDiv.firstChild);
        addCorrectAnswerInputButton.click();
    }

    function generatePictureResponse(){
        const questionDiv = document.createElement("div");
        questionDiv.classList.add("question", "main-content");

        const label = document.createElement('label');
        label.textContent = `${questionIndex+1}. Picture Response`;

        const questionText = document.createElement("input");
        questionText.type = "text";
        questionText.name = `questionText:q=${questionIndex}`;
        questionText.placeholder = "Enter question text...";
        questionText.required = true;

        const pictureUrl = document.createElement("input");
        pictureUrl.type = "text";
        pictureUrl.name = `pictureUrl:q=${questionIndex}`;
        pictureUrl.placeholder = "Enter picture url...";
        pictureUrl.required = true;

        const inputInfo = document.createElement("div");
        inputInfo.className = "input-info";
        inputInfo.appendChild(questionText);
        inputInfo.appendChild(pictureUrl);

        const answersDropDown = document.createElement('span');
        answersDropDown.className = 'dropdown-icon';
        answersDropDown.innerHTML = '&#9650;';
        answersDropDown.addEventListener('click', function(event) {
            toggleSubelements(answersDropDown);
            event.stopPropagation();
        });

        const addCorrectAnswerInputButton = document.createElement("button");
        addCorrectAnswerInputButton.type = "button";
        addCorrectAnswerInputButton.textContent = "Add Answer";
        addCorrectAnswerInputButton.addEventListener("click", addCorrectAnswerInput);

        const answersDiv = document.createElement("div");
        answersDiv.classList.add("answers", "sub-content");
        answersDiv.setAttribute('data-index', questionIndex);
        answersDiv.style.display = 'block';


        const questionType = document.createElement("input");
        questionType.type = "hidden";
        questionType.name = `questionType:q=${questionIndex}`;
        questionType.value = "Picture-Response";

        questionDiv.appendChild(questionType);
        questionDiv.appendChild(label);
        questionDiv.appendChild(inputInfo);
        questionDiv.appendChild(addCorrectAnswerInputButton);
        questionDiv.appendChild(answersDropDown);
        questionDiv.appendChild(answersDiv);
        questionsDiv.insertBefore(questionDiv, questionsDiv.firstChild);
        addCorrectAnswerInputButton.click();
    }

    function addChoiceAnswerInput(event){
        const answersDiv = event.target.parentElement.querySelector(".answers");
        var numAnswers = answersDiv.getElementsByClassName("answer").length;

        const label = document.createElement('label');
        label.textContent = `${numAnswers+1}.`;

        const answerDiv = document.createElement("div");
        answerDiv.className = "answer";

        const answerInput = document.createElement("input");
        answerInput.type = "text";
        answerInput.placeholder = "Enter answer...";
        var parentQuestionIndex = answersDiv.getAttribute("data-index");
        answerInput.name = `answer:q=${parentQuestionIndex},a=${numAnswers}`;
        answerInput.required = true;

        const correctCheckbox = document.createElement("input");
        correctCheckbox.type = "radio";
        correctCheckbox.name = `correct:q=${parentQuestionIndex}`;
        correctCheckbox.value = `${numAnswers}`;
        correctCheckbox.className = "correct-radio";
        correctCheckbox.checked = true;

        answerDiv.appendChild(label);
        answerDiv.appendChild(answerInput);
        answerDiv.appendChild(correctCheckbox);
        answersDiv.insertBefore(answerDiv, answersDiv.firstChild);
    }

    function addCorrectAnswerInput(event){
        const answersDiv = event.target.parentElement.querySelector(".answers");
        var numAnswers = answersDiv.getElementsByClassName("answer").length;

        const label = document.createElement('label');
        label.textContent = `${numAnswers+1}.`;

        const answerDiv = document.createElement("div");
        answerDiv.className = "answer";

        const answerInput = document.createElement("input");
        answerInput.type = "text";
        answerInput.placeholder = "Enter answer...";
        var parentQuestionIndex = answersDiv.getAttribute("data-index");
        answerInput.name = `answer:q=${parentQuestionIndex},a=${numAnswers}`;
        answerInput.required = true;

        answerDiv.appendChild(label);
        answerDiv.appendChild(answerInput);
        answersDiv.insertBefore(answerDiv, answersDiv.firstChild);
    }

    function toggleSubelements(triangle) {
        const item = triangle.closest('.main-content');
        const subElements = item.querySelector('.sub-content');
        const dropdownIcon = item.querySelector('.dropdown-icon');
        dropdownIcon.innerHTML = subElements.style.display === 'block' ?  '&#9776;' : '&#9650;';
        subElements.style.display = subElements.style.display === 'block' ? 'none' : 'block';

    }
    </script>
    <style>
    body{
        font-size: 1rem;
        line-height: 1;
        font-family: Sans-Serif;
    }
    .flash-card{
        background-color: #f1f1f1;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    }
    button {
        font-size: 1rem;
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

    input[type="text"],
    input[type="number"],
    textarea {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        width: 300px;
    }

    input[type="text"]:focus,
    input[type="number"]:focus,
    textarea:focus {
        border-color: #3e73b3;
        box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.5);
    }

    input[type="text"]:hover,
    input[type="number"]:hover,
    textarea:hover {
        border-color: #2a43b0;
    }

    select.dropdown-content {
        font-size: 1rem;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 100%;
        max-width: 300px;
        background-color: #f9f9f9;
        appearance: none;
        cursor: pointer;
    }

    select.dropdown-content::-ms-expand {
        display: none;
    }

    select.dropdown-content::before {
        content: '\25BC';
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
    }

    .centered {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .single-line-container {
        display: flex;
        gap: 20px;
        align-items: center;
    }

    #questionsDiv {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: flex-start;
    }

    .main-content {
        border: 1px solid #3e73b3;
        padding: 10px;
        margin: 10px;
    }

    .input-info {
        display: flex;
        flex-direction: column;
    }

    .preferences {
        display: flex;
        flex-direction: column;
    }

    .sub-content {
        display: block;
        margin-left: 20px;
    }

    .dropdown-icon {
        font-size: 20px;
        color: #3e73b3;
        cursor: pointer;
        padding: 3px;
    }

    .correct-radio {
        appearance: none;
        -webkit-appearance: none;
        width: 20px;
        height: 20px;
        border: 2px solid #3e73b3;
        border-radius: 50%;
        background-color: white;
        margin-right: 10px;
    }

    .correct-radio:checked {
        background-color: #3e73b3;
    }

    .bold {
        font-weight: bold;
    }

    .custom-checkbox {
        width: 24px;
        height: 24px;
    }

    .checkbox-label {
        display: flex;
        align-items: center;
        margin-bottom: 10px;
    }

    .info-input{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        margin-bottom: 20px;
    }

    </style>
</body>
</html>
