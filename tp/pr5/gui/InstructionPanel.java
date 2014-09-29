/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tp.pr5.Rotation;


/**
 * Implements the action panel with the action buttons and their listeners
 *
 */
public class InstructionPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		/**
		 * Default constructor
		 */
		public InstructionPanel(){}
	

		/**
		 * Constructor with parameters
		 * @param robot
		 * @param robotPanel
		 */
		public InstructionPanel(final GUIController guiController, final RobotPanel robotPanel, final MainWindow mainWindow){
			
		
			
			setBorder(new TitledBorder(null, "Instructions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setLayout(new GridLayout(0, 2, 0, 0));
				
			//Buttons
			
				JButton moveButton = new JButton("MOVE");
				JButton quitButton = new JButton("QUIT");
				final JComboBox<Rotation> comboBox = new JComboBox<Rotation>(Rotation.values());
				comboBox.removeItem(Rotation.UNKOWN);
				JButton turnButton = new JButton("TURN");
				final JTextField text = new JTextField();
				JButton pickButton = new JButton("PICK");
				JButton dropButton = new JButton("DROP");
				JButton operateButton = new JButton("OPERATE");
				
				// MoveButton
					add(moveButton);
					
					moveButton.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){		

							guiController.actionPerformed("MOVE");
						}
						
					});
					
				// QuitButton	
					add(quitButton);
					
					quitButton.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							
							mainWindow.showMessage("End game", "I have communication problems. Bye bye");
							System.exit(0);
						}
						
					});
						
				// TurnButton
					add(turnButton);
					
					turnButton.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							
							String str = comboBox.getSelectedItem().toString();
							guiController.actionPerformed("TURN " + str);
						}
						
					});
						
				// Directions
					add(comboBox);
					comboBox.getSelectedItem();
						
				// PickButton
					add(pickButton);
					
					pickButton.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){				

							String str = text.getText();
							guiController.actionPerformed("PICK " + str);
						}
						
					});
						
				// TextField
					add(text);
					text.setColumns(10);
						
				// DropButton
					add(dropButton);
					
					dropButton.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							
							String str = robotPanel.getSelectedItem();
							guiController.actionPerformed("DROP " + str);
						}
						
					});
						
				// OperateButton
					add(operateButton);
					
					operateButton.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							
							String str = robotPanel.getSelectedItem();
							guiController.actionPerformed("OPERATE " + str);
						}
						
					});			
		}
}
