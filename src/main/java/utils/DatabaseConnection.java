package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Public method to get a database connection
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/_____";
        String username = "_____";
        String password = "_____";
        return DriverManager.getConnection(url, username, password);
    }
}
