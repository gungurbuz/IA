package businesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.apache.commons.validator.routines.ISBNValidator;
import databaselayer.DatabaseConnector;

public class Library {
    private Scanner s = new Scanner(System.in);
    private Connection con;
    private static Library library = null;

    private Library() {
        try {
            con = DatabaseConnector.getConnection(); // get connection object created in database layer
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Library getLibrary() {
        if (library == null) {
            library = new Library();
        }
        return library;
    }

    public void addBookPublic() {
        try {
            String booktitle = "";
            ArrayList<String> authorFirstNames = new ArrayList<String>();
            ArrayList<String> authorLastNames = new ArrayList<String>();
            String ISBN = "";
            String ISBN13 = "";
            String genre = "";
            String pubYear = "";
            boolean isLang = false;
            boolean isPublisher = false;
            boolean isAuthor = false;
            Helper.clearConsole();
            System.out.println("Input book title:");
            booktitle = s.nextLine();
            do { // seperating author names by first name/last name
                Helper.clearConsole();
                System.out.println("Input author's first name");
                String tempFirstName = s.nextLine();
                Helper.clearConsole();
                System.out.println("Input author's last name, or type 999 for no last name");
                String tempLastName = s.nextLine();
                if (tempLastName.equals("999")) {
                    tempLastName = null;
                }
                authorFirstNames.add(tempFirstName);
                authorLastNames.add(tempLastName);
                Helper.clearConsole();
                System.out.println(
                        "Press enter on empty line to continue adding authors, or type 999 to continue");
                String continueInput = s.nextLine();
                if (continueInput.equals("999")) {
                    isAuthor = true;
                }
            } while (!isAuthor); // allow for multiple authors to be entered
            Helper.clearConsole();
            System.out.println("Input ISBN without hypens or spaces");
            ISBN = s.nextLine();
            if (ISBN.length() == 10) {
                ISBN13 = ISBNValidator.getInstance().convertToISBN13(ISBN); // converts pre-2007 10 digit ISBN numbers
                                                                            // to
                                                                            // the current 13 digit standard
            } else {
                ISBN13 = ISBN;
            }
            HashMap<Integer, String> languageNames = new HashMap<Integer, String>();
            int langChoice;

            do {
                langChoice = languageSelect(languageNames, isLang);
            } while (!isLang);

            Helper.clearConsole();

            System.out.println("[Optional]Input book genre (press enter to leave empty)");
            String tempGenre = s.nextLine();
            if (Objects.nonNull(tempGenre)) {
                genre = tempGenre;
            }

            Helper.clearConsole();

            System.out.println("Input year of publishing in YYYY format");
            pubYear = s.nextLine();

            HashMap<Integer, String> publishers = new HashMap<Integer, String>();
            int publisherChoice;
            do {
                publisherChoice = publisherSelect(publishers, isPublisher);
            } while (!isPublisher);
            // addBook(); to do
            // System.out.println(booktitle); // debugging stuff
            // for (int i = 0; i < authorFirstNames.size(); i++) {
            // System.out.println(authorFirstNames.get(i));
            // System.out.println(authorLastNames.get(i));
            // }
            // System.out.println(ISBN13);
            // for (String i : languageNames.values()) {
            // System.out.println(i);
            // }
            // try {
            // Statement langstmt = con.createStatement();
            // ResultSet langstmtrs = langstmt.executeQuery("select * from language;");
            // System.out.println(langstmtrs.getString("languagename"));
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        } catch (Exception e) {
            e.printStackTrace();
            // booktitle = ""; // unneeded code
            // authorFirstNames.clear();
            // authorLastNames.clear();
            // ISBN = "";
            // ISBN13 = "";
            // genre = "";
            // pubYear = "";
        }
    }

    private int languageSelect(HashMap<Integer, String> langNames, boolean isLang) {
        Helper.wait(500);
        String tempLang = "";
        Helper.clearConsole();
        System.out.println("Choose language from options below or enter 999 to add a new one");
        int i = 1;
        try {
            Statement langstmt = con.createStatement();
            ResultSet langstmtrs = langstmt.executeQuery("select * from language;");
            while (langstmtrs.next()) { // prints language list for user to choose
                tempLang = langstmtrs.getString("languagename");
                System.out.print(i + ". ");
                System.out.println(tempLang);
                langNames.put(i, tempLang); // adds languages to hashmap to check against when making a choice
                i = i + 1;
            }
            System.out.println("999. Add new language");
            String langChoiceString = s.nextLine();
            if (langChoiceString.equals(null)) {
                System.out.println("Invalid input, please enter a number.");
            } else if (Objects.nonNull(langChoiceString)) {
                int langId = Integer.parseInt(langChoiceString);
                if (Objects.isNull(langNames.get(langId)) && langId != 999) { // checks if user input is a valid
                                                                              // language and not a new language
                                                                              // entry
                    System.out.println("Invalid input, please choose a language from the list.");
                    return 0;
                } else if (Objects.isNull(langNames.get(langId)) && langId == 999) {
                    System.out.println("Type name of new language below and press enter:");
                    String newLang = s.nextLine();
                    PreparedStatement addlangstmt = con
                            .prepareStatement("INSERT INTO language (languagename) VALUES (?);");
                    addlangstmt.setString(1, newLang);
                    addlangstmt.executeUpdate();
                    isLang = true;
                    return i + 1;
                } else if (Objects.nonNull(langNames.get(langId))) {
                    isLang = true;
                    return langId;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input, try again.");
            e.printStackTrace();
        }
        System.out.println("test");
        return 0;
    }

    private int publisherSelect(HashMap<Integer, String> publisherNames, boolean isPublisher) {
        Helper.wait(500);
        String tempPublisher = "";
        Helper.clearConsole();
        System.out.println("Choose publisher from options below or enter 999 to add a new one");
        int i = 1;
        try {
            Statement publisherstmt = con.createStatement();
            ResultSet publisherstmtrs = publisherstmt.executeQuery("select * from publisher;");
            while (publisherstmtrs.next()) { // prints language list for user to choose
                tempPublisher = publisherstmtrs.getString("publishername");
                System.out.print(i + ". ");
                System.out.println(tempPublisher);
                publisherNames.put(i, tempPublisher); // adds publishers to hashmap to check against when making a
                                                      // choice
                i = i + 1;
            }
            System.out.println("999. Add new publisher");
            String PublisherChoiceString = s.nextLine();
            if (PublisherChoiceString.equals(null)) {
                System.out.println("Invalid input, please enter a number.");
            } else if (Objects.nonNull(PublisherChoiceString)) {
                int publisherId = Integer.parseInt(PublisherChoiceString);
                if (Objects.isNull(publisherNames.get(publisherId)) && publisherId != 999) { // checks if user input is
                                                                                             // a valid
                    // publisher and not a new publisher
                    // entry
                    System.out.println("Invalid input, please choose a publisher from the list.");
                    return 0;
                } else if (Objects.isNull(publisherNames.get(publisherId)) && publisherId == 999) {
                    System.out.println("Type name of new publisher below and press enter:");
                    String newPublisher = s.nextLine();
                    PreparedStatement addpubstmt = con
                            .prepareStatement("INSERT INTO publisher (publishername) VALUES (?);");
                    addpubstmt.setString(1, newPublisher);
                    addpubstmt.executeUpdate();
                    isPublisher = true;
                    return i + 1;
                } else if (Objects.nonNull(publisherNames.get(publisherId))) {
                    isPublisher = true;
                    return publisherId;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input, try again.");
            e.printStackTrace();
        }
        System.out.println("test");
        return 0;
    }

    private void addBook() {
        try {
            PreparedStatement addbookstmt = con.prepareStatement("INSERT INTO book (?, ? )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
