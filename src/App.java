import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.util.Scanner;

class App {
    private static Connection con = null;
    private static boolean isAuthenticated = false;
    private static boolean isAdmin = false;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://172.26.114.217:3306/library", "root", "1234");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        String username = null;

        while (isRunning) {
            try {
                if (!isAuthenticated) {
                    System.out.println("Select an option: 1. Signup 2. Login 3. Test 999. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            Helper.signup(con);
                            break;
                        case 2:
                            isAuthenticated = Helper.login(con);
                            isAdmin = 
                            if (isAuthenticated) {
                                System.out.println("Login successful.");
                                Helper.wait(100);
                                Helper.clearConsole();
                                username = Helper.getUsername();
                                System.out.println("Welcome " + username);
                                Helper.readLastLogin(con, username);
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
                            Helper.wait(500);
                            Helper.clearConsole();
                    }
                } else if (isAuthenticated && isAdmin) {
                    System.out.println("Select an option: 1. Logout 2. Add Book 999. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (choice) {
                        case 1:
                            logout();
                            break;
                        case 2:
                            System.out.println("testing book adder");
                            Library libraryClass = new Library();
                            libraryClass.addBookPublic();
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
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Failed to close the database connection: ");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }

    private static void logout() {
        isAuthenticated = false;
        Helper.logout();
        System.out.println("Logged out successfully.");
        Helper.wait(500);
        Helper.clearConsole();

    }
}
