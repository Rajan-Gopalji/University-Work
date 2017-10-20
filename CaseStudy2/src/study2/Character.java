package study2;
/*
 * Class Character - a character in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Character" represents one speaking entity in the scenery of the game.
 * 
 * @author  Ian T. Nabney
 * @version 1.0 (January 2005)
 */

public class Character extends Entity
{
	private String advice;

	/**
	 * Create a Character with a given name.
	 */
	public Character(String name, String advice)
	{
		super(name);
		this.advice = advice;
	}

	/**
	 * Create a string representation.
	 */
	public String toString()
	{
		return super.toString();
	}

	/**
	 * Returns advice
	 */
	public String getAdvice()
	{
		return advice;
	}
}

