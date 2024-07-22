import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.Console;

public class Helper {

    static Scanner s = new Scanner(System.in);
    static Console cons = System.console();
    private static String currentUsername;

    public static boolean login(Connection con) {
        System.out.println("Enter Username:");
        String uname = s.nextLine();
        System.out.println("Enter Password:");
        String plainpass = readPasswordtoString();
        String passhash = Password.makePass(plainpass);
        try {
            PreparedStatement loginstmt = con.prepareStatement("SELECT passhash FROM member WHERE uname = ?;");
            loginstmt.setString(1, uname);
            ResultSet loginrs = loginstmt.executeQuery();
            if (loginrs.next()) {
                if (loginrs.getString("passhash").equals(passhash)) {
                    currentUsername = uname;
                    return true;
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void writeLoginTime(Connection con) {
        try {
            PreparedStatement loginstampstmt = con.prepareStatement(
                    "UPDATE member SET lastlogin = ? WHERE uname = ?");
            loginstampstmt.setString(1, timeStamp());
            loginstampstmt.setString(2, currentUsername);
            loginstampstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readLastLogin(Connection con, String uname) {
        try {
            PreparedStatement loginreadstmt = con.prepareStatement("SELECT lastlogin FROM member WHERE uname = ?;");
            loginreadstmt.setString(1, uname);
            ResultSet loginreadrs = loginreadstmt.executeQuery();
            if (loginreadrs.next() && loginreadrs.getObject("lastlogin") != null) {
                System.out.println("Last seen: " + (loginreadrs.getString("lastlogin")));
                writeLoginTime(con);
            } else {
                System.out.println("First login, welcome!");
                writeLoginTime(con);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            plainpass = readPasswordtoString();
            System.out.println("Reenter Password:");
            if (readPasswordtoString().equals(plainpass)) {
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
            wait(500);
            clearConsole();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getUsername() {
        return currentUsername;
    }

    public static String timeStamp() {
        return ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));
    }

    private static String readPasswordtoString() {
        char[] passchars = cons.readPassword();
        return new String(passchars);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        Helper.cons.flush();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}