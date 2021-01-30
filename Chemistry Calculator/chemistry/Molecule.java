package chemistry;

import java.util.*;
import java.io.*;
import java.text.ParseException;

import use.*;
import uk.ac.manchester.libchebi.*;

/*
 * Code: class Molecule
 * Author: Michael Armendariz
 * Date: 3/28/20
 * Code Version: 2.0
 * Revisions:
 *
 * (4/3/20) Added readerSearch method to shorten constructors;
 * 			made Van Der Waals constants default -1; added compareTo;
 *			added sorting ArrayList elements
 *
 * (4/15/20) Added check for entry of symbol vs. full name to avoid confusion in search;
 * 			added fileSearch(), hasRepeats(), and fileContains() to improve pre-instance searches in further implementation
 * 
 * (4/17/20) Added "No results found!" to fileSearch() method
 * 
 * (7/21/20) Added getAtoms() method (mainly for the DegreeOfUnsaturation class);
 * 			added getMoleculeEntry() as a static method to improve functionality and provide additional implementation
 * 
 * (1/24/21) Dispersed intitialization actions from constructors to setFields(), where setter methods are called;
 * 
 * (1/26/21) All methods related to reading input from the Molecule Database text file were deleted;
 * 			most of the old functionality was moved or removed due to swapping filereading for webscraping (in regard to RealGas)
 * 			and official database search (in regard to CHEBI API) (version 2.0)
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code Molecule} class holds information on a single element, the information gathered on a search query. It serves as the functional 
 * component of a molecule generated from the CHEBI web API.
 * 
 * @author Michael Armendariz
 */

public class Molecule extends RepresentativeUnits
{
	private static final int NUM_SEARCH_RESULTS=15;
	private int charge;
	private ArrayList<Atom> elements=new ArrayList<Atom>();
	private String definition,smiles;

	/**
	 * Initializes a newly created {@code Molecule} object and accepts the search query {@code String search} for the database
	 * @throws ParseException
	 * @throws IOException 
	 */

	public Molecule(ChebiEntity search) throws IOException, ParseException
	{
		setFields(search);
	}

	/**
	 * Initializes a newly created {@code Molecule} object and accepts the search query {@code String search} for the database
	 * as well as initialize the number of moles of the molecule
	 *
	 * @param query to locate in the molecule database
	 * @param moles of the molecule
	 * @throws ParseException 
	 * @throws IOException 
	 */

	public Molecule(ChebiEntity search,double moles) throws IOException, ParseException
	{
		super(moles);
		setFields(search);
	}
	
	/**
	 * Initializes a newly created {@code Molecule} object with a {@code RealGas} object
	 *
	 * @param realGas molecule entry whose information will initialize the instance
	 */
	
	public Molecule(RealGas rg)
	{
		setFields(rg);
	}

	/**
	 * Initializes a newly created {@code Molecule} object with a {@code RealGas} object
	 *
	 * @param realGas molecule entry whose information will initialize the instance
	 * @param moles of the molecule
	 */
	
	public Molecule(RealGas rg,double moles)
	{
		super(moles);
		setFields(rg);
	}
	
	private void setFields(ChebiEntity search) throws IOException, ParseException
	{
		this.setName(Use.titlizer(search.getName()));
		this.setSymbol(search.getFormula());
		this.setMass(search.getMass());
		this.charge=search.getCharge();
		this.definition=search.getDefinition();
		this.smiles=search.getSmiles();
		try
		{
			this.elements=getAtomArrayFromSymbol(this.getSymbol());
		}
		catch(Exception e)
		{
			this.elements=getAtomArrayFromSymbol(this.getSymbol().substring(0,this.getSymbol().indexOf("R")));
		}
	}
	
	private void setFields(RealGas rg)
	{
		this.setName(rg.getName());
		this.setSymbol(rg.getSymbol());
		this.setMass(rg.getMass());
		this.elements=getAtomArrayFromSymbol(this.getSymbol());
	}
	
	/**
	 * Allows the client to convert any valid chemical formula to an {@code ArrayList} of {@code Atom} objects. The list
	 * is formatted with a unique element for each entry and the number of atoms of that element in the molecule within the
	 * {@code Atom} objects themselves.
	 * 
	 * @param symbol
	 * @return atoms in the molecule
	 */

	public static ArrayList<Atom> getAtomArrayFromSymbol(String symbol)
	{
		String str=symbol,elem="";
		int mult=0;
		ArrayList<Atom> atoms=new ArrayList<>();
		
		while(!str.isEmpty()) //repeats while string is filled
		{
			try //attempts to find next uppercase letter
			{
				elem=str.substring(0,Use.indexUpper(str));
				str=str.substring(Use.indexUpper(str));
			}
			catch(Exception e) //if no uppercase letter next, sets elem to rest of string and empties string
			{
				elem=str;
				str="";
			}
			if(Use.indexDigit(elem)!=-1) //if there is a digit
			{
				mult=Integer.parseInt(elem.substring(Use.indexDigit(elem)));
				elem=elem.substring(0,Use.indexDigit(elem));
				atoms.add(new Atom(elem,mult)); //new Atom with that number of moles
			}
			else
				atoms.add(new Atom(elem)); //default Atom
		}

		return atoms;
	}

	/**
	 * @return list of all elements in the molecule, each representing the total number of instances of that atom in the molecular structure
	 */
	
	public ArrayList<Atom> getAtoms()
	{
		return elements;
	}

	/**
	 * @return charge of the molecule
	 */

	public int getCharge()
	{
		return charge;
	}

	/**
	 * @return definition of the molecule on file
	 */
	
	public String getDefinition()
	{
		return this.definition;
	}
	
	/**
	 * @return SMILES code
	 */
	
	public String getSMILES()
	{
		return smiles;
	}
	
	public int compareTo(RepresentativeUnits other)
	{
		return (int)Math.signum(getMass()-other.getMass()); //returns mass comparison
	}
	
	/**
	 * Allows client class to easily search the molecule database without needing extra implementation. 
	 * This method directly allows the client to locate a search query in the database.
	 * 
	 * @return {@code ChebiEntity} match
	 * @throws ParseException 
	 * @throws ChebiException 
	 * @throws org.apache.lucene.queryparser.classic.ParseException
	 * @throws FileNotFoundException
	 */
	
	public static ChebiEntity getMoleculeEntry() throws IOException, ChebiException, ParseException, org.apache.lucene.queryparser.classic.ParseException
	{
		ChebiSearch searcher=ChebiSearch.getInstance();
		boolean solved=false;
		int index=0;
		Vector<ChebiEntity> results=new Vector<>();
		
		String search=Use.nextLine(">>> Enter search query: ");
		while(!solved)
		{
			results=searcher.getPotentialMatches(search,NUM_SEARCH_RESULTS,false);
			index=confirmResult(results);
			if(index==-1)
			{
				search=Use.nextLine(">>> Enter search query: "); //this allows user to search again if the initial input was incorrect
				continue;
			}
			solved=true;
		}
		return results.get(index);
	}
	
	public static int confirmResult(Vector<ChebiEntity> potentialMatches) throws IOException, ParseException
	{
		String str;
		System.out.printf("\n%-45s%s\n\n","=== Molecule ===","=== Index ===");
		for(int i=0;i<potentialMatches.size();i++)
		{
			str=Use.titlizer(potentialMatches.get(i).getName());
			System.out.printf("%-45s",str);
			System.out.printf("%s\n",i+1);
		}
		
		return getInput(potentialMatches);
	}
	
	private static int getInput(Vector<ChebiEntity> potentialMatches)
	{
		try
		{
			int input=Use.nextInt("\n>>> Enter index of desired result (-1 if search again): ");
			potentialMatches.get(input-1);
			return input-1;
		}
		catch(IndexOutOfBoundsException|InputMismatchException e) //user could input negative number (preferred) or gibberish
		{
			System.out.println("\nSearching again...");
			return -1;
		}
	}
	
	public String toString()
	{
		Collections.sort(elements);
		String str="";
		for(Atom atom:elements)
		{
			atom.setTempMoles(getMoles()); //temporarily changes number of moles in Atoms
			str+="\n"+atom;
		}
		return getMoles()+" moles of "+getName().toLowerCase()+" ("+getSymbol()+") molecules have mass of "
				+Use.round(getMass(),2)+" g"+"\n\nComponent atoms include\n\n"+str.substring(1);
	}
}