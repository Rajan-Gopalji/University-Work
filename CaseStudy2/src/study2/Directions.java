package study2;
import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all directions known to the game.
 * It is used to checkk directions and return their opposites
 *
 * @author  Ian T. Nabney
 * @version 1.0 (January 2005)
 * 
 */

public class Directions
{
	// a constant Map that holds all valid directions
	private static final HashMap<String, String> opposites;
	
	static {
		opposites = new HashMap<String, String>();
		opposites.put("north", "south");
		opposites.put("south", "north");
		opposites.put("east", "west");
		opposites.put("west", "east");
	}
	
	/**
	 * Constructor - initialise the command words.
	 */
	public Directions()
	{
		// nothing to do at the moment...
	}
	
	/**
	 * Check whether a given String is a valid direction.
	 * Return true if it is, false if it isn't.
	 */
	public static boolean isDirection(String aString)
	{
		return opposites.containsKey(aString);
	}
	
	/**
	 * Returns direction opposite to the given String.
	 */
	public static String opposite(String direction) {
		return (String) opposites.get(direction);
	}
	
	/**
	 * Provides a string representation of the directions available.
	 */
	public String toString()
	{
		return opposites.toString();
	}
}
