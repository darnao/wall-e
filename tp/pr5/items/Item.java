/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;



/**
 * The superclass of every type of item. 
 * It contains the common information for all the items 
 * and it defines the interface that the items must match
 *
 */
public abstract class Item implements Comparable<Item>{

	protected String id;
	protected String description;
	
	/**
	 * Builds an item from a given identifier and description
	 * @param id
	 * @param description
	 */
	public Item(String id, String description){
		
		this.id = id;
		this.description = description;
	}
	
	/**
	 *  Checks if the item can be used. Subclasses must override this method
	 * @return true if the item can be used
	 */
	public abstract boolean canBeUsed();
	
	/**
	 *  Try to use the item with a robot in a given place. 
	 *  Subclasses must override this method
	 * @param robot
	 * @param nav
	 * @return whether the action was completed.
	 */
	public abstract boolean use(RobotEngine robot, NavigationModule nav);

	/**
	 * @return the item identifier
	 */
	public String getId(){
		
		return this.id;
	}
	
	
	@Override
	public String toString(){
		
		return this.id + ": " + this.description;
	}
	
	
	/**
	 * Overrides hashCode method for override equals method. It is a recommendation of Object API
	 */
	@Override
	public int hashCode(){
		
		return this.id.hashCode();
	}
	
	
	/**
	 * Overrides equals method. It seems if the name of the items are the same
	 * @param p
	 * @return
	 */
	@Override
	public boolean equals(Object obj){
		
		    if (this == obj)
		        return true;
		    if (obj == null)
		        return false;
		    if(!(this instanceof Item))
		    	return false;
		    final Item other = (Item) obj;
		    if (this.id.equalsIgnoreCase(other.id))
		        return true;
		    
		    return false;
	}
	
	
	/**
	 * Overrides compareTo method from Comparable. The compare depends only in the name
	 * @param obj
	 * @return
	 */
	@Override
	public int compareTo(Item other){
		
		if(this == other)
			return 0;
		
		return (this.id.compareToIgnoreCase(other.id));
	}
	
	
	/**
	 * Devuelve un array de tamaño 2. En la primera posicion aparece el nombre del item
	 * y en la segunda, su descripcion.
	 * 
	 * @return an array with the information of the Item. In the position 0 will be the name of the item,
	 * and in the position 1 its description
	 */
	public String[] toArray(){
		
		String[] array = new String[2];
		array[0] = this.id;
		array[1] = this.description;
		
		return array;
	}
}
