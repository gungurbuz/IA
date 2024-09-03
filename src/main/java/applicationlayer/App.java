package applicationlayer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import databaselayer.GUIConnector;
import org.apache.commons.validator.routines.ISBNValidator;

import businesslayer.Book;
import businesslayer.Library;
import businesslayer.Member;

import static databaselayer.GUIConnector.getTextGUI;

public class App {
	
	private static final Scanner s = new Scanner(System.in);
	
	private static AtomicReference<Member> currentUser = new AtomicReference<Member>();
	private static ThreadLocal<Book> currentBook = new ThreadLocal<Book>();
	private static final WindowBasedTextGUI gui = getTextGUI();
	public static final AtomicBoolean isRunning = new AtomicBoolean(false);
	
	public static synchronized void setCurrentUser(Member currentUser) {
		App.currentUser.set(currentUser);
	}
	
	public static Book getCurrentBook() {
		return currentBook.get();
	}
	
	public static void setIsRunning(boolean isRunning) {
		App.isRunning.set(isRunning);
	}
	
	public static void setCurrentBook(Book currentBook) {
		App.currentBook.set(currentBook);
	}
	
	/**
	 * The main method serves as the entry point for the application.
	 * It initializes the GUI and controls the main loop for the
	 * application, managing the authorization state of the user.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		isRunning.set(true);
		
		while (isRunning.get()) {
			MainWindow unauthorizedWindow = new MainWindow();
			
			try {
				if (Objects.isNull(currentUser.get())) {
					gui.addWindowAndWait(unauthorizedWindow);
					currentUser.set(unauthorizedWindow.returnMember());
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
		currentBook.set(new Book());
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
			currentBook.get().setBooktitle(titleBox.getText());
			
			do { // separating author names by first name/last name
				
				System.out.println("Input author's first name");
				String tempFirstName = s.nextLine();
				
				System.out.println("Input author's last name, or type 999 for no last name");
				String tempLastName = s.nextLine();
				if (tempLastName.equals("999")) {
					tempLastName = null;
				}
				currentBook.get().addAuthorFirstNames(tempFirstName);
				currentBook.get().addAuthorLastNames(tempLastName);
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
					currentBook.get().setISBN13(ISBNValidator.getInstance().convertToISBN13(ISBN)); // converts pre-2007 10
					// digit
					// ISBNs
					// to
					// the current 13 digit standard
					isISBN = true;
				} else if (ISBN.length() == 13) {
					currentBook.get().setISBN13(ISBN);
					isISBN = true;
				} else {
					System.out.println("Invalid ISBN, please input 10 or 13 digit ISBN with no hyphens or spaces");
				}
			} while (!isISBN);
			HashMap<Integer, String> languageNames = new HashMap<Integer, String>();
			do {
				currentBook.get().setLangIds(businesslayer.Library.getLibrary().languageSelect(languageNames));
			} while (!currentBook.get().isLang());
			System.out.println("[Optional]Input book genre (press enter to leave empty)");
			String tempGenre = s.nextLine();
			if (Objects.nonNull(tempGenre)) {
				currentBook.get().setGenre(tempGenre);
			}
			System.out.println("Input year of publishing in YYYY format");
			currentBook.get().setPubYear(s.nextLine());
			HashMap<Integer, String> publishers = new HashMap<Integer, String>();
			do {
				currentBook.get().setPublisherId(businesslayer.Library.getLibrary().publisherSelect(publishers));
			} while (!currentBook.get().isPublisher());
			Library.getLibrary().printLibraryModel();
			System.out.println("Select location");
			int locationChoice = s.nextInt();
			currentBook.get().setLocation(Library.getLibraryLocations().get(locationChoice));
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
		Library.getLibrary().addBook(currentBook.get());
	}
	
	
}
