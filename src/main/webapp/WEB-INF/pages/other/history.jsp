<%@ page import="quizapp.managers.HistoryManager" %>
<%@ page import="quizapp.settings.Services" %>
<%@ page import="java.util.List" %>
<%@ page import="quizapp.models.domain.QuizHistory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HistoryManager historyManager = (HistoryManager) request.getServletContext().getAttribute(Services.HISTORY_MANAGER);
    List<QuizHistory> entries = historyManager.getAllEntries();
%>

<html>
<head>
    <title>Quiz History</title>
</head>
<body>
    <% for (QuizHistory entry : entries) { %>
        <p>
            User <a href="/secured/user?username=<%=entry.getUsername()%>"><%=entry.getUsername()%></a> completed quiz <a href="/Quiz/quizWelcomePage.jsp?quizId=<%=entry.get_quiz_id()%>"><%=entry.get_quiz_name()%></a> in <%=entry.get_completion_time()%> seconds with the score of <%= entry.getScore() %>.
        </p>
    <% } %>
</body>
</html>
