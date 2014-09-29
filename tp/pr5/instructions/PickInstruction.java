/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;



/**
 * This instruction tries to pick an Item from the current place and puts it the robot inventory. 
 * This instruction works if the user writes PICK id or COGER id
 *
 */
public class PickInstruction implements Instruction{

	private String id;
	private NavigationModule navigator;
	private ItemContainer container;
	private RobotEngine robot;
	
	
	public PickInstruction(){
		
		initCampos(null, null, null, null);
	}
	
	
	private PickInstruction(String id){
		
		initCampos(id, null, null, null);
	}
	
	/**
	 * Initialize the atributes
	 * @param id
	 * @param nav
	 * @param cont
	 */
	private void initCampos(String id, NavigationModule nav, ItemContainer cont, RobotEngine robot){
		
		this.id = id;
		this.navigator = nav;
		this.container = cont;
		this.robot = robot;
	}
	
	
	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		String array[] = cad.split(" ");
		
		if(array.length == 2 && (array[0].equalsIgnoreCase("pick") || array[0].equalsIgnoreCase("coger"))){
			
			return new PickInstruction(array[1]);
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
	
			
			if(this.container.containsItem(this.id)){
			
				throw new InstructionExecutionException("WALL·E says: I am stupid! I had already the object " + id + '\n');
			}
			else if(!this.navigator.getCurrentPlace().existItem(this.id)){
					
				throw new InstructionExecutionException("WALL·E says: Ooops, this place has not the object " + id + '\n');	
			}
			else {
				
				Item item = this.navigator.getCurrentPlace().pickItem(id);
				this.container.addItem(item);
				this.robot.saySomething("WALL·E says: I am happy! Now I have " + item.getId() + '\n');
			}
	}
	
	
	@Override
	public String getHelp(){
		
		return "PICK | COGER <id>";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		initCampos(this.id, navigation, robotContainer, engine);
	}
}
