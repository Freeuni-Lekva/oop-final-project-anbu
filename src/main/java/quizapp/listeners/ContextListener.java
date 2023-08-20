package quizapp.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import quizapp.managers.ChallengeManager;
import quizapp.managers.FriendManager;
import quizapp.managers.NoteManager;
import quizapp.managers.HistoryManager;
import quizapp.models.dao.QuizDAO;
import quizapp.models.dao.UserDAO;
import quizapp.settings.Services;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(Services.USER_DAO, new UserDAO());
        sce.getServletContext().setAttribute(Services.QUIZ_DAO, new QuizDAO());

        sce.getServletContext().setAttribute(Services.FRIEND_MANAGER, new FriendManager());
        sce.getServletContext().setAttribute(Services.CHALLENGE_MANAGER, new ChallengeManager());
        sce.getServletContext().setAttribute(Services.NOTE_MANAGER, new NoteManager());
	sce.getServletContext().setAttribute(Services.HISTORY_MANAGER, new HistoryManager());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.uncheckedShutdown();
    }
}
