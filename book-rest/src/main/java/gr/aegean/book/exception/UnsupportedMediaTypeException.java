package gr.aegean.book.exception;

import javax.xml.ws.WebFault;

/**
 * Thrown when improper queries, update statements are issued to the Book Service.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class UnsupportedMediaTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3746865303242633109L;
}
