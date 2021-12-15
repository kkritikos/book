package gr.aegean.book.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link BadRequestException} to the HTTP 400 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class BadRequestMapper implements
		ExceptionMapper<BadRequestException> {

	public static final int CODE = 400;

	public static final String DESCRIPTION = "The request cannot be fulfilled due to wrong parameter use";

	public Response toResponse(BadRequestException ex) {
		//System.out.println("The message is: " + ex.getMessage());
		//return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorItem(Response.Status.BAD_REQUEST.getStatusCode(), "bad_parameter", ex.getMessage())).type(MediaType.APPLICATION_XML).build();
		return ex.getResponse();
	}
}
