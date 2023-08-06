package quizapp.models.questions;

import java.util.ArrayList;
import java.util.List;

public class FillInTheBlank extends Question {


    public FillInTheBlank(String question) {
        super(QuestionType.FILL_IN_THE_BLANK, question);
    }

    public FillInTheBlank(int questionId, int quizId, String question) {
        super(questionId, quizId, QuestionType.FILL_IN_THE_BLANK, question);
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        return userAnswer != null && answerList.contains(new Answer(userAnswer,true));
    }

    @Override
    public String renderQuestionHTML() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div>");
        htmlBuilder.append("<p>").append(questionText).append("</p>");
        htmlBuilder.append("<input type=\"text\" name=\"answer\" />");
        htmlBuilder.append("</div>");
        return htmlBuilder.toString();
    }

}
