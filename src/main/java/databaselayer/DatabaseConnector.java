package databaselayer;

import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static @Nullable Connection con = null;

    public static @Nullable Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = CreateNewConnection();
        }
        return con;
    }

    private static @Nullable Connection CreateNewConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.1.108:3306/library", "root", "1234");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
        }
        return con;
    }
}
