package applicationlayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;

public class AuthorsWindow extends BookAddWindow{
	ArrayList<TextBox> authorFirstNameBoxes = new ArrayList<>();
	ArrayList<TextBox> authorLastNameBoxes = new ArrayList<>();
	AuthorsWindow(){
		super(15);
		Panel mainPanel = new Panel();
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		Panel buttonPanel = new Panel();
		buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
		Button addAuthor = new Button("Add Author", () -> mainPanel.addComponent(addAuthorLine()));
		Button exit = new Button("Continue", () -> {
			while (!authorFirstNameBoxes.isEmpty()) {
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
	public Component addAuthorLine(){
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
