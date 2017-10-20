/**
 * 
 */
package game;

/**
 * For modelling a raffle ticket.
 * 
 * @author S H S Wong
 * @version 6 Oct 2006 13:52:54
 */
public class Ticket {

	// a class variable for keeping track of the number of the previous ticket sold 
	private static int previous = 0;  
	
	private int num;
	private String buyer;
	
	/**
	 * Constructor: 
	 * Each ticket has a auto-generated number. 
	 * This ticket number serves as an unique identifier in the ticket system.
	 * 
	 * Each Ticket keeps track of its buyer.
	 */
	public Ticket(String name) {
		previous++;
		num = previous;
		buyer = name;
	}
	
	/**
	 * Returns the ticket number.
	 * @return
	 */
	public int num() {
		return num;
	}
	
	/**
	 * Returns the name of the one who bought this ticket.
	 * @return
	 */
	public String buyer() {
		return buyer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("Ticket Ref: %3d\tPurchase by: %s", num, buyer);
	}
}
