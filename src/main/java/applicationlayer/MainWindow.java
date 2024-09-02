package applicationlayer;

import businesslayer.Helper;
import com.googlecode.lanterna.gui2.*;

public class MainWindow extends BasicWindow{
    public MainWindow() {
        super("Library Catalogue System");
        Panel horizontalPanel = new Panel();
        horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel leftPanel = new Panel();
        leftPanel.addComponent(new Button("Login", () -> {
            Helper.getHelper().login();
            MainWindow.this.close();
        }));
        Panel middlePanel = new Panel();
        middlePanel.addComponent(new Button("Sign Up", () -> {
            Helper.getHelper().signup();
            MainWindow.this.close();
        }));
        Panel rightPanel = new Panel();
        rightPanel.addComponent(new Button("Exit", () -> {
            App.isRunning=false;
            MainWindow.this.close();
        }));



        horizontalPanel.addComponent(rightPanel.withBorder(Borders.singleLineBevel()));

        // This ultimately links in the panels as the window content
        setComponent(horizontalPanel);
    }
}
