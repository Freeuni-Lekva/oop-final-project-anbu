package quizapp.models.domain;

public class QuizHistory {
    public String user;
    public int quiz_id;
    public int score;
    public int completion_time;

    public QuizHistory(String user, int quiz_id, int score, int completion_time){
        this.quiz_id = quiz_id;
        this.user = user;
        this.score = score;
        this.completion_time = completion_time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int get_quiz_id() {
        return quiz_id;
    }

    public void set_quiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int get_completion_time() {
        return completion_time;
    }

    public void set_completion_time(int completion_time) {
        this.completion_time = completion_time;
    }
}
