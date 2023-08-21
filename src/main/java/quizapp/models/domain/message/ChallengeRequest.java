package quizapp.models.domain.message;

public class ChallengeRequest extends Message {
    private final int quiz_id;
    private final int score;

    public ChallengeRequest(String sender, String receiver, int quiz_id, int score) {
        super(sender, receiver, MessageType.CHALLENGE);
        this.quiz_id = quiz_id;
        this.score = score;
    }

    public int getQuizId() {
        return this.quiz_id;
    }

    public int getScore() {return this.score;}
}
