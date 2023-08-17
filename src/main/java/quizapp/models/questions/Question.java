package quizapp.models.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Question {
    protected int questionId;
    protected int quizId;
    protected final QuestionType questionType;
    protected final String questionText;
    protected final List<Answer> answerList;
    public Question(int questionId, int quizId, QuestionType questionType, String questionText){
        this.questionId = questionId;
        this.quizId =  quizId;
        this.questionType = questionType;
        this.questionText = questionText;
        answerList = new ArrayList<Answer>();
    }
    public Question(QuestionType questionType, String questionText){
        this.questionType = questionType;
        this.questionText = questionText;
        answerList = new ArrayList<Answer>();
    }
    public int getQuestionId(){
        return questionId;
    }
    public void setQuestionId(int id){
        this.questionId = questionId;
    }
    public int getQuizId(){
        return quizId;
    }

    public void setQuizId(int quizId){
        this.quizId = quizId;
    }

    public QuestionType getQuestionType(){
        return questionType;
    }
    public String getQuestionText(){
        return questionText;
    }
    public List<Answer> getAnswerList(){
        return answerList;
    }
    public void addAllAnswers(List<Answer> answerList){
        this.answerList.addAll(answerList);
    }
    public void addAnswer(Answer answer){
        answerList.add(answer);
    }
    public void addAnswer(String answerText, boolean correct){
        answerList.add(new Answer(answerText, correct));
    }
    public abstract String renderQuestionHTML(int questionIndex);

    public abstract boolean isAnswerCorrect(String userAnswer);

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Question other = (Question) obj;
        return this.questionId == other.questionId && this.questionId != 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ questionText: ").append(questionText);
        sb.append(", answers: ");
        for(Answer answer : answerList) {
            sb.append(answer.toString());
        }
        sb.append(", questionType: ").append(questionType.getValue());
        sb.append("}");
        return sb.toString();
    }
}
