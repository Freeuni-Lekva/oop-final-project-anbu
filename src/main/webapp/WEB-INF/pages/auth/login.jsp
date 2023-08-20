<%@ page import="quizapp.settings.Endpoints" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <div class = "centered content">

    <h1>Login page</h1>
    <p>Please log in</p>

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

    <form action="<%=Endpoints.LOGIN%>" method="post">
        <div class = "flash-card">
            <label for="userinput">name:</label>
            <input type="text" id="userinput" name="username">

            <label for="passinput">password:</label>
            <input type="password" id="passinput" name="password">
        </div>
        <div class = "divider"></div>
        <button type="submit">Login</button>
    </form>

    <a href="<%=Endpoints.REGISTER%>">Create New Account</a>
    </div>
     <style>
            body{
                font-family: Sans-Serif;
            }
            .middle-container{
                width: 100%;
                max-width: 100%;
            }
            .flash-card{
                background-color: #f1f1f1;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            }
            input[type="text"],
             input[type="password"]{
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                width: 300px;
            }

            input[type="text"]:focus,
            input[type="password"]:focus
             {
                border-color: #3e73b3;
                box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.5);
            }

            input[type="text"]:hover,
             input[type="password"]:hover{
                border-color: #2a43b0;
            }

            button {
                font-size: 1.5rem;
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
            label {
                font-weight: bold;
                display: block;
                margin-bottom: 10px;
            }
            .divider {
                margin-top: 20px;
                padding: 10px;
            }
             .centered {
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .content {
              display: flex;
              flex-direction: column;
              text-align: center;
            }
            </style>
</body>
</html>
