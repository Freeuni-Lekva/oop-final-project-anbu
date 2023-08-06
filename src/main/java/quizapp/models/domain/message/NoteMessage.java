package quizapp.models.domain.message;

import quizapp.models.domain.User;

public class NoteMessage  extends Message {
    private String note;

    public NoteMessage(String sender, String receiver, String note) {
        super(sender, receiver, MessageType.NOTE);
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }
}
