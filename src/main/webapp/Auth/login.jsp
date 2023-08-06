<%--
  Created by IntelliJ IDEA.
  User: atsin21
  Date: 8/3/23
  Time: 12:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <h1>Login page</h1>
    <p>Plaase log in</p>

    <%
        Object invalidCredentials = request.getAttribute("invalidCredentials");
        boolean invalidCredentialsFlag = true;
        if (invalidCredentials == null) {
            invalidCredentialsFlag = false;
        }
    %>
    <% if (invalidCredentialsFlag) { %>
        <p style="color: red">invalid credentials</p>
    <% }; %>

    <form action="/auth/login" method="post">
        <label for="userinput"></label>
        <input type="text" id="userinput" name="username">

        <label for="passinput"></label>
        <input type="text" id="passinput" name="password">
        <button type="submit">Login</button>
    </form>

    <a href="register">Create New Account</a>
</body>
</html>
