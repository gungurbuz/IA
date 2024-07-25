package databaselayer;

import java.io.Console;

public class ConsoleConnector {
    private static Console cons = System.console();

    public static Console getConsole(){
        if (cons == null){
            cons = createConsole();
        }
        return cons;
    }

    private static Console createConsole(){
        return System.console();
    }
}
