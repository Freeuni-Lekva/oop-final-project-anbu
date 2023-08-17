<%--
  Created by IntelliJ IDEA.
  User: atsin21
  Date: 8/3/23
  Time: 9:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Register</h1>

    <p>Please enter username and password to register</p>

    <form action="/auth/register" method="post">
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

    <a href="login">Already have an account? Login</a>
</body>
</html>
