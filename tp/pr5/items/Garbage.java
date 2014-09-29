/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;



/**
 * The garbage is a type of item that generates recycled material after using it. 
 * The garbage can be used only once. 
 * After using it, it must be removed from the robot inventory
 *
 */
public class Garbage extends Item{

	private int amount;
	private boolean canBeUsed;
	
	/**
	 * Garbage constructor
	 * @param id
	 * @param description
	 * @param recycledMaterial
	 */
	public Garbage(String id, String description, int recycledMaterial){
		
		super(id, description);
		this.amount = recycledMaterial;
		canBeUsed = true;
	}
	

	/**
	 * Overrides canBeUsed method from Item. A garbage can be used only once
	 */
	@Override
	public boolean canBeUsed(){
		
		return this.canBeUsed;
	}
	
	
	/**
	 * The garbage generates recycled material for the robot that uses it
	 * @returns true if the garbage was transformed in recycled material
	 */
	public boolean use(RobotEngine robot, NavigationModule nav){
		
		boolean use = false;
		
		if(this.canBeUsed){
			
			robot.addRecycledMaterial(this.amount);
			this.canBeUsed = false;
			use = true;
		}
		
		return use;
	}
	
	
	@Override
	public String toString(){
		
		return super.toString();
	}
}
