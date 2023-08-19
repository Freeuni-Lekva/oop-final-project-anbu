package quizapp.mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.managers.FriendManager;
import quizapp.models.domain.User;
import quizapp.settings.Endpoints;

import java.io.IOException;

/* rejectFriendRequestServlet, accepts only POST request, handles rejecting friend request functionality */
@WebServlet(name = "rejectFriendRequestServlet", value = Endpoints.REJECT_FRIEND_REQUEST)
public class RejectFriendRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        FriendManager friendManager = (FriendManager) req.getServletContext().getAttribute("friendManager");

        String sender = req.getParameter("sender");
        String receiver = currentUser.getUsername();

        friendManager.removeFriendRequest(sender, receiver);

        resp.sendRedirect(Endpoints.HOMEPAGE);
    }
}
