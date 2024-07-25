package businesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import databaselayer.*;
import java.io.Console;

public class Helper {

    private static Scanner s = new Scanner(System.in);
    private static Connection con;
    private static Console cons;
    private static String currentUsername;

    private Helper() {
        try {
            con = DatabaseConnector.getConnection(); // get connection object created in database layer
            cons = ConsoleConnector.getConsole();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        currentUsername = null;
    }

    public static boolean login() {
        return loginPrivate();
    }

    private static boolean loginPrivate() {
        Password password = new Password();
        System.out.println("Enter Username:");
        String uname = s.nextLine();
        System.out.println("Enter Password:");
        String plainpass = readPasswordtoString();
        String passhash = password.makePass(plainpass);
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

    private static void writeLoginTime() {
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

    public static void readLastLogin(String uname) {
        try {
            PreparedStatement loginreadstmt = con.prepareStatement("SELECT lastlogin FROM member WHERE uname = ?;");
            loginreadstmt.setString(1, uname);
            ResultSet loginreadrs = loginreadstmt.executeQuery();
            if (loginreadrs.next() && loginreadrs.getObject("lastlogin") != null) {
                System.out.println("Last seen: " + (loginreadrs.getString("lastlogin")));
                writeLoginTime();
            } else {
                System.out.println("First login, welcome!");
                writeLoginTime();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signup() {
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
        Password password = new Password();
        String passHash = password.makePass(plainpass);
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