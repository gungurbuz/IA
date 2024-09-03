package applicationlayer;

import businesslayer.Helper;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import databaselayer.GUIConnector;

import java.util.Arrays;

public class AuthWindow extends BasicWindow {
	public AuthWindow() {
		super("Library Catalogue System");
		setHints(Arrays.asList(Window.Hint.CENTERED));
		Panel horizontalPanel = new Panel();
		horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		Panel leftPanel = new Panel();
		leftPanel.addComponent(new Button("Search Book", () -> {
			MessageDialog.showMessageDialog(GUIConnector.getTextGUI(), "Error", "Not implemented yet");
			AuthWindow.this.close();
		}));
		Panel middlePanel = new Panel();
		middlePanel.addComponent(new Button("Add Book", () -> {
			//BookAddWindow should be displayed
			AuthWindow.this.close();
		}));
		Panel rightPanel = new Panel();
		rightPanel.addComponent(new Button("Log out", () -> {
			App.setCurrentUser(null);
			Helper.getHelper().logout();
			AuthWindow.this.close();
		}));
		
		
		
		horizontalPanel.addComponent(rightPanel.withBorder(Borders.singleLineBevel()));
		
		// This ultimately links in the panels as the window content
		setComponent(horizontalPanel);
	}
}
