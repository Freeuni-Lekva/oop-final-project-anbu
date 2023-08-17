package quizapp.models.domain.message;

public class FriendRequest extends Message {
    public FriendRequest(String sender, String receiver) {
        super(sender, receiver, MessageType.FRIEND);
    }
}
