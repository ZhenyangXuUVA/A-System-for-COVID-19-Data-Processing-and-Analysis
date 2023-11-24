package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * To log info, implemented as a Singleton 
 * @author Tan Tuan Yi
 *
 */
public class Logger {
	
	/**
	 * Use Printwriter instead of Filewriter, no exceptions 
	 */
	private PrintWriter out = null;
	
	/**
	 * Private logger constructor 
	 */
	private Logger() {}
	
	/**
	 * private instance single instantiate 
	 */
	private static Logger instance = new Logger();
	
	/**
	 * public getter for Logger single instance
	 * @return instance
	 */
	public static Logger getInstance() {
		return instance;
	}
	
	/**
	 * Logging of each flu tweet 
	 * @param tweet
	 */
	public void log(String tweet) {
		// Make sure instance is active 
		if (out != null) {
			out.println(tweet);
			out.flush();
		}
	}
	
	/**
	 * To change log file
	 * @param filename
	 * @throws IOException 
	 */
	public void setOutputDestination(String filename) throws IOException {
		
		// this use fileWriter so need exception handling 

		// meaning got existing printwriter must close 
		if (out != null) {
			out.close();
		}
		
		// then use FileWriter so that file is append mode 
		out = new PrintWriter(new FileWriter(filename, true));
		

	}
	
	// To make out disapper. 
	public void shutDown() {
		if (out != null) {
			out.close();
			out = null;
			//instance = null;
			
		}
		
	}
	
	
}

