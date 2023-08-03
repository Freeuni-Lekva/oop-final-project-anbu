package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;

import java.io.IOException;
import java.util.Optional;

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

            req.getRequestDispatcher("/Auth/welcome.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/Auth/invalidCredentials.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Auth/login.jsp").forward(req, resp);
    }
}
