package applicationlayer;


import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.apache.commons.validator.routines.ISBNValidator;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

public class ISBNWindow extends BookAddWindow {
	TextBox ISBNBox;
	String ISBN;
	
	ISBNWindow() {
		super(25);
		Panel ISBNPanel = new Panel();
		Button exit = new Button("Enter", () -> {
			if (ISBNValidator.getInstance().isValidISBN13(ISBNBox.getText())){
				ISBN = ISBNBox.getText();
			} else if (ISBNValidator.getInstance().isValid(ISBNBox.getText())){
				ISBN = ISBNValidator.getInstance().convertToISBN13(ISBNBox.getText());
			} else {
				MessageDialog.showMessageDialog(businesslayer.Helper.getGUI(), "Error", "Invalid input:" );
			}
			close();
		});
		ISBNBox = new TextBox(new TerminalSize(30, 1));
		ISBNPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		ISBNPanel.addComponent(ISBNBox);
		ISBNPanel.addComponent(exit);
		((Panel) this.getComponent()).addComponent(ISBNPanel.withBorder(Borders.singleLineBevel()));
	}
	public String getISBN() {
		return ISBN;
	}
}
