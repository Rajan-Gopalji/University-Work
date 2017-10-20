package seminar;
import java.util.*;
import java.io.*;

/**
 * A Conference object keeps a record of all seminar records that 
 * are in the conference.
 * 
 * @author Sylvia Wong
 * @version 25/09/06
 */
public class Conference {
	// the seminars in this conference
	private ArrayList<Seminar> talks;
	// the index of the next seminar that is scheduled to happen 
	private int nextSeminar = 0;
	
	/**
	 * Constructor - read seminar data from file to create the
	 * contents of the conference
	 * @param file	name of seminar data file 
	 */
	public Conference(String file) 
			throws IOException, NumberFormatException,
					BadDataFormatException {
		
		talks = new ArrayList<Seminar>();
		
		// read seminar records from data file
		Scanner in = new Scanner(new File(file));
		// read records one by one
		while(in.hasNextLine()){
			String record = in.nextLine();
			String[] attributes = record.split("\t");
			/* !!!! You need to add your modification here 
			 * to create a new Seminar object. You will also need to 
			 * track down the problem of bad data format. Don't 
			 * forget to throw the required exception.
			 */

			
			
			
		}
	}
	
	/**
	 * Let a specified seminar in the conference proceed.
	 * @param index	The seminar index
	 * @return what is said in the seminar
	 */
	public String getSeminar(int index){
		Seminar seminar = (Seminar) talks.get(index);
		return seminar.proceed();
	}
	
	/**
	 * Get the next seminar
	 * @return
	 */
	public String getNextSeminar(){
		if(nextSeminar < numOfSeminar()){
				Seminar seminar = (Seminar) talks.get(nextSeminar);
				nextSeminar++;
				return seminar.proceed();
			}
		else
			throw new ArrayIndexOutOfBoundsException("No more seminars.\n");
	}
	
	/**
	 * Reveal the number of seminars in this conference.
	 * @return number of seminars in this conference
	 */
	public int numOfSeminar(){
		return talks.size();
	}
	
	/**
	 * The Main: for a quick unit testing
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		if(args[0] != null){
			try{
				Conference c = new Conference(args[0]);
				for(int i=0; i<c.numOfSeminar(); i++){
					System.out.println(c.getSeminar(i));
				}
			}
			catch (BadDataFormatException e){
				System.out.println(e.getMessage());
			}
		}
	}

}
