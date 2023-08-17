package quizapp.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.settings.Endpoints;
import quizapp.settings.JSP;

import java.io.IOException;

@WebServlet(name = "homepageServlet", value = Endpoints.HOMEPAGE)
public class HomepageServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || !(boolean) session.getAttribute("AUTHENTICATED")) {
            resp.sendRedirect(Endpoints.LOGIN);
        }

        req.getRequestDispatcher(JSP.HOMEPAGE).forward(req, resp);
    }
}
