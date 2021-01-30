package chemistry;

import java.io.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/*
 * Code: class VanDerWaalsConstantsTable
 * Author: Michael Armendariz
 * Date: 1/24/21
 * Code Version: 1.1
 * Revisions:
 *
 * (12/25/20) added getRealGasFromString() to help verify Strings
 *
 * Availability: public, Eclipse IDE
 */

/**
* The {@code VanDerWaalsConstantsTable} class acts to initialize the {@code RealGas} objects that comprise the datatable via webscraping. 
* This way, the most important information from the webpage is parsed and loaded in one pass over the document, making information access fast.
* 
* @author Michael Armendariz
*/

public class VanDerWaalsConstantsTable
{
	protected static Document titlePageDoc;
	protected static ArrayList<RealGas> vanDerWaalsTable=new ArrayList<>();
	protected static final String vanDerWaalsTableLink="https://chem.libretexts.org/Bookshelves/Ancillary_Materials/Reference/Reference_Tables/Atomic_and_Molecular_Properties/A8%3A_van_der_Waal's_Constants_for_Real_Gases"; //source
	
	static
	{
		try
		{
			titlePageDoc=Jsoup.connect(vanDerWaalsTableLink).get();
			Elements lines=titlePageDoc.select("td");
			String name=null,symbol=null;
			double a=0,b=0;
			
			for(int i=0;i<lines.size();i+=4) //symbol, name, a, b
			{
				symbol=lines.get(i).text();
				name=lines.get(i+1).text();
				a=Double.parseDouble(lines.get(i+2).text());
				b=Double.parseDouble(lines.get(i+3).text().replace("`","")); //because there is a typo in one line again
				
				vanDerWaalsTable.add(new RealGas(name,symbol,a,b));
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * Returns the real gas requested from the input. This input <i>must</i> be a valid {@code String}, properly spelled and present in 
	 * the data table, otherwise this method will throw an IllegalArgumentException.
	 * 
	 * @param str
	 * @return {@code RealGas} associated with the input {@code String}
	 */
	
	public final static RealGas getRealGasFromString(String str)
	{
		if(str.contains(" "))
			str=str.substring(0,str.indexOf(" (")); //to include just name, just symbol, or both combined

		for(RealGas chem:vanDerWaalsTable)
			if(str.equals(chem.getName())||str.equals(chem.getSymbol()))
				return chem;
		
		throw new IllegalArgumentException("Search has not been validated and cannot be found in periodic table!");
	}
	
	/**
	 * Is called to initialize the static block so later computation runs much faster
	 */
	
	public static void initialize() {} //keep?
}