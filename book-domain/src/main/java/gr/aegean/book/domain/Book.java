package gr.aegean.book.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kyriakos Kritikos
 */

@XmlRootElement
public class Book {

	private String isbn = null;
    private String title = null;
    private String category = null;
    private List<String> authors = null;
    private String publisher = null;
    private String language = null;
    private String summary = null;
    private String date = null;
    
    public Book() {}

    public Book(String isbn, String title, String category, List<String> authors, String summary, String language, String publisher, String date, int totalItems, int borrowedItems) {
        this.isbn = isbn;
        this.title = title;
        this.category = category;
        this.authors = authors;
        this.summary = summary;
        this.language = language;
        this.publisher = publisher;
        this.date = date;
    }
    
    public String toString(){
    	return "Book(" + isbn + ", " + title + ", " + authors + ")";
    }

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
