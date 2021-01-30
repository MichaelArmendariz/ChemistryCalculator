package chemistry;

import java.util.*;
import use.*;

/*
 * Code: class VanDerWaalsSearcher
 * Author: Michael Armendariz
 * Date: 1/25/21
 * Code Version: 1.1
 * Revisions:
 *
 * (1/26/21) Fixed a lot of the bugs in getWeightedResults() and tweaked the weighting algorithm (see comment)
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code VanDerWaalsSearcher} class serves to provide searching functionality for retrieving values from the Van Der Waals Constants Table
 * 
 * @author Michael Armendariz
 */

public class VanDerWaalsSearcher extends VanDerWaalsConstantsTable
{
	private static final int LENGTH_PRECISION=3; //+- this many digits when trimming possible matches before processing with levenshtein distance
	private static final int NUM_SEARCH_RESULTS=15;
	private static final int LEVENSHTEIN_PRECISION=12; //the k in O(k*n) complexity
	
	/**
	 * @param query
	 * @return List of top matches related to the search query
	 */
	
	public static List<String> getSearchResultsByString(String query)
	{
		RealGas curr=null;
		String currName=null,currSymbol=null;
		
		ArrayList<RealGas> possibleMatches=new ArrayList<>();
		HashMap<String,Double> weightedResults=new HashMap<>();
		int size=vanDerWaalsTable.size();
		
		for(int i=0;i<size;i++)
		{
			curr=vanDerWaalsTable.get(i);
			currName=curr.getName();
			currSymbol=curr.getSymbol();
			
			if(Math.abs(currName.length()-query.length())<=LENGTH_PRECISION|| //is the length of the name/symbol within PRECISION chars of the current?
					Math.abs(currSymbol.length()-query.length())<=LENGTH_PRECISION)
				possibleMatches.add(curr);
		}
		
		weightedResults=getWeightedResults(possibleMatches,query);
		
		HashMap<String,Double> orderedByValueResults=sortValues(weightedResults); //sends HashMap to sortValues() in order to sort by the value
		List<Map.Entry<String,Double>> maps=new LinkedList<>(orderedByValueResults.entrySet()); //converts it back into a Map.Entry list
		List<String> results=new ArrayList<>();
		int mapsSize=maps.size();
		
		for(int i=0;i<NUM_SEARCH_RESULTS&&i<mapsSize;i++)
			results.add(maps.get(i).getKey()); //can't go over the length of the list
		
		return results;
	}
	
	/*
	 * EXPLANATION OF THE WEIGHTING ALGORITHM FOR FUZZY STRING SEARCH:
	 * 
	 * V = Value, Q = Query, S = Symbol, N = Name, |X| = Length of X, <x> = Vector x, lev(A,B) = Levenshtein distance from A to B
	 * 
	 * In researching fuzzy string matching algorithms, I found the matrix definition of lev(). This seemed too expensive for both
	 * memory and computation, because the matrix forces the data into O(n^2) many memory cells as well as having O(n^2) complexity.
	 * A recursive definition was much more efficient and took only the memory of each stack. It also let me include a limiting 
	 * factor, k, in the algorithm. This limits the number of character changes that can be found before the string is seen as too poor
	 * of a match and the method exists, saving all of the computation that may have been wasted finding more changes to an already bad
	 * string. This fixes the complexity from O(n^2) to O(k*n), a large improvement for long strings.
	 * 
	 * My first inclination was to make lev(Q,S+N) the value in the HashMap, but this caused strange errors, like Neon getting a
	 * higher rating than Chlorine when Q = Cl2.
	 * 
	 * I wanted to make a weighted average, so I tried making <w> = <|S|, |N|> and <x> = <lev(Q,S), lev(Q,N).
	 * That made this equation:
	 * 
	 * V = (1/(|S|+|N|))*[|S|*lev(Q,S)+|N|*lev(Q,N)]
	 * 
	 * Which still favors Neon over Chlorine when Q = Cl2. By making the weight vector <w> = <1, 1>:
	 * 
	 * V = lev(Q,S)+lev(Q,N)
	 * 
	 * Which is effectively the same as the initial attempt. If the weights are altered to be constant instead, but non-identical:
	 * 
	 * V = k(lev(Q,S)+lev(Q,N)/k)
	 * 
	 * the lev() of the smaller symbol can be prioritized somewhat over the lev() of the name. This allows the user to search for both names 
	 * and symbols and have a better chance of receiving a higher ranking on the output of the lev() function than if the strings were only 
	 * concatenated. In the case that k = 2, Chlorine is prioritized over Neon for Q = Cl2 and the extremes are satisfied.
	 */
	
	private static HashMap<String,Double> getWeightedResults(List<RealGas> possibleMatches,String query)
	{
		int size=possibleMatches.size();
		double levA=0,levB=0,value=0;
		RealGas curr;
		String currName=null,currSymbol=null;
		HashMap<String,Double> hash=new HashMap<>();
		
		for(int i=0;i<size;i++)
		{
			curr=possibleMatches.get(i);
			currName=curr.getName();
			currSymbol=curr.getSymbol();
			
			levA=Use.levenshteinDistance(query.toLowerCase(),currSymbol,LEVENSHTEIN_PRECISION);
			levB=Use.levenshteinDistance(query.toLowerCase(),currName,LEVENSHTEIN_PRECISION)/2;
			value=(levA+levB)*2;
			
			hash.put(currName+" ("+currSymbol+")",value);
		}
		
		return hash;
	}

	private static HashMap<String,Double> sortValues(HashMap<String,Double> hash)
	{
		List<Map.Entry<String,Double>> entries=new LinkedList<>(hash.entrySet());
		
		Collections.sort(entries,new Comparator<Map.Entry<String,Double>>()
				{
					public int compare(Map.Entry<String,Double> map_1,Map.Entry<String,Double> map_2)
					{
						return (int)(map_1.getValue()-map_2.getValue()); //values closest to 0 are prioritized first
					}
				});
		
		HashMap<String,Double> sorted=new LinkedHashMap<>();
		for(Map.Entry<String,Double> map:entries)
			sorted.put(map.getKey(),map.getValue());
		
		return sorted;
	}
	

	/**
	 * Presents the user with the list of results and allows them to input where the correct search result is or 
	 * decline and ask to search again
	 * 
	 * @param results
	 * @return Index of the result (positive value if within the bounds of the list) or -1
	 */
	
	public static int confirmResult(List<String> results)
	{
		String str=null;
		System.out.printf("\n%-45s%s\n\n","=== Gas Species ===","=== Index ===");
		for(int i=0;i<results.size();i++)
		{
			str=(String)results.get(i);
			System.out.printf("%-45s",str);
			System.out.printf("%s\n",i+1);
		}
		
		return getInput(results);
	}

	private static int getInput(List<String> results)
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