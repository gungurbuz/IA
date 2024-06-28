import java.sql.Connection;
import java.sql.DriverManager;

class App {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.102:3306/mydb", "root", "1234");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}