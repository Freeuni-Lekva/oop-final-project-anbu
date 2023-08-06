package quizapp.models.questions;

public class QuestionResponse extends Question {

    public QuestionResponse(String question) {
        super(QuestionType.QUESTION_RESPONSE, question);
    }

    public QuestionResponse(int questionId, int quizId, String question) {
        super(questionId, quizId, QuestionType.QUESTION_RESPONSE, question);
    }

    @Override
    public String renderQuestionHTML() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div>");
        htmlBuilder.append("<p>").append(questionText).append("</p>");
        htmlBuilder.append("<input type=\"text\" name=\"response\" required>");
        htmlBuilder.append("</div>");
        return htmlBuilder.toString();
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && answerList.contains(new Answer(userAnswer, true));
    }
}
