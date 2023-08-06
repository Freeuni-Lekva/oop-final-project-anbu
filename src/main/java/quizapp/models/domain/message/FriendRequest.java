package quizapp.models.domain.message;

import quizapp.models.domain.User;

public class FriendRequest extends Message {
    public FriendRequest(String sender, String receiver) {
        super(sender, receiver, MessageType.FRIEND);
    }
}
