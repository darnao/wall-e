/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;


import java.util.Iterator;

import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.Item;


/**
 * This class is in charge of the robot navigation features. 
 * It contains the city where the robot looks for garbage, 
 * the current place where the robot is, and the current direction of the robot. 
 * It contains methods to handle the different robot movements and to pick and drop items
 * at the current place.
 *
 */
public class NavigationModule extends MyObservable<NavigationObserver>{

	private Place place;
	private Direction direction;
	private City cityMap;

	/**
	 * Navigation module constructor
	 * @param aCity
	 * @param initialPlace
	 */
	public NavigationModule(City aCity, Place initialPlace){
		
		this.place = initialPlace;
		this.cityMap = aCity;
	}
	

	/**
	 * Checks if the robot has arrived at a spaceship
	 * @return true if an only if the current place is the spaceship
	 */
	public boolean atSpaceship(){
		
		return this.place.isSpaceship();
	}
	
	/**
	 * Updates the current direction of the robot according to the rotation
	 * It notifies the observers too
	 * @param rotation
	 */
	public void rotate(Rotation rotation){
		
		this.direction = this.direction.changeDirection(rotation, this.direction);
		
		this.notifyHeadingChange();
	}
	
	/**
	 * The method tries to move the robot following the current direction. 
	 * If the movement is not possible because there is no street, 
	 * or there is a street which is closed, then it throws an exception. 
	 * Otherwise the current place is updated according to the movement
	 * It notifies to the observers

	 * @throws InstructionExecutionException
	 */
	public void move()
	          throws InstructionExecutionException{
		
		Street street = cityMap.lookForStreet(this.place, this.direction);
		
		if(street == null){
			
			throw new InstructionExecutionException("There is no place in this direction");
		}
		else if(!street.isOpen()){
			
			throw new InstructionExecutionException("WALL·E says: Arrggg, there is a street but it is closed!");
		}
		else{
			
			this.place = street.nextPlace(this.place);
				
			this.notifyPlaceChange();
		}	
	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the current place. 
	 * If the action was completed the item is removed from the current place.

	 * @param id
	 * @return the item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItemFromCurrentPlace(String id){
		
		 return this.place.pickItem(id);
	}
	
	/**
	 * Drop an item in the current place. It assumes that there is no other item with the same name/id there. 
	 * Otherwise, the behaviour is undefined.

	 * @param it
	 */
	public void dropItemAtCurrentPlace(Item it){
		
		this.place.addItem(it);
	}
	
	/**
	 * Checks if there is an item with a given id in this place
	 * @param id
	 * @return true if and only if an item with this id is in the current place
	 */
	public boolean findItemAtCurrentPlace(String id){
		
		return this.place.existItem(id);
	}
	
	/**
	 * Initializes the current heading according to the parameter
	 * @param heading
	 */
	public void initHeading(Direction heading){
		
		this.direction = heading;
	}
	
	/**
	 * Tells the observers that the current place has been scanned
	 */
	public void scanCurrentPlace(){
		
		Iterator<NavigationObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			NavigationObserver observer = it.next();

			observer.placeScanned(this.place);
		}
	}
	
	/**
	 * Returns the street opposite the robot
	 * @return the street which the robot is facing, or null, if there is not any street in this direction
	 */
	public Street getHeadingStreet(){
		
		return this.cityMap.lookForStreet(this.place, this.direction);
	}
	
	/**
	 * Returns the robot heading
	 * @return the direction where the robot is facing to
	 */
	public Direction getCurrentHeading(){
		
		return this.direction;
	}
	
	/**
	 * Returns the current place where the robot is
	 * @return the current place
	 */
	public Place getCurrentPlace (){
		
		return this.place;
	}
	
	
	/**
	 * Register a NavigationObserver to the model
	 * @param robotObserver
	 */
	public void addNavigationObserver(NavigationObserver robotObserver){
		
		this.addObserver(robotObserver);
	}
	
	
	/**
	 * Notifies to the observers that the NavigationModule has been started
	 */
	public void requestStartNavigator(){
		
		Iterator<NavigationObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			NavigationObserver observer = it.next();

			observer.initNavigationModule(this.place, this.direction);
		}
	}
	
	
	/**
	 * Notifies to the observers that the heading direction has been changed
	 */
	private void notifyHeadingChange(){
		
		Iterator<NavigationObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			NavigationObserver observer = it.next();

			observer.headingChanged(this.direction);
		}
	}
	
	
	/**
	 * Notifies to the observers that the current place has changed
	 */
	private void notifyPlaceChange(){
		
		Iterator<NavigationObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			NavigationObserver observer = it.next();

			observer.robotArrivesAtPlace(this.direction, this.place);
			
		}
	}
}
