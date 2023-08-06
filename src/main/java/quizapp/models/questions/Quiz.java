package quizapp.models.questions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz {
    private int quizId;
    private int creatorId;
    private String quizName;
    private String description;
    private Date creationDate;
    private List<Question> questions;

    public Quiz(int quizId,int creatorId, String quizName, String description, Date creationDate) {
        this.quizId = quizId;
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.creationDate = creationDate;
        this.questions = new ArrayList<>();
    }
    public Quiz(int creatorId, String quizName, String description) {
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.questions = new ArrayList<>();
    }
    public void setQuizId(int quizId){
        this.quizId = quizId;
    }
    public int getId(){
        return this.quizId;
    }
    public int getCreatorId(){
        return this.creatorId;
    }
    public String getQuizName(){
        return this.quizName;
    }
    public String getDescription(){
        return this.description;
    }
    public Date getCreationDate(){
        return this.creationDate;
    }

    public void addAllQuestions(List<Question> questions) {
        this.questions.addAll(questions);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

}
