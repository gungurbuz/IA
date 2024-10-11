package applicationlayer;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.ProgressBar;

import java.util.List;

public class BookAddWindow extends BasicWindow {
	
	public BookAddWindow(int progress) {
		super("Library Catalogue System");
		setHints(List.of(Hint.CENTERED));
		ProgressBar progressBar = new ProgressBar();
		Panel mainPanel = new Panel();
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		Panel progressBarPanel = new Panel();
		progressBarPanel.addComponent(progressBar);
		mainPanel.addComponent(progressBarPanel.withBorder(Borders.singleLineBevel("Book Addition Progress")));
		progressBar.setValue(progress);
		setComponent(mainPanel);
	}
	
}