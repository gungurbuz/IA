package businesslayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import databaselayer.GUIConnector;
import databaselayer.Password;

import java.util.List;

public class SignupWindow extends BasicWindow {
	
	WindowBasedTextGUI gui = GUIConnector.getTextGUI();
	TextBox usernameBox;
	TextBox firstnameBox;
	TextBox lastnameBox;
	TextBox passwordBox;
	TextBox confirmPasswordBox;
	Password passwordClass = new Password();
	
	SignupWindow() {
		super("Sign up for member account");
		setHints(List.of(Hint.CENTERED));
		Panel mainPanel = new Panel();
		Panel usernamePanel = new Panel();
		usernameBox = new TextBox(new TerminalSize(30, 1));
		usernamePanel.addComponent(usernameBox);
		Panel firstnamePanel = new Panel();
		firstnameBox = new TextBox(new TerminalSize(30, 1));
		firstnamePanel.addComponent(firstnameBox);
		Panel lastnamePanel = new Panel();
		lastnameBox = new TextBox(new TerminalSize(30, 1));
		lastnamePanel.addComponent(lastnameBox);
		Panel passwordPanel = new Panel();
		passwordBox = new TextBox(new TerminalSize(30, 1));
		passwordBox.setMask('*');
		passwordPanel.addComponent(passwordBox);
		Panel confirmPasswordPanel = new Panel();
		confirmPasswordBox = new TextBox(new TerminalSize(30, 1));
		confirmPasswordBox.setMask('*');
		confirmPasswordPanel.addComponent(confirmPasswordBox);
		Panel exitPanel = new Panel();
		Button exit = new Button("Enter", () -> {
			if (passwordBox.getText().equals(confirmPasswordBox.getText())) {
				SignupWindow.this.close();
			} else {
				MessageDialog.showMessageDialog(gui, "Error", "Passwords do not match");
			}
		});
		exitPanel.addComponent(exit);
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		mainPanel.addComponent(usernamePanel.withBorder(Borders.singleLine("Username")));
		mainPanel.addComponent(firstnamePanel.withBorder(Borders.singleLine("First name")));
		mainPanel.addComponent(lastnamePanel.withBorder(Borders.singleLine("Last name")));
		mainPanel.addComponent(passwordPanel.withBorder(Borders.singleLine("Password")));
		mainPanel.addComponent(confirmPasswordPanel.withBorder(Borders.singleLine("Confirm Password")));
		mainPanel.addComponent(exitPanel.withBorder(Borders.singleLine()));
		setComponent(mainPanel.withBorder(Borders.singleLine()));
	}
	
	public String getUsername() {
		return usernameBox.getText();
	}
	
	public String getFirstname() {
		return firstnameBox.getText();
	}
	
	public String getLastname() {
		return lastnameBox.getText();
	}
	
	public String getPassword() {
		return passwordClass.makePass((passwordBox.getText()));
	}
	
}
