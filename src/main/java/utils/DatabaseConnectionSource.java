package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseConnectionSource {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "quizDB";
    private static final String DB_USER = "_______";
    private static final String DB_PASS = "_______";

    private static DatabaseConnectionSource instance;

    private DatabaseConnectionSource() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            executeQueryFromResource("tables.sql");
        } catch (SQLException e) {
            System.out.println("Failed to establish proper connection with database or faulty query\nMessage: " + e.getMessage());
        }
    }

    /* returns connection source instance */
    public static DatabaseConnectionSource getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionSource();
        }
        return instance;
    }

    // Public method to get a database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASS);
    }

    // public method to execute external SQL scripts from resource file
    public static void executeQueryFromResource(final String resource) {
        try (Statement stmt = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS).createStatement();) {

            String sql = DatabaseConnectionSource.getSql(resource);
            String[] queries = sql.split(";");

            for (String qvevri : queries) {
                stmt.execute(qvevri);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // retrieves sql statements as semicolon-separated string from resource (filename)
    public static String getSql(final String resource) {
        return new BufferedReader(
                new InputStreamReader((Objects.requireNonNull(
                        DatabaseConnectionSource.class.getClassLoader().getResourceAsStream(resource)
                )))
        )
                .lines()
                .filter(line -> !line.trim().startsWith(("--")) && !line.isBlank())
                .collect(Collectors.joining());
    }
}
