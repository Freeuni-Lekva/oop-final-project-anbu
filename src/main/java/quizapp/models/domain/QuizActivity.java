package quizapp.models.domain;

import java.util.Date;

public class QuizActivity {
    private String creator;
    private int quiz_id;
    private String quiz_name;
    private Date creation_date;

    public QuizActivity(String creator, int quiz_id, String quiz_name, Date creation_date) {
        this.creator = creator;
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.creation_date = creation_date;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int get_quiz_id() {
        return quiz_id;
    }

    public void set_quiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String get_quiz_name() {
        return quiz_name;
    }

    public void set_quiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public Date get_creation_date() {
        return creation_date;
    }

    public void set_creation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}
