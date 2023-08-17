package quizapp.mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.managers.ChallengeManager;
import quizapp.managers.FriendManager;
import quizapp.managers.NoteManager;
import quizapp.models.domain.User;
import quizapp.models.domain.message.Message;
import quizapp.models.domain.message.MessageType;
import quizapp.models.domain.message.NoteMessage;

import java.io.IOException;

@WebServlet(name = "messageServlet", value = "/secured/message")
public class MessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sender = req.getParameter("sender");
        String receiver = req.getParameter("receiver");
        MessageType messageType = MessageType.resolve(req.getParameter("messageType"));

        if (messageType == null) {
            throw new RuntimeException("no message type was provided");
        }

        switch (messageType) {

            case FRIEND: {
                FriendManager friendManager = (FriendManager) req.getServletContext().getAttribute("friendManager");
                friendManager.makeFriendRequest(sender, receiver);
                break;
            }

            case NOTE: {
                NoteManager noteManager = (NoteManager) req.getServletContext().getAttribute("noteManager");
                String note = req.getParameter("note");
                noteManager.sendNote(sender, receiver, note);
                break;
            }

            case CHALLENGE: {
                ChallengeManager challengeManager = (ChallengeManager) req.getServletContext().getAttribute("challengeManager");
                int quiz_id = Integer.parseInt(req.getParameter("challenged_quiz_id"));
                challengeManager.sendChallenge(sender, receiver, quiz_id);
                break;
            }

            default: throw new RuntimeException("illegal message type");
        }

    }
}
