package quizapp.mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.managers.FriendManager;
import quizapp.models.domain.User;

import java.io.IOException;

@WebServlet(name = "rejectFriendRequestServlet", value = "/rejectfriendrequest")
public class RejectFriendRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        FriendManager friendManager = (FriendManager) req.getServletContext().getAttribute("friendManager");

        String sender = req.getParameter("sender");
        String receiver = currentUser.getUsername();

        friendManager.removeFriendRequest(sender, receiver);

        resp.sendRedirect("/secured/homepage");
    }
}
