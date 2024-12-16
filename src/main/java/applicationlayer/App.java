package applicationlayer;

import businesslayer.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

import static databaselayer.GUIConnector.getTextGUI;

public class App {
	
	private static final Scanner s = new Scanner(System.in);
	
	private static Member currentUser = null;
	private static Book currentBook = new Book();
	private static final WindowBasedTextGUI gui = getTextGUI();
	public static final AtomicBoolean isRunning = new AtomicBoolean(false);
	
	public static synchronized void setCurrentUser(Member currentUser) {
		App.currentUser = currentUser;
	}
	
	public static synchronized String getCurrentUserID() {
		return currentUser.getIdmember();
	}
	
	public static Book getCurrentBook() {
		return currentBook;
	}
	
	public static void setIsRunning(boolean isRunning) {
		App.isRunning.set(isRunning);
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
		isRunning.set(true);
		
		while (isRunning.get()) {
			MainWindow mainWindow = new MainWindow();
			
			try {
				if (Objects.isNull(currentUser)) {
					gui.addWindowAndWait(mainWindow);
					mainWindow.close();
				} else {
					AuthWindow authorizedWindow = new AuthWindow();
					MessageDialog.showMessageDialog(gui, "Last Login:", Helper.getHelper().readLastLogin(currentUser.getUsername()));
					gui.addWindowAndWait(authorizedWindow);
					
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		
	}
	public static void addBookApp() {
		String ISBN;
		currentBook = new Book();
		Window currentWindow = null;
		try {
			boolean isAuthor = false;
			currentWindow = new TitleWindow();
			gui.addWindowAndWait(currentWindow);
			currentBook.setBooktitle(((TitleWindow) currentWindow).getBookTitle());
			currentWindow = new AuthorsWindow();
			gui.addWindowAndWait(currentWindow);
			currentWindow = new ISBNWindow();
			gui.addWindowAndWait(currentWindow);
			currentBook.setISBN13(((ISBNWindow)currentWindow).getISBN());
			currentWindow.close();
			HashMap<Integer, String> languageNames = new HashMap<>();
			currentBook.setLangIds(businesslayer.Library.getLibrary().languageSelect(languageNames));
			languageNames = null;
			currentWindow.close();
			HashMap<Integer, String> publishers = new HashMap<>();
			currentBook.setPublisherId(businesslayer.Library.getLibrary().publisherSelect(publishers));
			languageNames = null;
			currentWindow = new LocationWindow();
			gui.addWindowAndWait(currentWindow);
			
			/*
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
			if (currentWindow != null) {
				MessageDialog.showMessageDialog(gui, "Error", "An error occurred:" + currentWindow.getClass().getName());
				currentWindow.close();
			}
			MessageDialog.showMessageDialog(gui, "Error", "An unknown error occurred:" + Arrays.toString(e.getStackTrace()));
		}
		Library.getLibrary().addBook(currentBook);
		new MessageDialogBuilder()
				.setTitle("Book Successfully Added To Database")
				.addButton(MessageDialogButton.OK);
	}
	
	
}
