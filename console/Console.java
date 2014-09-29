/**
 * @author Daniel Arnao Rodriguez
 */


package console;

import java.util.List;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * The view that displays the application on the System.out. 
 * It implements all the observer interfaces in order to be notified 
 * about every event that occur when the robot is running.
 *
 */
public class Console implements NavigationObserver, RobotEngineObserver, InventoryObserver{
	
	
	public Console(){};
	
	
	/**
	 * Writes on console the information about the heading change in the robot
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		
		this.showMessage("WALL·E is looking at direction " + newHeading + '\n');
	}

	/**
	 * Writes the description of the initial place of the robot and it's direction on console
	 */
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		
		this.showMessage(initialPlace.toString()  + '\n');
		this.showMessage("WALL·E is looking at direction " + heading  + '\n');
	}

	/**
	 * Writes the description of the place where the robot has arrived
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		
		this.showMessage("WALL·E says: Moving in direction " + heading + '\n' +
					place.toString() + '\n');
	}

	/**
	 * Writes on console the information of the given place
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		
		this.showMessage(placeDescription.toString());
	}

	/**
	 * Writes on console the information of the given place
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		
		this.showMessage(placeDescription.toString());
	}

	/**
	 * Show on console the given string as an error
	 */
	@Override
	public void raiseError(String msg) {
		
		this.showMessage(msg);
	}

	/**
	 * Writes on console the given string. It is supposed to be a string that
	 * represents the information about all the possible action for the robot
	 */
	@Override
	public void communicationHelp(String help) {
		
		this.showMessage(help);	
	}

	/**
	 * Writes on console that the application is finished.
	 * If the robot is at spaceship show another message than if it isn't
	 * It used when the simulation ends if the robot is out of fuel or it arrive at spaceship
	 */
	@Override
	public void engineOff(boolean atShip) {
		
		if(atShip){
			
			this.showMessage("WALL·E says: I am at my spaceship. Bye bye" + '\n');
		}
		else{
			
			this.showMessage("WALL·E says: I run out of fuel. I cannot move. Shutting down..." + '\n');
		}
	}

	/**
	 * Writes on console that the application is finished
	 * It is used when the simulation finish if the option QUIT has been used
	 */
	@Override
	public void communicationCompleted() {
		
		this.showMessage("WALL·E> WALL·E says: I have communication problems. Bye bye" + '\n');
	}

	/**
	 * Writes on console the power and the recycled material of the robot
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		
		this.showMessage("      * My power is " + fuel + '\n'
				+ "      * My recycled material is " + recycledMaterial + '\n');
	}

	/**
	 * Writes on console the given string. It is used for different messages of the robot
	 */
	@Override
	public void robotSays(String message) {
		
		this.showMessage(message);
	}

	/**
	 * Aunque el inventorio cambie, no se refleja por consola
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {	}

	/**
	 * Writes on console the information of the inventory of the robot
	 */
	@Override
	public void inventoryScanned(String inventoryDescription) {
		
		this.showMessage("WALL·E says: I am carrying the following items" + '\n');
		this.showMessage(inventoryDescription);
	}

	/**
	 * Writes on console the information of the item scanned
	 */
	@Override
	public void itemScanned(String description) {
		
		this.showMessage(description);
	}

	/**
	 * Aunque un objeto se quede vacio, no se refleja en la consola
	 */
	@Override
	public void itemEmpty(String itemName){}
	
	
	/**
	 * Prints the given string on console
	 * @param str
	 */
	public void showMessage(String str){
			
		System.out.print(str);
	}
}
