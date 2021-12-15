package gr.aegean.book.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link MyInternalServerErrorException} to the HTTP 500 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class MyInternalServerErrorMapper implements
		ExceptionMapper<MyInternalServerErrorException> {

	public static final int CODE = 500;

	public static final String DESCRIPTION = "The request cannot be fulfilled due to an internal server error";

	public Response toResponse(MyInternalServerErrorException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
