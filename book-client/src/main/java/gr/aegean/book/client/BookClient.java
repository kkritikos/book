package gr.aegean.book.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import gr.aegean.book.domain.Book;

public class BookClient {
	private String ip;
    private int port;
    private String basePath;
    private static int id = 1;
    
    public BookClient() {
    	this.ip = PropertyReader.getIp();
    	this.port = Integer.parseInt(PropertyReader.getPort());
    	basePath = "http://" + ip + ":" + port + "/book/rest/book"; 
    }
    
    public BookClient(String ip, int port) {
    	this.ip = ip;
    	this.port = port;
    	basePath = "http://" + ip + ":" + port + "/book/rest/book"; 
    }
    
    private WebTarget getTarget(String methodName) {
    	ClientConfig cc = new ClientConfig();
    	Client c = ClientBuilder.newClient(cc);
    	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
    	c.register(feature);
    	WebTarget target = c.target(basePath + methodName);

		return target;
    }
    
    public void getBooks(boolean json) {
    	System.out.println("Invoking GET /book with no query parameters");
    	WebTarget r = getTarget("");
    	Invocation.Builder builder = null;
    	if (!json)
    		builder = r.request(MediaType.TEXT_HTML);
    	else
    		builder = r.request(MediaType.APPLICATION_JSON);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getBooks");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void getBooksWithParams(String title, String publisher, List<String> authors, boolean json) {
    	System.out.println("Invoking GET /book with query parameters");
    	WebTarget r = getTarget("");
    	if (title != null) r = r.queryParam("title",title);
    	if (publisher != null) r = r.queryParam("publisher",publisher);
    	if (authors != null && !authors.isEmpty()) {
    		for (String s: authors) r.queryParam("author",s); 
    	}
    	Invocation.Builder builder = null;
    	if (!json)
    		builder = r.request(MediaType.TEXT_HTML);
    	else
    		builder = r.request(MediaType.APPLICATION_JSON);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getBooks with parameters");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void getBook(String isbn, boolean json) {
    	System.out.println("Invoking GET /book/" + isbn);
    	WebTarget r = getTarget("/" + isbn);
    	Invocation.Builder builder = null;
    	if (!json)
    		builder = r.request(MediaType.TEXT_HTML);
    	else
    		builder = r.request(MediaType.APPLICATION_JSON);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	if (json) {
 	    		Book book = response.readEntity(Book.class);
 	    		System.out.println("Got book: " + book);
 	    	}
 	    	else System.out.println(response.readEntity(String.class));
 	    }
    }
    
    private Book createBook(String isbn) {
    	Book book = new Book();
    	book.setIsbn(isbn);
    	book.setTitle("Title" + id);
    	book.setPublisher("Publisher" + id);
    	List<String> authors = new ArrayList<String>();
    	authors.add("Auth1");
    	authors.add("Auth2");
    	book.setAuthors(authors);
    	id++;
    	
    	return book;
    }
    
    public void addBook(String isbn) {
    	System.out.println("Invoking PUT /book/admin/");
    	WebTarget r = getTarget("/admin");
    	Book book = createBook(isbn);
    	Invocation.Builder builder = r.request();
 	    Response response = builder.post(Entity.entity(book,MediaType.APPLICATION_JSON),Response.class);
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling addBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void updateBook(String isbn) {
    	System.out.println("Invoking PUT /book/admin/" + isbn);
    	WebTarget r = getTarget("/admin/" + isbn);
    	Book book = createBook(isbn);
    	book.setTitle("Title3");
    	Invocation.Builder builder = r.request();
 	    Response response = builder.put(Entity.entity(book,MediaType.APPLICATION_JSON),Response.class);
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling updateBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void deleteBook(String isbn) {
    	System.out.println("Invoking DELETE /book/admin/" + isbn);
    	WebTarget r = getTarget("/admin/" + isbn);
    	Invocation.Builder builder = r.request();
 	    Response response = builder.delete();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling deleteBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public static void main(String[] args) {
    	BookClient bc = new BookClient();
    	//Adding two books
    	bc.addBook("isbn1");
    	bc.addBook("isbn2");
    	
    	//Getting all books in different media types
    	bc.getBooks(false);
    	bc.getBooks(true);
    	
    	//Getting filtered books in different media types
    	bc.getBooksWithParams("Title1",null,null,false);
    	bc.getBooksWithParams(null,"Publisher2",null,true);
    	
    	//Getting one book in different media types
    	bc.getBook("isbn1",false);
    	bc.getBook("isbn1",true);
    	
    	//Updating one book & checking the update
    	bc.updateBook("isbn1");
    	bc.getBook("isbn1",true);
    	
    	//Deleting first book & checking the deletion
    	bc.deleteBook("isbn1");
    	bc.getBooks(true);
    }

}
