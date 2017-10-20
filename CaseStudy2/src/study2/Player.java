package study2;
import java.util.*;
/**
 * This class represents players in the game. Each player has 
 * a current location and a maximum weight that they can carry.
 * 
 * @author Michael Kolling
 * @version 1.0 (December 2002)
 * @version 2.0 (January 2005)
 */
public class Player
{
	private Room currentRoom;
	private ArrayList<Item> items;
	private int currentWeight;  // Weight of items that the player is carrying
	private int maxWeight;
	private final int DEFAULT_WEIGHT = 50;
	
	/**
	 * Constructor for objects of class Player
	 */
	public Player()
	{
		currentRoom = null;
		currentWeight = 0;
		items = new ArrayList<Item>();
		maxWeight = DEFAULT_WEIGHT;
	}
	
	/**
	 * Return the current room for this player.
	 */
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
	
	/**
	 * Set the current room for this player.
	 */
	public void setCurrentRoom(Room room)
	{
		currentRoom = room;
	}
	
	/**
	 * Returns true if the player can take on the given weight.
	 */
	public boolean canCarry(int weight)
	{
		return ((currentWeight + weight) <= maxWeight);
	}
	
	/**
	 * Pick up item to add to player's collection
	 */
	public void pickUpItem(Item item)
	{
		items.add(item);
		currentWeight += item.getWeight();
	}
	
	
	/**
	 * Try to walk in a given direction. If there is a door
	 * this will change the player's location.
	 */
	public void walk(String direction)
	{
		// Try to leave current room.
		Room nextRoom = currentRoom.getExit(direction);
		
		if (nextRoom == null)
			System.out.println("There is no door!");
		else {
			setCurrentRoom(nextRoom);
			System.out.println(nextRoom.getLongDescription());
		}
	}
}
