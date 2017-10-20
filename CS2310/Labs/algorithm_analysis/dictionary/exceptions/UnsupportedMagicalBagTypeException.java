/**
 * 
 */
package dictionary.exceptions;

/**
 * This exception is thrown when a specified type of bag collection 
 * 	is not defined in this project.
 * 
 * @author S H S Wong
 * @version 14 Oct 2006 14:59:46
 */
public class UnsupportedMagicalBagTypeException extends RuntimeException {

	public UnsupportedMagicalBagTypeException() {
		super("Unsupported MagicalBag Type.");
	}
	
}
