package study2;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 * @version 2.0 (January 2005)  
 */

public class Game 
{
	private Parser parser;
	private Player player;

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() 
	{
		parser = new Parser();
		player = new Player();
		createRooms();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms()
	{
		Room outside, theatre, pub, lab, office;

		// create the rooms
		outside = new Room("outside the main entrance of the university");
		theatre = new Room("in a lecture theatre");
		pub = new Room("in the campus pub");
		lab = new Room("in a computing lab");
		office = new Room("in the computing admin office");

		// initialise room exits
		outside.joinRooms("east", theatre);
		outside.joinRooms("south", lab);
		outside.joinRooms("west", pub);
		lab.joinRooms("east", office);

		// Create some characters and items
		Character portia;
		portia = new Character("Portia", "Tell me where is fancy bred?");
		Item gold, silver, lead;
		gold = new Item("gold_casket", 40);
		silver = new Item("silver_casket", 15);
		lead = new Item("lead_casket", 5);

		// Put them into rooms
		lab.addEntity(portia);
		lab.addEntity(lead);
		office.addEntity(silver);
		office.addEntity(gold);

		player.setCurrentRoom(outside);  // start game outside
	}

	/**
	 *  Main play routine.  Loops until end of play.
	 */
	public void play() 
	{            
		printWelcome();

		// Enter the main command loop.  Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (! finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Main method to start the game outside BlueJ
	 */
	public static void main(String[] args) {
		Game g = new Game();
		g.play();
		System.out.println(g);
	}

	/*
	 * Provide string representation
	 */
	public String toString() {
		String s = "Current room is " + player.getCurrentRoom();
		return s;
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome()
	{
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(player.getCurrentRoom().getLongDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * If this command ends the game, true is returned, otherwise false is
	 * returned.
	 */
	private boolean processCommand(Command command)
	{
		boolean wantToQuit = false;

		if(command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		switch (command.getCommandWord()) {
		case "help":
			printHelp();
			break;
		case "go":
			goRoom(command);
			break;
		case "pick":
			pickItem(command);
			break;
		case "drop":
			dropItem(command);
			break;
		case "ask":
			ask(command);
			break;
		case "look":
			look(command);
			break;
		case "quit":
			wantToQuit = quit(command);
			break;
		}

		return wantToQuit;
	}

	// implementations of user commands:

	/**
	 * Print out some help information.
	 * Here we print some stupid, cryptic message and a list of the 
	 * command words.
	 */
	private void printHelp() 
	{
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/** 
	 * Try to go to one direction. If there is an exit, enter the new
	 * room, otherwise print an error message.
	 */
	private void goRoom(Command command) 
	{
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
			return;
		}

		String direction = command.getSecondWord();

		// Try to leave current room.
		player.walk(direction);
	}

	/**
	 * Pick up an item in the current room.
	 */
	private void pickItem(Command command)
	{
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know what to pick up
			System.out.println("Pick up what?");
			return;
		}

		String itemName = command.getSecondWord();
		Room currentRoom = player.getCurrentRoom();
		Item item = currentRoom.getItem(itemName);
		if (item != null) {
			if (player.canCarry(item.getWeight())) {
				player.pickUpItem(item);
				currentRoom.removeItem(item);
			}
			else {
				System.out.println("It's too heavy.");
			}
		}
		else {
			System.out.println("That item is not in this room.");
		}
	}

	/**
	 * Drop an item in the current room.
	 */
	private void dropItem(Command command)
	{
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know what to drop
			System.out.println("Drop what?");
			return;
		}
		
		String itemName = command.getSecondWord();
		Room currentRoom = player.getCurrentRoom();
		Item item = player.dropItem(itemName);
		if (item != null) 
		{
			currentRoom.addEntity(item);
		}
		else {
			System.out.println("You are not carrying that item.");
		}
	}

	/**
	 * Ask a character for advice.
	 */
	private void ask(Command command)
	{
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know who to ask
			System.out.println("Ask who?");
			return;
		}

		String characterName = command.getSecondWord();
		Room currentRoom = player.getCurrentRoom();
		Character c = currentRoom.getCharacter(characterName);
		if (c != null) {
			System.out.println(c.getAdvice());
		}
		else {
			System.out.println("That character is not in the current room.");
		}
	}



	/** 
	 * "Quit" was entered. Check the rest of the command to see
	 * whether we really quit the game. Return true, if this command
	 * quits the game, false otherwise.
	 */
	private boolean quit(Command command) 
	{
		if(command.hasSecondWord()) {
			System.out.println("Quit what?");
			return false;
		}
		else
			return true;  // signal that we want to quit
	}
}
