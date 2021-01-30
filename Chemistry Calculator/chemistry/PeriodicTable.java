package chemistry;

import java.io.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/*
 * Code: class PeriodicTable
 * Author: Michael Armendariz
 * Date: 1/23/21
 * Code Version: 1.1
 * Revisions:
 *
 * (1/24/21) Added getChemicalElementFromString() and getChemicalElementFromAtomicNumber()
 *
 * Availability: public, Eclipse IDE
 */

/**
* The {@code PeriodicTable} class acts to initialize the {@code ChemicalElement} objects that comprise the periodic table via webscraping. 
* This way, the most important information from the webpage is parsed and loaded in one pass over the document, making information access fast.
* 
* @author Michael Armendariz
*/

public class PeriodicTable
{
	protected static Document titlePageDoc;
	protected static ArrayList<ChemicalElement> periodicTable=new ArrayList<>(); //every element in the periodic table
	protected static final String periodicTableLink="https://periodic.lanl.gov/list.shtml"; //source
	
	//extremely slow static block to initialize the webscraped information in all elements just once for the entire class type
	//Try multithreading? Would need to convert ArrayLists to Vectors to maintain syncronicity
	
	static
	{
		try
		{
			titlePageDoc=Jsoup.connect(periodicTableLink).get(); //connect to the front page link
			Elements lines=titlePageDoc.select("tr");
			String[] words;
			String name="",symbol="",number="";
			
			for(int i=1;i<lines.size();i++) //symbol, name, number
			{
				words=lines.get(i).text().split(" ");
				symbol=words[0];
				name=words[1];
				number=words[2];
				periodicTable.add(new ChemicalElement(symbol,name,number)); //instantiate all of the elements in the global array
				if(name.equals("Meitnerium"))
					break;
				
				symbol=words[3];
				name=words[4];
				number=words[5];
				if(name.equals("Neilsborium")) //because this government-run site has multiple listings for element 107
					continue;
				periodicTable.add(new ChemicalElement(symbol,name,number)); //instantiate all of the elements in the global array
				
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Returns the chemical element requested from the input. This input <i>must</i> be a valid {@code String}, properly spelled and present in 
	 * the periodic table, otherwise this method will throw an IllegalArgumentException.
	 * 
	 * @param str
	 * @return {@code ChemicalElement} associated with the input {@code String}
	 */
	
	public final static ChemicalElement getChemicalElementFromString(String str)
	{
		if(str.contains(" "))
			str=str.substring(0,str.indexOf(" (")); //to include just name, just symbol, or both combined

		boolean stringIsName=str.length()>2; //should speed up computation
		for(ChemicalElement chem:periodicTable)
		{
			if(stringIsName&&str.equals(chem.getName())||str.equals(chem.getSymbol()))
				return chem;
		}
		
		throw new IllegalArgumentException("Search has not been validated and cannot be found in periodic table!");
	}
	
	/**
	 * Returns the chemical element requested from the input. This input <i>must</i> be a valid atomic number, present in the periodic table, 
	 * otherwise this method will throw an IllegalArgumentException.
	 * 
	 * @param num
	 * @return {@code ChemicalElement} associated with the input {@code int}
	 */
	
	public final static ChemicalElement getChemicalElementFromAtomicNumber(int num)
	{
		try
		{
			return periodicTable.get(num-1);
		}
		catch(IndexOutOfBoundsException|InputMismatchException e)
		{
			throw new IllegalArgumentException("Search has not been validated and cannot be found in periodic table!");
		}
	}
	
	/**
	 * Is called to initialize the static block so later computation runs much faster
	 */
	
	public static void initialize() {} //keep?
}
