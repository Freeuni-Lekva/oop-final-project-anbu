import org.junit.jupiter.api.*;
import quizapp.managers.NoteManager;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import quizapp.models.domain.message.NoteMessage;
import utils.DatabaseConnectionSource;
import utils.HashService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestNoteManager {

    private static Connection _conn;
    private static UserDAO _userDao;
    private static NoteManager _noteManager;

    private static String sender_username = "sender";
    private static String sender_password = "senderpass";
    private static String receiver_username = "receiver";
    private static String receiver_password = "receiverpass";

    @BeforeAll
    public static void init() throws SQLException {
        _userDao = new UserDAO();
        _noteManager = new NoteManager();
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
    public void testSendNote() {
        _noteManager.sendNote(sender_username, receiver_username, "EXAMPLE NOTE MESSAGE");

        String sql = "select * from notes where receiver = '" + receiver_username + "'";

        try (Statement stmt = _conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            assertTrue(rs.next());

            String sender = rs.getString("sender");
            assertEquals(sender_username, sender);

            String note = rs.getString("note");
            assertEquals("EXAMPLE NOTE MESSAGE", note);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(2)
    public void testGetNotesByUsername() {
        List<NoteMessage> notes = _noteManager.getNotesByUsername(receiver_username);
        assertEquals(1, notes.size());
    }

}
