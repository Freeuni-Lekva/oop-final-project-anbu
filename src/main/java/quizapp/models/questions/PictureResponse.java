package quizapp.models.questions;

public class PictureResponse extends Question {

    private String pictureUrl;

    public PictureResponse(String pictureUrl, String questionText) {
        super(QuestionType.PICTURE_RESPONSE, questionText);
        this.pictureUrl = pictureUrl;
    }

    public PictureResponse(int questionId, int quizId, String pictureUrl, String questionText) {
        super(questionId,quizId, QuestionType.PICTURE_RESPONSE, questionText);
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String renderQuestionHTML(int questionIndex) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<p>").append(questionIndex).append(". ").append(questionText).append("</p>");
        htmlBuilder.append("<img src=\"").append(pictureUrl).append("\" alt=\"Image\">");
        htmlBuilder.append("<input type=\"text\" name=\"answer").append(questionIndex).append("\" id = \"").append(questionIndex).append("\">");
        return htmlBuilder.toString();
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && answerList.contains(new Answer(userAnswer, true));
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String newUrl){
        this.pictureUrl = newUrl;
    }
}
