/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;

import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;


/**
 * It represents a place in the city. 
 * Places are connected by streets according to the 4 compass directions, 
 * North, East, South and West. Every place has a name and a textual description about itself. 
 * This description is displayed when the robot arrives at the place.
 * A place can represent the spaceship where the robot is safe. 
 * When the robot arrives at this place, the application is over.
 *
 */
public class Place implements PlaceInfo{

	private String name;
	private String descripcion;
	private boolean isSpaceship;
	private ItemContainer container;
	
	/**
	 * Creates the place
	 * @param name
	 * @param isSpaceShip
	 * @param description
	 */
	public Place(String name, boolean isSpaceShip, String description){
		this.name = name;
		this.isSpaceship = isSpaceShip;
		this.descripcion = description;
		this.container = new ItemContainer();
	}
	
	
	@Override
	public boolean isSpaceship(){
		return this.isSpaceship;
	}
	
	
	/**
	 * Overrides toString method. Returns the place name, its description and the list of items contained in the place
	 */	
	public String toString(){
		String string = this.name + System.getProperty("line.separator") + this.descripcion + System.getProperty("line.separator");
				
		if(this.container.numberOfItems() != 0){
				
			string = string + "The place contains these objects:" + System.getProperty("line.separator") + container.toString();
		}
		else{
			
			string = string + "The place is empty. There are no objects to pick" + System.getProperty("line.separator");
		}
		
		return string;
	}
	
	/**
	 * Tries to add an item to the place. 
	 * The operation can fail (if the place already contains an item with the same name)
	 * @param item
	 * @return true if and only if the item can be added to the place, i.e., the place does not contain an item with the same name
	 */
	public boolean addItem(Item item){
		
		return this.container.addItem(item);
	}
	
	/**
	 * Tries to pick an item characterized by a given identifier from the place. 
	 * If the action was completed the item must be removed from the place.
	 * @param id
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItem(String id){
		
		return this.container.pickItem(id);
	}
	
	/**
	 * Checks if an item is in this place
	 * @param id
	 * @return true if and only if the place contains the item identified by id
	 */
	public boolean existItem(String id){
		
		return this.container.getItem(id) != null;
	}
	
	/**
	 * Drop an item in this place. The operation can fail, returning false
	 * @param it
	 * @return true if and only if the item is dropped in the place, i.e., 
	 * an item with the same identifier does not exists in the place
	 */
	public boolean dropItem(Item it){
		
		return this.container.addItem(it);
	}
	
	
	/**
	 * Overrides hashCode method for override equals, recommended by Object API.
	 * The hashCode depends on the name of the place.
	 */
	@Override
	public int hashCode(){
		
		return this.name.hashCode();
	}
	
	
	
	/**
	 * Overrides equals method from object. It seems if the name of the places are the same
	 * Doesn't recognized ghost places (places without name)
	 * @param p
	 * @return
	 */
	@Override
	public boolean equals(Object obj){
		
		
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    final Place other = (Place) obj;
	    if (name == "" || other.name == "")
	    	return false;
	    if (name.equalsIgnoreCase(other.name))
	        return true;
	    
	    return false;
	}


	@Override
	public String getName() {
		
		return this.name;
	}


	@Override
	public String getDescription() {
		
		return this.descripcion;
	}
}
