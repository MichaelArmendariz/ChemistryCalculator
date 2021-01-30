package chemistry;

import java.util.*;
import use.*;

/*
 * Code: class Atom
 * Author: Michael Armendariz
 * Founding Date: 3/28/20
 * Code Version: 2.0
 * Revisions:
 *
 * (3/29/20) Added tempMoles instance variable to help Molecule toString() with accuracy
 * 
 * (4/3/20) Added readerSearch method to shorten constructors;
 * 			added compareTo()
 * 
 * (4/8/20) Made both input string and fileReader string lowercase for search
 * 
 * (4/14/20) Took off lowercase because of errors with locating correct elements
 * 
 * (4/15/20) Added check for entry of symbol versus full name to avoid confusion in search;
 * 			added fileSearch(), hasRepeats(), and fileContains() to improve pre-instance searches in further implementation
 * 
 * (4/17/20) Fixed readerSearch() to take in short entries correctly——substring from next uppercase letter to the atomic number
 * 
 * (7/21/20) Added static method getAtomEntry() to aid in implementation and remove clutter from client
 * 
 * (1/8/21) Added fields electronConfig and arraylist oxidation states, also added their setter and getter methods
 * 
 * (1/24/21) Dispersed intitialization actions from constructors to setFields(), where setter methods are called;
 * 			getAtomEntryByString() and getAtomEntryByAtomicNumber() were added;
 * 			removed electronConfig because of lack of functionality
 * 
 * (1/25/21) All methods related to reading input from the Periodic Table text file were deleted and the ChemicalElement class
 * 			implemented in the setFields() methods to serve as a new way to acquire the same information from the internet;
 * 			most of the old functionality was moved or removed due to swapping filereading for webscraping (version 2.0)
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code Atom} class holds information on a single element, the information gathered on a search query, and allows for more mobility
 * and versitility than instances of {@link ChemicalElement}.
 * 
 * @author Michael Armendariz
 */

public class Atom extends RepresentativeUnits
{
	private int atomicNumber;
	private double tempMoles=1.0;
	private ArrayList<Integer> oxidationStates=new ArrayList<>();

	/**
	 * Initializes a newly created {@code Atom} object and accepts the search query {@code String}. The search entry <i>must</i> be validated 
	 * using the {@link chemistry.Atom.getAtomEntryByString getAtomEntryByString()} method or else it may not be located, causing errors.
	 *
	 * @param search query to locate in the element database
	 */

	public Atom(String search)
	{
		setFields(search);
	}

	/**
	 * Initializes a newly created {@code Atom} object and accepts the search query {@code String} and number of moles. The search entry 
	 * <i>must</i> be validated using the {@link chemistry.Atom.getAtomEntryByString getAtomEntryByString()} method or else it may not be 
	 * located, causing errors.
	 *
	 * @param query to locate in the molecule database
	 * @param moles of the element
	 */

	public Atom(String search,double moles)
	{
		super(moles);
		setFields(search);
	}
	
	/**
	 * Initializes a newly created {@code Atom} object and accepts the atomic number as an {@code int} value. The search entry <i>must</i> 
	 * be validated using the {@link chemistry.Atom.getAtomEntryByAtomicNumber getAtomEntryByAtomicNumber()} method or else it may not be 
	 * located, causing errors.
	 *
	 * @param query to locate in the molecule database
	 */

	public Atom(int search)
	{
		setFields(search);
	}
	
	/**
	 * Initializes a newly created {@code Atom} object and accepts the atomic number as an {@code int} value. The search entry <i>must</i> 
	 * be validated using the {@link chemistry.Atom.getAtomEntryByAtomicNumber getAtomEntryByAtomicNumber()} method or else it may not be 
	 * located, causing errors.
	 *
	 * @param query to locate in the molecule database
	 * @param moles of the element
	 */

	public Atom(int search,double moles)
	{
		super(moles);
		setFields(search);
	}
	
	private void setFields(String search) //initialize all global fields through setter methods
	{
		ChemicalElement element=ChemicalElement.getChemicalElementFromString(search);
		
		this.setName(element.getName());
		this.setSymbol(element.getSymbol());
		this.setOxidationStates(element.getOxidationStates());
		this.setMass(element.getMass());
		this.setAtomicNumber(element.getAtomicNumber());
	}
	
	private void setFields(int search) //initialize all global fields through setter methods
	{
		ChemicalElement element=ChemicalElement.getChemicalElementFromAtomicNumber(search);
		
		this.setName(element.getName());
		this.setSymbol(element.getSymbol());
		this.setOxidationStates(element.getOxidationStates());
		this.setMass(element.getMass());
		this.setAtomicNumber(search);
	}	
	
	/**
	 * Sets a temporary reference to a number of moles of the element.
	 * 
	 * @param tempMoles
	 */

	public void setTempMoles(double tempMoles)
	{
		this.tempMoles=tempMoles;
	}

	/**
	 * Sets the array of <i>known</i> oxidation states of the element.
	 * 
	 * @param states
	 */

	public void setOxidationStates(int[] states)
	{
		for(int i:states)
			this.oxidationStates.add(i);
	}

	/**
	 * @param atomicNumber
	 */
	
	public void setAtomicNumber(int atomicNumber)
	{
		this.atomicNumber=atomicNumber;
	}
	
	public int compareTo(RepresentativeUnits other)
	{
		if(other instanceof Atom)
			return (int)Math.signum(this.atomicNumber-((Atom)other).getAtomicNumber()); //returns atomic number if Atom
		return (int)Math.signum(this.getMass()-other.getMass()); //otherwise returns mass comparison
	}

	/**
	 * @return atomic number of the element
	 */

	public int getAtomicNumber()
	{
		return atomicNumber;
	}
	
	/**
	 * Gets the list of known oxidation states for the element represented by the {@code Atom} instance.
	 * 
	 * @return {@code ArrayList<Integer>} oxidation states of the element
	 */
	
	public ArrayList<Integer> getOxidationStates()
	{
		return this.oxidationStates;
	}
	
	/**
	 * A method to include when the user is required to enter a valid name and symbol. A prompt appears, then a search on the input results in
	 * a list of the best matches being printed to the screen. The user can then input the desired index from the list.
	 * 
	 * @return index of the desired entry, -1 if result was undesired or invalid index was entered
	 */
	
	public static String getAtomEntryByString()
	{
		boolean solved=false;
		int index=0;
		List<String> results=null;
		
		String search=Use.nextLine(">>> Enter search query: ");
		while(!solved)
		{
			results=PeriodicTableSearcher.getSearchResultsByString(search);
			index=PeriodicTableSearcher.confirmResult(results);
			if(index==-1)
			{
				search=Use.nextLine(">>> Enter search query: "); //this allows user to search again if the initial input was incorrect
				continue;
			}
			solved=true;
		}
		return results.get(index);
	}
	
	/**
	 * A method to include when the user is required to enter a valid atomic number. A prompt appears, then a search on the input results in
	 * a list of the best matches being printed to the screen. The user can then input the desired index from the list.
	 * 
	 * @return index of the desired entry, -1 if result was undesired or invalid index was entered
	 */
	
	public static int getAtomEntryByAtomicNumber()
	{
		boolean solved=false;
		int index=0;
		List<String> results=null;
		
		int search=Use.nextInt(">>> Enter search query: ");
		while(!solved)
		{
			results=PeriodicTableSearcher.getSearchResultsByAtomicNumber(search);
			index=PeriodicTableSearcher.confirmResult(results);
			if(index==-1)
			{
				search=Use.nextInt(">>> Enter search query: "); //this allows user to search again if the initial input was incorrect
				continue;
			}
			solved=true;
		}
		return Integer.parseInt(results.get(index));
	}
	
	
	public String toString()
	{
		String str=tempMoles*getMoles()+" moles of "+getName().toLowerCase()+" ("+getSymbol()
				+") atoms have atomic number "+atomicNumber+" and mass of "+Use.round(getMass()*tempMoles,2)+" g";
		tempMoles=1;
		return str;
	}
}