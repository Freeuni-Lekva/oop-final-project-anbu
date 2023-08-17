import org.junit.jupiter.api.*;
import quizapp.managers.FriendManager;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import quizapp.models.domain.message.FriendRequest;
import utils.DatabaseConnectionSource;
import utils.HashService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFriendManager {
    private static Connection _conn;
    private static UserDAO _userDao;
    private static FriendManager _friendManager;

    private static String sender_username = "sender";
    private static String sender_password = "senderpass";
    private static String receiver_username = "receiver";
    private static String receiver_password = "receiverpass";

    @BeforeAll
    public static void init() throws SQLException {
        _userDao = new UserDAO();
        _friendManager = new FriendManager();
        _conn = DatabaseConnectionSource.getInstance().getConnection();

        User sender_user = new User();
        sender_user.setUsername(sender_username);
        sender_user.setPasswordHash(HashService.hash(sender_password));

        User receiver_user = new User();
        receiver_user.setUsername(receiver_username);
        receiver_user.setPasswordHash(HashService.hash(receiver_password));

        _userDao.save(sender_user);
        _userDao.save(receiver_user);
    }

    @Test
    @Order(1)
    public void testMakeFriendRequest() {
        _friendManager.makeFriendRequest(sender_username, receiver_username);

        String sql = "select * from friend_requests where receiver = '" + receiver_username + "'";

        try(Statement stmt = _conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(sql);

            assertTrue(rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testRemoveFriendRequest() {
        _friendManager.removeFriendRequest(sender_username, receiver_username);

        String sql = "select * from friend_requests where receiver = '" + receiver_username + "'";

        try (Statement stmt = _conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            assertFalse(rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    public void testMakeFriends() {
        _friendManager.makeFriends(sender_username, receiver_username);

        String sql = "select * from friends where username_1 = '" + sender_username +"' and username_2 = '" + receiver_username + "'";

        try (Statement stmt = _conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            assertTrue(rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void testRemoveFriends() {
        _friendManager.removeFriends(sender_username, receiver_username);

        String sql = "select * from friends where username_1 = '" + sender_username +"' and username_2 = '" + receiver_username + "'";

        try (Statement stmt = _conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(sql);

            assertFalse(rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    public void testGetFriendRequests() {
        List<FriendRequest> requests = _friendManager.getFriendRequests(receiver_username);
        assertTrue(requests.isEmpty());

        _friendManager.makeFriendRequest(sender_username, receiver_username);
        List<FriendRequest> requests_after = _friendManager.getFriendRequests(receiver_username);
        assertEquals(1, requests_after.size());

        _friendManager.removeFriendRequest(sender_username, receiver_username);
    }

    @Test
    @Order(6)
    public void getFriends() {
        List<String> friends = _friendManager.getFriends(receiver_username);
        assertTrue(friends.isEmpty());

        _friendManager.makeFriends(sender_username, receiver_username);
        List<String> friends_after = _friendManager.getFriends(receiver_username);
        assertEquals(1, friends_after.size());

        _friendManager.removeFriends(sender_username, receiver_username);
    }

    @Test
    @Order(7)
    public void testFriendRequestIsSent() {
        boolean before = _friendManager.friendRequestIsSent(sender_username, receiver_username);
        assertFalse(before);

        _friendManager.makeFriendRequest(sender_username, receiver_username);
        boolean after = _friendManager.friendRequestIsSent(sender_username, receiver_username);
        assertTrue(after);

        _friendManager.removeFriendRequest(sender_username, receiver_username);
    }

    @Test
    @Order(8)
    public void testAreFriends() {
        boolean before = _friendManager.areFriends(sender_username, receiver_username);
        assertFalse(before);

        _friendManager.makeFriends(sender_username, receiver_username);
        boolean after = _friendManager.areFriends(sender_username, receiver_username);
        assertTrue(after);

        _friendManager.removeFriends(sender_username, receiver_username);
    }

}
