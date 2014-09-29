/**
 * @author Daniel Arnao Rodriguez
 */

package console;

import java.util.Scanner;

import tp.pr5.Controller;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * The controller employed when the application is configured as a console application. 
 * It contains the simulation loop that executes the instructions written by the user 
 * on the console.
 */
public class ConsoleController extends Controller{


	/**
	 * Default constructor
	 */
	public ConsoleController(){
		
		super(null);
	}
	
	/**
	 * Constructor of the controller. It receives the model main class.
	 * @param game
	 */
	public ConsoleController(RobotEngine game){
		
		super(game);
	}
	
	/**
	 * Adds the observers to the robot. The only observer will be the console
	 * @param console
	 */
	public void addObserver(Console console){
		
		
		this.registerNavigationObserver(console);
		this.registerItemContainerObserver(console);
		this.registerEngineObserver(console);
	}
	
	
	/**
	 *  It starts the robot engine. Basically, it reads a line, 
	 *  the interpreter generates the corresponding instruction and executes it. 
	 *  The simulation finishes when the robot arrives at the space ship, 
	 *  the user types "QUIT", or the robot runs out of fuel
	 */
	public void startController(){
		
		Scanner sc = new Scanner(System.in);
		
		this.engine.requestStart();
		
		while(!this.engine.isOver()){	
			
			this.engine.saySomething("WALL·E> "); 
			String inst = sc.nextLine();
			
			Instruction instruction;
			
			try{
				
				instruction = Interpreter.generateInstruction(inst);
				this.engine.communicateRobot(instruction);
				
			}catch(WrongInstructionFormatException e){
				
				this.engine.requestError("WALL·E says: I do not understand. Please repeat" + '\n');
			}
		}
		
		sc.close();
	}
}
