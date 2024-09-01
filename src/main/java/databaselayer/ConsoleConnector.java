package databaselayer;

import java.io.Console;

import static java.lang.System.console;

public class ConsoleConnector {
    private static Console cons;

    public static Console getConsole() {
        if (cons == null) {
            cons = createConsole();
        }
        return cons;
    }

    private static Console createConsole() {
        return console();
    }
}
