package businesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import applicationlayer.App;
import databaselayer.DatabaseConnector;

public class Library {
    private Scanner s = new Scanner(System.in);
    private Connection con;
    private PreparedStatement getLastInsertIdStatement;
    private static Library library;

    private Library() {
        try {
            con = DatabaseConnector.getConnection(); // get connection object created in database layer
            getLastInsertIdStatement = con.prepareStatement("SELECT LAST_INSERT_ID();");
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

    public ArrayList<Integer> languageSelect(HashMap<Integer, String> langNames) {
        Helper.getHelper().wait(500);
        String tempLang = "";
        ArrayList<Integer> langIds = new ArrayList<Integer>(); // the language id(s) of the current book
        Helper.getHelper().clearConsole();
        System.out.println("Choose language from options below or enter 999 to add a new one");
        int i = 1;
        try {
            while (!App.getCurrentBook().isLang()) {
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
                        return null;
                    } else if (Objects.isNull(langNames.get(langId)) && langId == 999) {
                        System.out.println("Type name of new language below and press enter:");
                        String newLang = s.nextLine();
                        PreparedStatement addlangstmt = con
                                .prepareStatement("INSERT INTO language (languagename) VALUES (?);");
                        addlangstmt.setString(1, newLang);
                        addlangstmt.executeUpdate();
                        langIds.add(getLastInsertId());

                    } else if (Objects.nonNull(langNames.get(langId))) {

                        langIds.add(langId);
                    }

                }
                boolean continueTest = false;
                do {
                    System.out.println("Do you want to add another language? (Y/N)");
                    String continueString = s.nextLine();
                    if (continueString.toUpperCase().equals("N")) {
                        App.getCurrentBook().setLang(true);
                        continueTest = true;
                    } else if (continueString.toUpperCase().equals("Y")) {
                        continueTest = true;
                    } else {
                        System.out.println("Invalid input, try again.");
                    }
                } while (!continueTest);
            }

        } catch (Exception e) {
            System.out.println("Invalid input, try again.");
            e.printStackTrace();
        }
        return langIds;
    }

    public int publisherSelect(HashMap<Integer, String> publisherNames) {
        Helper.getHelper().wait(500);
        String tempPublisher = "";
        int tempPublisherId;
        Helper.getHelper().clearConsole();
        System.out.println("Choose publisher from options below or enter 999 to add a new one");
        try {
            Statement publisherstmt = con.createStatement();
            ResultSet publisherstmtrs = publisherstmt.executeQuery("select * from publisher;");
            while (publisherstmtrs.next()) { // prints language list for user to choose
                tempPublisher = publisherstmtrs.getString("publishername");
                tempPublisherId = publisherstmtrs.getInt("idpublisher");
                System.out.print(tempPublisherId + ". ");
                System.out.println(tempPublisher);
                publisherNames.put(tempPublisherId, tempPublisher); // adds publishers to hashmap to check against when
                                                                    // making a
                // choice
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
                    return getLastInsertId();
                } else if (Objects.nonNull(publisherNames.get(publisherId))) {
                    App.getCurrentBook().setPublisher(true);
                    System.out.println(publisherNames.get(publisherId)); //debugging
                    return publisherId;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input, try again.");
            e.printStackTrace();
        }
        System.out.println("error");
        return 0;
    }

    public void addBook(Book currentBook) { // to be completed
        try {
            PreparedStatement addBookStatement = con.prepareStatement(
                    "INSERT INTO book (bookname, isbn, genre, pubyear, idpublisher) VALUES (?, ?, ?, ?, ?);");
            addBookStatement.setString(1, currentBook.getBooktitle());
            addBookStatement.setString(2, currentBook.getISBN13());
            if (currentBook.getGenre().equals("")) {
                addBookStatement.setString(3, null);
            } else {
                addBookStatement.setString(3, currentBook.getGenre());
            }
            addBookStatement.setString(4, currentBook.getPubYear());
            addBookStatement.setInt(5, currentBook.getPublisherId());
            addBookStatement.executeUpdate();
            int bookId = getLastInsertId();

            Stack<Integer> authorIDStack = new Stack<Integer>();
            PreparedStatement addBookAuthorsStatement = con
                    .prepareStatement("INSERT INTO author (authorfname, authorsname) VALUES (?, ?);");
            for (int i = 0; i < currentBook.getAuthorFirstNames().size(); i++) {
                addBookAuthorsStatement.setString(1, currentBook.getAuthorFirstNames().get(i));
                addBookAuthorsStatement.setString(2, currentBook.getAuthorLastNames().get(i));
                addBookAuthorsStatement.executeUpdate();
                authorIDStack.push(getLastInsertId());
            }
            PreparedStatement addBookAuthorsToBridgeStatement = con
                    .prepareStatement("INSERT INTO bookauthors VALUES (?, ?);");
            for (int i = 1; i < authorIDStack.size(); i++) {
                addBookAuthorsToBridgeStatement.setInt(1, bookId);
                addBookAuthorsToBridgeStatement.setInt(2, authorIDStack.pop());
                addBookAuthorsToBridgeStatement.executeUpdate();
            }
            PreparedStatement addLanguagesToBridgeStatement = con
                    .prepareStatement("INSERT INTO booklanguages VALUES (?, ?);");
            for (int i = 0; i < currentBook.getLangIds().size(); i++) {
                addLanguagesToBridgeStatement.setInt(1, bookId);
                addLanguagesToBridgeStatement.setInt(2, currentBook.getLangIds().get(i));
                addLanguagesToBridgeStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getLastInsertId() throws Exception {
        getLastInsertIdStatement.executeQuery();
        ResultSet getLastInsertIdStatementResultSet = getLastInsertIdStatement.getResultSet();
        getLastInsertIdStatementResultSet.next();
        return getLastInsertIdStatementResultSet.getInt(1);
    }

}
