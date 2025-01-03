package businesslayer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Book {
	
	private @Nullable String bookid = null;
	private String booktitle = "";
	private ArrayList<String> authorFirstNames = new ArrayList<String>();
	private ArrayList<String> authorLastNames = new ArrayList<String>();
	private ArrayList<Integer> langIds = new ArrayList<>();
	private boolean isLang = false;
	private boolean isPublisher = false;
	private String ISBN13 = "";
	private String genre = "";
	private String pubYear = "";
	private int publisherId;
	private Coordinate location;
	
	public Coordinate getLocation() {
		return location;
	}
	
	public @NotNull String getBookX(){
		return String.valueOf(location.x());
	}
	
	public @NotNull String getBookY(){
		return String.valueOf(location.y());
	}
	
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	
	public Book(String booktitle, ArrayList<String> authorFirstNames, ArrayList<String> authorLastNames, String iSBN,
	            String iSBN13, String genre, String pubYear) {
		this.booktitle = booktitle;
		this.authorFirstNames = authorFirstNames;
		this.authorLastNames = authorLastNames;
		ISBN13 = iSBN13;
		this.genre = genre;
		this.pubYear = pubYear;
	}
	
	public Book() {
	}
	
	public boolean isLang() {
		return isLang;
	}
	
	public void setLang(boolean isLang) {
		this.isLang = isLang;
	}
	
	public boolean isPublisher() {
		return isPublisher;
	}
	
	public void setPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher;
	}
	
	public int getPublisherId() {
		return publisherId;
	}
	
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	
	public ArrayList<Integer> getLangIds() {
		return langIds;
	}
	
	public void setLangIds(ArrayList<Integer> langIds) {
		this.langIds = langIds;
	}
	
	public void addLangIds(int langId) {
		langIds.add(langId);
	}
	
	public String getBooktitle() {
		return booktitle;
	}
	
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	
	public ArrayList<String> getAuthorFirstNames() {
		return authorFirstNames;
	}
	
	public void setAuthorFirstNames(ArrayList<String> authorFirstNames) {
		this.authorFirstNames = authorFirstNames;
	}
	
	public void addAuthorFirstNames(String authorFirstName) {
		authorFirstNames.add(authorFirstName);
	}
	
	public ArrayList<String> getAuthorLastNames() {
		return authorLastNames;
	}
	
	public void setAuthorLastNames(ArrayList<String> authorLastNames) {
		this.authorLastNames = authorLastNames;
	}
	
	public void addAuthorLastNames(String authorLastName) {
		authorLastNames.add(authorLastName);
	}
	
	public String getISBN13() {
		return ISBN13;
	}
	
	public void setISBN13(String iSBN13) {
		ISBN13 = iSBN13;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getPubYear() {
		return pubYear;
	}
	
	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}
	
	public @Nullable String getBookid() {
		return bookid;
	}
	
	public void setBookid(@Nullable String bookid) {
		this.bookid = bookid;
	}
	
}
