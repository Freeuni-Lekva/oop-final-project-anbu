package quizapp.managers;

import quizapp.models.domain.message.FriendRequest;
import utils.DatabaseConnectionSource;
import utils.MyLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendManager {

    private final DatabaseConnectionSource _source;

    public FriendManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    public void makeFriendRequest(String sender, String receiver) {
        String sql = "insert into friend_requests(sender, receiver) values (?, ?);";

        try(Connection conn = _source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, sender);
            ps.setString(2, receiver);

            MyLogger.info(ps.toString());
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

            MyLogger.info(ps.toString());
            ps.execute();
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

            MyLogger.info(ps.toString());
            ps.execute();

            removeFriendRequest(username_1, username_2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriends(String username_1, String username_2) {
        String sql = "delete from friends where (username_1 = ? and username_2 = ?) or (username_1 = ? and username_2 = ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username_1);
            ps.setString(2, username_2);

            ps.setString(3, username_2);
            ps.setString(4, username_1);

            MyLogger.info(ps.toString());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FriendRequest> getFriendRequests(String username) {
        String sql = "select * from friend_requests where receiver = ? order by request_date";

        List<FriendRequest> requests = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            MyLogger.info(ps.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String sender = rs.getString("sender");
                String receiver= rs.getString("receiver");

                FriendRequest fr = new FriendRequest(sender, receiver);
                requests.add(fr);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return requests;
    }

    public List<String> getFriends(String username) {
        String sql = "select * from friends where username_1 = ? or username_2 = ?";

        List<String> friendList = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, username);

            MyLogger.info(ps.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String username_1 = rs.getString("username_1");
                String username_2 = rs.getString("username_2");

                if (username_1.equals(username)) {
                    friendList.add(username_2);
                } else {
                    friendList.add(username_1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return friendList;
    }

    public boolean friendRequestIsSent(String from, String to) {
        String sql = "select * from friend_requests where sender = ? and receiver = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, from);
            ps.setString(2, to);

            MyLogger.info(ps.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean areFriends(String username_1, String username_2) {
        String sql = "select * from friends where (username_1 = ? and username_2 = ?) or (username_1 = ? and username_2 = ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username_1);
            ps.setString(2, username_2);
            ps.setString(3, username_2);
            ps.setString(4, username_1);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

}
