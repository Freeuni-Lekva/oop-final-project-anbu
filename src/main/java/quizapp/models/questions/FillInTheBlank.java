package quizapp.models.questions;

public class FillInTheBlank implements Question {
    private int id;
    private int quizId;
    private final QuestionType questionType = QuestionType.FILL_IN_THE_BLANK;
    private final String question;
    private final Answer correctAnswer;

    public FillInTheBlank(String question, Answer correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public FillInTheBlank(int id, int quizId, String question, Answer correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && userAnswer.equals(correctAnswer.getAnswerText());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getQuizId() {
        return quizId;
    }

    @Override
    public void setQuizId(int id) {
        this.quizId = id;
    }

    @Override
    public String renderQuestionHTML() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div>");
        htmlBuilder.append("<p>").append(question).append("</p>");
        htmlBuilder.append("<input type=\"text\" name=\"answer\" />");
        htmlBuilder.append("</div>");
        return htmlBuilder.toString();
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public String getQuestionText() {
        return question;
    }

    public Answer getAnswer() {
        return correctAnswer;
    }
}
