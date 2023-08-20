package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizapp.settings.Endpoints;
import quizapp.settings.JSP;

import java.io.IOException;

@WebServlet(name = "logoutServlet", value = Endpoints.LOGOUT)
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(false).invalidate();
        req.getRequestDispatcher(JSP.LOGIN).forward(req, resp);
    }
}
