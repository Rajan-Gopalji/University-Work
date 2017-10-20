/**
 * 
 */
package dictionary;

import java.util.Iterator;

/**
 * An Iterator for an array. 
 * 
 * The constructor creates a new array from the given array. 
 *  
 * This class provides a concrete implementation for methods in the
 * Java interface Iterator.
 * 
 * @author S H S Wong
 * @version 13 Oct 2007
 *
 */
public class ArrayIterator<T> implements Iterator<T> {

	private T[] contents;
	private int next;		// a pointer to keep track of the next position 
	
	/**
	 * Constructor: 
	 * Create an ArrayIterator object whose contents is kept in an array of type T.
	 * @param array
	 */
	public ArrayIterator(T[] array) {
		/* ++++ Java does not support the creation of a generic array.
		 * 		To overcome this problem, we first of all create the most general
		 * 		type of array (i.e. Object[]). We then cast this general array
		 * 		into the required type, i.e. T[].
		 */
		contents = (T[]) new Object[array.length];
		// Populate the new array with elements in the given array.
		for(int i = 0; i < array.length; i++) {
			contents[i] = array[i];
		}
			
		this.next = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return (next < contents.length);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		T item = contents[next];
		next++;
		return item;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		/* ++++
		 * As an ArrayIterator does not keep a reference to 
		 *  the helping collection object, the remove() operation 
		 *  cannot be supported.
		 *  
		 *  We therefore simply throw an appropriate exception.
		 */
		throw new UnsupportedOperationException();
	}
	

}
