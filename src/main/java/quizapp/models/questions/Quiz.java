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
    private boolean randomizedOrder;
    private boolean singlePageQuestions;
    private boolean immediateCorrection;

    public Quiz(int quizId, int creatorId, String quizName, String description, Date creationDate, boolean randomizedOrder, boolean singlePageQuestions, boolean immediateCorrection) {
        this.quizId = quizId;
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.creationDate = creationDate;
        this.questions = new ArrayList<>();
        this.randomizedOrder = randomizedOrder;
        this.singlePageQuestions = singlePageQuestions;
        this.immediateCorrection = immediateCorrection;
    }

    public Quiz(int creatorId, String quizName, String description, boolean randomizedOrder, boolean singlePageQuestions, boolean immediateCorrection) {
        this.creatorId = creatorId;
        this.quizName = quizName;
        this.description = description;
        this.questions = new ArrayList<>();
        this.randomizedOrder = randomizedOrder;
        this.singlePageQuestions = singlePageQuestions;
        this.immediateCorrection = immediateCorrection;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuizId() {
        return this.quizId;
    }

    public int getCreatorId() {
        return this.creatorId;
    }

    public String getQuizName() {
        return this.quizName;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getCreationDate() {
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

    public boolean getRandomizedOrder() {
        return randomizedOrder;
    }

    public boolean getSinglePageQuestions() {
        return singlePageQuestions;
    }

    public boolean getImmediateCorrection() {
        return immediateCorrection;
    }

    public void setRandomizedOrder(boolean bool) {
        randomizedOrder = bool;
    }

    public void setSinglePageQuestions(boolean bool) {
        singlePageQuestions = bool;
    }

    public void setImmediateCorrection(boolean bool) {
        immediateCorrection = bool;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setQuizName(String name) {
        this.quizName = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

