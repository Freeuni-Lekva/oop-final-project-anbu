package quizapp.models.questions;

import java.util.List;

public class MultipleChoice extends Question {

    public MultipleChoice( String questionText) {
        super(QuestionType.MULTIPLE_CHOICE, questionText);
    }

    public MultipleChoice(int questionId, int quizId, String questionText) {
        super(questionId, quizId, QuestionType.MULTIPLE_CHOICE, questionText);
    }

    @Override
    public String renderQuestionHTML() {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<div>");
        htmlBuilder.append("<p>").append(questionText).append("</p>");
        for (int i = 0; i < answerList.size(); i++) {
            String choice = answerList.get(i).getAnswerText();
            htmlBuilder.append("<input type=\"radio\" name=\"answer\" value=\"")
                    .append(i).append("\" id=\"option").append(i).append("\">")
                    .append("<label for=\"option").append(i).append("\">")
                    .append(choice).append("</label><br>");
        }
        htmlBuilder.append("</div>");

        return htmlBuilder.toString();
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && answerList.contains(new Answer(userAnswer, true));
    }
}
