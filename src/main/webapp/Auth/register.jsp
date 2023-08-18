<%@ page import="quizapp.settings.Endpoints" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Register</h1>

    <p>Please enter username and password to register</p>

    <form action="<%=Endpoints.REGISTER%>" method="post">
        <label for="userinput"></label>
        <input type="text" id="userinput" name="username" required><br><br>

        <% Object usernameExists = request.getAttribute("alreadyExists"); %>
        <% if (usernameExists != null) { %>
            <p style="color: red">username <%= (String) usernameExists %> already exists</p>
        <% }; %>

        <label for="passinput"></label>
        <input type="text" id="passinput" name="password" required>
        <button type="submit">Register</button>
    </form>

    <a href="<%=Endpoints.LOGIN%>">Already have an account? Login</a>
</body>
</html>
