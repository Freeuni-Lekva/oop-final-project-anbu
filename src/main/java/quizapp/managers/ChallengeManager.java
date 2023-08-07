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

public class ChallengeManager {
    private final DatabaseConnectionSource _source;

    public ChallengeManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

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

    public List<ChallengeRequest> getChallengeRequests(String username) {
        String sql = "select * from challenge_requests where receiver = ? order by request_date";

        List<ChallengeRequest> challenges = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            MyLogger.info(ps.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String sender = rs.getString("sender");
                String receiver = username;
                String quiz_id = rs.getString("quiz_id");

                ChallengeRequest chr = new ChallengeRequest(sender, receiver, quiz_id);

                challenges.add(chr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return challenges;
    }

}
