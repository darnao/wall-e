/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.gui;



import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import tp.pr5.RobotEngineObserver;


/**
 * This class creates the window for the Swing interface according to the wording
 *
 */
public class MainWindow extends JFrame implements RobotEngineObserver{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelPrincipal;
	private NavigationPanel navPanel;
	private RobotPanel robotPanel;
	private InstructionPanel instructPanel;
	private Menu menu;
	private InfoPanel infoPanel;
	private GUIController gameController;
	
	
	/**
	 * Creates the window and the panels using Swing Components. 
	 * It stores a reference to the RobotEngine object 
	 * and provides the panels to the robot engine in order to communicate the simulation events.
	 * @param gameController
	 */
	public MainWindow(GUIController gameController){
		
		this.gameController = gameController;
		setTitle("WALL·E The garbage collector");
		setSize(1100, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(170, 50, 990, 660);
		

		
	//*******************************************  Menu  **********************************************
		this.menu = new Menu();
		setJMenuBar(this.menu);
					
	//*******************************************  InfoPanel  **********************************************	
		this.infoPanel = new InfoPanel();
		
		
	// ***********************  RobotPanel & NavigationPanel & InstructionPanel ***********************
		this.navPanel = new NavigationPanel();
		this.robotPanel = new RobotPanel();
		this.instructPanel = new InstructionPanel(this.gameController, this.robotPanel, this);
	
		
	//*****************************************  Aux panel  *******************************************
		JSplitPane auxPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.instructPanel, this.robotPanel);
		
		
	//*****************************************  Main panel  ******************************************
		this.panelPrincipal = new JPanel();
		this.panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		
	//****************************************  Frame layout  *****************************************
		GroupLayout layout = new GroupLayout(panelPrincipal);
	
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.TRAILING)
					
						.addComponent(auxPanel,GroupLayout.PREFERRED_SIZE, 974, GroupLayout.PREFERRED_SIZE)
						.addComponent(navPanel, GroupLayout.PREFERRED_SIZE, 974, GroupLayout.PREFERRED_SIZE)
						.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 974, GroupLayout.PREFERRED_SIZE)
			);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.TRAILING).
					addGroup(layout.createSequentialGroup().
							addGroup(layout.createParallelGroup(Alignment.LEADING).
									addComponent(auxPanel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)).
									addPreferredGap(ComponentPlacement.RELATED).
					addComponent(navPanel, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE).
					addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).
					addContainerGap())
		);
		

		panelPrincipal.setLayout(layout);
		setContentPane(panelPrincipal);
		setVisible(true);
		
		
		/*
		Configuracion de los observadores. 
		Notese que algunos de ellos implementan varias inrerfaces, 
		por lo que se agregan mas de una vez
		*/
		this.gameController.registerNavigationObserver(navPanel);
		this.gameController.registerNavigationObserver(infoPanel);
		this.gameController.registerItemContainerObserver(robotPanel);
		this.gameController.registerItemContainerObserver(infoPanel);
		this.gameController.registerEngineObserver(robotPanel);
		this.gameController.registerEngineObserver(this);
		this.gameController.registerEngineObserver(infoPanel);
	}
	
	
	
	/**
	 * Show the given message on a dialog with the given title
	 * @param title
	 * @param message
	 */
	public void showMessage(String title, String message){
		
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show a JOptionPane with an error
	 */
	@Override
	public void raiseError(String msg) {
		
		this.showMessage("Error", msg);
	}

	/**
	 * No necesario en este tipo de ejecucion
	 */
	@Override
	public void communicationHelp(String help){}

	/**
	 * Show a JOptionPane that contains the information about the end of the simulation.
	 * The message will change depended on if the robot is at spacehip or it is not.
	 * Then close the window
	 */
	@Override
	public void engineOff(boolean atShip) {
		
		if(atShip){
			
			this.showMessage("End", "WALL·E says: I am at my spaceship. Bye bye" + '\n');
		}
		else{
			
			this.showMessage("End", "WALL·E says: I run out of fuel. I cannot move. Shutting down..." + '\n');
		}
		
		System.exit(0);
	}

	/**
	 * Show a JOptionPane that contains the information about the of the simulation.
	 * It is used when the user choose the option QUIT from actionPanel or from Menu
	 */
	@Override
	public void communicationCompleted() {
		
		this.showMessage("End", "WALL·E> WALL·E says: I have communication problems. Bye bye" + '\n');
	}

	/**
	 * No necessary on MainWindow
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {}


	/**
	 * No necessary on MainWindow
	 */
	@Override
	public void robotSays(String message) {}
}
