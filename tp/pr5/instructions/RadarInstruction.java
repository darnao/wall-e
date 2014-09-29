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
 * This Instruction shows the description of the current place and the items in it. 
 * This Instruction works if the user writes RADAR
 *
 */
public class RadarInstruction implements Instruction{

	private NavigationModule navigator;
	
	
	public RadarInstruction(){
		
		this.navigator = null;
	}
	

	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		
		if(cad.equalsIgnoreCase("radar")){
			
			return new RadarInstruction();
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
		
			this.navigator.scanCurrentPlace();
	}
	
	
	@Override
	public String getHelp(){
		
		return "RADAR";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		this.navigator = navigation;
	}
}
