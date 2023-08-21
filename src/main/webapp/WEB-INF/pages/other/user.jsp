<%@ page import="quizapp.managers.FriendManager" %>
<%@ page import="java.util.List" %>
<%@ page import="quizapp.models.domain.User" %>
<%@ page import="quizapp.settings.Endpoints" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.settings.Services" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%@ page import="quizapp.models.dao.UserDAO" %>
<%@ page import="java.util.Optional" %>
<%@ page import="quizapp.managers.HistoryManager" %>
<%@ page import="quizapp.models.domain.QuizHistory" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = request.getParameter("username");
    User current_user = (User) session.getAttribute("user");
    String current_username = current_user.getUsername();

    boolean isCurrentUser = current_username.equals(username);
    FriendManager friendManager = (FriendManager) request.getServletContext().getAttribute("friendManager");
    boolean areFriends = friendManager.areFriends(current_username, username);
    boolean friendRequestIsSent = friendManager.friendRequestIsSent(current_username, username);
    boolean friendRequestIsReceived = friendManager.friendRequestIsSent(username, current_username);

    UserDAO userDao = (UserDAO) request.getServletContext().getAttribute(Services.USER_DAO);
    User search_user = userDao.getByUsername(username).get();

    QuizDAO quizDao = (QuizDAO) request.getServletContext().getAttribute(Services.QUIZ_DAO);
    List<Quiz> quizzes_created = quizDao.getAllByCreator(search_user.getId());

    HistoryManager historyManager = (HistoryManager) request.getServletContext().getAttribute(Services.HISTORY_MANAGER);
    List<QuizHistory> quizzes_taken = historyManager.getUserActivity(username);
%>

<html>
<head>
    <title>User - <%=username%></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#add-friend-btn").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.MESSAGE%>',
                    data: {
                        sender: '<%=current_username%>',
                        receiver: '<%=username%>',
                        messageType: "friend"
                    },
                    success: function(response) {
                        location.reload();
                    }
                })
            })

            $("#accept-friend-request").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.ADD_FRIEND%>',
                    data: {
                        sender: '<%=username%>'
                    },
                    success: function(response) {
                        location.reload()
                    }
                })
            })

            $("#reject-friend-request").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.REJECT_FRIEND_REQUEST%>',
                    data: {
                        sender: '<%=username%>'
                    },
                    success: function(response) {
                        location.reload()
                    }
                })
            })

            $("#send-note-btn").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.MESSAGE%>',
                    data: {
                        sender: '<%=current_username%>',
                        receiver: '<%=username%>',
                        messageType: 'note',
                        note: $('#note-input').val()
                    },
                    success: function(response) {
                        $('#note-input').val('');
                    }
                })
            })

        })
    </script>
</head>
<body>
    <div class = "flash-card">
        <h1>username: <%=username%></h1>
        <% if (!isCurrentUser) { %>
            <% if (!areFriends) { %>
                <% if (friendRequestIsSent) { %>
                    <p>Friend request is already sent.</p>
                <% } else if (friendRequestIsReceived) { %>
                    <button id="accept-friend-request">Accept</button>
                    <button id="reject-friend-request">Reject</button>
                <% } else { %>
                    <button id="add-friend-btn">Add Friend</button>
                <% } %>
            <% } else { %>
                <p>You are already friends.</p>
            <% } %>

            <input id="note-input" type="text" placeholder="Note Message"><button id="send-note-btn">Send Message</button>
        <% } %>

        <h2>Quizzes created:</h2>
        <span class = "dropdown-icon">&#9650;</span>
        <div class = "sub-content" style="display: block;">
            <ul>
                <% for (Quiz quiz : quizzes_created) { %>
                <li>
                    <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
                </li>
                <% }; %>
            </ul>
        </div>

        <h2>Quizzes taken:</h2>
        <span class = "dropdown-icon" >&#9650;</span>
        <div class = "sub-content" style="display: block;">
            <ul>
                <% for (QuizHistory activity : quizzes_taken) { %>
                <li>
                    <p>Completed <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=activity.get_quiz_id()%>" style="text-decoration: underline"><%=activity.get_quiz_name()%></a> with the score of <%=activity.getScore()%></p>
                </li>
                <% }; %>
            </ul>
        </div>
    </div>
    <div class="divider"></div>
    <form action="<%=Endpoints.HOMEPAGE%>" method="get">
        <button type="submit">go back to homepage</button>
    </form>

     <style>
        body{
            font-family: Sans-Serif;
            position: relative;
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
         .flash-card{
                background-color: #f1f1f1;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            }

        button:hover {
            background-color: #2a43b0;
        }

        input[type="text"]{
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

        input[type="text"]:hover{
            border-color: #2a43b0;
        }

        .divider {
           margin-top: 20px;
           padding: 10px;
        }
     </style>
</body>
</html>
