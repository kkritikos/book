package gr.aegean.book.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link UnsupportedMediaTypeException} to the HTTP 415 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class UnsupportedMediaTypeMapper implements
		ExceptionMapper<UnsupportedMediaTypeException> {

	public static final int CODE = 415;

	public static final String DESCRIPTION = "The request entity has a media type which the server or resource does not support";

	public Response toResponse(UnsupportedMediaTypeException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
