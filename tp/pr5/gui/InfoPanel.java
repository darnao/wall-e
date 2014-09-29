/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;


/**
 * Panel at the bottom of the window that displays messages about the events 
 * that occur during the simulation. This panel implements all the observer interfaces 
 * in order to be notified about all event ocurred
 *
 */
public class InfoPanel extends JPanel implements RobotEngineObserver, NavigationObserver, InventoryObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel infoLabel;
	
	
	/**
	 * Default constructor
	 */
	public InfoPanel(){
		
		this.infoLabel = new JLabel();
		this.infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.infoLabel, BorderLayout.SOUTH);
	}
	
	
	/**
	 * Set the given string on the label
	 * @param str
	 */
	public void setText(String str){
		
		this.infoLabel.setText(str);
	}
	
	
	
	@Override
	public void raiseError(String msg) {
		
		this.setText(msg);
	}

	@Override
	public void communicationHelp(String help) {
		
		this.setText(help);
	}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void engineOff(boolean atShip) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void communicationCompleted() {}

	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		
		this.setText("Robot Attributes has been update: (" + fuel + ", " + recycledMaterial + ")");
	}

	@Override
	public void robotSays(String message) {
		
		this.setText(message);
	}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 * Segun el ejemplo de ejecucion del PDF, despues de cambiar la direction el panel
	 * muestra el fuel y el material reciclado
	 */
	@Override
	public void headingChanged(Direction newHeading) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void inventoryScanned(String inventoryDescription) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void itemScanned(String description) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void itemEmpty(String itemName) {}
}
