/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.HelpInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.QuitInstruction;
import tp.pr5.instructions.RadarInstruction;
import tp.pr5.instructions.ScanInstruction;
import tp.pr5.instructions.TurnInstruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;


/**
 * The interpreter is in charge of converting the user input 
 * in an instruction for the robot
 *
 */
public class Interpreter {
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	public static final Instruction[] INSTRUCTIONS = {
		new DropInstruction(), 
		new HelpInstruction(), 
		new MoveInstruction(), 
		new OperateInstruction(), 
		new PickInstruction(), 
		new QuitInstruction(), 
		new RadarInstruction(),
		new ScanInstruction(), 
		new TurnInstruction()
		};
	public static final List<Instruction> LISTINSTRUCTIONS = new ArrayList<Instruction>(Arrays.asList(INSTRUCTIONS));
	
	/**
	 * Generates a new instruction according to the user input
	 * @param line
	 * @return The instruction read from the given line. If the instruction is not correct, then it throws an exception.
	 * @throws WrongInstructionFormatException
	 */
	public static Instruction generateInstruction(String line)
			throws WrongInstructionFormatException{
		
		Instruction instruc = null;
		boolean parsed = false;
		Iterator<Instruction> it = LISTINSTRUCTIONS.iterator();
	
		while(it.hasNext() && !parsed){
			
			try{
				
				instruc = it.next();
				instruc = instruc.parse(line);
				parsed = true;
			
			}catch(WrongInstructionFormatException e){}
		}
		
		if(!parsed){
		
			throw new WrongInstructionFormatException();
		}
		
		return instruc;
	}
	
	
	/**
	 * Returns a string with the information about all the available instructions
	 * @return A string with the information about all the available instructions
	 */
	public static String interpreterHelp(){
		
		String string = "";
		
		Iterator<Instruction> it = LISTINSTRUCTIONS.iterator();
		Instruction instruc;
		
		while(it.hasNext()){
			
			instruc = it.next();
			string = string + instruc.getHelp() + LINE_SEPARATOR;
		}
		
		return string;
	}
}
