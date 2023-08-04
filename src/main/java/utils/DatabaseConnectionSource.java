package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseConnectionSource {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "quizDB";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "sql25799750";

    private static DatabaseConnectionSource instance;

    private DatabaseConnectionSource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement statement = conn.createStatement()) {
                String query = getSql("tables.sql");
                String[] queries = query.split(";");

                System.out.println(query);

                for (String qvevri : queries) {
                    statement.execute(qvevri);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to establish proper connection with database or faulty query\nMessage: " + e.getMessage());
        }
//        } catch (SQLException e) {
//            System.out.println("Failed to establish proper connection with database or faulty query\nMessage: " + e.getMessage());
//        }
    }

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

    // retrieves sql statements as list from resource (filename)
    private static String getSql(final String resource) {
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
