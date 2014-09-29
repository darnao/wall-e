/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;

import tp.pr5.items.InventoryObserver;


/**
 * Generic class of controller that will be extended for concreted controllers
 * (ConsoleController and GUIController)
 * @author usuario_local
 *
 */
public abstract class Controller {

	protected RobotEngine engine;
	
	
	/**
	 * Default constructor
	 */
	public Controller(){
		
		this.engine = null;
	}
	
	/**
	 * Constructor with a robot engine as parameter
	 * @param engine
	 */
	public Controller(RobotEngine engine){
		
		this.engine = engine;
	}
	
	/**
	 * Adds a RobotEngineObserver to the robot list of observers
	 * @param engineObserver
	 */
	public void registerEngineObserver(RobotEngineObserver engineObserver){
		
		this.engine.addEngineObserver(engineObserver);
	}
	
	/**
	 * Adds an InventoryObserver to the robot list of observers
	 * @param inventoryObserver
	 */
	public void registerItemContainerObserver(InventoryObserver inventoryObserver){
		
		this.engine.addItemContainerObserver(inventoryObserver);
	}
	
	/**
	 * Adds a NavigationObserver to the robot list of observers
	 * @param navObserver
	 */
	public void registerNavigationObserver(NavigationObserver navObserver){
		
		this.engine.addNavigationObserver(navObserver);
	}
	
	/**
	 * Start the controller.
	 * It must be implemented for all the classes that extends Controller
	 */
	public abstract void startController();
}
