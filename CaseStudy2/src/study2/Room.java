package study2;
import java.util.*;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 * @version 2.0 (January 2005)
 */

public class Room 
{
	protected String description;
	protected HashMap<String, Room> exits;        // stores exits of this room.
	protected ArrayList<Entity> entities;   // stores entities in this room.
	
	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "in a kitchen" or "in an open court 
	 * yard".
	 */
	public Room(String description) 
	{
		this.description = description;
		exits = new HashMap<String, Room>();
		entities = new ArrayList<Entity>();
	}
	
	/**
	 * Define an exit from this room.
	 */
	public void setExit(String direction, Room neighbor) 
	{
		if (Directions.isDirection(direction)) {
			exits.put(direction, neighbor);
		}
	}
	
	/**
	 * Connect two rooms.
	 */
	public void joinRooms(String direction, Room otherRoom)
	{
		this.setExit(direction, otherRoom);
		otherRoom.setExit(Directions.opposite(direction), this);
	}
	
	/**
	 * Add entity to room.
	 */
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	/**
	 * Return named item from room.
	 */
	public Item getItem(String name)
	{
		Entity e;
		for (Iterator<Entity> i = entities.iterator(); i.hasNext(); ) {
			e = i.next();
			if (e.getName().equals(name)) {
				// Entity has same name, but is it an item?
				if (e instanceof Item) {
					return (Item) e;
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes item from room
	 */
	public void removeItem(Item item)
	{
		for (Iterator<Entity> i = entities.iterator(); i.hasNext(); ) {
			// Use == as want to compare object references
			if (i.next() == item) {
				// Remove item and we're done here
				i.remove();
				return;
			}
		}
	}
	
	/**
	 * Return named character from room.
	 */
	public Character getCharacter(String name)
	{
		Entity e;
		for (Iterator<Entity> i = entities.iterator(); i.hasNext(); ) {
			e = i.next();
			if (e.getName().equals(name)) {
				// Entity has same name, but is it a character?
				if (e instanceof Character) {
					return (Character) e;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a String representation of the room
	 */
	public String toString() {
		return getShortDescription();
	}
	
	/**
	 * Return the description of the room (the one that was defined in the
	 * constructor).
	 */
	public String getShortDescription()
	{
		return description;
	}
	
	/**
	 * Return a long description of this room, in the form:
	 *     You are in the kitchen.
	 *     Exits: north west
	 *     In the room there are: chocolate wine
	 */
	public String getLongDescription()
	{
		StringBuffer b = new StringBuffer("You are " + description);
		b.append(".\n" + getExitString());
		b.append(".\n" + getEntityString());
		return b.toString();
	}
	
	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west".
	 */
	private String getExitString()
	{
		String returnString = "Exits:";
		Set<String> keys = exits.keySet();
		for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
			returnString += " " + iter.next();
		return returnString;
	}
	
	/** 
	 * Return a string describing the entities in the room.
	 */
	private String getEntityString()
	{
		int numEntities = entities.size();
		StringBuffer sb = new StringBuffer("");
		if (numEntities == 0) {
			sb.append("In the room there is nothing.");
		}
		else {
			if (numEntities == 1) {
				sb.append("In the room there is:");
			}
			else {
				sb.append("In the room there are:");
			}
			
			for (Iterator<Entity> i = entities.iterator(); i.hasNext(); ) {
				Entity e = i.next();
				sb.append(" " + e.toString());
				if (i.hasNext())
					sb.append(",");
				else
					sb.append(".");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 */
	public Room getExit(String direction) 
	{
		return (Room)exits.get(direction);
	}
}

