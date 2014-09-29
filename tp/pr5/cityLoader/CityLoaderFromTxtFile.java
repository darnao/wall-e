/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5.cityLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;
import tp.pr5.items.Item;


/**
 * Cityloader
 *
 */
public class CityLoaderFromTxtFile {

	
	private ArrayList<Place> places;
	private ArrayList<Street> streets;
	private Place initial_place;

	/**
	 * Creates the different array lists
	 */
	public CityLoaderFromTxtFile(){

		this.places = new ArrayList<Place>();
		this.streets = new ArrayList<Street>();
		this.initial_place = null;
	}
	
	
	/**
	 * 
	 * @param file The input stream where the city is stored
	 * @return The city
	 * @throws java.io.IOException When there is some format error in the file 
	 * (WrongCityFormatException) or some errors in IO operations.
	 */
	public City loadCity(InputStream file)
            throws java.io.IOException{
		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(file));
			
		//Check if the next line in the buffer is BeginCity
		checkNextStringBuffer("BeginCity", input);
		
		//Read places, streets and items
		readPlaces(input);
		readStreets(input);
		readItems(input);
		
		//Check if the next line in the buffer is EndCity
		checkNextStringBuffer("EndCity", input);		

		this.initial_place = this.places.get(0);
		
		//Utilizamos el construtor por copia para evitar el paso AraryList -> Array -> ArrayList
		return new City(streets);

	}
	
	/**
	 * Read the places from the buffer following the given format
	 * @param input
	 * @throws IOException if the file format is wrong (Scanner throws IOException, and WrongCityFormatException extends IOException)
	 */
	private void readPlaces(BufferedReader input)
			throws IOException{
	
		
			Scanner sc = null;
			
			//Check If the next line in the buffer is BeginPlaces
			checkNextStringBuffer("BeginPlaces", input);
						
			int i = 0;		
			String description = null;
			boolean isSpaceShip = false;
					
			String id = null;
				
			//Read next line
			String str = readFromBuffer(input);
					
			while(str != null && !str.equals("EndPlaces")){
				
				//Try-catch needed for finally sentence
				try {
						
					sc = new Scanner(str);
						
					//Checks if the next word is place
					checkNextStringScanner("place", sc);
						
					//Check if the next integer is right
					checkNextIntScanner(i, sc);
						
					//Read the place's id
					id = readStringFromScanner(sc);
							
					//Read the place's description
					description = readStringFromScanner(sc).replace("_", " ");
						
					//Checks if the next string is "noSpaceShip" or "spaceShip"
					isSpaceShip = readIsSpaceShip(sc);
					
					//Creates the place
					Place place = new Place(id, isSpaceShip, description);
						
					//add the new place to the arrayList
					this.places.add(place);
					
					i++;
							
					str = readFromBuffer(input);
						
				} catch (IOException e){
						
					throw e;
				}
					
				finally{
						
					sc.close();
				}				
			}
	}


	/**
	 * Read the streets from the buffer following the given format
	 * @param input
	 * @throws IOException if the file format is wrong (Scanner throws IOException, and WrongCityFormatException extends IOException)
	 */
	private void readStreets(BufferedReader input)
			throws IOException{
		
	
		//Checks if the next line in the buffer is "BeginStreets"
		checkNextStringBuffer("BeginStreets", input);
				
		int i = 0;
		Scanner sc = null;
		int source, target;
			
			
		String str = readFromBuffer(input);
			
		while(str != null && !(str.equals("EndStreets")) ){
			
			//Try-catch needed for finally sentence
			try{
					
				sc = new Scanner(str);
					
				//Checks if the next string is "street"
				checkNextStringScanner("street", sc);
					
				//Checks if the next integer is right
				checkNextIntScanner(i, sc);
					
				//Checks if the next string is "place"
				checkNextStringScanner("place", sc);
						
				//Read the index of the source place
				source = checkIndex(sc);		
					
				//Read the direction of the street
				Direction dir = readDirection(sc);
					
				//Checks if the next string is "place"
				checkNextStringScanner("place", sc);
						
				//Read the index of the target place
				target = checkIndex(sc);
					
				//Creates the street
				Street street = createStreet(sc, source, target, dir);
				
				//Adds the street to streets
				this.streets.add(i, street);
					
				str = readFromBuffer(input);
				i++;
							
			}catch (IOException e) {
					
				throw e;
			} 
				
				
			finally 
			{
				sc.close();
			}		
		}
	}
	
	
	/**
	 * Read the items from the buffer following the given format
	 * @param input
	 * @throws IOException if the file format is wrong (Scanner throws IOException, and WrongCityFormatException extends IOException)
	 */
	private void readItems(BufferedReader input)
			throws IOException{
		
			
		//Checks if the next line of the buffer is "BeginItems"
		checkNextStringBuffer("BeginItems", input);
			
		int i=0, index;
		Scanner sc = null;
		String tipo, id, description;
			
		String str = readFromBuffer(input);
			
		while (str != null && !str.equals("EndItems")){
			
			//Try-catch needed for finally sentence
			try{
					
				Item item;
					
				sc = new Scanner(str);
					
				//Read the type of the item
				tipo = readTypeItem(sc);
					
				//Checks if the next integer from the scanner is right
				checkNextIntScanner(i, sc);
					
				//Read the item's id
				id = readStringFromScanner(sc);
					
				//Read the item's description
				description = readStringFromScanner(sc).replace('_', ' ');
					
				//Create the new item
				item = createItem(tipo, id, description, sc);
					
				//Checks if the next string is "place"
				checkNextStringScanner("place", sc);

				//Read the index of the place where the item is
				index = checkIndex(sc);
					
				//Add the item at the place
				this.places.get(index).addItem(item);
								
				str = readFromBuffer(input);
				i++;
			}
			catch (IOException e) 
			{
				throw new WrongCityFormatException();
			}
				
			finally 
			{
				sc.close();
			}
		}
	}
	
	/**
	 * Try to create a new item with the given information and the next scanner input (according to the item subclasses)
	 * @param tipo
	 * @param id
	 * @param descrip
	 * @param sc
	 * @return a new item if it can create it. Otherwise returns null
	 * @throws IOException if Scanner does
	 */
	private Item createItem(String tipo, String id, String descrip, Scanner sc) 
		throws IOException{
		
		Item item = null;
			
		if (tipo.equals("fuel")) {
			
			int x = readIntFromScanner(sc);
			int y = readIntFromScanner(sc);
			item = new Fuel(id, descrip, x, y);
		}
		else if (tipo.equals("codecard")){
					
			String code = readStringFromScanner(sc);
			item = new CodeCard(id, descrip, code);	
		}
		else if (tipo.equals("garbage")){
			
			int garbage = readIntFromScanner(sc);
			item = new Garbage(id, descrip, garbage);
		}
		
		return item;
	}
	
	/**
	 * @return The initial place
	 */
	public Place getInitialPlace(){
		
		return this.initial_place;
	}
	
	/**
	 * Read the next line of the BufferedReader if the buffer is ready 
	 * @param input
	 * @return a string with the next line
	 * @throws IOException
	 */
	private String readFromBuffer(BufferedReader input)
		throws IOException{
	
		if(!input.ready()){
			
			throw new WrongCityFormatException();
		}			
		
		return input.readLine().trim();
	}
	
	/**
	 * Read the next string from the scanner if it can be readed
	 * @param sc
	 * @return the next string
	 * @throws WrongCityFormatException if the scanner can't be readed
	 */
	private String readStringFromScanner(Scanner sc)
			throws WrongCityFormatException{
		
		if(!sc.hasNext()){
			
			throw new WrongCityFormatException();
		}			
		return sc.next();
	}
	
	/**
	 * Read the next int from the scanner if it can be readed. This int can be positive or negative but with 1 or 2 digits
	 * @param sc
	 * @return the next int in the scanner
	 * @throws WrongCityFormatException if the scanner hasn't next or if there's no int
	 */
	private int readIntFromScanner(Scanner sc)
		throws WrongCityFormatException{
		
		if(!sc.hasNext()){
			
			throw new WrongCityFormatException();
		}
		
		String str = sc.next();
		
		if(!isInteger(str)){
			
			throw new WrongCityFormatException();
		}
		
		return Integer.parseInt(str);
	
	}
	
	/**
	 * Checks if the string is an integer
	 * @param str
	 * @return true if the string fills an integer
	 */
	private boolean isInteger (String str){
		
		int i = 0;
		
		char[] charArray = str.toCharArray();
		
		boolean isInt = (charArray.length != 0);
		

		if(charArray[0] == '-' && charArray.length > 1){ //comprobamos si el numero es negativo
			
			i++;
		}
		
		while(i < charArray.length && isInt){
			
			char aux = charArray[i];
			isInt = (aux >= '0' && aux <= '9');
			
			i++;
		}
		
		
		return isInt;
	}
	
	
	
	/**
	 * Checks if the next string of the SCANNER and the given string are equals.
	 * If not, throws and exception
	 * 
	 * @param str
	 * @param sc
	 * @throws WrongCityFormatException
	 */
	private void checkNextStringScanner(String str, Scanner sc) 
		throws WrongCityFormatException {
		
		//Throw exception if sc.next() is no the same than str
		String aux = readStringFromScanner(sc);		
		if(!aux.equals(str)){
			
			throw new WrongCityFormatException();
		}
	}
	
	
	/**
	 * Checks if the next integer from the SCANNER is the same that the given
	 * @param i
	 * @param sc
	 * @throws WrongCityFormatException if the integers are different
	 */
	private void checkNextIntScanner(int i, Scanner sc) 
			throws WrongCityFormatException{
		
		//Throw exception if the next int is different than i
		int x = readIntFromScanner(sc);
		if(x != i){
				
			throw new WrongCityFormatException();
		}
	}
	
	
	/**
	 * Checks if the next string from the BUFFER is the same that the given. If not, throws an exception.
	 * 
	 * @param str
	 * @param input
	 * @throws IOException
	 */
	private void checkNextStringBuffer(String str, BufferedReader input)
		throws IOException{
		
		String string = readFromBuffer(input);
		
		if(string == null || !string.equals(str)){
			
			throw new WrongCityFormatException();	
		}
	}
	
	
	/**
	 * Try to read from scanner if the next string is "noSpaceShip" or "spaceShip" and return a boolean
	 * that represents what it reads.
	 * If the string is another, throws an exception
	 * 
	 * @param sc
	 * @return a boolean that represents is the place is the spaceShip or it isn't
	 * @throws WrongCityFormatException
	 */
	private boolean readIsSpaceShip(Scanner sc) 
			throws WrongCityFormatException{
		
		boolean isSpaceShip;
		
		//Throw exception if sc.next() is no "noSpaceShip" or "spaceShip"
		String aux = readStringFromScanner(sc);	
		if(aux.equals("noSpaceShip")){
						
			isSpaceShip = false;
		}
		else if(aux.equals("spaceShip")){
				
			isSpaceShip = true;
		}
		else{
					
			throw new WrongCityFormatException();	
		}
		
		return isSpaceShip;
	}
	
	
	/**
	 * Check if the next integer from the scanner is lower or equal that the number of places.
	 * If not, throws an exception
	 * 
	 * @param sc
	 * @return the integer readed if it is valid
	 * @throws WrongCityFormatException
	 */
	private int checkIndex(Scanner sc) 
			throws WrongCityFormatException{
		
		int index = readIntFromScanner(sc);		
		if(index >= this.places.size()){
					
			throw new WrongCityFormatException();
		}
		
		return index;
	}
	
	
	/**
	 * Read and parse the direction of the street from the scanner.
	 * If it is a no valid direction, throws an exception.
	 * 
	 * @param sc
	 * @return a valid direction
	 * @throws WrongCityFormatException
	 */
	private Direction readDirection(Scanner sc)
			throws WrongCityFormatException{
		
		String aux = readStringFromScanner(sc);
		Direction dir = Direction.parserDirection(aux);
		if(dir == Direction.UNKNOWN){
				
			throw new WrongCityFormatException();	
		}
		
		return dir;
	}
	
	
	/**
	 * Creates a street with the given information and the next string from the scanner.
	 * If the next string is "closed", it also read an integer that represents the code of the door, an include
	 * this information in the street.
	 * If it is "open" create an open street.
	 * If the string is no "closed" or "open", or there's no integer to read on "closed" case, throws an exception.
	 * 
	 * @param sc
	 * @param source
	 * @param target
	 * @param dir
	 * @return the street that has been created
	 * @throws WrongCityFormatException
	 */
	private Street createStreet(Scanner sc, int source, int target, Direction dir)
			throws WrongCityFormatException{
		
		
		String aux = readStringFromScanner(sc);
		Street street;
		
		if (aux.equals("closed")){
				
			String code = readStringFromScanner(sc);
			street = new Street(this.places.get(source), dir, this.places.get(target), false, code);
		}
		else if(aux.equals("open")){
				
			street = new Street(this.places.get(source), dir, this.places.get(target));
		}
		else{
				
			throw new WrongCityFormatException();
		}
		
		
		return street;
	}
	
	
	/**
	 * Read the type of the item and return it (garbage, codecard or fuel)
	 * @param sc
	 * @return a valid type of item (garbage, codecard or fuel)
	 * @throws WrongCityFormatException if there's nothing to read or it is a no valid type
	 */
	private String readTypeItem(Scanner sc)
			throws WrongCityFormatException{
		
		String aux = readStringFromScanner(sc);	
		if (!aux.equals("garbage") && !aux.equals("fuel") && !aux.equals("codecard")){
				
			throw new WrongCityFormatException();
		}
		
		return aux;
	}
}
