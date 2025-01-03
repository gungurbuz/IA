package applicationlayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import databaselayer.GUIConnector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AuthorsWindow extends BookAddWindow{
	@NotNull WindowBasedTextGUI gui = GUIConnector.getTextGUI();
	@NotNull ArrayList<TextBox> authorFirstNameBoxes = new ArrayList<>();
	@NotNull ArrayList<TextBox> authorLastNameBoxes = new ArrayList<>();
	AuthorsWindow(){
		super(15);
		setHints(List.of(Hint.CENTERED));
		Panel mainPanel = new Panel();
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		Panel buttonPanel = new Panel();
		buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		Button addAuthor = new Button("Add Author", () -> mainPanel.addComponent(addAuthorLine()));
		Button exit = new Button("Continue", () -> {
			while (!authorFirstNameBoxes.isEmpty()) {
				if(authorFirstNameBoxes.getFirst().getText().isEmpty()){
					MessageDialog.showMessageDialog(gui, "Error", "Please input a first name");
				}
				App.getCurrentBook().addAuthorFirstNames(authorFirstNameBoxes.removeFirst().getText());
				App.getCurrentBook().addAuthorLastNames(authorLastNameBoxes.removeFirst().getText());
			}
			this.close();
		});
		buttonPanel.addComponent(addAuthor);
		buttonPanel.addComponent(exit);
		mainPanel.addComponent(buttonPanel.withBorder(Borders.singleLineBevel("Authors")));
		setComponent(mainPanel);
		
		
	}
	private @NotNull Component addAuthorLine(){
		Panel authorPanel = new Panel();
		Panel authorFirstNamePanel = new Panel();
		TextBox authorFirstNameBox = new TextBox(new TerminalSize(40, 1));
		authorFirstNameBoxes.add(authorFirstNameBox);
		authorFirstNamePanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		authorFirstNamePanel.addComponent(authorFirstNameBox);
		Panel authorLastNamePanel = new Panel();
		TextBox authorLastNameBox = new TextBox(new TerminalSize(40, 1));
		authorLastNameBoxes.add(authorLastNameBox);
		authorLastNamePanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		authorLastNamePanel.addComponent(authorLastNameBox);
		authorPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		authorPanel.addComponent(authorFirstNamePanel.withBorder(Borders.singleLine("First Name")));
		authorPanel.addComponent(authorLastNamePanel.withBorder(Borders.singleLine("Last Name")));
		return authorPanel;
	}
}
