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
    public String renderQuestionHTML(int questionIndex) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<p>").append(questionIndex).append(". ").append(questionText).append("</p>");
        htmlBuilder.append("<input type=\"text\" name=\"answer").append(questionIndex).append("\" id = \"").append(questionIndex).append("\">");
        return htmlBuilder.toString();
    }

}
