package quizapp.models.domain.message;

public enum MessageType {
    FRIEND,
    CHALLENGE,
    NOTE;

    public static MessageType resolve(String value) {
        switch (value) {
            case "friend": return FRIEND;
            case "challenge": return CHALLENGE;
            case "note": return NOTE;
            default: {
                return null;
            }
        }
    }
}
