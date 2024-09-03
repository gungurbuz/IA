package applicationlayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import databaselayer.GUIConnector;
import org.apache.commons.validator.routines.ISBNValidator;

import businesslayer.Book;
import businesslayer.Helper;
import businesslayer.Library;
import businesslayer.Member;

public class App {
	
	private static final Scanner s = new Scanner(System.in);
	
	private static Member currentUser;
	private static Book currentBook;
	private static final WindowBasedTextGUI gui = GUIConnector.getTextGUI();
	public static boolean isRunning;
	
	public static void setCurrentUser(Member currentUser) {
		App.currentUser = currentUser;
	}
	
	public static Book getCurrentBook() {
		return currentBook;
	}
	
	public static void setIsRunning(boolean isRunning) {
		App.isRunning = isRunning;
	}
	
	public static void setCurrentBook(Book currentBook) {
		App.currentBook = currentBook;
	}
	
	/**
	 * The main method serves as the entry point for the application.
	 * It initializes the GUI and controls the main loop for the
	 * application, managing the authorization state of the user.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		isRunning = true;
		
		while (isRunning) {
			MainWindow unauthorizedWindow = new MainWindow();
			
			try {
				if (Objects.isNull(currentUser)) {
					gui.addWindowAndWait(unauthorizedWindow);
					currentUser = unauthorizedWindow.returnMember();
					unauthorizedWindow.close();
				} else {
					AuthWindow authorizedWindow = new AuthWindow();
					gui.addWindowAndWait(authorizedWindow);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	
	private static void addBookApp() {
		String ISBN;
		currentBook = new Book();
		try {
			boolean isAuthor = false;
			BookAddWindow TitleWindow = new BookAddWindow(0);
			Panel TitlePanel = new Panel();
			Button exit = new Button("Enter", TitleWindow::close);
			TextBox titleBox = new TextBox(new TerminalSize(30,1));
			TitlePanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			TitlePanel.addComponent(titleBox);
			TitlePanel.addComponent(exit);
			((Panel) TitleWindow.getComponent()).addComponent(TitlePanel);
			gui.addWindowAndWait(TitleWindow);
			currentBook.setBooktitle(titleBox.getText());
			
			do { // separating author names by first name/last name
				
				System.out.println("Input author's first name");
				String tempFirstName = s.nextLine();
				
				System.out.println("Input author's last name, or type 999 for no last name");
				String tempLastName = s.nextLine();
				if (tempLastName.equals("999")) {
					tempLastName = null;
				}
				currentBook.addAuthorFirstNames(tempFirstName);
				currentBook.addAuthorLastNames(tempLastName);
				System.out.println(
						"Press enter on empty line to continue adding authors, or type 999 to continue");
				String continueInput = s.nextLine();
				if (continueInput.equals("999")) {
					isAuthor = true;
				}
			} while (!isAuthor); // allow for multiple authors to be entered
			boolean isISBN = false;
			do {
				System.out.println("Input ISBN without hyphens or spaces");
				ISBN = s.nextLine();
				if (ISBN.length() == 10) {
					currentBook.setISBN13(ISBNValidator.getInstance().convertToISBN13(ISBN)); // converts pre-2007 10
					// digit
					// ISBNs
					// to
					// the current 13 digit standard
					isISBN = true;
				} else if (ISBN.length() == 13) {
					currentBook.setISBN13(ISBN);
					isISBN = true;
				} else {
					System.out.println("Invalid ISBN, please input 10 or 13 digit ISBN with no hyphens or spaces");
				}
			} while (!isISBN);
			HashMap<Integer, String> languageNames = new HashMap<Integer, String>();
			do {
				currentBook.setLangIds(businesslayer.Library.getLibrary().languageSelect(languageNames));
			} while (!currentBook.isLang());
			System.out.println("[Optional]Input book genre (press enter to leave empty)");
			String tempGenre = s.nextLine();
			if (Objects.nonNull(tempGenre)) {
				currentBook.setGenre(tempGenre);
			}
			System.out.println("Input year of publishing in YYYY format");
			currentBook.setPubYear(s.nextLine());
			HashMap<Integer, String> publishers = new HashMap<Integer, String>();
			do {
				currentBook.setPublisherId(businesslayer.Library.getLibrary().publisherSelect(publishers));
			} while (!currentBook.isPublisher());
			Library.getLibrary().printLibraryModel();
			System.out.println("Select location");
			int locationChoice = s.nextInt();
			currentBook.setLocation(Library.getLibraryLocations().get(locationChoice));
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
		
	}
	
}
