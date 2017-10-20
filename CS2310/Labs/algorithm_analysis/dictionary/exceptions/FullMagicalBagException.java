/**
 * 
 */
package dictionary.exceptions;

/**
 * An Exception to be thrown when a MagicalBag is full.
 * 
 * @author S H S Wong
 * @version 14 Oct 2006 18:53:20
 */
public class FullMagicalBagException extends RuntimeException {

	/**
	 * Constructor
	 */
	public FullMagicalBagException() {
		super("The magical bag is full! No more items can be added.");
	}
}
