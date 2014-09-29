/**
 * @author Daniel Arnao Rodriguez
 */

package tp.pr5;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


/**
 * A class that provides a list of observables and operations over them
 * @param <T> The concrete observable
 */
public class MyObservable<T> implements Iterable<T>{

	protected List<T> observers;
	
	
	/**
	 * DefaultConstructor
	 */
	public MyObservable(){
		
		this.observers = new ArrayList<T>();
	}
	
	
	/**
	 * Adds an observer to the set of observers for this object, 
	 * provided that it is not the same as some observer already in the set.
	 * @param t
	 */
	public void addObserver(T t){
		
		Iterator<T> it = observers.iterator();
		boolean encontrado = false;
		T valor = null;
		
		observers.contains(t);
		while(it.hasNext() && !encontrado){
				
			valor = it.next();	
			encontrado = (valor.equals(t));
		}
		
		if(!encontrado){
			
			this.observers.add(t);
		}
	}
	
	
	/**
	 * Deletes an observer from the set of observers of this object.
	 * @param t
	 */
	public void removeObserver(T t){
		
		Iterator<T> it = observers.iterator();
		boolean encontrado = false;
		T valor = null;
		
		
		while(it.hasNext() && !encontrado){
				
			valor = it.next();	
			encontrado = (valor.equals(t));
		}
		
		
		if(!encontrado){
			
			this.observers.remove(t);
		}
		
	}
	
	@Override
	public Iterator<T> iterator() {
		
		return observers.iterator();
	}

}
