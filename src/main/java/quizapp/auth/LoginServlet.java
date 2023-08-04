package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/auth/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO dao = (UserDAO) req.getServletContext().getAttribute("userDao");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (dao.checkCredentials(username, password)) {
            req.setAttribute("username", username);
            req.setAttribute("password", password);

            User user = dao.getByUsername(username).get();

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("AUTHENTICATED", true);

            req.getRequestDispatcher("/Auth/welcome.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/Auth/invalidCredentials.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        boolean isAuthenticated = (boolean) session.getAttribute("AUTHENTICATED");

        if (isAuthenticated) {
            req.getRequestDispatcher("/Auth/welcome.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher("/Auth/login.jsp").forward(req, resp);
    }
}
