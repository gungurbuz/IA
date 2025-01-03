package businesslayer;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

import applicationlayer.App;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import databaselayer.DatabaseConnector;
import databaselayer.GUIConnector;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Library {
	
	private final Scanner s = new Scanner(System.in);
	private final Connection con;
	private final PreparedStatement getLastInsertIdStatement;
	private static Library library;
	private static final HashMap<Integer, Coordinate> libraryLocations = new HashMap<>();
	private static final WindowBasedTextGUI gui = GUIConnector.getTextGUI();
	
	public static HashMap<Integer, Coordinate> getLibraryLocations() {
		return libraryLocations;
	}
	
	private Library() {
		try {
			con = DatabaseConnector.getConnection(); // get connection object created in database layer
			getLastInsertIdStatement = con.prepareStatement("SELECT LAST_INSERT_ID();");
			libraryLocations.put(1, new Coordinate(1, 1));
			libraryLocations.put(2, new Coordinate(2, 1));
			libraryLocations.put(3, new Coordinate(4, 1));
			libraryLocations.put(4, new Coordinate(6, 1));
			libraryLocations.put(5, new Coordinate(7, 1));
			libraryLocations.put(6, new Coordinate(1, 2));
			libraryLocations.put(7, new Coordinate(2, 2));
			libraryLocations.put(8, new Coordinate(3, 2));
			libraryLocations.put(9, new Coordinate(4, 2));
			libraryLocations.put(10, new Coordinate(5, 2));
			libraryLocations.put(11, new Coordinate(6, 2));
			libraryLocations.put(12, new Coordinate(7, 2));
			libraryLocations.put(13, new Coordinate(1, 3));
			libraryLocations.put(14, new Coordinate(2, 3));
			libraryLocations.put(15, new Coordinate(6, 3));
			libraryLocations.put(16, new Coordinate(7, 3));
			libraryLocations.put(17, new Coordinate(1, 4));
			libraryLocations.put(18, new Coordinate(2, 4));
			libraryLocations.put(19, new Coordinate(6, 4));
			libraryLocations.put(20, new Coordinate(7, 4));
			libraryLocations.put(21, new Coordinate(2, 5));
			libraryLocations.put(22, new Coordinate(6, 5));
			libraryLocations.put(23, new Coordinate(7, 5));
			libraryLocations.put(24, new Coordinate(1, 6));
			libraryLocations.put(25, new Coordinate(2, 6));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Library getLibrary() {
		if (library == null) {
			library = new Library();
		}
		return library;
	}
	
	public Coordinate setLocation(int x, int y) {
		return new Coordinate(x, y);
	}
	
	/*public ArrayList<Integer> languageSelect(HashMap<Integer, String> langNames) {
		String tempLang;
		ArrayList<Integer> langIds = new ArrayList<>(); // the language id(s) of the current book
		int i = 1;
		try {
			while (!App.getCurrentBook().isLang()) {
				Statement langstmt = con.createStatement();
				ResultSet langstmtrs = langstmt.executeQuery("select * from language;");
				TerminalSize size = new TerminalSize(20, 14);
				CheckBoxList<String> langList = new CheckBoxList<>(size);
				while (langstmtrs.next()) { // prints language list for user to choose
					tempLang = langstmtrs.getString("languagename");
					langNames.put(i, tempLang); // adds languages to hashmap to check against when making a choice
					langList.addItem(tempLang);
					i = i + 1;
				}
				BasicWindow langListHolder = new BasicWindow("Choose languages from options below or select Add to add a new one");
				langListHolder.setComponent(langList);
				String langChoiceString = s.nextLine();
				if (langChoiceString.equals(null)) {
					System.out.println("Invalid input, please enter a number.");
				} else if (nonNull(langChoiceString)) {
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
						
					} else if (nonNull(langNames.get(langId))) {
						
						langIds.add(langId);
					}
					
				}
				boolean continueTest = false;
				do {
					System.out.println("Do you want to add another language? (Y/N)");
					String continueString = s.nextLine();
					if (continueString.equalsIgnoreCase("N")) {
						App.getCurrentBook().setLang(true);
						continueTest = true;
					} else if (continueString.equalsIgnoreCase("Y")) {
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
	*/
	
	public ArrayList<Integer> languageSelect(HashMap<Integer, String> langNames) {
		ArrayList<Integer> langIds = new ArrayList<>();
		// Create main window
		BasicWindow languageWindow = new BasicWindow("Select Languages");
		languageWindow.setHints(List.of(Window.Hint.CENTERED));
		try {
			while (!App.getCurrentBook().isLang()) {
				// Clear previous entries
				langNames.clear();
				
				Panel mainPanel = new Panel();
				mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
				
				// Create table
				TerminalSize tableSize = new TerminalSize(60, 20);
				Table<String> languageTable = new Table<>("ID", "Language Name");
				languageTable.setPreferredSize(tableSize);
				
				// Fetch languages from database
				Statement langstmt = con.createStatement();
				ResultSet langstmtrs = langstmt.executeQuery("SELECT * FROM language;");
				
				// Populate table and language names map
				while (langstmtrs.next()) {
					int languageId = langstmtrs.getInt("idlang");
					String tempLang = langstmtrs.getString("languagename");
					
					// Store language in the map
					langNames.put(languageId, tempLang);
					
					// Add to table
					languageTable.getTableModel().addRow(String.valueOf(languageId), tempLang);
				}
				
				// Add select button
				languageTable.setSelectAction(() -> {
					new ActionListDialogBuilder()
							.setTitle(languageTable.getTableModel().getCell(1, languageTable.getSelectedRow()))
							.addAction("Confirm Language?", () -> {
								int selectedRow = languageTable.getSelectedRow();
								
								if (selectedRow >= 0) {
									int languageId = Integer.parseInt(languageTable.getTableModel().getCell(0, selectedRow));
									langIds.add(languageId);
									
								} else {
									MessageDialog.showMessageDialog(
											GUIConnector.getTextGUI(),
											"Error",
											"Please select a language"
									);
								}
							})
							.build()
							.showDialog(gui);
				});
				
				// Add new language button
				Button addNewLanguageButton = new Button("Add New", () -> {
					String newLang = new TextInputDialogBuilder()
							.setTitle("Add New Language")
							.setDescription("Enter the name of the new language:")
							.build()
							.showDialog(gui);
					
					if (newLang != null && !newLang.isEmpty()) {
						try {
							PreparedStatement addlangstmt = con
									.prepareStatement("INSERT INTO language (languagename) VALUES (?);");
							addlangstmt.setString(1, newLang);
							addlangstmt.executeUpdate();
							
							// Get the ID of the newly inserted language
							int newLanguageId = getLastInsertId();
							
							// Refresh table
							languageTable.getTableModel().addRow(
									String.valueOf(newLanguageId),
									newLang
							);
							
							// Prompt for another language
							MessageDialogButton response = MessageDialog.showMessageDialog(
									GUIConnector.getTextGUI(),
									"Add Another",
									"Do you want to add/choose another language?",
									MessageDialogButton.Yes, MessageDialogButton.No
							);
							
							if (response == MessageDialogButton.No) {
								App.getCurrentBook().setLang(true);
								languageWindow.close();
							}
						} catch (Exception e) {
							MessageDialog.showMessageDialog(
									GUIConnector.getTextGUI(),
									"Error",
									"Failed to add new language: " + e.getMessage()
							);
						}
					}
				});
				Button confirmLanguages = new Button("Continue", () -> {
					App.getCurrentBook().setLang(true);
					languageWindow.close();
				});
				// Create button panel
				Panel buttonPanel = new Panel();
				buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
				buttonPanel.addComponent(addNewLanguageButton);
				buttonPanel.addComponent(confirmLanguages);
				
				// Assemble main panel
				mainPanel.addComponent(languageTable);
				mainPanel.addComponent(buttonPanel);
				
				languageWindow.setComponent(mainPanel);
				
				// Show the window and wait for user interaction
				GUIConnector.getTextGUI().addWindowAndWait(languageWindow);
			}
		} catch (Exception e) {
			MessageDialog.showMessageDialog(
					GUIConnector.getTextGUI(),
					"Error",
					"Error in language selection: " + e.getMessage()
			);
			System.out.println(e.getMessage());
			languageWindow.close();
		}
		languageWindow.close();
		return langIds;
	}
	
	int tempPub;
	
	private void setTempPub(int i){
		tempPub = i;
	}
	
	private int getTempPub(){
		return tempPub;
	}
	
	
	public int publisherSelect(HashMap<Integer, String> publisherNames) {
		tempPub = 0;
		try {
			// Clear previous entries
			publisherNames.clear();
			// Create main window
			BasicWindow publisherWindow = new BasicWindow("Select Publisher");
			publisherWindow.setHints(List.of(Window.Hint.CENTERED));
			Panel mainPanel = new Panel();
			mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
			
			// Create table
			TerminalSize tableSize = new TerminalSize(80, 20);
			Table<String> publisherTable = new Table<>("ID", "Publisher Name");
			publisherTable.setPreferredSize(tableSize);
			
			// Fetch publishers from database
			String publisherFetchSQL = "SELECT * FROM publisher;";
			PreparedStatement publisherstmt = con.prepareStatement(publisherFetchSQL);
			ResultSet publisherstmtrs = publisherstmt.executeQuery();
			
			// Populate table and publisher names map
			while (publisherstmtrs.next()) {
				int tempPublisherId = (publisherstmtrs.getInt("idpublisher"));
				String tempPublisher = publisherstmtrs.getString("publishername");
				
				// Store publisher in the map
				publisherNames.put(tempPublisherId, tempPublisher);
				
				// Add to table
				publisherTable.getTableModel().addRow(
						String.valueOf(tempPublisherId),
						tempPublisher
				);
			}
			publisherTable.setSelectAction(() -> {
				new ActionListDialogBuilder()
						.setTitle(publisherTable.getTableModel().getCell(1, publisherTable.getSelectedRow()))
						.addAction("Select Publisher", () -> {
							int selectedRow = publisherTable.getSelectedRow();
							
							if (selectedRow >= 0) {
								 setTempPub(Integer.parseInt(publisherTable.getTableModel().getCell(0, selectedRow)));
								App.getCurrentBook().setPublisher(true);
								publisherWindow.close();
								GUIConnector.getTextGUI().removeWindow(publisherWindow);
							} else {
								MessageDialog.showMessageDialog(
										GUIConnector.getTextGUI(),
										"Error",
										"Please select a publisher"
								);
							}
						})
						.build()
						.showDialog(gui);
			});
			Button addNewPublisherButton = new Button("Add New", () -> {
				String newPublisher = new TextInputDialogBuilder()
						.setTitle("Add New Publisher")
						.setDescription("Enter the name of the new publisher:")
						.build()
						.showDialog(gui);
						
				
				if (newPublisher != null && !newPublisher.isEmpty()) {
					try {
						String addPublisherSQL = "INSERT INTO publisher (publishername) VALUES (?);";
						PreparedStatement addpubstmt = con.prepareStatement(addPublisherSQL);
						addpubstmt.setString(1, newPublisher);
						addpubstmt.executeUpdate();
						
						// Get the ID of the newly inserted publisher
						int newPublisherId = getLastInsertId();
						
						// Add to table
						publisherTable.getTableModel().addRow(
								String.valueOf(newPublisherId),
								newPublisher
						);
						
					} catch (Exception e) {
						MessageDialog.showMessageDialog(
								GUIConnector.getTextGUI(),
								"Error",
								"Failed to add new publisher: " + e.getMessage()
						);
					}
				}
			});
			
			// Create button panel
			Panel buttonPanel = new Panel();
			buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
			buttonPanel.addComponent(addNewPublisherButton);
			
			// Assemble main panel
			mainPanel.addComponent(publisherTable);
			mainPanel.addComponent(buttonPanel);
			
			publisherWindow.setComponent(mainPanel);
			
			// Show the window and wait for user interaction
			gui.addWindowAndWait(publisherWindow);
		} catch (Exception e) {
			MessageDialog.showMessageDialog(
					GUIConnector.getTextGUI(),
					"Error",
					"Error in publisher selection: " + e.getMessage()
			);
			MessageDialog.showMessageDialog(gui, "Error", "Invalid input:" + e.getMessage());
			throw new RuntimeException(e);
		}
		
		return tempPub;
	}
	
	/*public int publisherSelect(HashMap<Integer, String> publisherNames) {
		Helper.getHelper().wait(500);
		String tempPublisher;
		int tempPublisherId;
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
			} else if (nonNull(PublisherChoiceString)) {
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
				} else if (nonNull(publisherNames.get(publisherId))) {
					App.getCurrentBook().setPublisher(true);
					System.out.println(publisherNames.get(publisherId)); // debugging
					return publisherId;
				}
			}
		} catch (Exception e) {
			MessageDialog.showMessageDialog(gui, "Error", "Invalid input:" + e.getMessage());
			throw new RuntimeException(e);
		}
		System.out.println("error");
		return 0;
	}*/
	
	public void addBook(Book currentBook) { // to be completed
		try {
			PreparedStatement addBookStatement = con.prepareStatement(
					"INSERT INTO book (pubyear, bookname, isbn, genre, idpublisher, locationx, locationy) VALUES (?, ?, ?, ?, ?, ?, ?);");
			addBookStatement.setString(2, currentBook.getBooktitle());
			addBookStatement.setString(3, currentBook.getISBN13());
			if (currentBook.getGenre().isEmpty()) {
				addBookStatement.setString(4, null);
			} else {
				addBookStatement.setString(4, currentBook.getGenre());
			}
			if (currentBook.getPubYear().isEmpty()){
				addBookStatement.setString(1, null);
			}else {
				addBookStatement.setString(1, currentBook.getPubYear());
			}
			addBookStatement.setInt(5, currentBook.getPublisherId());
			addBookStatement.setInt(6, currentBook.getLocation().getX());
			addBookStatement.setInt(7, currentBook.getLocation().getY());
			addBookStatement.executeUpdate();
			int bookId = getLastInsertId();
			
			Stack<Integer> authorIDStack = new Stack<>();
			PreparedStatement addBookAuthorsStatement = con
					.prepareStatement("INSERT INTO author (authorfname, authorsname) VALUES (?, ?);");
			for (int i = 0; i < currentBook.getAuthorFirstNames().size(); i++) {
				addBookAuthorsStatement.setString(1, currentBook.getAuthorFirstNames().get(i));
				addBookAuthorsStatement.setString(2, currentBook.getAuthorLastNames().get(i));
				addBookAuthorsStatement.executeUpdate();
				authorIDStack.push(getLastInsertId());
			}
			PreparedStatement addBookAuthorsToBridgeStatement =
					con
							.prepareStatement("INSERT INTO bookauthors VALUES (?, ?);");
			for (int i = 1; i <= authorIDStack.size(); i++) {
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
			throw new RuntimeException(e);
		}
	}
	
	public List<Book> searchBooks(String searchTerm) throws SQLException {
		List<Book> results = new ArrayList<>();
		CallableStatement searchstmt = con.prepareCall("CALL SearchBooks(?)");
		{
			
			// Trim and validate search term
			if (searchTerm == null || searchTerm.trim().isEmpty()) {
				return results;
			}
			
			searchstmt.setString(1, searchTerm.trim());
			
			try (ResultSet rs = searchstmt.executeQuery()) {
				while (rs.next()) {
					// Create a new Book instance
					Book book = new Book();
					
					// Set basic book information
					book.setBookid(rs.getString("idbook"));
					book.setBooktitle(rs.getString("bookname"));
					book.setGenre(rs.getString("genre"));
					book.setPubYear(rs.getString("pubyear"));
					book.setISBN13(rs.getString("isbn"));
					book.setLocation(new Coordinate(rs.getInt("locationx"), rs.getInt("locationy")));
					
					// Process authors
					processAuthors(book, rs.getString("authors"));
					
					// Process languages
					processLanguages(book, con, book.getBooktitle());
					
					// Set publisher information
					book.setPublisherId(getPublisherId(con, rs.getString("publishername")));
					book.setPublisher(true);
					
					results.add(book);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		return results;
	}
	
	/**
	 * Process authors for a book
	 *
	 * @param book          The book to add authors to
	 * @param authorsString Comma-separated list of authors
	 */
	private void processAuthors(Book book, String authorsString) {
		if (authorsString == null || authorsString.isEmpty()) {
			return;
		}
		
		String[] authors = authorsString.split(",");
		for (String fullName : authors) {
			fullName = fullName.trim();
			String[] nameParts = fullName.split("\\s+");
			
			if (nameParts.length > 0) {
				// First name is the first part
				book.addAuthorFirstNames(nameParts[0]);
				
				// If there are more parts, consider the last part as last name
				if (nameParts.length > 1) {
					book.addAuthorLastNames(nameParts[nameParts.length - 1]);
				}
			}
		}
	}
	
	/**
	 * Process languages for a book
	 *
	 * @param book      The book to add languages to
	 * @param conn      Database connection
	 * @param bookTitle The title of the book
	 */
	private void processLanguages(Book book, Connection conn, String bookTitle) {
		try (PreparedStatement stmt = conn.prepareStatement(
				"SELECT idlang FROM booklanguages bl " +
						"JOIN book b ON bl.idbook = b.idbook " +
						"WHERE b.bookname = ?")) {
			
			stmt.setString(1, bookTitle);
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					book.addLangIds(rs.getInt("idlang"));
				}
			}
			
			// Set language flag if languages exist
			book.setLang(!book.getLangIds().isEmpty());
		} catch (SQLException e) {
			System.err.println("Error fetching languages: " + e.getMessage());
		}
	}
	
	/**
	 * Get publisher ID for a given publisher name
	 *
	 * @param conn          Database connection
	 * @param publisherName Name of the publisher
	 * @return Publisher ID
	 */
	private int getPublisherId(Connection conn, String publisherName) {
		try (PreparedStatement stmt = conn.prepareStatement(
				"SELECT idpublisher FROM publisher WHERE publishername = ?")) {
			
			stmt.setString(1, publisherName);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("idpublisher");
				}
			}
		} catch (SQLException e) {
			System.err.println("Error fetching publisher ID: " + e.getMessage());
		}
		return -1; // Return -1 if no publisher found
	}
	
	/**
	 * Combine author first and last names
	 *
	 * @param book Book with authors
	 * @return Comma-separated list of author names
	 */
	private String combineAuthors(Book book) {
		List<String> authorFirstNames = book.getAuthorFirstNames();
		List<String> authorLastNames = book.getAuthorLastNames();
		
		StringBuilder authorsBuilder = new StringBuilder();
		
		for (int i = 0; i < authorFirstNames.size(); i++) {
			if (i > 0) {
				authorsBuilder.append(", ");
			}
			
			authorsBuilder.append(authorFirstNames.get(i));
			
			if (i < authorLastNames.size()) {
				authorsBuilder.append(" ").append(authorLastNames.get(i));
			}
		}
		
		return authorsBuilder.toString();
	}
	
	/**
	 * Displays search results in a table within the given window
	 *
	 * @param searchTerm The search term
	 */
	public void displaySearchResults(String searchTerm) throws SQLException {
		// Perform the search
		List<Book> results = searchBooks(searchTerm);
		
		// If no results, show a message dialog
		if (results.isEmpty()) {
			new MessageDialogBuilder()
					.setTitle("Search Results")
					.setText("No books found matching '" + searchTerm + "'")
					.addButton(MessageDialogButton.Close)
					.build()
					.showDialog(gui);
			return;
		}
		
		// Prepare table data
		String[] columnLabels = {
				"Book ID", "Title", "Authors", "Genre", "Publish Year", "ISBN", "X Position", "Y Position"
		};
		
		// Convert results to 2D array for table
		String[][] tableData = new String[results.size()][];
		for (int i = 0; i < results.size(); i++) {
			Book book = results.get(i);
			
			// Combine author names
			String authors = combineAuthors(book);
			
			tableData[i] = new String[]{
					book.getBookid(),
					book.getBooktitle(),
					authors,
					book.getGenre(),
					book.getPubYear(),
					book.getISBN13(),
					book.getBookX(),
					book.getBookY()
			};
		}
		
		// Create a new window for results
		BasicWindow resultsWindow = new BasicWindow("Search Results");
		Panel mainPanel = new Panel();
		resultsWindow.setHints(List.of(Window.Hint.CENTERED));
		
		// Create table
		Table<String> resultsTable = new Table<>(columnLabels);
		for (String[] tableDatum : tableData) {
			resultsTable.getTableModel().addRow(tableDatum);
		}
		resultsTable.setSelectAction(() -> {
			new ActionListDialogBuilder()
					.setTitle("Book Actions")
					.addAction("Take out", () -> {
						int bookID = Integer.parseInt(resultsTable.getTableModel().getCell(0, resultsTable.getSelectedRow()));
						String loanerName;
						try {
							loanerName = isBookOutOnLoan(con, bookID);
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
						if (isNull(loanerName)) {
							try (PreparedStatement loanStatement = con.prepareStatement("INSERT INTO loan (idmember, takedate, idbook, returnbydate) VALUES (?, CURDATE(), ?, DATE_ADD(CURDATE(), INTERVAL 2 WEEK))")) {
								loanStatement.setInt(1, Integer.parseInt(App.getCurrentUserID())); // Set the idmember value
								loanStatement.setInt(2, bookID);   // Set the idbook value
								loanStatement.executeUpdate();
							} catch (SQLException e) {
								throw new RuntimeException(e);
							}
						
						new MessageDialogBuilder()
								.setTitle("Loan Successful")
								.setText("Return by date: " + Helper.getHelper().timeStamp())
								.addButton(MessageDialogButton.Close)
								.build()
								.showDialog(gui);
						resultsWindow.close();
					}else {
							new MessageDialogBuilder()
									.setTitle("Loan Error")
									.setText("The book you selected is currently on loan to: " + loanerName)
									.addButton(MessageDialogButton.Close)
									.build()
									.showDialog(gui);
						}
					})
					.build()
					.showDialog(gui);
		});
		
		// Set table size and add to panel
		resultsTable.setPreferredSize(new TerminalSize(120, 20));
		mainPanel.addComponent(resultsTable);
		
		// Add a close button
		Button closeButton = new Button("Close", resultsWindow::close);
		mainPanel.addComponent(closeButton);
		
		// Set window content and show
		resultsWindow.setComponent(mainPanel);
		gui.addWindow(resultsWindow);
	}
	
	public static void updateReturnBy(Connection connection, int idloan, int days) throws SQLException {
		String sql = "UPDATE loan " +
				"SET returnbydate = DATE_ADD(returnbydate, INTERVAL ? DAY) " +
				"WHERE idloan = ? AND returndate IS NULL";
		try (PreparedStatement updateReturnStatement = connection.prepareStatement(sql)) {
			// Set the parameters for the query
			updateReturnStatement.setInt(1, days);
			updateReturnStatement.setInt(2, idloan);
			
			// Execute the update query
			int rowsAffected = updateReturnStatement.executeUpdate();
		}
	}
	
	public void displayActiveLoans() throws SQLException {
		// Retrieve the current user's active loans
		List<Loan> activeLoans = getActiveLoans(Integer.parseInt(App.getCurrentUserID()));
		
		// If no active loans, show a message dialog
		if (activeLoans.isEmpty()) {
			new MessageDialogBuilder()
					.setTitle("Active Loans")
					.setText("You currently have no active loans.")
					.addButton(MessageDialogButton.Close)
					.build()
					.showDialog(gui);
			return;
		}
		
		// Prepare table data
		String[] columnLabels = {
				"Loan ID", "Book Title", "Take Date", "Return By Date"
		};
		
		// Convert results to 2D array for table
		String[][] tableData = new String[activeLoans.size()][];
		for (int i = 0; i < activeLoans.size(); i++) {
			Loan loan = activeLoans.get(i);
			tableData[i] = new String[]{
					String.valueOf(loan.getLoanId()),
					loan.getBookTitle(),
					loan.getTakeDate().toString(),
					loan.getReturnByDate().toString()
			};
		}
		
		// Create a new window for results
		BasicWindow loansWindow = new BasicWindow("Your Active Loans");
		loansWindow.setHints(List.of(Window.Hint.CENTERED));
		Panel mainPanel = new Panel();
		
		// Create table
		Table<String> loansTable = new Table<>(columnLabels);
		for (String[] tableDatum : tableData) {
			loansTable.getTableModel().addRow(tableDatum);
		}
		
		// Set select action for the table
		loansTable.setSelectAction(() -> {
			new ActionListDialogBuilder()
					.setTitle("Loan Actions")
					.addAction("Return Book", () -> {
						int loanID = Integer.parseInt(loansTable.getTableModel().getCell(0, loansTable.getSelectedRow()));
						try {
							returnBook(loanID);
							new MessageDialogBuilder()
									.setTitle("Return Successful")
									.setText("The book has been successfully returned.")
									.addButton(MessageDialogButton.Close)
									.build()
									.showDialog(gui);
							loansWindow.close();
						} catch (SQLException e) {
							new MessageDialogBuilder()
									.setTitle("Error")
									.setText("An error occurred while returning the book: " + e.getMessage())
									.addButton(MessageDialogButton.Close)
									.build()
									.showDialog(gui);
						}
					})
					.addAction("Extend Loan", () ->{
						int loanID = Integer.parseInt(loansTable.getTableModel().getCell(0, loansTable.getSelectedRow()));
						int extension = Integer.parseInt(new TextInputDialogBuilder()
								
								.setTitle("Loan Extension")
								.setDescription("How many more days from current return by date?")
								.setValidationPattern(Pattern.compile("^\\d+$"), "Enter a number")
								.build()
								.showDialog(gui));
						try {
							updateReturnBy(con, loanID, extension);
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					})
					.build()
					.showDialog(gui);
		});
		
		// Set table size and add to panel
		loansTable.setPreferredSize(new TerminalSize(80, 20));
		mainPanel.addComponent(loansTable);
		
		// Add a close button
		Button closeButton = new Button("Close", loansWindow::close);
		mainPanel.addComponent(closeButton);
		
		// Set window content and show
		loansWindow.setComponent(mainPanel);
		gui.addWindow(loansWindow);
	}
	
	// Helper method to retrieve active loans for a user
	private List<Loan> getActiveLoans(int userId) throws SQLException {
		List<Loan> loans = new ArrayList<>();
		String query = "SELECT l.idloan, b.bookname, l.takedate, l.returnbydate " +
				"FROM loan l " +
				"JOIN book b ON l.idbook = b.idbook " +
				"WHERE l.idmember = ? AND l.returndate IS NULL";
		
		try (PreparedStatement statement = con.prepareStatement(query)) {
			statement.setInt(1, userId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Loan loan = new Loan(
							rs.getInt("idloan"),
							rs.getString("bookname"),
							rs.getDate("takedate"),
							rs.getDate("returnbydate")
					);
					loans.add(loan);
				}
			}
		}
		return loans;
	}
	
	// Helper method to return a book
	private void returnBook(int loanId) throws SQLException {
		String updateQuery = "UPDATE loan SET returndate = CURDATE() WHERE idloan = ?";
		
		try (PreparedStatement statement = con.prepareStatement(updateQuery)) {
			statement.setInt(1, loanId);
			statement.executeUpdate();
		}
	}
	
	@org.jetbrains.annotations.Nullable
	public static String isBookOutOnLoan(Connection connection, int bookId) throws SQLException {
		// SQL query to check if a book is out on loan
		String sql = "SELECT m.fname AS first_name, m.sname AS last_name " +
				"FROM loan l " +
				"JOIN member m ON l.idmember = m.idmember " +
				"WHERE l.idbook = ? AND l.returndate IS NULL";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			// Set the book ID parameter
			preparedStatement.setInt(1, bookId);
			
			// Execute the query
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					// Retrieve the count of active loans
					String firstName = resultSet.getString("first_name");
					String lastName = resultSet.getString("last_name");
					return firstName + " " + lastName; // Return the full name of the user
				} else {
					return null;
				}
			}
		}
	}
	
	public static boolean returnBook(Connection connection, int bookId, int memberId) throws SQLException {
		// SQL query to return a book
		String sql = "UPDATE loan " +
				"SET returndate = CURRENT_DATE " +
				"WHERE idbook = ? AND idmember = ? AND returndate IS NULL";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			// Set the parameters for the query
			preparedStatement.setInt(1, bookId);
			preparedStatement.setInt(2, memberId);
			
			// Execute the update query
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0; // Return true if a row was updated, meaning the book was successfully returned
		}
	}
	
	private int getLastInsertId() throws Exception {
		getLastInsertIdStatement.executeQuery();
		ResultSet getLastInsertIdStatementResultSet = getLastInsertIdStatement.getResultSet();
		getLastInsertIdStatementResultSet.next();
		return getLastInsertIdStatementResultSet.getInt(1);
	}
	
	public static Table<String> getLibraryModel(){
		Table<String> table = new Table<>(
				"1",
				"2",
				"3",
				"4",
				"5"
		);
		table.setVisibleRows(15);

		// Custom table data to match the visual design
		String[][] tableData = {
				{"1", "2", "3", "4", "5"},
				{"6", "7", "8/9/10", "11", "12"},
				{"13", "14", "UNAVAILABLE", "15", "16"},
				{"17", "18", "UNAVAILABLE", "19", "20"},
				{"XX", "21", "UNAVAILABLE", "22", "23"},
				{"24", "25", "UNAVAILABLE", "XX", "XX"}
		};

		// Populate table
		for (String[] rowData : tableData) {
			table.getTableModel().addRow(rowData);
		}
		return table;
	}
	
	public void printLibraryModel() {
		System.out.println("╔═════════╦═════════╦══════════════════════╦═════════╦═════════╗");
		System.out.println("║    1    ║    2    ║           3          ║    4    ║    5    ║");
		System.out.println("╠═════════╬═════════╬═══════╦═══════╦══════╬═════════╬═════════╣");
		System.out.println("║    6    ║    7    ║   8   ║   9   ║  10  ║   11    ║    12   ║");
		System.out.println("╠═════════╬═════════╬═══════╩═══════╩══════╬═════════╬═════════╣");
		System.out.println("║    13   ║   14    ║██████████████████████║   15    ║    16   ║");
		System.out.println("╠═════════╬═════════╣██████████████████████╠═════════╬═════════╣");
		System.out.println("║    17   ║   18    ║█████UNAVAILABLE██████║   19    ║    20   ║");
		System.out.println("╠═════════╬═════════╣██████████████████████╠═════════╬═════════╣");
		System.out.println("║█████████║   21    ║██████████████████████║   22    ║    23   ║");
		System.out.println("╠═════════╬═════════╬══════════════════════╬═════════╬═════════╣");
		System.out.println("║         ║         ║█████UNAVAILABLE██████║█████████║█████████║");
		System.out.println("║         ║         ╠══════════════════════╣█████████║█████████║");
		System.out.println("║   24    ║   25    ║█████UNAVAILABLE██████║█████████║█████████║");
		System.out.println("║         ║         ╠══════════════════════╣█████████║█████████║");
		System.out.println("║         ║         ║█████UNAVAILABLE██████║█████████║█████████║");
		System.out.println("╚═════════╩═════════╩══════════════════════╩═════════╩═════════╝");
	}
	
}
