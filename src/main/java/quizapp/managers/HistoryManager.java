package quizapp.managers;

import quizapp.models.domain.QuizActivity;
import quizapp.models.domain.QuizHistory;
import quizapp.models.questions.Quiz;
import utils.DatabaseConnectionSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private final DatabaseConnectionSource _source;

    public HistoryManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    public void addEntry(String username, int quiz_id, int score, int completion_time_seconds) {
        String sql = "insert into quiz_history(username, quiz_id, score, completion_time_seconds) values(?, ?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, quiz_id);
            ps.setInt(3, score);
            ps.setInt(4, completion_time_seconds);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<QuizHistory> getAllEntries() {
        String sql = "select * from quiz_history qh join quizzes q on qh.quiz_id = q.quiz_id;";

        List<QuizHistory> entries = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

           ResultSet rs = ps.executeQuery();

           while(rs.next()) {
               int quiz_id = rs.getInt("quiz_id");
               String user = rs.getString("username");
               int score = rs.getInt("score");
               int completion_time_seconds = rs.getInt("completion_time_seconds");
               String quiz_name = rs.getString("quiz_name");

               QuizHistory entry = new QuizHistory(user, quiz_id, score, completion_time_seconds, quiz_name);

               entries.add(entry);
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entries;
    }

    public List<QuizHistory> getFriendActivity(String username) {
        FriendManager friendManager = new FriendManager();

        List<String> friends = friendManager.getFriends(username);

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM quiz_history qh join quizzes q on qh.quiz_id = q.quiz_id WHERE");

        for (String friend : friends) {
            queryBuilder.append(String.format(" (qh.username = '%s') OR", friend));
        }

        queryBuilder.append(" (1 = 0) order by completion_date desc;");

        String sql = queryBuilder.toString();

        List<QuizHistory> activities = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             Statement stmt = conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String user = rs.getString("username");
                int score = rs.getInt("score");
                int quiz_id = rs.getInt("quiz_id");
                int completion_time_seconds = rs.getInt("completion_time_seconds");
                String quiz_name = rs.getString("quiz_name");

                QuizHistory quizActivity = new QuizHistory(user, score, quiz_id, completion_time_seconds, quiz_name);

                activities.add(quizActivity);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return activities;
    }

    public List<QuizHistory> getUserActivity(String username) {
        String sql = "select * from quiz_history qh join quizzes q on q.quiz_id = qh.quiz_id where username = ?";

        List<QuizHistory> activities = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String user = rs.getString("username");
                int score = rs.getInt("score");
                int quiz_id = rs.getInt("quiz_id");
                int completion_time_seconds = rs.getInt("completion_time_seconds");
                String quiz_name = rs.getString("quiz_name");

                QuizHistory quizActivity = new QuizHistory(user, score, quiz_id, completion_time_seconds, quiz_name);

                activities.add(quizActivity);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return activities;
    }

}
