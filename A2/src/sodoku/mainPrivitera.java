package sodoku;

import java.io.FileNotFoundException;

public class mainPrivitera {

	/**
	 * 
	 * @author Caleb Privitera 
	 * Class 406.02 
	 * Assignment 2 
	 * Date of Submission:
	 * 
	 */

	public static void main(String[] args) throws FileNotFoundException {

		SodokuToSatReducerPrivitera satReducer;

		if (args.length < 1) {
			System.out.println("Must specify inputFileName from command line");
			System.exit(1);
		}
		if (args.length == 1) {
			satReducer = new SodokuToSatReducerPrivitera(args[0]);

		} else if (args.length > 1) {
			for (String input : args) {
				satReducer = new SodokuToSatReducerPrivitera(input);
			}
		} else {
			System.out.println("Must specify inputFileName from command line");
			System.exit(1);
		}

	}

}
