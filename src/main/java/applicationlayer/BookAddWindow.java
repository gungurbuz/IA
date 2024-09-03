package applicationlayer;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Panel;

import java.util.Arrays;

public class BookAddWindow extends BasicWindow {
	
	public BookAddWindow(int progress) {
		super("Library Catalogue System");
		setHints(Arrays.asList(Window.Hint.CENTERED));
		ProgressBar progressBar = new ProgressBar();
		Panel mainPanel = new Panel();
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		Panel progressBarPanel = new Panel();
		progressBarPanel.addComponent(progressBar);
		mainPanel.addComponent(progressBarPanel.withBorder(Borders.singleLineBevel("Book Addition Progress")));
		if (progress < 0 || progress > 100) {
			throw new IllegalArgumentException("Progress value must be between 0 and 100");
		}
		progressBar.setValue(progress);
		setComponent(mainPanel);
	}
	
}