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

@WebServlet(name = "removeFriendServlet", value = Endpoints.REMOVE_FRIEND)
public class RemoveFriendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        String username_1 = currentUser.getUsername();
        String username_2 = req.getParameter("friendToRemove");

        FriendManager friendManager = (FriendManager) req.getServletContext().getAttribute("friendManager");
        friendManager.removeFriends(username_1, username_2);

        resp.sendRedirect(Endpoints.HOMEPAGE);
    }
}
