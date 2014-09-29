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
 * This instruction drops an Item from the current place and puts it the robot inventory. 
 * This instruction works if the user writes DROP or SOLTAR
 *
 */
public class DropInstruction implements Instruction{

	private String id;
	private NavigationModule navigator;
	private ItemContainer container;
	private RobotEngine robot;
	

	public DropInstruction(){
		
		initCampos(null, null, null, null);
	}
	
	private DropInstruction(String id){
		
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
		
		if(array.length == 2 && (array[0].equalsIgnoreCase("drop") || array[0].equalsIgnoreCase("soltar"))){
			
			return new DropInstruction(array[1]);
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
		
			Item item = this.container.getItem(this.id);
			
			if(item == null){
				
				throw new InstructionExecutionException("You do not have any " + id);
			}
			else if(this.navigator.getCurrentPlace().existItem(this.id)){
				
				throw new InstructionExecutionException("WALL·E says: Ooops, this place already has the object " + id + '\n');	
			}
			else {
				
				this.container.pickItem(this.id);
				this.navigator.getCurrentPlace().addItem(item);
				this.robot.saySomething("Great! I have dropped " + item.getId() + '\n');
			}
	}
	
	
	@Override
	public String getHelp(){
		
		return "DROP <id>" + System.getProperty("line.separator") + "SOLTAR <id>";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		initCampos(this.id, navigation, robotContainer, engine);
	}
}
