package chemistry;

import use.*;
import java.util.*;

/*
 * Code: class PeriodicTableSearcher
 * Author: Michael Armendariz
 * Date: 1/15/21
 * Code Version: 1.2
 * Revisions:
 *
 * (1/22/21) fixed getSearchResultsByString() to have a legitimate fuzzy string search using the Levenshtein distance algorithm - recursive 
 * 			definition in the Use class because it seems very helpful in other applications as well. The matrix definition seemed more 
 * 			computationally expensive than I was willing to employ;
 * 			Changed data structure of a lot of the ArrayLists in the same method to HashMaps because of their usefulness in associating one 
 * 			of the lev(a,b) outputs with its string
 * 
 * (1/23/21) Moved static block to PeriodicTable class and extended PeriodicTable;
 * 			also changed PeriodicTableLink interface to PeriodicTable class for that exact reason
 *
 * Availability: public, Eclipse IDE
 */

/**
* The {@code PeriodicTableSearcher} class allows a client class to easily access a periodic table dataset
* 
* @author Michael Armendariz
*/

public class PeriodicTableSearcher extends PeriodicTable
{
	private static final int NUM_SEARCH_RESULTS=10;
	
	/**
	 * Searches the periodic table using a {@code String} query that can either be the element name or symbol
	 * @param query
	 * @return List of top search results in the form of <b>Name (Symbol)</b>
	 */

	public static List<String> getSearchResultsByString(String query)
	{
		ChemicalElement curr=null;
		String currString=null,currName=null,currSymbol=null;
		boolean isName=query.length()>2; //determine if it is looking for a name or symbol
		
		HashMap<String,Integer> weightedResults=new HashMap<>();
		
		for(int i=0;i<118;i++)
		{
			curr=periodicTable.get(i); //takes each element
			currName=curr.getName();
			currSymbol=curr.getSymbol();
			
			if(isName)
				currString=currName;
			else
				currString=currSymbol;
			
			//fuzzy string search by Levenshtein distance algorithm!
			weightedResults.put(currName+" ("+currSymbol+")",Use.levenshteinDistance(query,currString));
		}
		HashMap<String,Integer> orderedByValueResults=sortValues(weightedResults); //sends HashMap to sortValues() in order to sort by the value
		List<Map.Entry<String,Integer>> maps=new LinkedList<>(orderedByValueResults.entrySet()); //converts it back into a Map.Entry list
		List<String> results=new ArrayList<>();
		int mapsSize=maps.size();
		
		for(int i=0;i<NUM_SEARCH_RESULTS&&i<mapsSize;i++)
			results.add(maps.get(i).getKey()); //the return list has a cutoff number of results (can't go over the length of the list)
		
		return results;
	}
	
	/**
	 * Searches the periodic table using a {@code int} query as the atomic number of the element
	 * @param query
	 * @return List of top search results in the form of <b>Atomic_Number</b>
	 */
	
	public static List<String> getSearchResultsByAtomicNumber(int query) //basically convert integer to String and parse back to save space
	{
		ChemicalElement curr=null;
		int currNum=0;
		
		HashMap<String,Integer> weightedResults=new HashMap<>();
		
		for(int i=0;i<118;i++)
		{
			curr=periodicTable.get(i);
			currNum=curr.getAtomicNumber();
			
			//fuzzy string search by Levenshtein distance algorithm!
			weightedResults.put(currNum+"",Use.levenshteinDistance(query+"",currNum+""));
		}
		HashMap<String,Integer> orderedByValueResults=sortValues(weightedResults); //sends HashMap to sortValues() in order to sort by the value
		List<Map.Entry<String,Integer>> maps=new LinkedList<>(orderedByValueResults.entrySet()); //converts it back into a Map.Entry<> list
		List<String> results=new ArrayList<>();
		int mapsSize=maps.size();
		
		for(int i=0;i<NUM_SEARCH_RESULTS&&i<mapsSize;i++)
			results.add(maps.get(i).getKey()); //the return list has a cutoff number of results (can't go over the length of the list)
		
		return results;
	}

	private static HashMap<String,Integer> sortValues(HashMap<String,Integer> hash)
	{
		List<Map.Entry<String,Integer>> entries=new LinkedList<>(hash.entrySet()); //converts the HashMap to a list of Maps
		
		Collections.sort(entries,new Comparator<Map.Entry<String,Integer>>() //inner interface definition
				{
					public int compare(Map.Entry<String,Integer> map_1,Map.Entry<String,Integer> map_2) //defines how to compare the Maps
					{
						return map_1.getValue()-map_2.getValue(); //so that the values closest to 0 are prioritized first
					}
				});
		
		HashMap<String,Integer> sorted=new LinkedHashMap<>();
		for(Map.Entry<String,Integer> map:entries)
			sorted.put(map.getKey(),map.getValue());
		
		return sorted;
	}

	/**
	 * Presents the user with the list of results and allows them to input where the correct search result is or 
	 * decline and ask to search again
	 * @param results
	 * @return Index of the result (positive value if within the bounds of the list) or -1
	 */
	
	public static <T> int confirmResult(List<T> results)
	{
		if(results.get(0) instanceof String) //in the case that this is the name/symbol
		{
			String str=null;
			System.out.printf("\n%-30s%s\n\n","=== Element ===","=== Index ===");
			for(int i=0;i<results.size();i++)
			{
				str=(String)results.get(i);
				System.out.printf("%-30s",str);
				System.out.printf("%s\n",i+1);
			}
		}
		else if(results.get(0) instanceof Integer) //case that this is the atomic number
		{
			Integer num=null;
			System.out.printf("\n%-30s%s\n\n","=== Atomic Number ===","=== Index ===");
			for(int i=0;i<results.size();i++)
			{
				num=(Integer)results.get(i);
				System.out.printf("%-30s",num);
				System.out.printf("%s\n",i+1);
			}
		}
		else
			throw new IllegalArgumentException("List must be an output of PeriodicTableSearcher.getSearchResultBy______");
		
		return getInput(results);
	}
	
	private static <T> int getInput(List<T> results)
	{
		try
		{
			int input=Use.nextInt("\n>>> Enter index of desired result (-1 if search again): ");
			results.get(input-1);
			return input-1;
		}
		catch(IndexOutOfBoundsException|InputMismatchException e) //user could input negative number (preferred) or gibberish
		{
			System.out.println("\nSearching again...");
			return -1;
		}
	}
}