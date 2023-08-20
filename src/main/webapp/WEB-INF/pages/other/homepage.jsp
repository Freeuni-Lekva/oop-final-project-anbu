<%@ page import="quizapp.managers.NoteManager" %>
<%@ page import="quizapp.models.domain.message.NoteMessage" %>
<%@ page import="java.util.List" %>
<%@ page import="quizapp.models.domain.User" %>
<%@ page import="quizapp.managers.FriendManager" %>
<%@ page import="quizapp.models.domain.message.FriendRequest" %>
<%@ page import="quizapp.managers.ChallengeManager" %>
<%@ page import="quizapp.models.domain.message.ChallengeRequest" %>
<%@ page import="quizapp.models.dao.QuizDAO" %>
<%@ page import="quizapp.models.questions.Quiz" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="quizapp.models.dao.UserDAO" %>
<%@ page import="quizapp.settings.Endpoints" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    User currentUser = (User) request.getSession().getAttribute("user");
    String username = currentUser.getUsername();
%>
<head>
    <title>Welcome <%= username %></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#search-btn").click(function () {
                const username = $("#search-input").val();

                $.ajax({
                    type: "GET",
                    contentType: "text/html",
                    url: '<%=Endpoints.SEARCH%>',
                    data: { search_username: username },
                    success: function(response) {
                        $("#search-result").html(response);
                    }
                })
            })
        })
    </script>
</head>
<body>
    <%
        NoteManager noteManager = (NoteManager) request.getServletContext().getAttribute("noteManager");
        List<NoteMessage> notes = noteManager.getNotesByUsername(username);

        FriendManager friendManager = (FriendManager) request.getServletContext().getAttribute("friendManager");
        List<String> friendList = friendManager.getFriends(username);
        List<FriendRequest> friendRequests = friendManager.getFriendRequests(username);

        ChallengeManager challengeManager = (ChallengeManager) request.getServletContext().getAttribute("challengeManager");
        List<ChallengeRequest> challengeRequests = challengeManager.getChallengeRequests(username);

        QuizDAO quizDao = (QuizDAO) request.getServletContext().getAttribute("quizDao");
        List<Quiz> quizzes = quizDao.getAll();
        List<Quiz> recent_quizzes = quizzes.stream().limit(5).collect(Collectors.toList());

        UserDAO userDao = (UserDAO) request.getServletContext().getAttribute("userDao");

        List<Quiz> own_quizzes = quizDao.getAllByCreator(currentUser.getId());
        List<Quiz> recent_own_quizzes = own_quizzes.stream().limit(5).collect(Collectors.toList());

        List<Quiz> popular_quizzes = quizDao.getPopularQuizzes();
    %>


    <div id = "logout-div">
        <form action="<%=Endpoints.LOGOUT%>" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>
    <div class = "container">

        <div class="column">
            <div>
                <input type="text" id="search-input" placeholder="Username">
                <button id="search-btn">Search</button>
                <div id="search-result"></div>
            </div>
            <div class = "main-content flash-card">
                <h2>Notes:</h2>
                 <span class = "dropdown-icon">&#9650;</span>
                 <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (NoteMessage note : notes) { %>
                            <li>
                                <p><%=note.getSender()%>: <%= note.getNote() %></p>
                            </li>
                        <% }; %>
                    </ul>
                </div>
            </div>

            <div class = "main-content flash-card">
                <h2>Challenge Requests:</h2>
                 <span class = "dropdown-icon">&#9650;</span>
                 <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (ChallengeRequest challengeRequest : challengeRequests) { %>
                        <li>
                            <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=challengeRequest.getQuizId()%>"><%=challengeRequest.getQuizId()%></a>
                        </li>
                        <% }; %>
                    </ul>
                </div>
            </div>

            <div class = "main-content flash-card">
                <h2>Friends:</h2>
                 <span class = "dropdown-icon">&#9650;</span>
                 <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (String friend : friendList) { %>
                        <li>
                            <a href="/secured/user?username=<%= friend %>"><%= friend %></a>
                            <form action="<%=Endpoints.REMOVE_FRIEND%>" method="post">
                                <input type="hidden" name="friendToRemove" value="<%= friend %>">
                                <button type="submit">Remove</button>
                            </form>
                        </li>
                        <% }; %>
                    </ul>
                </div>
            </div>

            <div class = "main-content flash-card">
                <h2>Friend Requests:</h2>
                 <span class = "dropdown-icon">&#9650;</span>
                 <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (FriendRequest fr : friendRequests) { %>
                            <li>
                                <a href="/secured/user?username=<%= fr.getSender() %>"><%= fr.getSender() %></a>
                                <form action="<%=Endpoints.ADD_FRIEND%>" method="post">
                                    <input type="hidden" name="sender" value="<%= fr.getSender() %>">
                                    <button type="submit">Accept</button>
                                </form>
                                <form action="<%=Endpoints.REJECT_FRIEND_REQUEST%>" method="post">
                                    <input type="hidden" name="sender" value="<%= fr.getSender() %>">
                                    <button type="submit">Reject</button>
                                </form>
                            </li>
                        <% }; %>
                    </ul>
                </div>
            </div>
        </div>
        <div class = "column">
                <div id ="make-quiz-button-div">
                  <form action="<%=Endpoints.MAKE_QUIZ%>" method="get">
                      <button type="submit">make new quiz</button>
                  </form>
                </div>
            <% if (!own_quizzes.isEmpty()) { %>
                 <div class = "main-content flash-card">
                    <h2>Own quizzes:</h2>
                    <span class = "dropdown-icon">&#9650;</span>
                    <div class = "sub-content" style="display: block;">
                        <ul>
                            <% for (Quiz quiz : recent_own_quizzes) { %>
                            <li>
                                <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
                            </li>
                            <% }; %>
                        </ul>
                    </div>
                 </div>
            <% } %>

            <div class = "main-content flash-card">
                <h2>Popular quizzes:</h2>
                <span class = "dropdown-icon">&#9650;</span>
                <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (Quiz quiz : popular_quizzes) { %>
                        <li>
                            <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
                        </li>
                        <% }; %>
                    </ul>
                </div>
            </div>

            <div class = "main-content flash-card">
                <h2>Recently created quizzes:</h2>
                <span class = "dropdown-icon">&#9650;</span>
                <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (Quiz quiz : recent_quizzes) { %>
                        <li>
                            <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
                        </li>
                        <% }; %>
                    </ul>
                </div>
            </div>

            <div class = "main-content flash-card">
                <h2>Friend Activities:</h2>
                 <span class = "dropdown-icon" >&#9650;</span>
                 <div class = "sub-content" style="display: block;">
                    <ul>
                        <% for (Quiz quiz : recent_quizzes) { %>
                        <li>
                            <a href="<%=Endpoints.TAKE_QUIZ%>?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
                        </li>
                        <% }; %>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <script>
     var spans = document.querySelectorAll('.dropdown-icon');

     for (var i = 0; i < spans.length; i++) {
      spans[i].addEventListener('click', function(event) {
         toggleSubelements(event.currentTarget);
         event.stopPropagation();
     });
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
            position: relative;
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

        input[type="text"]{
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            width: 300px;
        }

        input[type="text"]:focus{
            border-color: #3e73b3;
            box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.5);
        }

        input[type="text"]:hover{
            border-color: #2a43b0;
        }
        .centered {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .main-content {
            position: relative;
            border: 1px solid #3e73b3;
            padding: 10px;
            margin: 10px;
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
            position: absolute;
              top: 0;
              left: 0;
              padding: 5px;
              cursor: pointer;
        }
         .column {
           width: 49%;
           display: inline-block;
           vertical-align: top;
           padding: 10px;
           box-sizing: border-box;
         }
         #logout-div {
            position: absolute;
            top: 0;
            right: 0;
         }
    </style>
</body>
</html>
