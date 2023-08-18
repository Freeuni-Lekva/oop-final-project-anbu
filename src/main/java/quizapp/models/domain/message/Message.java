package quizapp.models.domain.message;

import java.sql.Timestamp;

public abstract class Message {
    private int id;
    protected String sender;
    protected String receiver;
    protected MessageType type;
    protected Timestamp timestamp;

    public Message(String sender, String receiver, MessageType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
