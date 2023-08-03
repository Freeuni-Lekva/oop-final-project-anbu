package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import utils.HashService;

import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/auth/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO dao = (UserDAO) req.getServletContext().getAttribute("userDao");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        req.setAttribute("username", username);

        if (dao.checkUser(username).isPresent()) {
            req.getRequestDispatcher("/Auth/usernameAlreadyExists.jsp").forward(req, resp);
        } else {
            User user = new User();

            user.setUsername(username);
            user.setPasswordHash(HashService.hash(password));

            dao.save(user);

            req.getRequestDispatcher("/Auth/welcome.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Auth/register.jsp").forward(req, resp);
    }
}
