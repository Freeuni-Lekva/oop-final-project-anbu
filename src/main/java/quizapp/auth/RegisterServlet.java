package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import utils.HashService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "registerServlet", value = "/auth/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO dao = (UserDAO) req.getServletContext().getAttribute("userDao");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (dao.checkUser(username).isPresent()) {
            req.setAttribute("alreadyExists", username);
            req.getRequestDispatcher("/Auth/register.jsp").forward(req, resp);
        } else {
            User user = new User();

            user.setUsername(username);
            user.setPasswordHash(HashService.hash(password));

            dao.save(user);

            Optional<User> newUser = dao.getByUsername(username);

            if (newUser.isPresent()) {
                User createdUser = newUser.get();

                HttpSession session = req.getSession(true);
                session.setAttribute("user", createdUser);
                session.setAttribute("AUTHENTICATED", true);
            } else {
                req.getRequestDispatcher("/Auth/registerError.jsp").forward(req, resp);
                return;
            }

            resp.sendRedirect("/secured/homepage");
        }
    }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session != null) {
            if ((boolean) session.getAttribute("AUTHENTICATED")) {
                resp.sendRedirect("/secured/homepage");
                return;
            }
        }

        req.getRequestDispatcher("/Auth/register.jsp").forward(req, resp);
    }
}
