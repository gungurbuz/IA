package databaselayer;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;


import java.io.IOException;

public class GUIConnector {
    private static SwingTerminal terminal;
    private static Screen screen;
    private static WindowBasedTextGUI gui;

    public static WindowBasedTextGUI getTextGUI() {
        if (gui == null) {
            gui = createWindowBasedTextGUI();
        }

        return gui;
    }

    private static WindowBasedTextGUI createWindowBasedTextGUI() {
        WindowBasedTextGUI tempgui = null;
        try {
            terminal = new SwingTerminal();
            screen = new TerminalScreen(terminal);
            tempgui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.GREEN));
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempgui;
    }
}
