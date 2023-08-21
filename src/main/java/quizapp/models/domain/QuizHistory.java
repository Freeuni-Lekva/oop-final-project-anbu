package quizapp.models.domain;

public class QuizHistory {
    public String username;
    public int quiz_id;
    public int score;
    public int completion_time;
    public String quiz_name;

    public QuizHistory(String username, int quiz_id, int score, int completion_time, String quiz_name){
        this.quiz_id = quiz_id;
        this.username = username;
        this.score = score;
        this.completion_time = completion_time;
        this.quiz_name = quiz_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String get_quiz_name() {
        return quiz_name;
    }

    public void set_quiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }
}
