package gr.aegean.book.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link NotAcceptedException} to the HTTP 406 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class NotAcceptedMapper implements
		ExceptionMapper<NotAcceptedException> {

	public static final int CODE = 406;

	public static final String DESCRIPTION = "The requested resource is only capable of generating content not acceptable according to the Accept headers sent in the request";

	public Response toResponse(NotAcceptedException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
