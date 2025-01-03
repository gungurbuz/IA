package businesslayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import databaselayer.Password;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoginWindow extends BasicWindow {
	
	private final Password passwordClass = new Password();
	private final @NotNull TextBox usernameBox;
	private final @NotNull TextBox passwordBox;
	private boolean isComplete = false;
	
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
		Button enter = new Button("Enter", () -> {
			isComplete = true;
			LoginWindow.this.close();
		});
		buttonPanel.addComponent(enter);
		Button exit = new Button("Exit", LoginWindow.this::close);
		buttonPanel.addComponent(exit);
		buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		mainPanel.addComponent(usernamePanel.withBorder(Borders.singleLine("Username")));
		mainPanel.addComponent(passwordPanel.withBorder(Borders.singleLine("Password")));
		mainPanel.addComponent(buttonPanel.withBorder(Borders.singleLine()));
		setComponent(mainPanel.withBorder(Borders.singleLine()));
		
	}
	
	public String getUsername() {
		return usernameBox.getText();
	}
	
	public @Nullable String getPassword() {
		return passwordClass.makePass(passwordBox.getText());
	}
	
	public boolean isComplete(){
		return isComplete;
	}
	
}
