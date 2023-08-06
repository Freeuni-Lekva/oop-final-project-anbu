package quizapp.managers;

import utils.DatabaseConnectionSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendManager {

    private final DatabaseConnectionSource _source;

    public FriendManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    public void makeFriendRequest(String sender, String receiver) {
        String sql = "insert into friend_requests(sender, receiver, friendship_status) values (?, ?, ?);";

        try(Connection conn = _source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, "PENDING");

            System.out.println(ps);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriendRequest(String sender, String receiver) {
        String sql = "delete from friend_requests where sender = ? and receiver = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, sender);
            ps.setString(2, receiver);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateFriendRequest(String sender, String receiver, String status) {
        String sql = "update friend_requests set friendship_status = ? where sender = ?, receiver = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, status);
            ps.setString(2, sender);
            ps.setString(3, receiver);

            ps.executeUpdate();

            if (status.equals("ACCEPTED")) {
                this.makeFriends(sender, receiver);
                this.removeFriendRequest(sender, receiver);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeFriends(String username_1, String username_2) {
        String sql = "insert into friends(username_1, username_2) values (?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username_1);
            ps.setString(2, username_2);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriends(String username_1, String username_2) {
        String sql = "delete from friends where username_1 = ? and username_2 = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username_1);
            ps.setString(2, username_2);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
