package gr.aegean.book.service;

import java.util.List;

import gr.aegean.book.domain.Book;
import gr.aegean.book.exception.BadRequestException;
import gr.aegean.book.exception.MyInternalServerErrorException;
import gr.aegean.book.utility.DBHandler;
import gr.aegean.book.utility.HTMLHandler;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/book")

public class BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getBooksInHtml(@QueryParam("title") @DefaultValue("") String title, 
			@QueryParam("author") List<String> authors, @QueryParam("publisher") @DefaultValue("") String publisher)
					throws NotFoundException, MyInternalServerErrorException{
		if ((title != null && !title.trim().equals("")) || (authors != null && !authors.isEmpty()) || (publisher != null && !publisher.trim().equals(""))){
			List<Book> books = DBHandler.getBooks(title,authors,publisher);
			if (books != null) {
				String answer = HTMLHandler.createHtmlBooks(books);
				return Response.ok(answer, MediaType.TEXT_HTML).build();
			}
			else throw new NotFoundException();
		}
		else {
			List<Book> books = DBHandler.getAllBooks();
			String answer = HTMLHandler.createHtmlBooks(books);
			return Response.ok(answer, MediaType.TEXT_HTML).build();
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public List<Book> getBooks(@QueryParam("title") @DefaultValue("") String title, 
			@QueryParam("author") List<String> authors, @QueryParam("publisher") @DefaultValue("") String publisher)
					throws NotFoundException, MyInternalServerErrorException{
		if ((title != null && !title.trim().equals("")) || (authors != null && !authors.isEmpty()) || (publisher != null && !publisher.trim().equals(""))){
			List<Book> books = DBHandler.getBooks(title,authors,publisher);
			if (books != null) {
				return books;
			}
			else throw new NotFoundException();
		}
		else {
			List<Book> books = DBHandler.getAllBooks();
			return books;
		}
	}
	
	@GET
	@Path ("/{isbn}")
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getBookInHtml(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		Book book = DBHandler.getBook(isbn);
		if (book != null) {
			String answer = HTMLHandler.createHtmlBook(book);
			return Response.ok(answer + "", MediaType.TEXT_HTML).build();
		}
		else throw new NotFoundException();
	}
	
	@GET
	@Path ("/{isbn}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Book getBook(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		Book book = DBHandler.getBook(isbn);
		if (book != null) {
			return book;
		}
		else throw new NotFoundException();
	}
	
	@POST
	@Path ("/admin")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response addBook(Book book) throws BadRequestException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got book: " + book);
		
		if (book == null) {
			throw new BadRequestException("Did not provide any book content");
		}
			
		String bookIsbn = book.getIsbn();
		if (bookIsbn == null || bookIsbn.trim().equals("")) {
			throw new BadRequestException("Did not provide an isbn in the supplied book");
		}
			
		//Checking all obligatory fields in book apart from isbn
		List<String> authors = book.getAuthors();
		if (authors == null || authors.isEmpty()) 
			throw new BadRequestException("MUST provide the authors of the book");
		String publisher = book.getPublisher();
		if (publisher == null || publisher.trim().equals(""))
			throw new BadRequestException("MUST provide the publisher of the book");
		String title = book.getTitle();
		if (title == null || title.trim().equals(""))
			throw new BadRequestException("MUST provide the title of the book");
			
		boolean exists = DBHandler.existsBook(bookIsbn);
		if (exists) throw new BadRequestException("Book with given isbn already exists");
		else DBHandler.createBook(book);
			
		return Response.ok().build();
	}
	
	@PUT
	@Path ("/admin/{isbn}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response putBook(@PathParam("isbn") String isbn, Book book) throws NotFoundException, BadRequestException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got book: " + book);
		
		if (book == null) {
			throw new BadRequestException("Did not provide any book content");
		}
			
		String bookIsbn = book.getIsbn();
		if (bookIsbn == null || bookIsbn.trim().equals("")) {
			throw new BadRequestException("Did not provide an isbn in the supplied book");
		}
		else if (!bookIsbn.equals(isbn)) {
			throw new BadRequestException("The book's isbn does not match the isbn path parameter");
		}
			
		//Checking all obligatory fields in book apart from isbn
		List<String> authors = book.getAuthors();
		if (authors == null || authors.isEmpty()) 
			throw new BadRequestException("MUST provide the authors of the book");
		String publisher = book.getPublisher();
		if (publisher == null || publisher.trim().equals(""))
			throw new BadRequestException("MUST provide the publisher of the book");
		String title = book.getTitle();
		if (title == null || title.trim().equals(""))
			throw new BadRequestException("MUST provide the title of the book");
			
		boolean exists = DBHandler.existsBook(isbn);
		if (exists) DBHandler.updateBook(book);
		else throw new NotFoundException();
			
		return Response.ok().build();
	}
	
	@DELETE
	@Path ("/admin/{isbn}")
	public Response deleteBook(@PathParam("isbn") String isbn) throws NotFoundException, MyInternalServerErrorException {
		// TODO Auto-generated method stub
		logger.info("Got isbn: " + isbn);
		
		boolean deleted = DBHandler.deleteBook(isbn);
		if (deleted) {
			return Response.ok().build();
		}
		else throw new NotFoundException();
	}
}
