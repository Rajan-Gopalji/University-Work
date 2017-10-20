/**
 * 
 */
package dictionary;

import java.util.Iterator;
import java.util.Random;

import dictionary.exceptions.EmptyMagicalBagException;
import dictionary.exceptions.FullMagicalBagException;

/**
 * A MagicalBag is a set-like collection (i.e. all elements in the collection must be unique)
 * modelled by an array. The items in the bag are kept consecutively.
 * 
 * The maximum number of items that can be kept in this type of bag
 * 	is defined in Class WordPicker. Hence, if the bag is full, 
 * 	further addition of items is prohibited.
 * 
 * @author S H S Wong
 * @version 16 Feb 2016
 */
public class MagicalBag1<T> implements MagicalBag<T> {

	/* A random number generator for helping with the picking and removing
	 * 	of random elements from the bag.
	 */ 
	private static Random randomiser = new Random();
	
	private T[] contents;
	private int size;
	
	/**
	 * Constructor
	 */
	public MagicalBag1() {
		contents = (T[]) new Object[WordPicker.MAX_BAG_CAPACITY];
		size = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#add(java.lang.Object)
	 */
	public void add(T item) {
		
		/* ++++
		 * When a MagicalBag1 object has reached its storage limit, 
		 * no more item can be added. In this case, simply throw
		 * an appropriate exception. 
		 */
		if(size() >= WordPicker.MAX_BAG_CAPACITY) {
			throw new FullMagicalBagException();
		}
		
		int i = 0;
		boolean found = false;
		
		while(i < size && !found) {
			if(contents[i].equals(item)) { 
				found = true;
			}
			i++;
		}
		
		if(!found) {
			contents[size] = item;
			size++;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#pick()
	 */
	public T pick() {
		if(size() > 0) {
			int index = randomiser.nextInt(size());
			return contents[index]; 
		}
		else 
			throw new EmptyMagicalBagException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#remove()
	 */
	public T remove() {
		
		if(size() > 0) {
			/* Picks the item randomly */
			int index = randomiser.nextInt(size());
			T item = contents[index];

			/* Reshuffles the contents of the bag to remove gap */
			int lastElementIndex = size - 1;
			// Overrides the "gap" with the last element in the array. 
			contents[index] = contents[lastElementIndex];
			contents[lastElementIndex] = null;
			
			// Updates the size of this bag.
			size--;
			return item;
		}
		else {
			throw new EmptyMagicalBagException();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#size()
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns the contents of the bag as a string.
	 * @return
	 */
	public String toString() {
		String result = "";
		for(T item : contents) {
			result += item + "  ";
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		/* ++++
		 * Create a suitable Iterator object for returning.
		 */
		return new ArrayIterator<T>(contents);
	}
	
}
