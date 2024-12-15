package applicationlayer;

import businesslayer.Helper;
import businesslayer.Library;
import businesslayer.SearchWindow;
import com.googlecode.lanterna.gui2.*;

import java.sql.SQLException;
import java.util.List;

public class AuthWindow extends BasicWindow {
	public AuthWindow() {
		super("Library Catalogue System");
		setHints(List.of(Hint.CENTERED));
		Panel horizontalPanel = new Panel();
		horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		Panel leftPanel = new Panel();
		leftPanel.addComponent(new Button("Search Book", () -> {
			try {
				Window searchWindow = new SearchWindow();
				getTextGUI().addWindowAndWait(searchWindow);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		}));
		Panel middleLeftPanel = new Panel();
		middleLeftPanel.addComponent(new Button("Add Book", App::addBookApp));
		Panel middleRightPanel = new Panel();
		middleRightPanel.addComponent(new Button("Loan Actions", () -> {
			try {
				Library.getLibrary().displayActiveLoans();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}));
		Panel rightPanel = new Panel();
		rightPanel.addComponent(new Button("Log out", () -> {
			App.setCurrentUser(null);
			Helper.getHelper().logout();
			AuthWindow.this.close();
		}));
		
		
		
		horizontalPanel.addComponent(rightPanel.withBorder(Borders.singleLineBevel()));
		horizontalPanel.addComponent(middleLeftPanel.withBorder(Borders.singleLineBevel()));
		horizontalPanel.addComponent(middleRightPanel.withBorder(Borders.singleLineBevel()));
		horizontalPanel.addComponent(leftPanel.withBorder(Borders.singleLineBevel()));
		
		// This ultimately links in the panels as the window content
		setComponent(horizontalPanel);
	}
}
