import java.sql.Connection;
import java.sql.PreparedStatement;

public class Library {
    public static String ISBN10toISBN13(String ISBN10) {
        // original code taken from https://stackoverflow.com/a/29455307/15302425
        String ISBN13 = ISBN10;
        ISBN13 = "978" + ISBN13.substring(0, 9);
        int d;

        int sum = 0;
        for (int i = 0; i < ISBN13.length(); i++) {
            d = ((i % 2 == 0) ? 1 : 3);
            sum += ((((int) ISBN13.charAt(i)) - 48) * d);
        }
        sum = 10 - (sum % 10);
        String sumString = "";
        if (sum == 10)
            sumString = "X";
        else
            sumString += sum;
        ISBN13 += sumString;

        return ISBN13;
    }

    //private void addBook(){
    //    Connection con = App.getConnection();
    //    try{
    //        PreparedStatement addbookstmt = con.prepareStatement("INSERT INTO book (?, ? )")
    //    } catch(Exception e){
    //        e.printStackTrace();
    //    }
    //}
}
