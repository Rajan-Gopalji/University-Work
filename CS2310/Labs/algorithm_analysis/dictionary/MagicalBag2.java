/**
 * 
 */
package dictionary;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import dictionary.exceptions.EmptyMagicalBagException;

/**
 * A MagicalBag is a set-like collection (i.e. all elements in the collection must be unique) 
 * modelled by an ArrayList.
 * 
 * @author S H S Wong
 * @version 16 Feb 2016
 */
public class MagicalBag2<T> implements MagicalBag<T> {
	/* A random number generator for helping with the picking and removing
	 * 	of random elements from the bag.
	 */ 
	private static Random randomiser = new Random();
	
	private List<T> contents;
	
	/**
	 * Constructor
	 */
	public MagicalBag2() {
		contents = new ArrayList<T>(WordPicker.MAX_BAG_CAPACITY);
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#add(java.lang.Object)
	 */
	public void add(T item) {
		if(!contents.contains(item)) {
			contents.add(item);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#pick()
	 */
	public T pick() {
		if(contents.isEmpty()) {
			throw new EmptyMagicalBagException();
		}
		int index = randomiser.nextInt(size());
		return contents.get(index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#remove()
	 */
	public T remove() {
		if(contents.isEmpty()) {
			throw new EmptyMagicalBagException();
		}
		else {
			/* Remove a random item */
			int index = randomiser.nextInt(size());
			T item = contents.remove(index);

			return item;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see dictionary.MagicalBag#size()
	 */
	public int size() {
		return contents.size();
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
		return contents.iterator();
	}
	
}
