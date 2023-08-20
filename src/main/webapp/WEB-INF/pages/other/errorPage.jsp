<%@ page import="quizapp.settings.Endpoints" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
    <h1>Unexpected Error</h1>
    <p> <%= (String) request.getAttribute("error_message") %></p>
    <a href="<%=Endpoints.HOMEPAGE%>">go back to homepage</a>
    <style>
        body{
            font-family: Sans-Serif;
        }
    </style>
</body>
</html>
