import org.junit.jupiter.api.*;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import utils.DatabaseConnectionSource;
import utils.HashService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserDAO {
    private static DatabaseConnectionSource _source;
    private static UserDAO _userDao;
    private static Connection _conn;

    private final String created_username = "username_1";
    private final String created_password = "password";

    private final String existing_username = "example_user";
    private final String existing_password = "hashed_password";

    @BeforeAll
    public static void init() throws SQLException {
        _userDao = new UserDAO();
        _conn = DatabaseConnectionSource.getInstance().getConnection();

        DatabaseConnectionSource.executeQueryFromResource("testData.sql");
    }

    @Test
    @Order(1)
    public void testSave() {
        User user = new User();

        user.setUsername(created_username);
        user.setPasswordHash(HashService.hash(created_password));

        _userDao.save(user);

        String sql = "select * from users where username = ?";

        try(PreparedStatement ps = _conn.prepareStatement(sql);) {

            ps.setString(1, created_username);

            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testGetAll() {
        List<User> users = _userDao.getAll();
        assertEquals(2, users.size());
    }

    @Test
    @Order(3)
    public void testGetByUsername() {
        Optional<User> userOptional = _userDao.getByUsername(created_username);
        assertTrue(userOptional.isPresent());

        User user = userOptional.get();
        assertNotNull(user);

        assertEquals(HashService.hash(created_password), user.getPasswordHash());

        Optional<User> another_user_optional = _userDao.getByUsername(existing_username);
        assertTrue(another_user_optional.isPresent());

        User another_user = another_user_optional.get();
        assertNotNull(user);

        assertEquals(existing_password, another_user.getPasswordHash());
    }

    @Test
    @Order(4)
    public void testCheckCredentials() {
        assertTrue(_userDao.checkCredentials(created_username, created_password));
        assertFalse(_userDao.checkCredentials("invalid", "invalid"));
    }

    @AfterAll
    public static void dispose() throws SQLException {
        if (!_conn.isClosed()) {
            _conn.close();
        }
    }

}
