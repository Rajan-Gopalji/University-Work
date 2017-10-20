/**
 * 
 */
package ancestry;

/**
 * To model a female person
 * 
 * @author Sylvia Wong
 * @param 20/10/06
 *
 */
public class Female {

	private String name;
	
	/**
	 * Constructor
	 */
	public Female(String name) {
		this.name = name;
	}
	
	/**
	 * Return a string version of this female object
	 */
	public String toString()
	{
		return name;
	}

}
