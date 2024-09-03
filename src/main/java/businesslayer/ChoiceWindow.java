package businesslayer;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

import java.util.Arrays;

public class ChoiceWindow extends BasicWindow {
	public ChoiceWindow() {
		super("Choice");
		setHints(Arrays.asList(Window.Hint.CENTERED));
		Panel mainPanel = new Panel();
		Panel listPanel = new Panel();
		Panel buttonPanel = new Panel();
		
	}
}
