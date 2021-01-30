package use;
import java.io.*;
import java.math.*;
import java.util.*;

/**
 * A class containing simpliying algorithms that are easily executed in typical projects but can be highly repetitive. Use in place of writing 
 * the code every time it is needed.
 * 
 * @author Michael Armendariz
 * @author Ananya Kharche
 */

public class Use
{
	static Scanner scan;
	
	/**
	 * Replaces Scanner to scan an integer
	 * 
	 * @return input
	 */

	public static int nextInt()
	{
		scan=new Scanner(System.in);
		int ret=scan.nextInt();
		
		return ret;
	}

	/**
	 * Replaces Scanner and System.out.println() prompt to scan an integer
	 * 
	 * @return input
	 */

	public static int nextInt(String str)
	{
		scan=new Scanner(System.in);
		System.out.print(str);
		int ret=scan.nextInt();
		
		return ret;
	}

	/**
	 * Replaces Scanner to scan a double
	 * 
	 * @return input
	 */

	public static double nextDouble()
	{
		scan=new Scanner(System.in);
		double ret=scan.nextDouble();
		
		return ret;
	}

	/**
	 * Replaces Scanner and System.out.println() prompt to scan a double
	 * 
	 * @return input
	 */

	public static double nextDouble(String str)
	{
		scan=new Scanner(System.in);
		System.out.print(str);
		double ret=scan.nextDouble();
		
		return ret;
	}

	/**
	 * Replaces Scanner to scan a word
	 * 
	 * @return input
	 */

	public static String next()
	{
		scan=new Scanner(System.in);
		String ret=scan.next();
		
		return ret;
	}

	/**
	 * Replaces Scanner and System.out.println() prompt to scan a word
	 * 
	 * @return input
	 */

	public static String next(String str)
	{
		scan=new Scanner(System.in);
		System.out.print(str);
		String ret=scan.next();
		
		return ret;
	}

	/**
	 * Replaces Scanner to scan a String
	 * 
	 * @return input
	 */

	public static String nextLine()
	{
		scan=new Scanner(System.in);
		String ret=scan.nextLine();
		
		return ret;
	}

	/**
	 * Replaces Scanner and System.out.println() prompt to scan a String
	 * 
	 * @return input
	 */

	public static String nextLine(String str)
	{
		scan=new Scanner(System.in);
		System.out.print(str);
		String ret=scan.nextLine();
		
		return ret;
	}

	/**
	 * Replaces Scanner to scan a boolean
	 * 
	 * @return input
	 */

	public static boolean nextBool()
	{
		scan=new Scanner(System.in);
		boolean ret=scan.nextBoolean();
		
		return ret;
	}

	/**
	 * Replaces Scanner and System.out.println() prompt to scan a boolean
	 * 
	 * @return input
	 */

	public static boolean nextBool(String str)
	{
		scan=new Scanner(System.in);
		System.out.print(str);
		boolean ret=scan.nextBoolean();
		
		return ret;
	}

	/**
	 * Replaces Scanner to scan a String, then convert to a char[]
	 * 
	 * @return input
	 */

	public static char[] nextChArr()
	{
		scan=new Scanner(System.in);
		String ret=scan.nextLine();
		
		return ret.toCharArray();
	}

	/**
	 * Replaces Scanner and System.out.println() prompt to scan a String, then convert to a char[]
	 * 
	 * @return input
	 */

	public static char[] nextChArr(String str)
	{
		scan=new Scanner(System.in);
		System.out.println(str);
		String ret=scan.nextLine();
		
		return ret.toCharArray();
	}

	/**
	 * Converts a char[] to a String
	 * 
	 * @return input
	 */

	public static String toString(char[] arr)
	{
		String ret="";
		for(char ch:arr)
			ret+=ch+"";
		return ret;
	}

	/**
	 * Finds index of first appearance of an uppercase character in the String
	 * 
	 * @return index
	 */

	public static int indexUpper(String str)
	{
		for(int i=1;i<str.length();i++)
			if(Character.isUpperCase(str.charAt(i)))
				return i;
		return -1;
	}

	/**
	 * Finds index of first appearance after given index of an uppercase character in the String
	 * 
	 * @return index
	 */

	public static int indexUpper(String str,int index)
	{
		for(int i=index+1;i<str.length();i++)
			if(Character.isUpperCase(str.charAt(i)))
				return i;
		return -1;
	}
	
	/**
	 * Finds index of the first appearance of a digit in the String
	 * 
	 * @return index of digit, -1 if not present
	 */

	public static int indexDigit(String str)
	{
		for(int i=1;i<str.length();i++)
			if(Character.isDigit(str.charAt(i)))
				return i;
		return -1;
	}

	/**
	 * Finds index of first appearance after given index of a digit in the String
	 * 
	 * @return index
	 */
	
	public static int indexDigit(String str,int index)
	{
		for(int i=index+1;i<str.length();i++)
			if(Character.isDigit(str.charAt(i)))
				return i;
		return -1;
	}
	
	/**
	 * Reads an input file and returns the initialized {@code Scanner}
	 * 
	 * @return Scanner
	 * @throws FileNotFoundException
	 */

	public static Scanner fileReader(String file) throws FileNotFoundException
	{
		File f=new File(file);
		return new Scanner(f);
	}
	
	/**
	 * Converts all characters in a {@code char[]} into their lowercase form
	 * 
	 * @param arr
	 * @return lowercased array
	 */
	
	public static char[] toLower(char[] arr)
	{
		String str="";
		for(char ch:arr)
			str+=ch;
		return str.toLowerCase().toCharArray();
	}

	/**
	 * Converts all characters in a {@code char[]} into their uppercase form
	 * 
	 * @param arr
	 * @return uppercased array
	 */
	
	public static char[] toUpper(char[] arr)
	{
		String str="";
		for(char ch:arr)
			str+=ch;
		return str.toUpperCase().toCharArray();
	}

	/**
	 * Converts an array to an ArrayList
	 * 
	 * @param <T>
	 * @param arr
	 * @return ArrayList
	 */
	
	public static <T> ArrayList<T> toArrList(T[] arr)
	{
		ArrayList<T> ret=new ArrayList<T>();
		for(T e:arr)
			ret.add(e);
		return ret;
	}

	/**
	 * Converts an ArrayList to an array
	 * 
	 * @param <T>
	 * @param arr
	 * @return array
	 */
	
	public static <T> T[] toArr(ArrayList<T> arr)
	{
		@SuppressWarnings("unchecked")
		T[] ret=(T[]) new Object[arr.size()];
		for(int i=0;i<arr.size();i++)
			ret[i]=arr.get(i);
		return ret;
	}

	/**
	 * Converts an {@code String[]} into a {@code int[]} by converting all number characters into primitives
	 * 
	 * @param arr
	 * @return int-converted array
	 */
	
	public static int[] toIntArr(String[] arr)
	{
		int[] curr=new int[arr.length];
		for(int i=0;i<arr.length;i++)
			curr[i]=Integer.parseInt(arr[i]);
		return curr;
	}

	/**
	 * Converts an {@code int[]} into a {@code boolean[]} by replacing all elements not equal to one with false and all equal to one with true
	 * 
	 * @param arr
	 * @return boolean-converted array
	 */
	
	public static boolean[] toBooleanArr(int[] arr)
	{
		boolean[] curr=new boolean[arr.length];
		for(int i=0;i<arr.length;i++)
			curr[i]=arr[i]==1;
		return curr;
	}

	/**
	 * Bubble sorts an {@code int[]}
	 * 
	 * @param arr
	 * @return bubble-sorted array
	 */
	
	public static int[] bubbleSort(int[] arr)
	{
		boolean sorted=false;
		int curr;
		while(!sorted)
		{
			sorted=true;
			for(int i=0;i<arr.length-1;i++)
				if(arr[i+1]<arr[i])
				{
					curr=arr[i];
					arr[i+1]=arr[i];
					arr[i]=curr;
					sorted=false;
				}
		}
		return arr;
	}

	/**
	 * Generates an array of {@code Integer} compatible primitives
	 * 
	 * @param arr
	 * @return unwrapped array
	 */
	
	public static int[] unwrap(Integer[] arr)
	{
		int[] ret=new int[arr.length];
		for(int i=0;i<arr.length;i++)
			ret[i]=arr[i].intValue();
		return ret;
	}

	/**
	 * Generates an array of {@code Double} compatible primitives
	 * 
	 * @param arr
	 * @return unwrapped array
	 */
	
	public static double[] unwrap(Double[] arr)
	{
		double[] ret=new double[arr.length];
		for(int i=0;i<arr.length;i++)
			ret[i]=arr[i].doubleValue();
		return ret;
	}

	/**
	 * Generates an array of {@code Character} compatible primitives
	 * 
	 * @param arr
	 * @return unwrapped array
	 */
	
	public static char[] unwrap(Character[] arr)
	{
		char[] ret=new char[arr.length];
		for(int i=0;i<arr.length;i++)
			ret[i]=arr[i].charValue();
		return ret;
	}

	/**
	 * Generates an array of {@code int} compatible wrapper classes
	 * 
	 * @param arr
	 * @return wrapped array
	 */
	
	public static Integer[] wrap(int[] arr)
	{
		Integer[] ret=new Integer[arr.length];
		for(int i=0;i<arr.length;i++)
			ret[i]=arr[i];
		return ret;
	}

	/**
	 * Generates an array of {@code double} compatible wrapper classes
	 * 
	 * @param arr
	 * @return wrapped array
	 */
	
	public static Double[] wrap(double[] arr)
	{
		Double[] ret=new Double[arr.length];
		for (int i=0;i<arr.length;i++)
			ret[i]=arr[i];
		return ret;
	}

	/**
	 * Generates an array of {@code char} compatible wrapper classes
	 * 
	 * @param arr
	 * @return wrapped array
	 */
	
	public static Character[] wrap(char[] arr)
	{
		Character[] ret=new Character[arr.length];
		for(int i=0;i<arr.length;i++)
			ret[i]=arr[i];
		return ret;
	}

	/**
	 * Sorts an {@code int[]}
	 * 
	 * @param arr
	 * @return sorted array
	 */
	
	public static int[] sort(int[] arr)
	{
		return unwrap(toArr(sort(toArrList(wrap(arr)))));
	}

	/**
	 * Sorts a {@code double[]}
	 * 
	 * @param arr
	 * @return sorted array
	 */
	
	public static double[] sort(double[] arr)
	{
		return unwrap(toArr(sort(toArrList(wrap(arr)))));
	}

	/**
	 * Searches an for an exact match of the given element
	 * 
	 * @param arr
	 * @param e
	 * @return index of match, -1 if not found
	 */
	
	public static <T> int search(T[] arr, T e)
	{
		for(int i=0;i<arr.length;i++)
			if(e.equals(arr[i]))
				return i;
		return -1;
	}

	/**
	 * Searches a {@String[]} for an exact match of the given element
	 * 
	 * @param arr
	 * @param e
	 * @return index of match, -1 if not found
	 */
	
	public static int search(String[] arr, String e)
	{
		for(int i=0;i<arr.length;i++)
			if(e.equals(arr[i]))
				return i;
		return -1;
	}
	
	/**
	 * Sorts an ArrayList of objects extending Comparable
	 * 
	 * @param <T>
	 * @param arr
	 * @return sorted ArrayList
	 */

	public static <T extends Comparable<? super T>> ArrayList<T> sort(ArrayList<T> arr)
	{
		Collections.sort(arr);
		
		return arr;
	}

	/**
	 * Prints an array
	 * 
	 * @param arr
	 */
	
	public static <T> void printArr(T[] arr)
	{
		for(T t:arr)
			System.out.print(t+" ");
		System.out.println();
	}

	/**
	 * Prints an array with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static <T> void printArr(T[] arr,String delim)
	{
		for(T t:arr)
			System.out.print(t+""+delim);
		System.out.println();
	}

	/**
	 * Prints a 2D array
	 * 
	 * @param arr
	 */
	
	public static <T> void printArr(T[][] arr)
	{
		for(T[] row:arr)
			printArr(row);
	}

	/**
	 * Prints a 2D array with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static <T> void printArr(T[][] arr,String delim)
	{
		for (T[] row:arr)
			printArr(row,delim);
	}

	/**
	 * Prints an {@code int[]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(int[] arr)
	{
		for(int i:arr)
			System.out.print(i+" ");
		System.out.println();
	}

	/**
	 * Prints an int[] with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(int[] arr,String delim)
	{
		for(int i:arr)
			System.out.print(i+delim);
		System.out.println();
	}

	/**
	 * Prints an {@code int[][]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(int[][] arr)
	{
		for(int[] i:arr)
			printArr(i);
	}

	/**
	 * Prints an {@code int[][]} with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(int[][] arr,String delim)
	{
		for(int[] i:arr)
			printArr(i,delim);
	}

	/**
	 * Prints a {@code double[]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(double[] arr)
	{
		for(double i:arr)
			System.out.print(i+" ");
		System.out.println();
	}

	/**
	 * Prints a {@code double[]} with a delimter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(double[] arr,String delim)
	{
		for(double i:arr)
			System.out.print(i+delim);
		System.out.println();
	}

	/**
	 * Prints a {@code double[][]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(double[][] arr)
	{
		for(double[] i:arr)
			printArr(i);
	}

	/**
	 * Prints a {@code double[][]} with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(double[][] arr,String delim)
	{
		for(double[] i:arr)
			printArr(i,delim);
	}

	/**
	 * Prints a {@code char[]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(char[] arr)
	{
		for(char i:arr)
			System.out.print(i+" ");
		System.out.println();
	}

	/**
	 * Prints a {@code char[][]} with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(char[] arr,String delim)
	{
		for(char i:arr)
			System.out.print(i+delim);
		System.out.println();
	}

	/**
	 * Prints a {@code char[][]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(char[][] arr)
	{
		for(char[] i:arr)
			printArr(i);
	}

	/**
	 * Prints a {@code char[][]} with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(char[][] arr,String delim)
	{
		for(char[] i:arr)
			printArr(i,delim);
	}

	/**
	 * Prints a {@code String[]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(String[] arr)
	{
		for(String i:arr)
			System.out.print(i+" ");
		System.out.println();
	}

	/**
	 * Prints a {@code String[]} with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(String[] arr,String delim)
	{
		for(String i:arr)
			System.out.print(i+delim);
		System.out.println();
	}

	/**
	 * Prints a {@code String[][]}
	 * 
	 * @param arr
	 */
	
	public static void printArr(String[][] arr)
	{
		for(String[] i:arr)
			printArr(i);
	}

	/**
	 * Prints a {@code String[][]} with a delimeter between each element
	 * 
	 * @param arr
	 * @param delim
	 */
	
	public static void printArr(String[][] arr,String delim)
	{
		for(String[] i:arr)
			printArr(i,delim);
	}

	/**
	 * Reverses the order of elements in an ArrayList
	 * 
	 * @param <T>
	 * @param arr
	 * @return reversed ArrayList
	 */
	
	public static <T> ArrayList<T> reverse(ArrayList<T> arr)
	{
		ArrayList<T> ret=new ArrayList<T>();
		for(T t:arr)
			ret.add(0,t);
		
		return ret;
	}

	/**
	 * Reverses the order of elements in an input array
	 * 
	 * @param <T>
	 * @param arr
	 * @return reversed array
	 */
	
	public static <T> T[] reverse(T[] arr)
	{
		return toArr(reverse(toArrList(arr)));
	}

	/**
	 * Reverses an input {@code String}
	 * @param str
	 * @return reversed string
	 */
	
	public static String reverse(String str)
	{
		String ret="";
		for(int i=str.length()-1;i>=0;i--)
			ret+=str.charAt(i);
		
		return ret;
	}

	/**
	 * Generates a random boolean value from the input keys
	 * 
	 * @param threshold
	 * @param max
	 * @return random boolean
	 */
	
	public static boolean random(int threshold,int max)
	{
		return (int)(Math.random()*max)<threshold;
	}

	/**
	 * Rounds a given number to the given number of <i>decimal places</i>, not significant figures
	 * 
	 * @param num
	 * @param places
	 * @return rounded number
	 */
	
	public static double round(double num,int places)
	{
		if(places<0)
			throw new IllegalArgumentException();
		BigDecimal bd=BigDecimal.valueOf(num);
		bd=bd.setScale(places,RoundingMode.HALF_UP);
		
		return bd.doubleValue();
	}
	
	/**
	 * A method that formats an input {@code String} to be bound on the top and bottom by a border of "=========" fitting the length of the
	 * largest subsection of the input.
	 * <p>
	 * <b>Examples:</b>
	 * <p>
	 * formatResult("Hello") returns "=====\nHello\n====="; printing this creates
	 * <p>
	 * =====<br>
	 * Hello<br>
	 * =====<br>
	 * <p>
	 * formatResult("Why do programmers always mix up Halloween and Christmas?\nBecause Oct 31 equals Dec 25."):
	 * <p>
	 * =========================================================<br>
	 * Why do programmers always mix up Halloween and Christmas?<br>
	 * Because Oct 31 equals Dec 25.<br>
	 * =========================================================<br>
	 * <p>
	 * This effectively formats any input into a neat result if the printed symbols are in a font where all characters have the same width
	 * 
	 * @param str
	 * @return formatted result
	 */
	
	public static String formatResult(String str)
	{
		int longest=0;
		if(!str.contains("\n"))
			longest=str.length();
		else
		{
			String[] sections=str.split("\n");
			
			for(String s:sections)
				if(s.length()>longest)
					longest=s.length();
		}
		
		String border="";
		for(int i=0;i<longest;i++)
			border+="=";
		str=border+"\n"+str+"\n"+border;
		return str;
	}
	
	/**
	 * Computes the Levenshtein distance between two strings
	 * 
	 * @param a
	 * @param b
	 * @return lev(a,b)
	 */
	
	public static int levenshteinDistance(String a,String b)
	{
		if(a.length()==0)
			return b.length();
		if(b.length()==0)
			return a.length();
		if(a.charAt(0)==b.charAt(0))
			return levenshteinDistance(a.substring(1),b.substring(1));
		
		int first=0,second=0,third=0,biggest=0;

		first=levenshteinDistance(a.substring(1),b);
		second=levenshteinDistance(a,b.substring(1));
		third=levenshteinDistance(a.substring(1),b.substring(1));
		biggest=Math.min(Math.min(first,second),third);
		
		return 1+biggest;
	}
	
	/**
	 * Computes the Levenshtein distance between two strings with a threshold, making the maximum distance between two strings that threshold,
	 * saving resources for strings of large size
	 * 
	 * @param a
	 * @param b
	 * @param threshold
	 * @return lev(a,b), with a maximum value of <i>threshold</i>
	 */
	
	public static int levenshteinDistance(String a,String b,int threshold)
	{
		if(threshold==0)
			return 0;
		if(a.length()==0)
			return b.length();
		if(b.length()==0)
			return a.length();
		if(a.charAt(0)==b.charAt(0))
			return levenshteinDistance(a.substring(1),b.substring(1),threshold-1);
		
		int first=0,second=0,third=0,biggest=0;

		first=levenshteinDistance(a.substring(1),b,threshold-1);
		second=levenshteinDistance(a,b.substring(1),threshold-1);
		third=levenshteinDistance(a.substring(1),b.substring(1),threshold-1);
		biggest=Math.min(Math.min(first,second),third);
		
		return 1+biggest;
	}
	
	/**
	 * Formats a {@code String} into a "title" by capitalizing the words after spaces
	 * @param str
	 * @return titlized {@code String}
	 */
	
	public static String titlizer(String str)
	{
		String[] words=str.split(" ");
		String ret="",curr="";
		
		for(int i=0;i<words.length;i++)
		{
			curr=words[i];
			ret+=Character.toUpperCase(curr.charAt(0))+curr.substring(1)+" ";
		}
		
		return ret.substring(0,ret.length()-1);
	}
}