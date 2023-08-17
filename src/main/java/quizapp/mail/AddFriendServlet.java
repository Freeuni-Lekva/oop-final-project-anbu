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

@WebServlet(name = "addFriendServlet", value = Endpoints.ADD_FRIEND)
public class AddFriendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FriendManager friendManager = (FriendManager) req.getServletContext().getAttribute("friendManager");

        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        String receiver = currentUser.getUsername();
        String sender = req.getParameter("sender");

        friendManager.makeFriends(sender, receiver);

        resp.sendRedirect(Endpoints.HOMEPAGE);
    }
}
