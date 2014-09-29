/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;


/**
 * PlaceInfo defines a non-modifiable interface over a Place. 
 * It is employed by the classes that need to access the information contained 
 * in the place but that cannot modify the place itself.
 *
 */
public interface PlaceInfo {

	
	/**
	 * Return the place name
	 * @return the place name
	 */
	public String getName();
	
	
	/**
	 * Return the place description
	 * @return the place description
	 */
	public String getDescription();


	/**
	 * Is this place the space ship?
	 * @return true if the place represents a space ship
	 */
	public boolean isSpaceship();
}
