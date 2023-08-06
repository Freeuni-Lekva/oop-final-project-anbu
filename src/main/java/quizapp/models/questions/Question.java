package quizapp.models.questions;

import java.util.ArrayList;
import java.util.List;

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

}
