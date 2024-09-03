package applicationlayer;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Panel;

public class BookAddWindow extends BasicWindow {
	
	public BookAddWindow(int progress) {
		super("Library Catalogue System");
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