package databaselayer;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


import java.io.IOException;

/**
 * GUIConnector is a utility class that provides methods for creating
 * and managing a text-based graphical user interface (GUI) using
 * lanterna library. It includes methods to create and get instances
 * of WindowBasedTextGUI.
 */
public class GUIConnector {
	
	private static Screen screen;
    private static WindowBasedTextGUI gui;
    
    public static void stopScreen(){
	    try {
		    screen.stopScreen();
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }
    }

    public static synchronized WindowBasedTextGUI getTextGUI() {
        if (gui == null) {
            gui = createWindowBasedTextGUI();
        }

        return gui;
    }

    private static WindowBasedTextGUI createWindowBasedTextGUI() {
        WindowBasedTextGUI tempgui;
        try {
	        Terminal terminal = new DefaultTerminalFactory().createTerminal();
	        screen = new TerminalScreen(terminal);
            tempgui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.CYAN));
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Text GUI", e);
        }
        return tempgui;
    }
}
