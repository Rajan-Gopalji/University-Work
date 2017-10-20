/**
 * 
 */
package dictionary.exceptions;

/**
 * An Exception to be thrown when a MagicalBag is empty.
 * 
 * @author S H S Wong
 * @version 13 Oct 2007
 */
public class EmptyMagicalBagException extends RuntimeException {

	public EmptyMagicalBagException() {
		super("The magical bag is empty!");
	}
}
