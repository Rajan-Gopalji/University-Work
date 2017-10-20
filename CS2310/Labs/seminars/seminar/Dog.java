package seminar;

/**
 * A Dog object models limited behaviours of a dog.
 * A Dog can speak, so it can introduce itself and even make a speech.
 * 
 * @author Sylvia Wong
 * @version 25/09/10
 */
/* !!!! Modify the class declaration to define the
 * 		realisation relationship between classes Dog and Speaker.  
 */
public class Dog implements Speaker{

	// the name of this dog object
	private String name;
	
	/**
	 * Constructor for creating a Dog object
	 * @param name	the name of the dog
	 */
	public Dog(String name){
		this.name = name;
	}

	/**
	 * A dog introduces itself
	 * @return the self introduction statement 
	 */
	public String introduceOneself() {
		String intro = "";
		// my name is...
		intro += Speaker.nameIntro + name + ". ";
		// thanks
		intro += Speaker.thanks;
		return speak(intro);
	}

	/**
	 * Speak out an utterance with "Woof!"s in between.
	 * @param speech	the utterance for speaking out
	 * @return the uttered speech with a dog's speaking manner 
	 */
	public String speak(String speech) {
		// split the text string into word tokens
		String[] text = speech.split(" ");
		
		String mySpeech = " ";
		
		int counter = 0;
		for(int i=0; i<text.length; i++){
			counter++;
			if(counter == 6){
				mySpeech += "Woof! ";
				counter = 0;
			}
			mySpeech += text[i] + " ";
		
		/*for (int k = 0; k < text.length; k++) {
			//Use the modulus of 5 to get remainder zero to be certain of the 5th position of the array.
			if (((k % 5) == 0) && k > 1) {  
				mySpeech += "Woof! ";
				mySpeech += text[k] + " ";
			} else {
				mySpeech += text[k]+ " ";
			}*/

		}
			
	
		/* !!!! Complete the implementation for generating the dog's
		 * utterance, i.e. the speech with the dog's speaker characteristics
		 * added.
		 * 
		 * A dog can speak like a human being(!). However, every now and then 
		 * the old speaking habits creep into its speech. Thus after a dog has 
		 * uttered five words, it will automatically utter a â€œWoof!â€�. 
		 * Hence, if a dog wants to say â€œMy name is Super Dog. Thank you for 
		 * listening to me.â€�, you will hear â€œMy name is Super Dog. Woof! 
		 * Thank you for listening to Woof! me.â€�.
		 * 
		 * You will need to insert "Woof!" after every five words in the dog's
		 * speech.
		 */

		
		return mySpeech;
	}

	/**
	 * The Main -- for a quick unit testing
	 * @param args
	 */
	public static void main(String[] args){
		Dog d = new Dog("Lucky");
		System.out.println(d.introduceOneself());
		System.out.println(d.speak("In general, polymorphism describes multiple possible states for a single property (it is said to be polymorphic)."));
	}
	
}
