import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

class App {
    public static void main(String[] args) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://172.26.114.217:3306/mydb", "root", "1234");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        boolean isAuthenticated = false;
        String username = null;

        while (isRunning) {
            try {
                if (!isAuthenticated) {
                    System.out.println("Select an option: 1. Signup 2. Login 3. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            Helper.signup(con);
                            break;
                        case 2:
                            isAuthenticated = Helper.login(con);
                            if (isAuthenticated) {
                                System.out.println("Login successful.");
                                username = Helper.getUsername();
                            }
                            break;
                        case 3:
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("Select an option: 1. Logout 2. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            isAuthenticated = false;
                            System.out.println("Logged out successfully.");
                            break;
                        case 2:
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
                scanner.nextLine(); // Consume newline if invalid input
            }
        }

        scanner.close();
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Failed to close the database connection: " + e);
        }
    }
}
