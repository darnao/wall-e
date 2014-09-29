/**
 * @author Daniel Arnao Rodriguez
 */


package tp.pr5.gui;

import tp.pr5.Controller;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

public class GUIController extends Controller{

	
	/**
	 * Constructor that uses the model
	 * @param robot
	 */
	public GUIController(RobotEngine robot) {
		
		super(robot);
	}
	
	
	/**
	 * Starts the simulation
	 */
	@Override
	public void startController() {
		
		this.engine.requestStart();
		
	}
	
	
	/**
	 * It receives a string that it supposed to be a valid action for the robot.
	 * The controller parsed it and communicate it to the robot to execute it.
	 * If it is a no valid instruction, tells the robot to inform of error
	 * @param str
	 */
	public void actionPerformed(String str){
		
		Instruction instruction;
		
		try{
			
			instruction = Interpreter.generateInstruction(str);
			this.engine.communicateRobot(instruction);
			
		}catch(WrongInstructionFormatException e){
			
			this.engine.requestError("WALL·E says: I do not understand. Please repeat" + '\n');
		}
	}

}
