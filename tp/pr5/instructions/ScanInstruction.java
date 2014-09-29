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
 * The execution of this instruction shows the information of the inventory of the robot or the complete description about the item with identifier id contained in the inventory 
 * This Instruction works if the player writes SCAN or ESCANEAR (id is not mandatory)
 *
 */
public class ScanInstruction implements Instruction{

	private String id;
	private ItemContainer container;
	private RobotEngine robot;
	
	
	public ScanInstruction(){
		
		initCampos(null, null, null);
	}
	
	private ScanInstruction(String id){
		
		initCampos(id, null, null);
	}
	
	/**
	 * Initialize the atributes
	 * @param id
	 * @param nav
	 * @param cont
	 */
	private void initCampos(String id, ItemContainer cont, RobotEngine robot){
		
		this.id = id;
		this.container = cont;
		this.robot = robot;
	}
	

	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		String array[] = cad.split(" ");
		
		if(cad.equalsIgnoreCase("scan") || cad.equalsIgnoreCase("escanear")){
			
			return new ScanInstruction();
		}
		else if(array.length == 2 && (array[0].equalsIgnoreCase("scan") || array[0].equalsIgnoreCase("escanear"))){
			
			return new ScanInstruction(array[1]);
		}
		else {
			
			throw new WrongInstructionFormatException();
		}
	}
	
	
	@Override
	public void execute()
            throws InstructionExecutionException{
		
		
			if(this.container.numberOfItems() == 0){
				
				throw new InstructionExecutionException("WALL·E says: My inventory is empty");
			}
			else if(this.id == null){
				
				this.container.requestScanCollection();
			}
			else if(this.container.containsItem(this.id)){
				
				this.container.requestScanItem(id);
			}
			else {
				
				throw new InstructionExecutionException("WALL·E says: I have not such object");
			}
	}
	
	
	@Override
	public String getHelp(){
		
		return "SCAN | ESCANEAR [ <id> ]";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		initCampos(this.id, robotContainer, engine);
	}
}
