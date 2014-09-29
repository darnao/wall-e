/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;



/**
 * Its execution rotates the robot 
 * This Instruction works if the robot writes TURN LEFT or RIGHT or GIRAR LEFT or RIGHT
 *
 */
public class TurnInstruction implements Instruction{

	private Rotation rot;
	private RobotEngine robot;
	private NavigationModule navigator;
	private static final int FUEL_TURN = -5;
	
	
	public TurnInstruction(){
		
		initCampos(null, null, null);
	}
	
	private TurnInstruction(Rotation rot){
		
		initCampos(rot, null, null);
	}
	
	/**
	 * Initialize the atributes
	 * @param id
	 * @param nav
	 * @param cont
	 */
	private void initCampos(Rotation rotation, RobotEngine robot, NavigationModule nav){
		
		this.rot = rotation;
		this.robot = robot;
		this.navigator = nav;
	}
	
	
	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		String array[] = cad.split(" ");
		
		if(array.length == 2 && (array[0].equalsIgnoreCase("turn") || array[0].equalsIgnoreCase("girar")) && 
				array[1].equalsIgnoreCase("LEFT")){
			
			return new TurnInstruction(Rotation.LEFT);
		}
		else if(array.length == 2 && (array[0].equalsIgnoreCase("turn") || array[0].equalsIgnoreCase("girar"))
				&& array[1].equalsIgnoreCase("RIGHT")){
			
			return new TurnInstruction(Rotation.RIGHT);
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
		
		if(this.rot != Rotation.UNKOWN){
		
			this.navigator.rotate(this.rot);
			this.robot.addFuel(FUEL_TURN);
		}
		else {
			
			throw new InstructionExecutionException();
		}
	}
	
	
	@Override
	public String getHelp(){
		
		return "TURN | GIRAR <id>";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		initCampos(this.rot, engine, navigation);
	}
}
