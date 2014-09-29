/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;

import javax.swing.JButton;

import tp.pr5.Place;
import tp.pr5.PlaceInfo;


/**
 * Represents a Place in the city on the Swing interface. 
 * It is a button, which name is the place name. 

 * A PlaceCell needs to store a reference to the place that it represents. 
 * However, this place should not be modified by the PlaceCell 

 * When the user clicks on a PlaceCell the CityPanel will show the place description 
 * if the Place was previously visited.

 */
public class PlaceCell extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlaceInfo place;
	
	
	/**
	 * Default constructor
	 */
	public PlaceCell(){
		
		this.place = null;
	}
	
	
	/**
	 * Constructor with parameters
	 * @param place
	 * @param navPanel
	 */
	public PlaceCell(final Place place){
		
		this.place = place;
		setName(this.place.getName());
		setVisible(true);
	}
	
	/**
	 * Returns the place contains into
	 * @return the place
	 */
	public PlaceInfo getPlace(){
		
		return this.place;
	}
	
	
	/**
	 * Sets the place
	 * @param p
	 */
	public void setPlace(PlaceInfo p){
		
		this.place = p;
	}
}
