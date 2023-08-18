import org.junit.jupiter.api.*;
import quizapp.managers.ChallengeManager;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import quizapp.models.domain.message.ChallengeRequest;
import utils.DatabaseConnectionSource;
import utils.HashService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestChallengeManager {
    private static Connection _conn;
    private static UserDAO _userDao;
    private static ChallengeManager _challengeManager;

    private static String sender_username = "sender";
    private static String sender_password = "senderpass";
    private static String receiver_username = "receiver";
    private static String receiver_password = "receiverpass";

    @BeforeAll
    public static void init() throws SQLException {
        _userDao = new UserDAO();
        _challengeManager = new ChallengeManager();
        _conn = DatabaseConnectionSource.getInstance().getConnection();

        User sender_user = new User();
        sender_user.setUsername(sender_username);
        sender_user.setPasswordHash(HashService.hash(sender_password));

        User receiver_user = new User();
        receiver_user.setUsername(receiver_username);
        receiver_user.setPasswordHash(HashService.hash(receiver_password));

        _userDao.save(sender_user);
        _userDao.save(receiver_user);

        DatabaseConnectionSource.executeQueryFromResource("testData.sql");
    }

    @Test
    public void testSendChallenge() {
        _challengeManager.sendChallenge(sender_username, receiver_username, 1);

        String sql = "select * from challenge_requests where receiver = '" + receiver_username + "'";
        try (Statement stmt = _conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(sql);

            assertTrue(rs.next());

            assertEquals(sender_username, rs.getString("sender"));
            assertEquals(receiver_username, rs.getString("receiver"));
            assertEquals(1, rs.getInt("quiz_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetChallengeRequests() {
        List<ChallengeRequest> challengeRequests = _challengeManager.getChallengeRequests(receiver_username);
        assertEquals(1, challengeRequests.size());

        ChallengeRequest request = challengeRequests.get(0);

        assertEquals(1, request.getQuizId());
        assertEquals(sender_username, request.getSender());
        assertEquals(receiver_username, request.getReceiver());
    }

    @AfterAll
    public static void dispose() throws SQLException {
        if (!_conn.isClosed()) {
            _conn.close();
        }
    }

}
