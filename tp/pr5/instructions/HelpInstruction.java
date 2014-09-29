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
 * Shows the game help with all the instructions that the robot can execute. 
 * This instruction works if the user writes HELP or AYUDA
 *
 */
public class HelpInstruction implements Instruction{

	private RobotEngine robot;
	
	public HelpInstruction(){
		
		this.robot = null;
	}
	
	
	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		if(cad.equalsIgnoreCase("help") || cad.equalsIgnoreCase("ayuda")){
			
			return new HelpInstruction();
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
		
			this.robot.requestHelp();
	}
	
	
	@Override
	public String getHelp(){
		
		return "HELP | AYUDA";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		this.robot = engine;
	}
	
}
