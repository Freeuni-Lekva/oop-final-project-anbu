package quizapp.managers;

import quizapp.models.domain.QuizHistory;
import utils.DatabaseConnectionSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private final DatabaseConnectionSource _source;

    public HistoryManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    public void addEntry(String username, int quiz_id, int score, int completion_time) {
        String sql = "insert into quiz_history(user_id, quiz_id, score, completion_time) values(?, ?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, quiz_id);
            ps.setInt(3, score);
            ps.setInt(4, completion_time);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<QuizHistory> getAllEntries() {
        String sql = "select * from quiz_history";

        List<QuizHistory> entries = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

           ResultSet rs = ps.executeQuery();

           while(rs.next()) {
               int quiz_id = rs.getInt("quiz_id");
               String user = rs.getString("username");
               int score = rs.getInt("score");
               int completion_time = rs.getInt("completion_time");

               QuizHistory entry = new QuizHistory(user, quiz_id, score, completion_time);

               entries.add(entry);
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entries;
    }
}
