package quizapp.models.domain.message;

public class ChallengeRequest extends Message {
    private final int quiz_id;

    public ChallengeRequest(String sender, String receiver, int quiz_id) {
        super(sender, receiver, MessageType.CHALLENGE);
        this.quiz_id = quiz_id;
    }

    public int getQuizId() {
        return this.quiz_id;
    }
}
