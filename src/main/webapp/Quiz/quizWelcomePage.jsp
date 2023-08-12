<%@ page import = "java.util.List" %>
<%@ page import="quizapp.models.questions.Question" %>
<%@ page import="quizapp.models.questions.QuestionType" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.dao.UserDAO" %>
<%@ page import="quizapp.models.domain.User" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%
    int quizId = Integer.valueOf(request.getParameter("quizId"));
    QuizDAO quizDAO = new QuizDAO();
    UserDAO userDAO = new UserDAO();
    Quiz quiz = quizDAO.get(quizId).orElse(null);
    request.getSession().setAttribute("quiz",quiz);
    request.getSession().setAttribute("takingQuiz", false);
    if (quiz == null) {
        request.setAttribute("message", "The requested resource was not found.");
        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        return;
    }
    User authorUser = userDAO.get(quiz.getCreatorId()).orElse(null);
%>
<!DOCTYPE html>
<html>
<head>
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
    .multi-page-container{
        width: 50%;
        max-width: 50%;
    }
    .single-page-container{
        width: 100%;
        max-width: 100%;
    }
     .welcome-page-container{
        border: 1px solid #000;
        padding: 20px;
        text-align: left;
       width: 100%;
       max-width: 100%;
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

    .left-div, .center-div, .right-div {
        width: 30%; /* Adjust width as needed */
        display: inline-block; /* Display divs side by side */
        vertical-align: top; /* Align divs at the top */
        border: 1px solid #ccc;
        padding: 10px;
        box-sizing: border-box;
    }

    /* Clear floats after the three divs */
    .clearfix:after {
        content: "";
        display: table;
        clear: both;
    }
    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }
    </style>
</head>
<body>

    <div class = "flash-card welcome-page-container">
        <div class = "left-div">
            <h1><%= quiz.getQuizName()%></h1>
            <p><b>Author:</b> <%= authorUser.getUsername()%></p>
            <p><b>Date:</b> <%= quiz.getFormattedCreationDate()%></p>
            <p><b>About:</b> <%= quiz.getDescription()%></p>
            <p><b>Time Limit:</b> <%= quiz.getTimeLimitMinutes()%> minutes</p>
            <form method = "post" action = "/secured/takeQuizServlet">
                <button action="post" name>Start Quiz</button>
            </form>
        </div>
        <div class = "center-div">
         <p><b>top scores</b></p>
         <table>
             <thead>
                  <tr>
                      <th>User Name</th>
                      <th>Score</th>
                      <th>spent time</th>
                      <th>Date Taken</th>
                  </tr>
              </thead>
             <tbody>
                  <tr>
                      <td>pop</td>
                      <td>80</td>
                      <td>3</td>
                      <td>2023-08-15</td>
                  </tr>
                  <tr>
                      <td>bob</td>
                      <td>95</td>
                      <td>4</td>
                      <td>2023-08-20</td>
                  </tr>
                  <!-- Add more rows for other history quizzes -->
             </tbody>
         </table>
         </div>

         <div class = "right-div">
         <p><b>your attempts</b></p>
            <table>
                <thead>
                     <tr>
                         <th>User Name</th>
                         <th>Score</th>
                         <th>spent time</th>
                         <th>Date Taken</th>
                     </tr>
                 </thead>
                <tbody>
                     <tr>
                         <td>qoq</td>
                         <td>80</td>
                         <td>3 minutes</td>
                         <td>2023-08-15</td>
                     </tr>
                     <tr>
                         <td>dod</td>
                         <td>95</td>
                         <td>4 minutes</td>
                         <td>2023-08-20</td>
                     </tr>
                     <!-- Add more rows for other history quizzes -->
                </tbody>
            </table>
          </div>
    </div>
</body>
</html>
