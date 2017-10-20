package study2;
/*
 * Class Entity - an entity in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Entity" represents one object in the scenery of the game.  It is 
 * is specialised to be an item or a character.
 * 
 * @author  Ian T. Nabney
 * @version 1.0 (January 2005)
 */

public class Entity
{
    protected String name;

    /**
     * Create an entity with a given name.
     */
    public Entity(String name)
    {
	this.name = name;
    }

    /**
     * Return name of the entity.
     */
    public String getName()
    {
	return name;
    }
     
    /**
     * Create a string representation.
     */
    public String toString()
    {
	return name;
    }
}

