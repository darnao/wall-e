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
 * Its execution request the robot to finish the simulation. 
 * This Instruction works if the user writes QUIT or SALIR
 *
 */
public class QuitInstruction implements Instruction{

	private RobotEngine robot;
	
	public QuitInstruction(){
		
		this.robot = null;
	}
	
	
	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		
		if(cad.equalsIgnoreCase("quit") || cad.equalsIgnoreCase("salir")){
			
			return new QuitInstruction();
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
		
			this.robot.requestQuit();
	}
	
	
	@Override
	public String getHelp(){
		
		return "QUIT | SALIR";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		this.robot = engine;
	}
}
