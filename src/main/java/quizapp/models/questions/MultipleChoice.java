package quizapp.models.questions;

import java.util.List;

public class MultipleChoice implements Question {
    private int id;
    private int quizId;
    private final QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
    private final String question;
    private final List<Answer> choices;

    public MultipleChoice(String question, List<Answer> choices) {
        this.question = question;
        this.choices = choices;
    }

    public MultipleChoice(int id, int quizId, String question, List<Answer> choices) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.choices = choices;
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
        for (int i = 0; i < choices.size(); i++) {
            String choice = choices.get(i).getAnswerText();
            htmlBuilder.append("<input type=\"radio\" name=\"answer\" value=\"")
                    .append(i).append("\" id=\"option").append(i).append("\">")
                    .append("<label for=\"option").append(i).append("\">")
                    .append(choice).append("</label><br>");
        }
        htmlBuilder.append("</div>");

        return htmlBuilder.toString();
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public boolean isAnswerCorrect(String userAnswer) {
        if (userAnswer == null) return false;
        for (Answer answer : choices) {
            if (userAnswer.equals(answer.getAnswerText())) return answer.isCorrect();
        }
        return false;
    }

    @Override
    public String getQuestionText() {
        return question;
    }

    public List<Answer> getChoices() {
        return choices;
    }
}
