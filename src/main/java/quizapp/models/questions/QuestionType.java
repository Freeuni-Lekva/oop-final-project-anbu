package quizapp.models.questions;

public enum QuestionType {
    QUESTION_RESPONSE("Question-Response"),
    FILL_IN_THE_BLANK("Fill in the Blank"),
    MULTIPLE_CHOICE("Multiple Choice"),
    PICTURE_RESPONSE("Picture-Response");

    private final String value;

    QuestionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionType fromValue(String value) {
        for (QuestionType type : QuestionType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid question type: " + value);
    }
}
