package businesslayer;

import applicationlayer.App;
import applicationlayer.BookAddWindow;

import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;


public class LocationWindow extends BookAddWindow {
	private Coordinate tempCoord;
	public LocationWindow(){
		super(90);
		setHints(List.of(Hint.CENTERED));
		Panel mainPanel = new Panel();
		Panel tablePanel = new Panel();
		Table<String> libraryModel = Library.getLibraryModel();
		tablePanel.addComponent(libraryModel);
		libraryModel.setCellSelection(true);
		libraryModel.setSelectAction(() -> {
			int selectedRow = libraryModel.getSelectedRow();
			int selectedColumn = libraryModel.getSelectedColumn();
			tempCoord = new Coordinate(selectedRow,selectedColumn);
			App.getCurrentBook().setLocation(tempCoord);
			close();
		});
		mainPanel.addComponent(tablePanel.withBorder(Borders.singleLineBevel()));
		setComponent(mainPanel);
		
	}
}
