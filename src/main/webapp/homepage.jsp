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
    %>

    <div>
        <input type="text" id="search-input" placeholder="Username">
        <button id="search-btn">Search</button>
        <div id="search-result"></div>
    </div>

    <% if (!own_quizzes.isEmpty()) { %>
        <h2>Own quizzes:</h2><br>
        <ul>
            <% for (Quiz quiz : recent_own_quizzes) { %>
            <li>
                <a href="/Quiz/quizWelcomePage.jsp?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
            </li>
            <% }; %>
        </ul>
    <% } %>

    <h2>Recently created quizzes:</h2><br>
    <ul>
        <% for (Quiz quiz : recent_quizzes) { %>
        <li>
            <a href="/Quiz/quizWelcomePage.jsp?quizId=<%=quiz.getQuizId()%>"><%=quiz.getQuizName()%></a>
        </li>
        <% }; %>
    </ul>

    <h2>Notes:</h2><br>
    <ul>
        <% for (NoteMessage note : notes) { %>
            <li>
                <p><%=note.getSender()%>: <%= note.getNote() %></p>
            </li>
        <% }; %>
    </ul>

    <h2>Challenge Requests:</h2><br>
    <ul>
        <% for (ChallengeRequest challengeRequest : challengeRequests) { %>
        <li>
            <a href="/Quiz/quizWelcomePage.jsp?quizId=<%=challengeRequest.getQuizId()%>"><%=challengeRequest.getQuizId()%></a>
        </li>
        <% }; %>
    </ul>

    <h2>Friends:</h2><br>
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

    <h2>Friend Requests:</h2>
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

    <form action="<%=Endpoints.LOGOUT%>" method="post">
        <button type="submit">Logout</button>
    </form>
</body>
</html>
