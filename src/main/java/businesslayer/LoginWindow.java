package businesslayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import databaselayer.Password;

import java.util.List;

public class LoginWindow extends BasicWindow {
	
	private final Password passwordClass = new Password();
	private final TextBox usernameBox;
	private final TextBox passwordBox;
	
	public LoginWindow() {
		super("Login to member account");
		setHints(List.of(Hint.CENTERED));
		Panel mainPanel = new Panel();
		Panel usernamePanel = new Panel();
		usernameBox = new TextBox(new TerminalSize(30, 1));
		usernamePanel.addComponent(usernameBox);
		Panel passwordPanel = new Panel();
		passwordBox = new TextBox(new TerminalSize(30, 1));
		passwordBox.setMask('*');
		passwordPanel.addComponent(passwordBox);
		Panel buttonPanel = new Panel();
		ColoredButton exit = new ColoredButton("Exit", LoginWindow.this::close, TextColor.ANSI.RED);
		Button enter = new Button("Enter", LoginWindow.this::close);
		buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		buttonPanel.addComponent(exit);
		buttonPanel.addComponent(enter);
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		mainPanel.addComponent(usernamePanel.withBorder(Borders.singleLine("Username")));
		mainPanel.addComponent(passwordPanel.withBorder(Borders.singleLine("Password")));
		mainPanel.addComponent(buttonPanel.withBorder(Borders.singleLine()));
		setComponent(mainPanel.withBorder(Borders.singleLine()));
		
	}
	
	public String getUsername() {
		return usernameBox.getText();
	}
	
	public String getPassword() {
		return passwordClass.makePass(passwordBox.getText());
	}
	
}
