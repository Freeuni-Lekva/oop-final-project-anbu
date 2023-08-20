package quizapp.history;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.managers.HistoryManager;
import quizapp.models.domain.User;
import quizapp.settings.JSP;
import quizapp.settings.Services;

import java.io.IOException;

@WebServlet(name = "historyServlet", value = "/secured/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP.HISTORY).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HistoryManager historyManager = (HistoryManager) req.getServletContext().getAttribute(Services.HISTORY_MANAGER);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String username = user.getUsername();
        int quiz_id = Integer.parseInt(req.getParameter("quiz_id"));
        int score = Integer.parseInt(req.getParameter("score"));
        int completion_time = Integer.parseInt(req.getParameter("completion_time"));

        historyManager.addEntry(username, quiz_id, score, completion_time);
    }
}
