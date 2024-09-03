package businesslayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import databaselayer.Password;

import java.util.Arrays;

public class LoginWindow extends BasicWindow {
	Password passwordClass = new Password();
	TextBox usernameBox;
	TextBox passwordBox;
	public LoginWindow() {
		super("Login to member account");
		setHints(Arrays.asList(Window.Hint.CENTERED));
		Panel mainPanel = new Panel();
		Panel usernamePanel = new Panel();
		usernameBox = new TextBox(new TerminalSize(30, 1));
		usernamePanel.addComponent(usernameBox);
		Panel passwordPanel = new Panel();
		passwordBox = new TextBox(new TerminalSize(30, 1));
		passwordBox.setMask('*');
		passwordPanel.addComponent(passwordBox);
		Panel exitPanel = new Panel();
		Button exit = new Button("Enter", new Runnable() {
			public void run() {
				LoginWindow.this.close();
			}
		});
		exitPanel.addComponent(exit);
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		mainPanel.addComponent(usernamePanel.withBorder(Borders.singleLine("Username")));
		mainPanel.addComponent(passwordPanel.withBorder(Borders.singleLine("Password")));
		mainPanel.addComponent(exitPanel.withBorder(Borders.singleLine()));
		setComponent(mainPanel.withBorder(Borders.singleLine()));
		
	}
	
	public String getUsername() {
		return usernameBox.getText();
	}
	public String getPassword() {
		return passwordClass.makePass(passwordBox.getText());
	}
	
}
