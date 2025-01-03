package applicationlayer;

import businesslayer.Helper;
import businesslayer.Member;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import databaselayer.GUIConnector;

import java.util.List;

/**
 * MainWindow is the primary window for the Library Catalogue System application.
 * <p>
 * It provides three buttons for user actions:
 * - Login
 * - Sign Up
 * - Exit
 * <p>
 * When the Login button is clicked, the current user is set by invoking the login method of the Helper class.
 * When the Sign Up button is clicked, the signup method of the Helper class is invoked.
 * When the Exit button is clicked, the application is set to stop running and the window is closed.
 */
public class MainWindow extends BasicWindow {
	
	Member currentUser;
	
	public MainWindow() {
		super("Library Catalogue System");
		setHints(List.of(Hint.CENTERED));
		Panel horizontalPanel = new Panel();
			horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		Panel leftPanel = new Panel();
		leftPanel.addComponent(new Button("Login", () -> {
			Helper.getHelper().login();
			MainWindow.this.close();
		}));
		Panel middlePanel = new Panel();
		middlePanel.addComponent(new Button("Sign Up", () -> {
			Helper.getHelper().signup();
		}));
		Panel rightPanel = new Panel();
		rightPanel.addComponent(new Button("Exit", () -> {
			App.isRunning.set(false);
			MainWindow.this.close();
			GUIConnector.stopScreen();
		}));
		
		
		horizontalPanel.addComponent(leftPanel.withBorder(Borders.singleLineBevel()));
		horizontalPanel.addComponent(middlePanel.withBorder(Borders.singleLineBevel()));
		horizontalPanel.addComponent(rightPanel.withBorder(Borders.singleLineBevel()));
		
		// This ultimately links in the panels as the window content
		setComponent(horizontalPanel);
	}
	
}
