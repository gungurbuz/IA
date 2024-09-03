package applicationlayer;

import businesslayer.Helper;
import businesslayer.Member;
import com.googlecode.lanterna.gui2.*;
import databaselayer.GUIConnector;

import java.util.Arrays;

/**
 * MainWindow is the primary window for the Library Catalogue System application.
 *
 * It provides three buttons for user actions:
 * - Login
 * - Sign Up
 * - Exit
 *
 * When the Login button is clicked, the current user is set by invoking the login method of the Helper class.
 * When the Sign Up button is clicked, the signup method of the Helper class is invoked.
 * When the Exit button is clicked, the application is set to stop running and the window is closed.
 */
public class MainWindow extends BasicWindow{
    Member currentUser;
    public MainWindow() {
        super("Library Catalogue System");
        setHints(Arrays.asList(Window.Hint.CENTERED));
        Panel horizontalPanel = new Panel();
        horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel leftPanel = new Panel();
        leftPanel.addComponent(new Button("Login", () -> {
            currentUser = Helper.getHelper().login();
            MainWindow.this.close();
        }));
        Panel middlePanel = new Panel();
        middlePanel.addComponent(new Button("Sign Up", () -> Helper.getHelper().signup()));
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
    public Member returnMember(){
        return currentUser;
    }
}
