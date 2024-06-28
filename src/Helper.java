import java.sql.*;
import java.util.Scanner;


public class Helper {

    Scanner s = new Scanner(System.in);
    
    public void login(Connection con, String username, String passString){
        System.out.println("Enter Email:");
        String email = s.nextLine();
        System.out.println("Enter Password:");
        String plainpass = s.nextLine();
        String passhash = Password.makePass(plainpass);
        try{
        Statement loginstmt = con.createStatement();
        ResultSet loginrs = loginstmt.executeQuery("select passhash from member where email = " + email + ";");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
// try {
//     Class.forName("com.mysql.cj.jdbc.Driver");
//     Connection con = DriverManager.getConnection(
//             "jdbc:mysql://192.168.1.102:3306/mydb", "root", "1234");
//     // here sonoo is database name, root is username and password
//     Statement stmt = con.createStatement();
//     ResultSet rs = stmt.executeQuery("select * from member");
//     while (rs.next())
//         System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getInt(4));
//     con.close();
// } catch (Exception e) {
//     System.out.println(e);
// }
    
}