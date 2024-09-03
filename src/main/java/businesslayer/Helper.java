package businesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import applicationlayer.App;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import databaselayer.*;

public class Helper {
	
	private static Connection con;
	private static final WindowBasedTextGUI gui = GUIConnector.getTextGUI();
	private static Member currentUser;
	private static Helper helper;
	
	private Helper() {
		try {
			con = DatabaseConnector.getConnection(); // get connection object created in database layer
			// cons = TerminalConnector.getConsole();
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
	
	public synchronized void logout() {
		currentUser = null;
	}
	
	
	/**
	 * Authenticates a user by displaying a login window and validating the provided credentials.
	 *
	 * The method prompts the user to enter a username and password via a login window.
	 * It then checks these credentials against the stored values in the database. If the
	 * validation is successful, the method returns a Member object representing the logged-in user.
	 * If the credentials are incorrect or the user does not exist, appropriate error messages are displayed.
	 *
	 * @return a Member object representing the logged-in user, or the existing user if login fails
	 */
	public synchronized Member login() {
		LoginWindow login = new LoginWindow();
		gui.addWindowAndWait(login);
		String uname = login.getUsername();
		String passhash = login.getPassword();
		try {
			ResultSet loginrs;
			try (PreparedStatement loginstmt = con.prepareStatement("SELECT passhash FROM member WHERE uname = ?;")) {
				loginstmt.setString(1, uname);
				loginrs = loginstmt.executeQuery();
			}
			if (loginrs.next()) {
				if (loginrs.getString("passhash").equals(passhash)) {
					currentUser = new Member(uname, passhash);
					login.close();
					return currentUser;
				} else {
					MessageDialog.showMessageDialog(gui, "Error", "Invalid username or password");
				}
			} else {
				MessageDialog.showMessageDialog(gui, "Error", "User not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentUser;
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
	
	public String readLastLogin(String uname) {
		String lastLogin = null;
		try {
			PreparedStatement loginreadstmt = con.prepareStatement("SELECT lastlogin FROM member WHERE uname = ?;");
			loginreadstmt.setString(1, uname);
			ResultSet loginreadrs = loginreadstmt.executeQuery();
			if (loginreadrs.next() && loginreadrs.getObject("lastlogin") != null) {
				lastLogin = loginreadrs.getString("lastlogin");
				writeLoginTime();
			} else {
				writeLoginTime();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastLogin;
	}
	
	
	public synchronized void signup() {
	
	}
		/*
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
	*/
	
	public String timeStamp() {
		return ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));
	}
	
	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
}