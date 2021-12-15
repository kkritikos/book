package gr.aegean.book.exception;

import javax.xml.ws.WebFault;

/**
 * Thrown when requested media type is not acceptable.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class NotAcceptedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8763606630762171474L;
}
