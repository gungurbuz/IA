package applicationlayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.List;

public class TitleWindow extends BookAddWindow {
	
	TextBox titleBox;
	
	TitleWindow() {
		super(0);
		setHints(List.of(Hint.CENTERED));
		Panel titlePanel = new Panel();
		Button exit = new Button("Continue", this::close);
		Label label = new Label("Enter Book title");
		titleBox = new TextBox(new TerminalSize(30, 1));
		titlePanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		titlePanel.addComponent(label);
		titlePanel.addComponent(titleBox);
		titlePanel.addComponent(exit);
		((Panel) this.getComponent()).addComponent(titlePanel.withBorder(Borders.singleLineBevel()));
	}
	
	public String getBookTitle() {
		return titleBox.getText();
	}
	
}
