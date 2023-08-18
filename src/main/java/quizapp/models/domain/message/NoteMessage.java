package quizapp.models.domain.message;

public class NoteMessage  extends Message {
    private final String note;

    public NoteMessage(String sender, String receiver, String note) {
        super(sender, receiver, MessageType.NOTE);
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }
}
