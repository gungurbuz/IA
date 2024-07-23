import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.validator.routines.ISBNValidator;

public class Library {
    private static Scanner s = new Scanner(System.in);
    private static String booktitle = "";
    private static ArrayList<String> authors = new ArrayList<String>();
    private static String ISBN = "";
    private static String ISBN13 = "";
    private static Connection con = App.getConnection();

    public static void addBookPublic() {

        boolean check = false;
        Helper.clearConsole();
        System.out.println("Input book title:");
        booktitle = s.nextLine();
        Helper.clearConsole();
        System.out.println("Input book author(s), press enter after each author, press enter on empty line to proceed");
        do {
            String tempinput = s.nextLine();
            if (tempinput.equals("")) {
                check = true;
            } else {
                authors.add(tempinput);
            }
            Helper.clearConsole();
            System.out.println(
                    "Input book author(s), press enter after each author, press enter on empty line to proceed");
        } while (check == false);
        System.out.println("Input ISBN without hypens or spaces");
        ISBN = s.nextLine();
        if (ISBN.length() == 10) {
            ISBN13 = ISBNValidator.getInstance().convertToISBN13(ISBN);
        } else {
            ISBN13 = ISBN;
        }
        boolean isLang = false;

        do {
            HashMap<Integer, String> languageNames = new HashMap<Integer, String>();
            String tempLang = "";
            Helper.clearConsole();
            System.out.println("Choose language from options below or add a new one");
            int i = 1;
            try {
                Statement langstmt = con.createStatement();
                ResultSet langstmtrs = langstmt.executeQuery("select * from language;");
                while (langstmtrs.next()) {
                    tempLang = langstmtrs.getString("languagename");
                    System.out.print(i + ". ");
                    System.out.println(tempLang);
                    languageNames.put(i, tempLang);
                    i = i + 1;
                }
                String langChoice = s.nextLine();
                if(langChoice.equals(null) && !isLang){
                    System.out.println("Invalid input, try again.");
                }
                else if (Objects.nonNull(langChoice)) {
                    int langId = Integer.parseInt(langChoice);
                    if (languageNames.get(langId).equals(null)){
                        System.out.println("Invalid input, please choose a language from the list.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }
        } while (!isLang);
        // System.out.println(booktitle); // debugging stuff
        // for (String i : authors) {
        // System.out.println(i);
        // }
        // System.out.println(ISBN13);
    }

    private void addBook() {
        try {
            PreparedStatement addbookstmt = con.prepareStatement("INSERT INTO book (?, ? )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
