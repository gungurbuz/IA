import java.sql.Connection;
import java.sql.DriverManager;

class App {
    public static void main(String args[]) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://172.26.114.217:3306/mydb", "root", "1234");
        } catch (Exception e) {
            System.out.println(e);
        }

        // Testing purposes only
        // Helper.signup(con, "test", "test");
        // Helper.login(con, "test", "test");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Select an option: 1. Signup 2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            Helper.signup(con, "", ""); // Signup (username and password will be prompted)
        } else if (choice == 2) {
            Helper.login(con, "", ""); // Login (username and password will be prompted)
        }

        scanner.close();
    }
}