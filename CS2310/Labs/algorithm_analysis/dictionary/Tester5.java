/**
 * 
 */
package dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * A Tester to run all the required simulations.
 * This Tester uses I/O features from Java 1.5.
 * 
 * @author S H S Wong
 * @version 01-10-2015 
 */
public class Tester5 {

	/**
	 * The Main method - 
	 * 	Runs all the simulations on each available dictionary file, and
	 * 	Outputs the results of the simulations to the text file named
	 * 		(output.txt).
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try {
			PrintStream out = new PrintStream(
									new File("output.txt"), 
									"utf8");
			
			// Creates a File object for accessing the specified file folder.
			String dictionaryFolder = "dictionary_files/";
			File dir = new File(dictionaryFolder);
			
			/* For each file within the specified folder,
			 * 	run the simulation in WordPicker using different types of bags.
			 * 
			 * The results of the simulations are written to 
			 * 	the specified text file (see above).  
			 */ 
			for(String dictionaryFile : dir.list()) 
			{
				for(int i = 1; i <= WordPicker.MAX_BAG_TYPES; i++) 
				{
					out.printf("Running simulation...with MagicalBag%d\n", i);
					out.println("=================================");
					// Runs the simulation and output its result to file.
					out.println(WordPicker.simulation(
									dictionaryFolder+dictionaryFile, 
									i)
							);
					// Flushes the data within the PrintStream.
					out.flush();	
				}
			}
			// Closes the PrintStream
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
