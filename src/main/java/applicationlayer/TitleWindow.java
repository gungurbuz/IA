package applicationlayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

public class TitleWindow extends BookAddWindow {
	
	TextBox titleBox;
	
	TitleWindow() {
		super(0);
		Panel titlePanel = new Panel();
		Button exit = new Button("Enter", this::close);
		titleBox = new TextBox(new TerminalSize(30, 1));
		titlePanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		titlePanel.addComponent(titleBox);
		titlePanel.addComponent(exit);
		((Panel) this.getComponent()).addComponent(titlePanel.withBorder(Borders.singleLineBevel()));
	}
	
	public String getTitle() {
		return titleBox.getText();
	}
	
}
