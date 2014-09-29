/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;

import tp.pr5.items.CodeCard;



/**
 * A street links two places A and B in one direction. If it is defined as Street(A,NORTH,B) it means that Place B is at NORTH of Place A. Streets are two-way streets, i.e. if B is at NORTH of A then A is at SOUTH of B. 
 * Some streets are closed and a code (contained in a code card) is needed to open them
 *
 */
public class Street {
	
	private Place source;
	private Place target;
	private Direction dir;
	private boolean isOpen;
	private String codigo;
	
	/**
	 * Creates an open street and it have not any code to open or close it
	 * @param source
	 * @param direction
	 * @param target
	 */
	public Street(Place source, Direction direction, Place target){
		
		iniCampos(source, direction, target, true, null);
	}
	
	/**
	 * Creates a street that has a code to open and close it
	 * @param source
	 * @param direction
	 * @param target
	 * @param isOpen
	 * @param code
	 */
	public Street(Place source, Direction direction, Place target, boolean isOpen, String code){
		
		iniCampos(source, direction, target, isOpen, code);
	}
	
	
	/**
	 * Inicializa los atributos
	 * @param source
	 * @param direction
	 * @param target
	 * @param isOpen
	 * @param code
	 */
	private void iniCampos(Place source, Direction direction, Place target, boolean isOpen, String code){
		
		this.source = source;
		this.target = target;
		this.codigo = code;
		this.dir = direction;
		this.isOpen = isOpen;
	}
	
	
	/**
	 * Checks if the street comes out from a place in a given direction
	 * @param place
	 * @param whichDirection
	 * @return true if the street comes out from the input Place
	 */
	public boolean comeOutFrom(Place place, Direction whichDirection){
		
		/*
		 * En este caso comprobamos las direcciones de memoria ya que en caso de existir dos lugares
		 * con el mismo nombre unidos por una calle (como en NavigationModuleTest, test del profesor),
		 * da lugar a errores
		 */
		return (this.source == place && whichDirection == dir || 
				this.target == place && Direction.oppositeDirection(whichDirection) == dir);
	}
	

	/**
	 * @param whereAmI
	 * @return the Place at the other side of the street (even if the street is closed). 
	 *  Returns null if whereAmI does not belong to the street
	 */
	public Place nextPlace(Place whereAmI){
		
		Place place;
		
		if(whereAmI.equals(this.source)){
			place = target;
		}
		else if(whereAmI.equals(this.target)){
			place = source;
		}
		else{
			place = null;
		}
		
		return place;
	}
	

	/**
	 * Checks if the street is open or closed
	 * @return true, if the street is open, and false when the street is closed
	 */
	public boolean isOpen(){
		
		return this.isOpen;
	}
	

	/**
	 * Tries to open a street using a code card. 
	 * Codes must match in order to complete this action
	 * @param card
	 * @return true if the action has been completed or was open previously
	 */
	public boolean open(CodeCard card){
		
		if(card.getCode().equalsIgnoreCase(this.codigo) && !this.isOpen){
			
			this.isOpen = true;
			
		}
		
		return isOpen;
	}
	

	/**
	 * Tries to close a street using a code card. 
	 * Codes must match in order to complete this action
	 * @param card
	 * @return true if the action has been completed or was closed previously
	 */
	public boolean close(CodeCard card){
		
		if(card.getCode() == this.codigo && this.isOpen == true){
			
			this.isOpen = false;
		}
		
		return !isOpen;
	}
}
