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
    public String renderQuestionHTML(int questionIndex) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<p>").append(questionIndex).append(". ").append(questionText).append("</p>");
        for (int i = 0; i < answerList.size(); i++) {
            int radioId = Integer.valueOf(String.valueOf(i) + String.valueOf(questionIndex));
            String choice = answerList.get(i).getAnswerText();
            htmlBuilder.append("<input type=\"radio\" name=\"answer").append(questionIndex).append("\" value=\"")
                    .append(radioId).append("\" id=\"option").append(radioId).append("\">")
                    .append("<label for=\"option").append(radioId).append("\">")
                    .append(choice).append("</label><br>");
        }
        return htmlBuilder.toString();
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && answerList.contains(new Answer(userAnswer, true));
    }
}
