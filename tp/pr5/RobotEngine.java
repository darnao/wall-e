/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;

import java.util.*;


import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.ItemContainer;




/**
 * This class represents the robot engine. 
 * It controls robot movements by processing the instructions introduced with the keyboard. 
 * The engine stops when the robot arrives at the space ship, runs out of fuel or receives a quit instruction. 

 * The robot engine is also responsible for updating the fuel level 
 * and the recycled material according to the actions that the robot performs in the city. 

 * The robot engine also contains an inventory where the robot stores the items that it collects from the city. 

 * The robot engine can also have a reference to a window (in swing) 
 * and a robot panel where the robot will display some information if the swing interface is in use

 *
 */
public class RobotEngine extends MyObservable<RobotEngineObserver>{


	private int fuel;
	private int material;
	private ItemContainer robotContainer;
	private NavigationModule navigator;
	private boolean end;

	
	
	/**
	 * Creates the robot engine in an initial place, 
	 facing an initial direction and with a city map. 
	 Initially the robot has not any items or recycled material
	 but it has an initial amount of fuel (50).
	 
	 * @param cityMap
	 * @param initialPlace
	 * @param dir
	 */
	public RobotEngine(City cityMap, Place initialPlace, Direction dir){
		
		super();
		this.navigator = new NavigationModule(cityMap, initialPlace);
		this.navigator.initHeading(dir);
		this.fuel = 100;
		this.material = 0;
		this.robotContainer = new ItemContainer();
		this.end = false;
	}
	
	/**
	 * Adds an amount of fuel to the robot
	 * Tells the observers that the robot attributes have been updates
	 * If the robot has 0 fuel after add, tells the observers that the simulacion ends
	 * @param fuel
	 */
	public void addFuel(int fuel){
		
		this.fuel = this.fuel + fuel;
		
		if(this.fuel <= 0){
			
			this.fuel = 0;
			this.end = true;
		}
		
		
		this.notifyRobotUpdated(this.fuel, this.material);
		
		if(this.fuel == 0){
			
			this.end = true;
			this.notifyEnd();
		}
	}
	
	/**
	 * Increases the amount of recycled material of the robot
	 * Notifies the observers that the robot attributes have been updated
	 * @param weight
	 */
	public void addRecycledMaterial(int weight){
		
		this.material = this.material + weight;
		
		this.notifyRobotUpdated(this.fuel, this.material);
	}
	
	/**
	 * Returns The current fuel level of the robot
	 * @return current fuel level
	 */
	public int getFuel(){
		
		return this.fuel;
	}
	
	/**
	 * Returns the current recycled material of the robot
	 * @return current recycled material
	 */
	public int getRecycledMaterial(){
		
		return this.material;
	}
	
	
	/**
	 * It executes an instruction. The instruction must be configured with the context before executing it. 
	 * It controls the end of the simulation. If the execution of the instruction throws an exception, 
	 * then the corresponding message is printed
	 * @param c
	 */
	public void communicateRobot(Instruction c){
		
		c.configureContext(this, navigator, this.robotContainer);
		
		try{
			c.execute();
			
			if(this.navigator.atSpaceship()){
				
				this.end = true;
				this.notifyEnd();			
			}
			
		}catch(InstructionExecutionException e){
			
			this.requestError(e.getMessage());
		}
	}
	
	
	/**
	 * Checks if the simulation is finished
	 * @return true if the game has finished
	 */
	public boolean isOver(){
		
		return this.end;
	}
	
	
	/**
	 * Requests the engine to inform the observers that the simulation starts
	 */
	public void requestStart(){
		
		this.navigator.requestStartNavigator();
			
		Iterator<RobotEngineObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();

			observer.robotUpdate(this.fuel, this.material);
		}
	}
	
	
	/**
	 * Requests the end of the simulation
	 */
	public void requestQuit(){
		
		this.end = true;
		
		Iterator<RobotEngineObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();
				
				observer.communicationCompleted();
		}
	}
	
	
	
	/**
	 * Request to print help information
	 */
	public void requestHelp(){
		
		Iterator<RobotEngineObserver> it = observers.iterator();
		String help = Interpreter.interpreterHelp();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();
			observer.communicationHelp(help);
		}
	}
	
	
	/**
	 * Requests the engine to inform that an error has been raised
	 * @param msg
	 */
	public void requestError(String msg){
		
		Iterator<RobotEngineObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();

			observer.raiseError(msg);
		}
	}
	
	
	/**
	 * Request the engine to say something
	 * @param message
	 */
	public void saySomething(String message){
		
		Iterator<RobotEngineObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();

			observer.robotSays(message);
		}
	}
	
	
	/**
	 * Register a NavigationObserver to the model
	 * @param robotObserver
	 */
	public void addNavigationObserver(NavigationObserver robotObserver){
		
		this.navigator.addNavigationObserver(robotObserver);
	}
	
	
	/**
	 * Registers an EngineObserver to the model
	 * @param observer
	 */
	public void addEngineObserver(RobotEngineObserver observer){
		
		this.addObserver(observer);
	}
	
	
	/**
	 * Registers an ItemContainerObserver to the model
	 * @param observer
	 */
	public void addItemContainerObserver(InventoryObserver observer){
		
		this.robotContainer.addItemContainerObserver(observer);
	}
	
	
	/**
	 * Return true if the robot is at spaceship
	 * @return true if the robot is at spaceship
	 */
	public boolean atSpaceShip(){
		
		return this.navigator.atSpaceship();
	}
	
	
	/**
	 * Notifies to the observers that the attributes of the robot have been updated
	 * @param fuel
	 * @param material
	 */
	private void notifyRobotUpdated(int fuel, int material){
		
		Iterator<RobotEngineObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();
			observer.robotUpdate(this.fuel, this.material);		
		}
	}
	
	
	/**
	 * Notifies to the observers that the simulation ends
	 */
	private void notifyEnd(){
		
		Iterator<RobotEngineObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			RobotEngineObserver observer = it.next();		
			observer.engineOff(this.atSpaceShip());		
		}
	}
}


