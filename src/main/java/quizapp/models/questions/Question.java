package quizapp.models.questions;

public interface Question {
    int getId();
    void setId(int id);
    int getQuizId();
    void setQuizId(int id);
    String renderQuestionHTML();
    QuestionType getQuestionType();
    String getQuestionText();
    boolean isAnswerCorrect(String userAnswer);
}
