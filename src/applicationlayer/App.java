package applicationlayer;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.util.Scanner;

import businesslayer.Helper;
import businesslayer.Library;

class App {
   
    private static boolean isAuthenticated = false;
    private static boolean isAdmin = false;

    public static void main(String[] args) {
        
        
        
        
        
        
        

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
                            Helper.signup();
                            break;
                        case 2:
                            isAuthenticated = Helper.login();
                            if (isAuthenticated) {
                                System.out.println("Login successful.");
                                Helper.wait(100);
                                Helper.clearConsole();
                                username = Helper.getUsername();
                                System.out.println("Welcome " + username);
                                Helper.readLastLogin(username);
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
                            Library.getLibrary().addBookPublic();
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


    private static void logout() {
        isAuthenticated = false;
        Helper.logout();
        System.out.println("Logged out successfully.");
        Helper.wait(500);
        Helper.clearConsole();

    }
}
