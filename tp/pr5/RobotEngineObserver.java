/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;


/**
 * Interface of the observers that want to be notified about the events ocurred in the robot engine. 
 * The robot engine will notify the changes in the robot (fuel and recycled material), 
 * will inform about communication problems, errors and when the robot wants to say something. 
 * Finally, the engine will also notify when the user requests help and when the robot shuts down 
 * (because the robot run out of fuel or when it arrived at the spaceship)
 * @author usuario_local
 *
 */
public interface RobotEngineObserver {

	/**
	 * The robot engine informs that it has raised an error
	 * @param msg
	 */
	public void raiseError(String msg);
	
	
	/**
	 * The robot engine informs that the help has been requested
	 * @param help
	 */
	public void communicationHelp(String help);


	/**
	 * The robot engine informs that the robot has shut down (because it has arrived at the spaceship 
	 * or it has run out of fuel)
	 * @param atShip true if the robot shuts down because it has arrived at the spaceship 
	 * or false if it has run out of fuel
	 */
	public void engineOff(boolean atShip);


	/**
	 * The robot engine informs that the communication is over.
	 */
	public void communicationCompleted();


	/**
	 * The robot engine informs that the fuel and/or the amount of recycled material has changed
	 * @param fuel
	 * @param recycledMaterial
	 */
	public void robotUpdate(int fuel, int recycledMaterial);


	/**
	 * The robot engine informs that the robot wants to say something
	 * @param message
	 */
	public void robotSays(String message);

}
