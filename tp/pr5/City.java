/**
 * @author Daniel Arnao Rodriguez
 */


package tp.pr5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * This class represents the city where the robot is wandering. 
 * It contains information about the streets and the places in the city
 */
public class City {

	
	private ArrayList<Street> cityMap;
	
	/**
	 * Default constructor
	 */
	public City(){
		
		this.cityMap = new ArrayList<Street>();
	}
	
	/**
	 * Creates a city using an array of streets
	 * @param cityMap
	 */
	public City(Street[] cityMap) {
		
        this.cityMap = new ArrayList<Street>(Arrays.asList(cityMap));
    }
	
	/**
	 * Constructor by copy
	 * @param streets
	 */
	public City(ArrayList<Street> streets){
		
		this.cityMap = streets;
	}
	

	/**
	 * Looks for the street that starts from the given place in the given direction
	 * @param currentPlace
	 * @param currentHeading
	 * @return the street that stars from the given place in the given direction. 
	 * 		It returns null if there is not any street in this direction from the given place
	 */
	public Street lookForStreet(Place currentPlace, Direction currentHeading){
		
		Street street = null;
		boolean encontrado = false;
		
		Iterator<Street> it = cityMap.iterator();
		
		while(it.hasNext() && !encontrado){
				
			street = it.next();	
			encontrado = street.comeOutFrom(currentPlace, currentHeading);

		}
		
		if(!encontrado){
			
			street = null;
		}
		
		return street;
	}
}
