package quizapp.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import quizapp.managers.ChallengeManager;
import quizapp.managers.FriendManager;
import quizapp.managers.NoteManager;
import quizapp.models.dao.UserDAO;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userDao", new UserDAO());
        sce.getServletContext().setAttribute("friendManager", new FriendManager());
        sce.getServletContext().setAttribute("challengeManager", new ChallengeManager());
        sce.getServletContext().setAttribute("noteManager", new NoteManager());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.uncheckedShutdown();
    }
}
