package study2;
/*
 * Class Item - an item in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Item" represents one object in the scenery of the game.  It is 
 * something that can be viewed and picked up by the player.
 * 
 * @author  Ian T. Nabney
 * @version 1.0 (January 2005)
 */

public class Item extends Entity
{
	private int weight;

	/**
	 * Create an Item with a given name.
	 */
	public Item(String name, int weight)
	{
		super(name);
		this.weight = weight;
	}

	/**
	 * Return the weight of the item.
	 */
	public int getWeight()
	{
		return weight;
	}

	/**
	 * Create a string representation.
	 */
	public String toString()
	{
		return super.toString() + " with weight " + weight;
	}

	/**
	 * Test for equality.  Two items are the same if they have the same
	 * name (needed when trying to pick up or drop items.
	 */
	public boolean equals(Object o)
	{
		if (o instanceof Item) {
			if ( (((Item)o).name).equals(this.name) ) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}

