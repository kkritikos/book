package gr.aegean.book.exception;

import javax.xml.ws.WebFault;

import gr.aegean.book.domain.ErrorItem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Thrown when the processing of the request resulted in an unexpected failure.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class MyInternalServerErrorException extends WebApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7407455974024725662L;

	public MyInternalServerErrorException(String message){
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorItem(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "internal server error", message)).type(MediaType.APPLICATION_XML).build());
	}
	
	public MyInternalServerErrorException(){
		super();
	}
}
