package chemistry;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;

/*
 * Code: class ChemicalElement
 * Author: Michael Armendariz
 * Date: 1/15/21
 * Code Version: 1.1
 * Revisions:
 *
 * (1/18/21) Added constructor with call to setFields();
 * 			Decided to make the class supportive of instance variables (methods non-static);
 * 			Added setters, getters, and bracketBraceRemover()
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code ChemicalElement} class is an html-parser with webscraping functionality. The class is intended to represent any given chemical 
 * element and hold all necessary data about that element from the <b><a href="https://periodic.lanl.gov/list.shtml">Los Alamos National 
 * Laboratory Periodic Table</a></b> webpage and related hyperlinks.
 * 
 * @author Michael Armendariz
 */

public class ChemicalElement extends PeriodicTable implements Comparable<ChemicalElement>
{
	private String symbol,name;
	private int atomicNumber;
	private Double atomicRadius,atomicWeight;
	private int[] oxidationStates;
	
	/**
	 * Instantiates a {@code ChemicalElement} object and initializes its name, symbol, and atomic number, then webscrapes all remaining info.
	 *
	 * @param symbol
	 * @param name
	 * @param atomicNumber
	 * @throws IOException
	 */
	
	public ChemicalElement(String symbol,String name,String atomicNumber) throws IOException
	{
		this.symbol=symbol;
		this.name=name;
		this.atomicNumber=Integer.parseInt(atomicNumber);
		setFields();
	}
	
	private void setFields() throws IOException
	{
		//make new connection and connect to URL
		Connection periodicElementConnection=Jsoup.connect(periodicTableLink.replace("list",(atomicNumber+"")));
		//make new document and declare through URL connection
		Document periodicTableDoc=periodicElementConnection.get();
		//select lines that are rows of </datatable> (</tr> as opposed to </td>)
		Elements lines=periodicTableDoc.select("tr");
		
		for(Element e:lines)
		{
			String currLine=e.text();
			if(currLine.contains("Atomic Radius"))
				setAtomicRadius(currLine);
			else if(currLine.contains("Atomic Weight"))
				setAtomicWeight(currLine);
			else if(currLine.contains("Configuration")) //they spelled "oxidation states" and "electron configuration" wrong!
				setOxidationStates(currLine);	
		}
	}
	
	private void setOxidationStates(String str)
	{
		str=str.substring(str.indexOf("States"));
		//because "oxidtion" and "elctron"
		str=bracketBraceRemover(str);
		String[] nums=str.split(" ");
		int count=0;
		for(int i=0;i<nums.length;i++)
			if(nums[i].isEmpty())
				count++;
		oxidationStates=new int[nums.length-count];
		
		for(int i=0,localCounter=0;i<nums.length;i++)
			if(!nums[i].isEmpty())
				oxidationStates[i-localCounter]=Integer.parseInt(nums[i].trim());
		else
			localCounter++;
	}
	
	private String bracketBraceRemover(String str)
	{
		char[] chars=str.toCharArray();
		String ret="";
		boolean recording=true;
		
		for(char ch:chars) //break up string into characters and add to the return only if the characters are outside braces or brackets
		{
			if(ch=='['||ch=='(')
				recording=false;
			if(recording&&!Character.isAlphabetic(ch))
				ret+=ch;
			else if(ch==']'||ch==')')
				recording=true;
		}
		
		String[] nums=ret.split(", ");
		ret="";
		for(String num:nums)
			if(!num.isEmpty())
				ret+=num+" ";
		
		ret=ret.replace("+","").replace("−","-").replace(";","").replace(": ","").replace("  "," ");
		//This looks awful, but it just gets rid of gaps and prepares the numbers for parsing
		
		return ret;
	}

	private void setAtomicWeight(String str)
	{
		str=str.substring(0,str.indexOf("Boiling Point: ")); //I'm not storing boiling point --> search this a better way elsewhere
		str=str.substring(str.indexOf(":")+2);
		atomicWeight=Double.parseDouble(str);
	}

	private void setAtomicRadius(String str)
	{
		str=str.substring(str.indexOf("Atomic Radius: "));
		
		if(str.contains("--")) //some of the f-block elements have no data for atomic radius
		{
			atomicRadius=null;
			return;
		}
		
		String[] words=str.split(" ");
		for(int i=0;i<words.length;i++)
			if(words[i].contains("pm"))
			{
				atomicRadius=Double.parseDouble(words[i-1]);
				return;
				//one (1) singular element has "ATOMIC_RADIUS pm [some citation] pm" for no reason,
				//so it thinks "pm [some citation]" is a number and errors
				//hence the return
			}
	}
	
	/**
	 * @return atomic number of the chemical element
	 */
	
	public int getAtomicNumber()
	{
		return atomicNumber;
	}
	
	/**
	 * @return name of the chemical element
	 */
	
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return symbol of the chemical element
	 */
	
	public String getSymbol()
	{
		return symbol;
	}
	
	/**
	 * @return atomic radius of the chemical element
	 */
	
	public double getRadius()
	{
		return atomicRadius;
	}
	
	/**
	 * @return atomic mass of the chemical element
	 */
	
	public double getMass()
	{
		return atomicWeight;
	}
	
	/**
	 * @return array of positive and negative integers representing the <i>known</i> oxidation states of the chemical element
	 */
	
	public int[] getOxidationStates()
	{
		return oxidationStates;
	}
	
	public int compareTo(ChemicalElement element)
	{
		return (int)Math.signum(this.getAtomicNumber()-element.getAtomicNumber());
	}
}