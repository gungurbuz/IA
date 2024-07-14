import java.sql.*;
import java.util.Scanner;

public class Helper {

    static Scanner s = new Scanner(System.in);
    private static String currentUsername;

    public static boolean login(Connection con) {
        System.out.println("Enter Username:");
        String uname = s.nextLine();
        System.out.println("Enter Password:");
        String plainpass = s.nextLine();
        String passhash = Password.makePass(plainpass);
        try {
            PreparedStatement loginstmt = con.prepareStatement("SELECT password FROM member WHERE username = ?;");
            loginstmt.setString(1, uname);
            ResultSet loginrs = loginstmt.executeQuery();
            if (loginrs.next()) {
                if (loginrs.getString("password").equals(passhash)) {
                    currentUsername = uname;
                    return true;
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void signup(Connection con) {
        System.out.println("Enter Username:"); // same as login
        String uname = s.nextLine();
        System.out.println("Enter First Name:");
        String fName = s.nextLine();
        System.out.println("Enter Last Name:");
        String sName = s.nextLine();
        boolean passMatch = false;
        String plainpass;
        do {
            System.out.println("Enter Password:");
            plainpass = s.nextLine();
            System.out.println("Reenter Password:");
            if (s.nextLine().equals(plainpass)) {
                passMatch = true;
            } else {
                System.out.println("Passwords do not match!");
            }
        } while (!passMatch);
        String passHash = Password.makePass(plainpass);
        try {
            PreparedStatement signupstmt = con.prepareStatement(
                    "INSERT INTO member (uname, fname, sname, passhash) VALUES (?, ?, ?, ?);");
            signupstmt.setString(1, uname);
            signupstmt.setString(2, fName);
            signupstmt.setString(3, sName);
            signupstmt.setString(4, passHash);
            signupstmt.executeUpdate();
            System.out.println("Signup complete, proceed to login.");
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public static String getUsername() {
        return currentUsername;
    }
}