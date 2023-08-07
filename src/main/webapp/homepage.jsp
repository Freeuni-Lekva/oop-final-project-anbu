<%@ page import="quizapp.managers.NoteManager" %>
<%@ page import="quizapp.models.domain.message.NoteMessage" %>
<%@ page import="java.util.List" %>
<%@ page import="quizapp.models.domain.User" %>
<%@ page import="quizapp.managers.FriendManager" %>
<%@ page import="quizapp.models.domain.message.FriendRequest" %>
<%@ page import="quizapp.managers.ChallengeManager" %>
<%@ page import="quizapp.models.domain.message.ChallengeRequest" %><%--
  Created by IntelliJ IDEA.
  User: b3tameche
  Date: 8/6/23
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    User currentUser = (User) request.getSession().getAttribute("user");
    String username = currentUser.getUsername();
%>
<head>
    <title>Welcome <%= username %></title>
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
    %>

    <h2>Notes:</h2><br>
    <ul>
        <% for (NoteMessage note : notes) { %>
            <li>
                <p><%= note.getNote() %></p>
            </li>
        <% }; %>
    </ul>

    <h2>Challenge Requests:</h2><br>
    <ul>
        <% for (ChallengeRequest challengeRequest : challengeRequests) { %>
        <li>
            <p><%= challengeRequest.getQuizId() %></p>
        </li>
        <% }; %>
    </ul>

    <h2>Friends:</h2><br>
    <ul>
        <% for (String friend : friendList) { %>
        <li>
            <p><%= friend %></p>
            <form action="/removefriend" method="post">
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
                <p><%= fr.getSender() %></p>
            </li>
            <form action="/addfriend" method="post">
                <input type="hidden" name="sender" value="<%= fr.getSender() %>">
                <button type="submit">Accept</button>
            </form>
            <form action="/rejectfriendrequest" method="post">
                <input type="hidden" name="sender" value="<%= fr.getSender() %>">
                <button type="submit">Reject</button>
            </form>
        <% }; %>
    </ul>

    <form action="/auth/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</body>
</html>
