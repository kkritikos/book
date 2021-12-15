package gr.aegean.book.utility;

import java.util.List;

import gr.aegean.book.domain.Book;

public class HTMLHandler {
	
	public static String getAuthors(List<String> authors) {
		String str = authors.get(0);
		for (int i = 1; i < authors.size(); i++) str += ", " + authors.get(i);
		return str;
	}
	
	private static String getStringFieldValue(String fieldValue) {
		if (fieldValue == null || fieldValue.trim().equals("")) return "";
		else return fieldValue;
	}
	
	private static String createBookRow(Book book) {
		String str = "<tr>";
		str += "<td>" + book.getIsbn() + "</td>";
		str += "<td>" + book.getTitle() + "</td>";
		str += "<td>" + getAuthors(book.getAuthors()) + "</td>";
		str += "<td>" + book.getPublisher() + "</td>";
		str += "<td>" + getStringFieldValue(book.getCategory()) + "</td>";
		str += "<td>" + getStringFieldValue(book.getSummary()) + "</td>";
		str += "<td>" + getStringFieldValue(book.getLanguage()) + "</td>";
		str += "<td>" + getStringFieldValue(book.getDate()) + "</td>";
		str += "</tr>\n";
		
		return str;
	}
	
	public static String createHtmlBooks(List<Book> books) {
		String answer = "<html>\n";
		
		answer += "<head>\n";
		answer += "<title>The Books from Aegean Library</title>\n";
		answer += "</head>\n";
		
		answer += "<body>\n";
		answer += "<h1>BOOKS</h1>\n";
		answer += "<table border=\"1\" width=\"60%\" align=\"center\">\n";
		answer += "<caption>The requested books</caption>\n";
		answer += "<tr><th>Isbn</th><th>Title</th><th>Authors</th><th>Publisher</th>";
		answer += "<th>Category</th><th>Summary</th><th>Language</th>";
		answer += "<th>Pub. date</th>";
		answer += "</tr>\n";
		if (books != null) for (Book book: books) answer += createBookRow(book);
		answer += "</table>\n";
		answer += "</body>\n";
		
		answer += "\n</html>";
		
		return answer;
	}
	
	public static String createHtmlBook(Book book) {
		String answer = "<html>\n";
		
		answer += "<head>\n";
		answer += "<title>A Book from Aegean Library</title>\n";
		answer += "</head>\n";
		
		answer += "<body>\n";
		answer += "<h1>BOOK " + book.getIsbn() + "</h1>\n";
		answer += "<table border=\"1\" width=\"60%\" align=\"center\">\n";
		answer += "<caption>The requested book</caption>\n";
		answer += "<tr><th>Isbn</th><th>Title</th><th>Authors</th><th>Publisher</th>";
		answer += "<th>Category</th><th>Summary</th><th>Language</th>";
		answer += "<th>Pub. date</th>";
		answer += "</tr>\n";
		answer += createBookRow(book);
		answer += "</table>\n";
		answer += "</body>\n";
		
		answer += "\n</html>";
		
		return answer;
	}
}
