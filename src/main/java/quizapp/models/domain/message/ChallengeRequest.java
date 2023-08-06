package quizapp.models.domain.message;

import quizapp.models.domain.User;
import quizapp.models.questions.Quiz;

public class ChallengeRequest extends Message {

    private String quiz_id;

    public ChallengeRequest(String sender, String receiver, String quiz_id) {
        super(sender, receiver, MessageType.CHALLENGE);
        this.quiz_id = quiz_id;
    }

    public String getQuizId() {
        return this.quiz_id;
    }
}
