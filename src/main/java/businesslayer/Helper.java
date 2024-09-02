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
    private static final Console cons = ConsoleConnector.getConsole();
    private static Member currentUser;
    private static Helper helper;

    private Helper() {
        try {
            con = DatabaseConnector.getConnection(); // get connection object created in database layer
            // cons = ConsoleConnector.getConsole();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Helper getHelper() {
        if (helper == null) {
            helper = new Helper();
        }
        return helper;
    }

    public void logout() {
        currentUser = null;
    }

    public Member login() {
        clearConsole();
        return loginPrivate();
    }

    private Member loginPrivate() {
        Password password = new Password(); //move outside of method
        System.out.println("Enter Username:");
        String uname = s.nextLine();
        System.out.println("Enter Password:");
        String plainpass = readPasswordtoString();
        String passhash = password.makePass(plainpass);
        plainpass = null;
        try {
            PreparedStatement loginstmt = con.prepareStatement("SELECT passhash FROM member WHERE uname = ?;");
            loginstmt.setString(1, uname);
            ResultSet loginrs = loginstmt.executeQuery();
            if (loginrs.next()) {
                if (loginrs.getString("passhash").equals(passhash)) {
                    currentUser = new Member(uname, passhash);
                    return currentUser;
                } else {
                    System.out.println("Invalid username or password.");
                    wait(500);
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeLoginTime() {
        try {
            PreparedStatement loginstampstmt = con.prepareStatement(
                    "UPDATE member SET lastlogin = ? WHERE uname = ?");
            loginstampstmt.setString(1, timeStamp());
            loginstampstmt.setString(2, currentUser.getUsername());
            loginstampstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readLastLogin(String uname) {
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

    public void signup() {
        Helper.getHelper().clearConsole();
        System.out.println("Enter Username:"); // same as login
        String uname = s.nextLine();
        Helper.getHelper().clearConsole();
        System.out.println("Enter First Name:");
        String fName = s.nextLine();
        Helper.getHelper().clearConsole();
        System.out.println("Enter Last Name:");
        String sName = s.nextLine();
        boolean passMatch = false;
        String plainpass;
        do {
            Helper.getHelper().clearConsole();
            System.out.println("Enter Password:");
            plainpass = readPasswordtoString();
            Helper.getHelper().clearConsole();
            System.out.println("Reenter Password:");
            if (readPasswordtoString().equals(plainpass)) {
                passMatch = true;
            } else {
                System.out.println("Passwords do not match!");
                Helper.getHelper().clearConsole();
            }
        } while (!passMatch);
        Password password = new Password();
        String passHash = password.makePass(plainpass);
        plainpass = null;
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
        System.out.println("test");
        wait(500);
    }

    public String timeStamp() {
        return ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));
    }

    private String readPasswordtoString() {
        char[] passchars = cons.readPassword();
        return new String(passchars);
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        Helper.cons.flush();
    }

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}