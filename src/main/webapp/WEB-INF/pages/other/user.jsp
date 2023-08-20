<%@ page import="quizapp.managers.FriendManager" %>
<%@ page import="java.util.List" %>
<%@ page import="quizapp.models.domain.User" %>
<%@ page import="quizapp.settings.Endpoints" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = request.getParameter("username");
    User current_user = (User) session.getAttribute("user");
    String current_username = current_user.getUsername();

    boolean isCurrentUser = current_username.equals(username);
    FriendManager friendManager = (FriendManager) request.getServletContext().getAttribute("friendManager");
    boolean areFriends = friendManager.areFriends(current_username, username);
    boolean friendRequestIsSent = friendManager.friendRequestIsSent(current_username, username);
    boolean friendRequestIsReceived = friendManager.friendRequestIsSent(username, current_username);
%>

<html>
<head>
    <title>User - <%=username%></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#add-friend-btn").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.MESSAGE%>',
                    data: {
                        sender: '<%=current_username%>',
                        receiver: '<%=username%>',
                        messageType: "friend"
                    },
                    success: function(response) {
                        location.reload();
                    }
                })
            })

            $("#accept-friend-request").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.ADD_FRIEND%>',
                    data: {
                        sender: '<%=username%>'
                    },
                    success: function(response) {
                        location.reload()
                    }
                })
            })

            $("#reject-friend-request").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.REJECT_FRIEND_REQUEST%>',
                    data: {
                        sender: '<%=username%>'
                    },
                    success: function(response) {
                        location.reload()
                    }
                })
            })

            $("#send-note-btn").click(function () {
                $.ajax({
                    type: "POST",
                    url: '<%=Endpoints.MESSAGE%>',
                    data: {
                        sender: '<%=current_username%>',
                        receiver: '<%=username%>',
                        messageType: 'note',
                        note: $('#note-input').val()
                    },
                    success: function(response) {
                        $('#note-input').val('');
                    }
                })
            })

        })
    </script>
</head>
<body>
    <% if (!isCurrentUser) { %>
        <% if (!areFriends) { %>
            <% if (friendRequestIsSent) { %>
                <p>Friend request is already sent.</p>
            <% } else if (friendRequestIsReceived) { %>
                <button id="accept-friend-request">Accept</button>
                <button id="reject-friend-request">Reject</button>
            <% } else { %>
                <button id="add-friend-btn">Add Friend</button>
            <% } %>
        <% } else { %>
            <p>You are already friends.</p>
        <% } %>

        <input id="note-input" type="text" placeholder="Note Message"><button id="send-note-btn">Send Message</button>
    <% } %>
</body>
</html>
