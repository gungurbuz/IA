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
        Helper.signup(con, "test", "test");
        Helper.login(con, "test", "test");
    }
}