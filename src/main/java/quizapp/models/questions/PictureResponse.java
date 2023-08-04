package quizapp.models.questions;

public class PictureResponse implements Question {
    private int id;
    private int quizId;
    private final QuestionType questionType = QuestionType.PICTURE_RESPONSE;
    private final String pictureUrl;
    private final String question;
    private final Answer correctAnswer;

    public PictureResponse(String pictureUrl, String question, Answer correctAnswer) {
        this.pictureUrl = pictureUrl;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public PictureResponse(int id, int quizId, String pictureUrl, String question, Answer correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.pictureUrl = pictureUrl;
        this.question = question;
        this.correctAnswer = correctAnswer;
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
        htmlBuilder.append("<img src=\"").append(pictureUrl).append("\" alt=\"Image\">");
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

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && userAnswer.equals(correctAnswer.getAnswerText());
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Answer getAnswer() {
        return correctAnswer;
    }
}
