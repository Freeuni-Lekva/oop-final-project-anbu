package quizapp.models.questions;

import java.util.List;

public class MultipleChoice extends Question {

    public MultipleChoice(String questionText) {
        super(QuestionType.MULTIPLE_CHOICE, questionText);
    }

    public MultipleChoice(int questionId, int quizId, String questionText) {
        super(questionId, quizId, QuestionType.MULTIPLE_CHOICE, questionText);
    }

    @Override
    public String renderQuestionHTML(int questionIndex) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div class = \"question\">");
        htmlBuilder.append("<p>").append(questionIndex).append(". ").append(questionText).append("</p>");
        htmlBuilder.append("</div>");
        htmlBuilder.append("<div class = \"answer-input radio-input\">");
        for (int i = 0; i < answerList.size(); i++) {
            int radioId = Integer.valueOf(String.valueOf(i) + String.valueOf(questionIndex) );
            String choice = answerList.get(i).getAnswerText();

            htmlBuilder.append("<label class = \"radio-option\">");
            htmlBuilder.append("<input type=\"radio\" name=\"").append(questionId).append("\" value=\"")
                    .append(choice).append("\" id=\"").append(radioId).append("\">");
            htmlBuilder.append("<span class=\"radio-style\">");
            htmlBuilder.append("</span>");
            htmlBuilder.append(choice);
            htmlBuilder.append("</label><br>");
        }
        htmlBuilder.append("</div>");
        return htmlBuilder.toString();
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && answerList.contains(new Answer(userAnswer, true));
    }
}
