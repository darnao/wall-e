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
 * The Instruction for using an item. 
 * This Instruction works if the user writes OPERATE id or OPERAR id
 *
 */
public class OperateInstruction implements Instruction {

	private String id;
	private RobotEngine robot;
	private NavigationModule navigator;
	private ItemContainer container;
	
	
	public OperateInstruction(){
		
		initCampos(null, null, null, null);
	}
	
	private OperateInstruction(String id){
		
		initCampos(id, null, null, null);
	}
	
	/**
	 * Initialize the atributes
	 * @param id
	 * @param nav
	 * @param cont
	 */
	private void initCampos(String id, RobotEngine robot, NavigationModule nav, ItemContainer cont){
		
		this.id = id;
		this.robot = robot;
		this.navigator = nav;
		this.container = cont;
	}
	
	
	
	@Override
	public Instruction parse(String cad)
            throws WrongInstructionFormatException{
		
		String array[] = cad.split(" ");
		//boolean isOperate(String)
		if(array.length == 2 && (array[0].equalsIgnoreCase("operate") || array[0].equalsIgnoreCase("operar"))){
			
			return new OperateInstruction(array[1]);
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
				
				throw new InstructionExecutionException("WALL·E says: You do not have any " + id + '\n');
			}
			else if(!item.canBeUsed()){
				
				throw new InstructionExecutionException("WALL·E says: I have problems using the object " + item.getId() + '\n');
			}
			else{
				
				boolean use = item.use(this.robot, this.navigator);	
				
				this.container.useItem(item);
				
				//Verficación de si el item ha sido usado (por ejemplo, en caso de intentar abrir una puerta con una CodeCard incorrecta lanza excepcion)
				if(!use){
					
					throw new InstructionExecutionException("WALL·E says: I have problems using the object " + id + '\n');
				}
			}
	}
	
	
	@Override
	public String getHelp(){
		
		return "OPERATE | OPERAR <id>";
	}
	
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		
		initCampos(this.id, engine, navigation, robotContainer);
	}
}
