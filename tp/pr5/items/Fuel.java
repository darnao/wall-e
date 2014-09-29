/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;



/**
 * An item that represents fuel. 
 * This item can be used at least once and it provides power energy to the robot. 
 * When the item is used the configured number of times, 
 * then it must be removed from the robot inventory
 *
 */
public class Fuel extends Item{

	private int power;
	private int times;
	
	/**
	 * Fuel constructor
	 * @param id
	 * @param description
	 * @param power
	 * @param times
	 */
	public Fuel(String id, String description, int power, int times){
		
		super(id, description);
		this.power = power;
		this.times = times;		
	}
	
	
	/**
	 * Overrides canBeUsed method. A fuel can be used a limited number of times
	 */
	@Override
	public boolean canBeUsed(){
		
		return times != 0;
	}
	

	/**
	 * Using the fuel provides energy to the robot (if it can be used)
	 * @return true if the fuel has been used
	 */
	public boolean use(RobotEngine robot, NavigationModule nav){
		
		boolean use = false;
		
		if(times != 0){
			
			robot.addFuel(this.power);
			this.times--;
			use = true;
			
		}
		
		return use;
	}
	
	
	@Override
	public String toString(){
		
		return super.toString() + "// power = " + this.power + ", times = " + this.times;
	}
}
