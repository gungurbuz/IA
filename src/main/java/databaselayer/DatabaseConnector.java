package databaselayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection con = null;

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = CreateNewConnection();
        }
        return con;
    }

    private static Connection CreateNewConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://172.26.114.217:3306/library", "root", "1234");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
        }
        return con;
    }
}
