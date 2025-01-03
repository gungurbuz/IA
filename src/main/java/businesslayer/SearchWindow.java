package businesslayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.sql.SQLException;
import java.util.List;

public class SearchWindow extends BasicWindow {
	public SearchWindow() throws SQLException {
		super ("Search Library");
		setHints(List.of(Hint.CENTERED));
		Panel mainPanel = new Panel();
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		Panel searchPanel = new Panel();
		searchPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		Label searchLabel = new Label("Search in any field:");
		TextBox searchBox = new TextBox(new TerminalSize(30,1));
		Button searchConfirmButton = new Button("Enter", () -> {
			try {
				Library.getLibrary().displaySearchResults(searchBox.getText());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		Panel buttonPanel = new Panel();
		buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		Button exitButton = new Button("Exit", this::close);
		searchPanel.addComponent(searchLabel);
		searchPanel.addComponent(searchBox);
		buttonPanel.addComponent(searchConfirmButton);
		buttonPanel.addComponent(exitButton);
		mainPanel.addComponent(searchPanel);
		mainPanel.addComponent(buttonPanel);
		
		setComponent(mainPanel);
	}
	
	
}
