package quizapp.managers;

import quizapp.models.domain.message.ChallengeRequest;
import utils.DatabaseConnectionSource;
import utils.MyLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* ChallengeManager class, handles Challenge related stuff including storing and retrieving challenges from database */
public class ChallengeManager {
    private final DatabaseConnectionSource _source;

    public ChallengeManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    /* inserts challenge entry in database */
    public void sendChallenge(String sender, String receiver, int quiz_id) {
        String sql = "insert into challenge_requests(sender, receiver, quiz_id) values (?,?,?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setInt(3, quiz_id);

            MyLogger.info(ps.toString());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* retrieves challenge requests sent to a particular user identified with given [username] */
    public List<ChallengeRequest> getChallengeRequests(String username) {
        String sql = "select Max(qh.score) as score, qh.username as sender, Max(qh.quiz_id) as quiz_id from challenge_requests cr  join quiz_history qh on qh.quiz_id = cr.quiz_id where cr.receiver =  ?  and cr.sender = qh.username group by qh.username";

        List<ChallengeRequest> challenges = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            MyLogger.info(ps.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String sender = rs.getString("sender");
                String receiver = username;
                int quiz_id = rs.getInt("quiz_id");
                int score = rs.getInt("score");

                ChallengeRequest chr = new ChallengeRequest(sender, receiver, quiz_id, score);

                challenges.add(chr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return challenges;
    }

}
