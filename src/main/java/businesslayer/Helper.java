package businesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import applicationlayer.App;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import databaselayer.DatabaseConnector;
import databaselayer.GUIConnector;

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
			throw new RuntimeException(e);
		}
	}
	
	public static Helper getHelper() {
		if (helper == null) {
			helper = new Helper();
		}
		return helper;
	}
	
	public static WindowBasedTextGUI getGUI(){
		return gui;
	}
	
	public synchronized void logout() {
		currentUser = null;
	}
	
	
	/**
	 * Authenticates a user by displaying a login window and validating the provided credentials.
	 * <p>
	 * The method prompts the user to enter a username and password via a login window.
	 * It then checks these credentials against the stored values in the database. If the
	 * validation is successful, the method returns a Member object representing the logged-in user.
	 * If the credentials are incorrect or the user does not exist, appropriate error messages are displayed.
	 */
	
	
	public synchronized void login() {
		LoginWindow login = new LoginWindow();
		gui.addWindowAndWait(login);
		
		if (login.isComplete()) {
			
			String username = login.getUsername();
			String passhash = login.getPassword();
			String memberID;
			
			ResultSet loginResultSet;
			try (PreparedStatement loginStatement = con.prepareStatement("SELECT idmember, passhash FROM member WHERE uname = ?;")) {
				loginStatement.setString(1, username);
				loginResultSet = loginStatement.executeQuery();
				
				if (loginResultSet.next()) {
					if (loginResultSet.getString("passhash").equals(passhash)) {
						memberID = (String.valueOf(loginResultSet.getInt("idmember")));
						currentUser = new Member(memberID, username, passhash);
						login.close();
						writeLoginTime();
						App.setCurrentUser(currentUser);
					} else {
						MessageDialog.showMessageDialog(gui, "Error", "Invalid username or password");
					}
				} else {
					MessageDialog.showMessageDialog(gui, "Error", "User not found");
				}
			} catch (Exception e) {
				MessageDialog.showMessageDialog(gui, "Error", "Error connecting to database:" + e.getMessage());
				login.close();
			}
			
			
		}
	}
	
	private void writeLoginTime() {
		try {
			PreparedStatement loginTimeStampStatement = con.prepareStatement(
					"UPDATE member SET lastlogin = ? WHERE uname = ?");
			loginTimeStampStatement.setString(1, timeStamp());
			loginTimeStampStatement.setString(2, currentUser.getUsername());
			loginTimeStampStatement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String readLastLogin(String uname) {
		String lastLogin = null;
		try {
			PreparedStatement loginReadStatement = con.prepareStatement("SELECT lastlogin FROM member WHERE uname = ?;");
			loginReadStatement.setString(1, uname);
			ResultSet loginReadResultSet = loginReadStatement.executeQuery();
			if (loginReadResultSet.next() && loginReadResultSet.getObject("lastlogin") != null) {
				lastLogin = loginReadResultSet.getString("lastlogin");
				writeLoginTime();
			} else {
				writeLoginTime();
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return lastLogin;
	}
	
	
	public synchronized void signup() {
		SignupWindow signup = new SignupWindow();
		gui.addWindowAndWait(signup);
		if (signup.isComplete()) {
			String uname = signup.getUsername();
			String firstName = signup.getFirstname();
			String lastName = signup.getLastname();
			String passhash = signup.getPassword();
			try {
				PreparedStatement signupStatement = con.prepareStatement(
						"INSERT INTO member (uname, fname, sname, passhash) VALUES (?, ?, ?, ?);");
				signupStatement.setString(1, uname);
				signupStatement.setString(2, firstName);
				signupStatement.setString(3, lastName);
				signupStatement.setString(4, passhash);
				signupStatement.executeUpdate();
				MessageDialog.showMessageDialog(gui, "Success", "Proceed to login");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			wait(500);
		}
	}
	
	
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