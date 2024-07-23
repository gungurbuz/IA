import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.ArrayList;
import org.apache.commons.validator.routines.ISBNValidator;

public class Library {
    private static Scanner s = new Scanner(System.in);
    private static String booktitle = "";
    private static ArrayList<String> authors = new ArrayList<String>();
    private static String ISBN= "";
    private static String ISBN13 = "";
    Connection con = App.getConnection();

    public static void addBookPublic(){
        boolean check = false;
        System.out.println("Input book title:");
        booktitle = s.nextLine();
        Helper.clearConsole();
        System.out.println("Input book author(s), press enter after each author, press enter on empty line to proceed");
        do{
            String tempinput = s.nextLine();
            if(tempinput.equals("")){
                check = true;
            }
            else{
                authors.add(tempinput);
            }
            Helper.clearConsole();
        }
        while(check == false);
        System.out.println("Input ISBN without hypens or spaces");
        ISBN = s.nextLine();
        if (ISBN.length() == 10){ 
            ISBN13 = ISBNValidator.getInstance().convertToISBN13(ISBN);
        }
        else ISBN13 = ISBN;
        
        


    }

    private void addBook() {
        try {
            PreparedStatement addbookstmt = con.prepareStatement("INSERT INTO book (?, ? )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
