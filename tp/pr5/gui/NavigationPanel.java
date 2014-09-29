/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;



/**
 * This class is in charge of the panel that displays the information 
 * about the robot heading and the city that is traversing. 
 * It contains the grid that represents the city in the Swing interface, 
 * a text area to show the place descriptions, 
 * and a label with an icon which represents the robot heading 
 *
 */
public class NavigationPanel extends JPanel implements NavigationObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int LONGITUD = 11;
	private PlaceCell [][] places;
	private JTextArea text;
	private JTextField textField;
	private JSplitPane auxPanel;
	private JPanel map;
	private JPanel textoPanel;
	private Icon walle;
	private JLabel label;
	
	private int fila;
	private int columna;
	
	public NavigationPanel(){
		
		//************************************  MapPanel  ************************************
		this. map = new JPanel();
		map.setBorder(new TitledBorder(null, "City Map", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		map.setLayout(new GridLayout(LONGITUD, LONGITUD, 0, 0));
		
		//************************************  AuxPanel  ************************************
		this.auxPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.label, map);
	
		
		//*************************************  Imagen  *************************************
		this.label = new JLabel();
		setIcon(Direction.NORTH);
		this.auxPanel.add(this.label);
		
		//***********************************  TextPanel  ************************************
		this.textoPanel = new JPanel();
		this.textoPanel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
				
		//************************************  Layout  ************************************
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addComponent(this.textoPanel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addComponent(auxPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(auxPanel, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.textoPanel, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
		);
		
		
		this.text = new JTextArea(20, 100);
		this.text.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(this.text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(this.text);
		
		this.textField = new JTextField();
		this.textField.setText("login");
		this.textField.setVisible(true);
		this.textField.setColumns(10);
		scrollPane.add(this.textField);
		
		GroupLayout textLayout = new GroupLayout(this.textoPanel);
		textLayout.setHorizontalGroup(
			textLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
		);
		textLayout.setVerticalGroup(
			textLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
		);
		
		
		textoPanel.setLayout(textLayout);
		setLayout(layout);
		
		
		//***********************************  Cuadricula  ***********************************
		this.places = new PlaceCell[LONGITUD][LONGITUD];
		
		
		for (int i = 0; i < LONGITUD; i++){
			
			for (int j = 0; j < LONGITUD; j++){
							
				this.places[i][j] = new PlaceCell();				
				map.add(this.places[i][j]);
			}
		}
	}
	
	
	/**
	 * Sets the icon according to the given direction
	 * @param direction
	 */
	public void setIcon(Direction direction){
		
		
		this.walle = new ImageIcon(getClass().getResource("images/walle" + direction.toString() + ".png"));
		this.label.setIcon(this.walle);
	}
	
	
	/**
	 * Sets the given string into the text panel
	 * @param str
	 */
	public void setText(String str){

		this.text.setText(str);
		this.textoPanel.setVisible(true);
	}
	
	
	/**
	 * Sets the initial place
	 * @param place
	 */
	public void setInitPlace(PlaceInfo place){
		
		this.places[5][5].setPlace(place);
		this.fila = 5;
		this.columna = 5;
		
		this.places[5][5].setText(place.getName());
		this.places[5][5].setBackground(Color.GREEN);
		
		CellListener actListener = new CellListener();
		actListener.setPlace(place);
		this.places[5][5].addActionListener(actListener);
	}

	
	
	/**
	 * Fills the grid with the place and shows it in the frame according to the city map
	 * @param dir
	 * @param place
	 */
	public void activatePlace(Direction dir, PlaceInfo place){
		
		this.places[this.fila][this.columna].setBackground(Color.gray);
		
		switch(dir){
			case NORTH: this.fila--; break;
			case SOUTH: this.fila++; break;
			case WEST: this.columna--; break;
			case EAST: this.columna++; break;
				default: break;	
		}

		
		this.places[this.fila][this.columna].setPlace(place);
		
		this.places[this.fila][this.columna].setText(place.getName());
		this.places[this.fila][this.columna].setBackground(Color.GREEN);
		
		CellListener actListener = new CellListener();
		actListener.setPlace(place);
		this.places[this.fila][this.columna].addActionListener(actListener);	
	}
	
	
	
	/**
	 * Listener for the cells of the grid
	 *
	 */
	private class CellListener implements ActionListener{
		
		private PlaceInfo place;
		
		/**
		 * Sets the place needed for actionPerformed(ActionEvent e)
		 * @param place
		 */
		public void setPlace(PlaceInfo place){
			
			this.place = place;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setText(this.place.toString());			
		}
	}


	/**
	 * Updates the robot picture according to the new heading direction
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		
		this.setIcon(newHeading);
	}

	/**
	 * Shows the information of the navigationModule when the simulation starts.
	 * Show the description of the initial place on Log panel and its position in the grid.
	 * Also set the robot image according to the direction of the robot
	 */
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		
		this.setInitPlace(initialPlace);
		this.setText(initialPlace.toString());
		this.headingChanged(heading);
	}

	/**
	 * Change the position of the robot on the grid
	 * and show the description of the new place on Log panel
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		
		this.activatePlace(heading, place);
		this.setText(place.toString());
	}

	/**
	 * Shows the information of the given place on Log panel.
	 * It is used when a place has been scanned
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		
		this.setText(placeDescription.toString());
	}

	/**
	 * Shows the information of the given place on Log panel
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		
		this.setText(placeDescription.toString());
	};
}
