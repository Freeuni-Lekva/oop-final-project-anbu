package quizapp.search;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import quizapp.settings.Endpoints;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "searchServlet", value = Endpoints.SEARCH)
public class searchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String search_username = req.getParameter("search_username");

        UserDAO userDao = (UserDAO) req.getServletContext().getAttribute("userDao");
        Optional<User> search_user_optional = userDao.getByUsername(search_username);

        resp.setContentType("text/html");

        if (search_user_optional.isEmpty()) {
            resp.getWriter().write("<p>User with given username was not found.<p>");
            return;
        }

        HttpSession session = req.getSession(false);
        User current_user = (User) session.getAttribute("user");
        String current_username = current_user.getUsername();

        if (current_username.equals(search_username)) {
            resp.getWriter().write("<p>Given username belongs to you.<p>");
            return;
        }

        resp.getWriter().write(String.format("<a href=\"/secured/user?username=%s\">%s</a>", search_username, search_username));
    }
}
