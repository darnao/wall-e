/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * Its execution moves the robot from one place to the next one in the current direction. 
 * This instruction works if the user writes MOVE or MOVER
 *
 */
public class MoveInstruction implements Instruction{

	private NavigationModule navigator;
	private RobotEngine robot;
	private static final int FUEL_MOVE = -5;
	
	
	public MoveInstruction(){
		
		this.navigator = null;
		this.robot = null;
	}
	
	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		if(cad.equalsIgnoreCase("move") || cad.equalsIgnoreCase("mover")){
			
			return new MoveInstruction();
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
			
			this.navigator.move();
			this.robot.addFuel(FUEL_MOVE);
			
	}
	
	
	@Override
	public String getHelp(){
		
		return "MOVE | MOVER";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		this.robot = engine;
		this.navigator = navigation;
	}
}
