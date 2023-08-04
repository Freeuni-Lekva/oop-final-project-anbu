package quizapp.models.questions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz {
    private int quizId;
    private int creatorId;
    private String title;
    private String description;
    private Date createDate;
    private List<Question> questions;

    public Quiz(int quizId,int creatorId, String title, String description, Date createDate) {
        this.quizId = quizId;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.questions = new ArrayList<>();
    }
    public int getId(){
        return this.quizId;
    }
    public int getCreatorId(){
        return this.creatorId;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public Date getCreateDate(){
        return this.createDate;
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
