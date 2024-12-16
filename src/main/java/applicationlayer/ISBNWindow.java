package applicationlayer;


import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.apache.commons.validator.routines.ISBNValidator;
import com.googlecode.lanterna.TerminalSize;

public class ISBNWindow extends BookAddWindow {
	TextBox ISBNBox;
	String ISBN;
	
	ISBNWindow() {
		super(25);
		Panel ISBNPanel = new Panel();
		Label label = new Label("Enter 10 or 13 digit ISBN without hyphens or spaces");
		Button exit = new Button("Continue", () -> {
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
		ISBNPanel.addComponent(label);
		ISBNPanel.addComponent(ISBNBox);
		ISBNPanel.addComponent(exit);
		((Panel) this.getComponent()).addComponent(ISBNPanel.withBorder(Borders.singleLineBevel()));
	}
	public String getISBN() {
		return ISBN;
	}
}
