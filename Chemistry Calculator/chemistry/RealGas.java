package chemistry;

/*
 * Code: class RealGas
 * Author: Michael Armendariz
 * Date: 1/24/21
 * Code Version: 1.1
 * Revisions:
 *
 * (1/25/21) Moved molecularRepresentation to RealGasMolecule
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code RealGas} class is a storage class to hold information from the webscraping done by VanDerWaalsSearcher. The class is intended 
 * to represent any given real gas in the table and hold all necessary data about that molecule from the <b><a href="https://chem.libretexts.org/
 * Bookshelves/Ancillary_Materials/Reference/Reference_Tables/Atomic_and_Molecular_Properties/A8%3A_van_der_Waal's_Constants_for_Real_Gases">
 * Chemistry LibreTexts Van Der Waals Constants Table</a></b> webpage and related hyperlinks.
 * 
 * @author Michael Armendariz
 */

public class RealGas extends VanDerWaalsConstantsTable implements Comparable<RealGas>
{
	private double a,b,mass;
	private String symbol,name;
	
	/**
	 * Initializes the fields of a new {@code RealGas} object
	 * 
	 * @param name
	 * @param symbol
	 * @param a
	 * @param b
	 */
	
	public RealGas(String name,String symbol,double a,double b)
	{
		this.a=a;
		this.b=b;
		this.symbol=symbol;
		this.name=name;
	}
	
	/**
	 * @return Van Der Waals constant A
	 */
	
	public double getAConst()
	{
		return a;
	}
	
	/**
	 * @return Van Der Waals constant B
	 */
	
	public double getBConst()
	{
		return b;
	}

	/**
	 * @return Name of the real gas
	 */
	
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return Symbol of the real gas
	 */
	
	public String getSymbol()
	{
		return symbol;
	}
	
	/**
	 * @return Molecular representation of the {@code RealGas} instance as a {@code Molecule} object
	 */
	
	public Molecule getMolecularRepresentation()
	{
		return new Molecule(this); //this prevents client class from accessing the same molecule objects repeatedly
	}
	
	/**
	 * @return Molar mass of the gas molecule
	 */

	public double getMass()
	{
		return mass;
	}

	public int compareTo(RealGas other)
	{
		return (int)Math.signum(getMass()-other.getMass()); //returns mass comparison
	}
}