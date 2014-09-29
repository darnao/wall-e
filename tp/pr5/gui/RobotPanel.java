/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;



/**
 * RobotPanel displays information about the robot and its inventory. 
 * More specifically, it contains labels to show the robot fuel 
 * and the weight of recycled material and a table that represents the robot inventory. 
 * Each row displays information about an item contained in the inventory.
 *
 */
public class RobotPanel extends JPanel implements RobotEngineObserver, InventoryObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] COL_NAME = {"Id","Description"};
	
	private JTable table;
	private int recycledMaterial;
	private int fuel;
	private MyTableModel tableModel;
	private JLabel stateLabel;
	
	public RobotPanel() {
		
		setBorder(new TitledBorder(null, "Robot info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		this.fuel = 100;
		this.recycledMaterial = 0;
		this.stateLabel = new JLabel();
		setFuelAndMaterial(this.fuel, this.recycledMaterial);
		this.stateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.stateLabel, BorderLayout.NORTH);
		
		
		JScrollPane scroll = new JScrollPane();
		add(scroll, BorderLayout.CENTER);
		
		table = new JTable();
		
		this.tableModel = new MyTableModel();
			
		table.setModel(tableModel);
		
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(240);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll.setViewportView(table);
	}
	
	
	/**
	 * Updates the table with the new values
	 * @param array
	 */
	public void updateTabla(String[][] array){
		
		this.tableModel.updateTable(array);
	}
	

	
	/**
	 * 
	 * @return the name of the selected item
	 */
	public String getSelectedItem(){
		
		String str = "";
		
		if(this.table.getSelectedRow() != -1){
			
			str = this.table.getValueAt(this.table.getSelectedRow(), 0).toString();
		}
		
		return str;
	}
	
	
	/**
	 * Sets the fuel
	 * @param n
	 */
	public void setFuel(int n){
		
		this.fuel = n;
		this.stateLabel.setText("Fuel: " + this.fuel + " Recycled: " + this.recycledMaterial);
	}
	
	
	/**
	 * Sets the recycled material
	 * @param n
	 */
	public void setRecycledMaterial(int n){
		
		this.recycledMaterial = n;
		this.stateLabel.setText("Fuel: " + this.fuel + " Recycled: " + this.recycledMaterial);
	}
	
	
	/**
	 * Sets the fuel and the recycled material
	 * @param fuel
	 * @param material
	 */
	public void setFuelAndMaterial(int fuel, int material){
		
		this.recycledMaterial = material;
		this.fuel = fuel;
		this.stateLabel.setText("Fuel: " + this.fuel + " Recycled: " + this.recycledMaterial);
	}
	
	
	
	/**
	 * The tableModel
	 * @author Daniel Arnao Rodriguez
	 *
	 */
	private class MyTableModel extends DefaultTableModel {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	
		/**
		 * Default constructor
		 */
		public MyTableModel(){}
		
		/**
		 * Updates the values of the table
		 @param data
		 */
		public void updateTable(String[][] container){
			
			this.setDataVector(container, RobotPanel.COL_NAME);
			fireTableDataChanged();
		}
		
		@Override
		/** Return the number of columns
		 * @return 2
		 */
		public int getColumnCount() {
			
			return 2;
		}
		
		@Override
		/**
		 *  @return the name of the column
		 */
		public String getColumnName(int column) {
			
			return RobotPanel.COL_NAME[column];
		}
			
	}


	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void raiseError(String msg) {}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void communicationHelp(String help) {}

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

	/**
	 * Show the new robot attributes on the label
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		
		this.setFuelAndMaterial(fuel, recycledMaterial);
	}

	/**
	 * No es necesario realizar ningun cambio en el panel
	 */
	@Override
	public void robotSays(String message) {}

	/**
	 * Crea un array bidimensional a partir de la lista de items.
	 * En la primera columna aparecen los nombres de los items y en la segunda su description.
	 * Utiliza este array para actualizar la tabla con el inventorio del robot
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {
		
	//Pasar la lista a array bidimensional lo tenia implementado en ItemContainer. Paso el codigo aqui
	//para darle algun uso a esto
		
		Item item = null;
		Collections.sort(inventory);
		Iterator<Item> it = inventory.iterator();
		int i = 0;
		
		String[][] array = new String[inventory.size()][2];
		
		while(it.hasNext()){
			
			item = it.next();
			String[] itemArray = item.toArray();
			array[i] = itemArray;
			
			i++;
		}
		
		this.updateTabla(array);
	}

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
