package applicationlayer;

import businesslayer.Helper;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.apache.commons.validator.routines.ISBNValidator;

import businesslayer.Book;
import businesslayer.Library;
import businesslayer.Member;

import static databaselayer.GUIConnector.getTextGUI;

public class App {
	
	private static final Scanner s = new Scanner(System.in);
	
	private static AtomicReference<Member> currentUser = new AtomicReference<>();
	private static ThreadLocal<Book> currentBook = new ThreadLocal<>();
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
			MainWindow mainWindow = new MainWindow();
			
			try {
				if (Objects.isNull(currentUser.get())) {
					gui.addWindowAndWait(mainWindow);
					mainWindow.close();
				} else {
					AuthWindow authorizedWindow = new AuthWindow();
					MessageDialog.showMessageDialog(gui, "Last Login:", Helper.getHelper().readLastLogin(currentUser.get().getUsername()));
					gui.addWindowAndWait(authorizedWindow);
					
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		
	}
	
	
	public static void addBookApp() {
		String ISBN;
		currentBook.set(new Book());
		Window currentWindow = null;
		try {
			boolean isAuthor = false;
			TitleWindow titleWindow = new TitleWindow();
			currentWindow = titleWindow;
			gui.addWindowAndWait(titleWindow);
			currentBook.get().setBooktitle(titleWindow.getTitle());
			AuthorsWindow authorsWindow = new AuthorsWindow();
			currentWindow = authorsWindow;
			gui.addWindowAndWait(authorsWindow);
			/*
			testing
			for (int i = 0; i < currentBook.get().getAuthorFirstNames().size(); i++) {
				MessageDialog.showMessageDialog(gui, "Test", currentBook.get().getAuthorFirstNames().get(i) + " " + currentBook.get().getAuthorLastNames().get(i));
			}
			*/
			/*boolean isISBN = false;
			
			TO DO: IMPLEMENT ISBN INPUT WINDOW
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
			HashMap<Integer, String> languageNames = new HashMap<>();
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
			HashMap<Integer, String> publishers = new HashMap<>();
			do {
				currentBook.get().setPublisherId(businesslayer.Library.getLibrary().publisherSelect(publishers));
			} while (!currentBook.get().isPublisher());
			Library.getLibrary().printLibraryModel();
			System.out.println("Select location");
			int locationChoice = s.nextInt();
			currentBook.get().setLocation(Library.getLibraryLocations().get(locationChoice));
			 addBook(); to do
			 System.out.println(booktitle); // debugging stuff
			 for (int i = 0; i < authorFirstNames.size(); i++) {
			 System.out.println(authorFirstNames.get(i));
			 System.out.println(authorLastNames.get(i));
			 }
			 System.out.println(ISBN13);
			 for (String i : languageNames.values()) {
			 System.out.println(i);
			 }
			 try {
			 Statement langstmt = con.createStatement();
			 ResultSet langstmtrs = langstmt.executeQuery("select * from language;");
			 System.out.println(langstmtrs.getString("languagename"));
			 } catch (Exception e) {
			 e.printStackTrace();
			 }
			*/
		} catch (Exception e) {
			currentBook.remove();
			Window errorSource = currentWindow;
			if (((Object) currentWindow) != null) {
				currentWindow.close();
			}
			MessageDialog.showMessageDialog(gui, "Error", "An error occurred:" + errorSource.getClass().getName());
			
		}
		Library.getLibrary().addBook(currentBook.get());
	}
	
	
}
