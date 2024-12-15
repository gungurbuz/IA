package businesslayer;

import java.sql.Date;

public class Loan {
	
	private int idloan;
	private String bookname;
	private Date takedate;
	private Date returnbydate;
	
	public Loan(int idloan, String bookname, Date takedate, Date returnbydate) {
		this.idloan = idloan;
		this.bookname = bookname;
		this.takedate = takedate;
		this.returnbydate = returnbydate;
	}
	
	public int getLoanId() {
		return idloan;
	}
	
	public String getBookTitle() {
		return bookname;
	}
	
	public Date getTakeDate() {
		return takedate;
	}
	
	public Date getReturnByDate() {
		return returnbydate;
	}
	
}
