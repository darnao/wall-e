/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.cityLoader.cityLoaderExceptions;

import java.io.IOException;


/**
 * Exception thrown by the map loader when the file does not adhere to the file format.
 *
 */
public class WrongCityFormatException extends IOException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2924897505962750839L;


	/**
	 * Constructor without parameters (no message is given
	 */
	public WrongCityFormatException(){}
	
	
	/**
	 * The exception thrown is created with a problem message
	 * @param msg User-friendly string that explains the error.
	 */
	public WrongCityFormatException(String msg) {
		
		super(msg);
	}
	
	
	/**
	 * Constructor to create the exception with a nested cause and an error message.
	 * @param msg User-friendly string that explains the error.
	 * @param arg Nested exception.
	 */
	public WrongCityFormatException(String msg, Throwable arg){
		
		super(msg, arg);
	}
	
	
	/**
	 * Constructor to create the exception with a nested cause.
	 * @param arg Nested exception.
	 */
	public WrongCityFormatException(Throwable arg){
		
		super(arg);
	}
}
