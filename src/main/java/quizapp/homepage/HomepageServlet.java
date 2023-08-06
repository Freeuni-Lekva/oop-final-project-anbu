package quizapp.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "homepageServlet", value = "/secured/homepage")
public class HomepageServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || !(boolean) session.getAttribute("AUTHENTICATED")) {
            resp.sendRedirect("/auth/login");
        }

        req.getRequestDispatcher("/homepage.jsp").forward(req, resp);
    }
}
