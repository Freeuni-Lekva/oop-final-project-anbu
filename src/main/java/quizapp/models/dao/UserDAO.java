package quizapp.models.dao;

import quizapp.models.domain.User;
import utils.DatabaseConnectionSource;
import utils.HashService;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User> {
    private final DatabaseConnectionSource _source;

    public UserDAO() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    @Override
    public Optional<User> get(int id) {
        String query = "select * from users where user_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setId(id);
                user.setUsername(rs.getString("username"));
                user.setRegistrationDate(rs.getDate("registration_date"));
                user.setPasswordHash(rs.getString("password_hash"));

                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String query = "select * from users";

        List<User> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRegistrationDate(rs.getDate("registration_date"));
                user.setPasswordHash(rs.getString("password_hash"));

                list.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to database: UserDAO::getAll");
        }

        return list;
    }

    @Override
    public boolean save(User user) {
        String query = "insert into users(username, password_hash) values (?,?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());

            ps.execute();

        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to database: UserDAO::save");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(User user) {
        String query = "update users set username = ?, password_hash = ? where user_id = ?";

//        StringBuilder sb = new StringBuilder("update users set");
//        sb.append(" %s = ?,".repeat(params.length));
//        sb.append(String.format("where id = %s", user.id));
//        String query = sb.toString();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());

            ps.setInt(3, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error occurred while connecting to database: UserDAO::update");
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(User user) {
        String query = "delete from users where user_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, user.getId());

            ps.execute();

        } catch (SQLException e) {
            System.out.println("error occurred while connecting to database: UserDAO::delete");
            return false;
        }

        return true;
    }

    public Optional<User> getByUsername(String username) {
        String query = "select * from users where username = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.id = rs.getInt("user_ud");
                user.username = rs.getString("username");
                user.passwordHash = rs.getString("password_hash");
                user.registrationDate = rs.getDate("registration_date");

                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public Optional<User> checkUser(String username) {
        String query = "select * from users where username = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            User user = new User();

            if (rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRegistrationDate(rs.getDate("registration_date"));

                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
//            System.out.println("Error occured while connecting to database");
        }

        return Optional.empty();
    }

    public boolean checkCredentials(String username, String password) {

        User user = null;
        Optional<User> possibleUser = checkUser(username);
        if (possibleUser.isPresent()) user = possibleUser.get();

        if (user != null) {
            String providedPasswordHash = HashService.hash(password);
            if (providedPasswordHash.equals(user.passwordHash)) return true;
        }

        return false;
    }

}