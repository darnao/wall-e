/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Street;


/**
 * A CodeCard can open or close the door placed in the streets. 
 * The card contains a code that must match the street code in order to perform the action.
 *
 */
public class CodeCard extends Item{

	private String code;
	
	
	/**
	 * Code card constructor
	 * @param id
	 * @param description
	 * @param code
	 */
	public CodeCard(String id, String description, String code){
		
		super(id, description);
		this.code = code;
	}
	
	
	/**
	 * Overrides canBeUsed method from item. A CodeCard can be used always
	 */
	@Override
	public boolean canBeUsed(){
		
		return true;
	}
	
	
	/**
	 * The method to use a code card. If the robot is in a place which contains a street 
	 * in the direction he is looking at, then the street is opened or closed if the street 
	 * code and the card code match
	 *
	 * @return true If the codecard can complete the action of opening or closing a street. 
	 * Otherwise it returns false.
	 */
	public boolean use(RobotEngine robot, NavigationModule nav){

		Street street = nav.getHeadingStreet();
		boolean use;
		
		if(street != null && street.isOpen()){
			
			use = street.close(this);
			
		}else if(street != null && !street.isOpen()){
			
			use = street.open(this);
			
		}else {
			
			use = false;
		}
		
		return use;	
	}
	
	
	/**
	 * Gets the code stored in the code card
	 * @return the code in stored in the code card
	 */
	public String getCode(){
		
		return this.code;
	}
}
