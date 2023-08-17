package quizapp.search;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizapp.settings.Endpoints;
import quizapp.settings.JSP;

import java.io.IOException;

@WebServlet(name = "userServlet", value = Endpoints.USER)
public class userServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP.USER).forward(req, resp);
    }
}
