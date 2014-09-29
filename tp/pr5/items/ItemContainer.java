/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import tp.pr5.MyObservable;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;



/**
 * A container of items. It can be employed by any class that stores items. 
 * A container cannot store two items with the same identifier 
 * It provides methods to add new items, access them and remove them from the container.
 *
 */
public class ItemContainer extends MyObservable<InventoryObserver>{

	private ArrayList<Item> container;
	
	
	/**
	 * Empty container constructor
	 */
	public ItemContainer(){
		
		super();
		this.container = new ArrayList<Item>();
	}
	
	
	/**
	 * Returns the container's size
	 * @return the number of items in the container
	 */
	public int numberOfItems(){
		
		return this.container.size();
	}
	
	
	/**
	 * Add an item to the container. The operation can fail, returning false
	 * It tells the observers that the inventory has change if the item has been added
	 * @param item
	 * @return true if and only if the item is added, i.e., 
	 an item with the same identifier does not exists in the container. Otherwise returns false
	 */
	public boolean addItem(Item item){
		
		boolean added = false;
		
		if(!this.container.contains(item)){
			
			this.container.add(item);
			added = true;
			
			this.notifyInventoryChange();
		}
		
		return added;
	}
	
	
	/**
	 * Returns an item with that name or null if the container does not store an item with the given name.
	 * 
	 * @param id
	 * @return item with that name or null if the container does not store an item with that name.
	 */
	public Item getItem(String id){
	
		Item item = null;
		
		int i = -1;
		
		if(!this.container.isEmpty()){
		
			i = this.container.indexOf(new AuxItem(id));
		}
		
		if(i != -1){
			
			item = this.container.get(i);
		}
		
		return item;
	}
	
	
	
	
	/**
	 * Returns and deletes an item from the inventory. This operation can fail, returning null
	 * Tells the observers that the inventory has change if the item has been removed
	 * @param id
	 * @return An item if and only if the item identified by id exists in the inventory. Otherwise it returns null
	 */
	public Item pickItem(String id){
		
		AuxItem auxItem = new AuxItem(id);
		Item item = null;
		
		if(!this.container.isEmpty() && this.container.contains(auxItem)){
			
			int i = this.container.indexOf(auxItem);
			item = this.container.remove(i);
			
			this.notifyInventoryChange();
		}
		
		return item;
	}
	
	
	/**
	 *  Generates a String with information about the items contained in the container.
	 *  Note that the items must appear sorted by the item name
	 */
	public String toString(){
		
		String string = "";
		
		Collections.sort(this.container);
		Iterator<Item> it = this.container.iterator();
		
		while(it.hasNext()){
			
			string = string + "   " + it.next().getId() + System.getProperty("line.separator");
		}
		
		return string;
	}
	
	
	/**
	 * Checks if there is an item contains in the ItemContainer with the given name.
	 * 
	 * @param id
	 * @return true if the container as an item with that name.
	 */
	public boolean containsItem(String id){
		
		AuxItem auxItem = new AuxItem(id);
		
		return this.container.contains(auxItem);
	}
	
	
	/**
	 * A class that extends Item for needs
	 * @author User
	 */
	private class AuxItem extends Item{
		
		
		public AuxItem(String id){
			
			super(id, null);
		}
		
		/**
		 * @return true
		 */
		public boolean canBeUsed(){
			
			return true;
		}
		
		/**
		 * @param robot
		 * @param nav
		 * @return true
		 */
		public boolean use(RobotEngine robot, NavigationModule nav){
			
			return true;
		}
	}
	
	
	
	/**
	 * Registers an ItemContainerObserver to the model
	 * @param observer
	 */
	public void addItemContainerObserver(InventoryObserver observer){
		
		this.addObserver(observer);
	}
	
	
	/**
	 * Notifies to the observers that the inventory has changed
	 */
	private void notifyInventoryChange(){
		
		Iterator<InventoryObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			InventoryObserver observer = it.next();
			
			observer.inventoryChange(this.container);		
		}	
	}
	
	

	/**
	 * Method called by the OperateInstruction when an item stored in the collection 
	 * is successfully used. 
	 * The collection then checks if the item could be used again in the future. 
	 * If it is not possible because the item is "empty", 
	 * then it is removed from the collection (and the method notifies all the observers).
	 * @param item
	 */
	public void useItem(Item item){
		
		if(!item.canBeUsed()){
				
			this.pickItem(item.getId());
			
			this.notifyInventoryChange();
		}
	}
	
	
	/**
	 * Notifies to the observers that there are a request of scan the ItemContainer
	 */
	public void requestScanCollection(){
		
		Iterator<InventoryObserver> it = observers.iterator();
		
		while(it.hasNext()){
				
			InventoryObserver observer = it.next();
			
			observer.inventoryScanned(this.toString());	
		}
	}
	
	
	/**
	 * PRECOND: The item exists
	 * Notifies the observers that the item that correspond with the given name
	 */
	public void requestScanItem(String id){
		
		Iterator<InventoryObserver> it = observers.iterator();
		
		Item item = this.getItem(id);
		
		while(it.hasNext()){
				
			InventoryObserver observer = it.next();

			observer.itemScanned(item.toString());		
		}	
	}
}
