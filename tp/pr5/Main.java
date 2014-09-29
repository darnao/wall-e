/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;

import java.io.FileInputStream;
import java.io.IOException;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import console.Console;
import console.ConsoleController;


import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.gui.GUIController;
import tp.pr5.gui.MainWindow;



/**
 * Application entry-point. The application admits a parameter -m | --map with the name of the map file to be used and a parameter -i | --interface with the type of interface (console or swing) 

 *If no arg is specified (or more than one file is given), 
 *it prints an error message (in System.err) 
 *and the application finishes with an error code (-1). 

 *If the map file cannot be read (or it does not exist), 
 *the application ends with a different error code (-2). 

 *If the interface arg is not correct (console or swing) 
 *the application prints a message and the application finishes with an error code (-3). 
 *If the interface arg is not included it starts the application in console mode. 
 *Otherwise, the simulation starts and eventually the application will end normally (return code 0).

 */
public class Main {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		CommandLineParser parser = new BasicParser();
		CommandLine command = null;
		
		Options option = new Options();	
		option.addOption("h", "help", false, " Shows this help message");
		option.addOption("i", "interface", true, " The type of interface: console, swing or both");
		option.addOption("m", "map", true, " File with the description of the city");
		
		try{

			command = parser.parse(option, args);
			
		} catch(ParseException e){
			
		}
		
		
		if(command.hasOption("h")){
			
			System.out.println("Execute this assignment with these parameters:");
			System.out.println("usage: tp.pr4.Main [-h] [-i <type>] [-m <mapfile>]");
			System.out.println(" -h,--help               Shows this help message");
			System.out.println(" -i,--interface <type>   The type of interface: console, swing, or both");
			System.out.println(" -m,--map <mapfile>      File with the description of the city");
			System.exit(0);	
		}
		
		
		if(!command.hasOption("m")){
			
			System.err.println("Map file not specified");
			System.exit(1);
		}
	
		String mapa = command.getOptionValue("m");
		
		if(!command.hasOption("i")){
			
			System.err.println("Interface not specified");
			System.exit(1);
		}
		
		String interfaz = command.getOptionValue("i");
		
		if(!interfaz.equalsIgnoreCase("console") && !interfaz.equalsIgnoreCase("swing") && !interfaz.equalsIgnoreCase("both")){
			
			System.err.println("Wrong type of interface");
			System.exit(3);
		}
		
		
		try{
			 
		
			CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
			FileInputStream file = new FileInputStream(mapa);
			City city = cityLoader.loadCity(file);
					
			RobotEngine robot = new RobotEngine(city, cityLoader.getInitialPlace(), Direction.NORTH);
			

			
			
			if(interfaz.equalsIgnoreCase("swing")){
								 
				GUIController guiController = new GUIController(robot);
				MainWindow mainWindow = new MainWindow(guiController);
				guiController.startController();
			}
			
			if(interfaz.equalsIgnoreCase("console")){
				
				Console console = new Console();
				ConsoleController consoleController = new ConsoleController(robot);
				consoleController.addObserver(console);
				consoleController.startController();
			}
			
			if(interfaz.equalsIgnoreCase("both")){
				
				GUIController guiController = new GUIController(robot);
				MainWindow mainWindow = new MainWindow(guiController);
				
				Console console = new Console();
				ConsoleController consoleController = new ConsoleController(robot);
				consoleController.addObserver(console);
				
				guiController.startController();
			}
					
				
		}catch (IOException e){
			 
			System.err.println("Error reading the map file: " + mapa +  " (No existe el fichero o el directorio)");
			System.exit(2);
				
		}	
	}
}