/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;


/**
 * Valid directions
 *
 */
public enum Direction {
	
	NORTH, EAST, SOUTH, WEST, UNKNOWN;
	
	
	/**
	 * Returns the opposite direction of the given
	 * @param dir
	 * @return
	 */
	public static Direction oppositeDirection(Direction dir){
		
		switch (dir){
		   case NORTH: dir = SOUTH; break;
		   case SOUTH: dir = NORTH; break;
		   case WEST: dir = EAST; break;
		   case EAST: dir = WEST; break;
		   case UNKNOWN: dir = UNKNOWN; break;
		}
		
		return dir;
	}
	
	
	/**
	 * Change the given direction at given direction
	 * @param rot
	 * @param direction
	 * @return the new direction according to the rotation
	 */
	public Direction changeDirection(Rotation rot, Direction direction){
		
		Direction dir = UNKNOWN;
		
		if(rot == Rotation.LEFT){
			
			if(direction == NORTH){	
				dir = WEST;
					
			} 
			else if(direction == WEST){
				dir = SOUTH;
				
			}
			else if(direction == SOUTH){
				dir = EAST;
				
			}
			else if(direction == EAST){
				dir = NORTH;
			}
		}
		else if(rot == Rotation.RIGHT){
			
			if(direction == NORTH){
				dir = EAST;
				
			} 
			else if(direction == WEST){
				dir = NORTH;
				
			}
			else if(direction == SOUTH){
				dir = WEST;
				
			}
			else if(direction == EAST){
				dir = SOUTH;
			}
		}
		
		
		return dir;
	}
	
	/**
	 * Try to parser a direction from a given string.
	 * @param dir
	 * @return the Direction that represent the given string or UNKNOWN if it is a no valid direction
	 */
	public static Direction parserDirection(String dir){
		
		Direction aux = UNKNOWN;
		
		if(dir.equalsIgnoreCase("NORTH")){
			aux = NORTH;
			
		} 
		else if(dir.equalsIgnoreCase("SOUTH")){
			aux = SOUTH;
			
		}
		else if(dir.equalsIgnoreCase("WEST")){
			aux = WEST;
			
		}
		else if(dir.equalsIgnoreCase("EAST")){
			aux = EAST;
		}
		
		return aux;
	}
}
