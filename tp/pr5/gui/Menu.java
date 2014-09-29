/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/**
 * Implements the menu of the frame
 *
 */
public class Menu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu(){
		
		JMenu menuFile = new JMenu("File");
		add(menuFile);
		
		JMenuItem menuItemQuit = new JMenuItem("QUIT");
		
		menuItemQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
				
				if(respuesta == JOptionPane.YES_OPTION){
					
					System.exit(0);
				}
			}
		});
		
		menuFile.add(menuItemQuit);
	}
}
