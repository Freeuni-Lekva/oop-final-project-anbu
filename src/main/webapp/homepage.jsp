<%@ page import="quizapp.managers.NoteManager" %>
<%@ page import="quizapp.models.domain.message.NoteMessage" %>
<%@ page import="java.util.List" %>
<%@ page import="quizapp.models.domain.User" %><%--
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
    %>
    <h2>Notes:</h2><br>
    <ul>
        <% for (NoteMessage note : notes) { %>
            <li>
                <p><%= note.getNote() %></p>
            </li>
        <% }; %>
    </ul>
    <form action="/auth/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</body>
</html>
