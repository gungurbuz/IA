package applicationlayer;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import org.apache.commons.validator.routines.ISBNValidator;

import businesslayer.Book;
import businesslayer.Helper;
import businesslayer.Library;
import businesslayer.Member;

public class App {
    private static Scanner s = new Scanner(System.in);
    private static Member currentUser;
    private static Book currentBook;

    public static Book getCurrentBook() {
        return currentBook;
    }

    public static void setCurrentBook(Book currentBook) {
        App.currentBook = currentBook;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            try {
                if (Objects.isNull(currentUser)) {
                    System.out.println("Select an option: 1. Signup 2. Login 3. Test 999. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            Helper.getHelper().signup();
                            break;
                        case 2:
                            currentUser = Helper.getHelper().login();
                            if (Objects.nonNull(currentUser)) {
                                System.out.println("Login successful.");
                                Helper.getHelper().wait(100);
                                Helper.getHelper().clearConsole();
                                System.out.println("Welcome " + currentUser.getUsername());
                                Helper.getHelper().readLastLogin(currentUser.getUsername());
                            }
                            break;
                        case 3:
                            // PreparedStatement deletemembers = con.prepareStatement("DELETE FROM
                            // member;");
                            // deletemembers.executeUpdate();
                            System.out.println("no active tests");
                            break;
                        case 999:
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            Helper.getHelper().wait(500);
                            Helper.getHelper().clearConsole();
                    }
                } else {
                    System.out.println("Select an option: 1. Logout 2. Add Book 999. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (choice) {
                        case 1:
                            logout();
                            break;
                        case 2:
                            System.out.println("testing book adder");
                            addBookApp();
                            break;
                        case 999:
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: ");
                e.printStackTrace();
                scanner.nextLine(); // Consume newline if invalid input
            }
        }

        scanner.close();
    }

    private static void addBookApp() {
        String ISBN;
        currentBook = new Book();
        try {
            boolean isAuthor = false;
            Helper.getHelper().clearConsole();
            System.out.println("Input book title:");
            currentBook.setBooktitle(s.nextLine());
            do { // seperating author names by first name/last name
                Helper.getHelper().clearConsole();
                System.out.println("Input author's first name");
                String tempFirstName = s.nextLine();
                Helper.getHelper().clearConsole();
                System.out.println("Input author's last name, or type 999 for no last name");
                String tempLastName = s.nextLine();
                if (tempLastName.equals("999")) {
                    tempLastName = null;
                }
                currentBook.addAuthorFirstNames(tempFirstName);
                currentBook.addAuthorLastNames(tempLastName);
                Helper.getHelper().clearConsole();
                System.out.println(
                        "Press enter on empty line to continue adding authors, or type 999 to continue");
                String continueInput = s.nextLine();
                if (continueInput.equals("999")) {
                    isAuthor = true;
                }
            } while (!isAuthor); // allow for multiple authors to be entered
            Helper.getHelper().clearConsole();
            boolean isISBN = false;
            do{
            System.out.println("Input ISBN without hypens or spaces");
            ISBN = s.nextLine();
            if (ISBN.length() == 10) {
                currentBook.setISBN13(ISBNValidator.getInstance().convertToISBN13(ISBN)); // converts pre-2007 10 digit
                                                                                          // ISBN numbers
                // to
                // the current 13 digit standard
                isISBN = true;
            } else if (ISBN.length() == 13) {
                currentBook.setISBN13(ISBN);
                isISBN = true;
            } else {
                System.out.println("Invalid ISBN, please input 10 or 13 digit ISBN with no hypens or spaces");
            }
        } while (!isISBN);
            HashMap<Integer, String> languageNames = new HashMap<Integer, String>();
            do {
                currentBook.setLangIds(businesslayer.Library.getLibrary().languageSelect(languageNames));
            } while (!currentBook.isLang());
            Helper.getHelper().clearConsole();
            System.out.println("[Optional]Input book genre (press enter to leave empty)");
            String tempGenre = s.nextLine();
            if (Objects.nonNull(tempGenre)) {
                currentBook.setGenre(tempGenre);
            }
            Helper.getHelper().clearConsole();
            System.out.println("Input year of publishing in YYYY format");
            currentBook.setPubYear(s.nextLine());
            HashMap<Integer, String> publishers = new HashMap<Integer, String>();
            do {
                currentBook.setPublisherId(businesslayer.Library.getLibrary().publisherSelect(publishers));
            } while (!currentBook.isPublisher());
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
        Library.getLibrary().addBook(currentBook);
    }

    private static void logout() {
        currentUser = null;
        Helper.getHelper().logout();
        System.out.println("Logged out successfully.");
        Helper.getHelper().wait(500);
        Helper.getHelper().clearConsole();

    }
}
