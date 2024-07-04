import java.sql.*;
import java.util.Scanner;

public class Helper {

    static Scanner s = new Scanner(System.in);

    public static void login(Connection con, String username, String passString) {
        System.out.println("Enter Email:"); // all println's to be moved to App, only here for testing purposes
        String email = s.nextLine();
        System.out.println("Enter Password:");
        String plainpass = s.nextLine();
        String passhash = Password.makePass(plainpass);
        try {
            Statement loginstmt = con.createStatement();
            ResultSet loginrs = loginstmt.executeQuery("select passhash from member where email = '" + email + "';");
            // if (loginrs.getString("passhash").equals(passhash)){
            // 
            // }
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public static void signup(Connection con, String username, String newPass) {

        System.out.println("Enter Email:"); // same as login
        String email = s.nextLine();
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
        } while (passMatch == false);
        String passHash = Password.makePass(plainpass);
        try {
            Statement signupstmt = con.createStatement();
            signupstmt.executeUpdate(
                    "insert into member(fname, sname, email, phone, passhash) values ('firstname', 'surname', '" + email
                            + "' , 905347257709, '" + passHash + "');");
            System.out.println("Signup complete, proceed to login.");
        } catch (Exception e) {
            System.out.println(e);
        }

        // try {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // Connection con = DriverManager.getConnection(
        // "jdbc:mysql://192.168.1.102:3306/mydb", "root", "1234");
        // // here sonoo is database name, root is username and password
        // Statement stmt = con.createStatement();
        // ResultSet rs = stmt.executeQuery("select * from member");
        // while (rs.next())
        // System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " +
        // rs.getString(3) + " " + rs.getInt(4));
        // con.close();
        // } catch (Exception e) {
        // System.out.println(e);
        // }

    }
}