package chemistry;

import java.util.*;
import use.*;

/*
 * Code: class RealGasMolecule
 * Author: Michael Armendariz
 * Date: 1/24/21
 * Code Version: 1.1
 * Revisions:
 *
 * (1/25/21) Added molecularRepresentation field to hold a model of the real gas as a Molecule object
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code RealGasMolecule} class holds information on a single real gas, the information gathered on a search query, and allows for more 
 * mobility and versitility than instances of {@link RealGas} 
 * 
 * @author Michael Armendariz
 */

public class RealGasMolecule extends RepresentativeUnits
{
	private double a,b;
	private Molecule molecularRepresentation;
	private ArrayList<Atom> atoms;
	
	/**
	 * Initializes a newly created {@code RealGasMolecule} object and accepts the search query {@code String search} for the database
	 *
	 * @param search query to locate in the element database
	 */

	public RealGasMolecule(String search)
	{
		setFields(search);
	}

	/**
	 * Initializes a newly created {@code RealGasMolecule} object and accepts the search query {@code String search} for the database as well as initialize 
	 * the number of moles of the element
	 *
	 * @param query to locate in the molecule database
	 * @param moles of the element
	 */

	public RealGasMolecule(String search,double moles)
	{
		super(moles);
		setFields(search);
	}
	
	private void setFields(String search)
	{
		RealGas real=RealGas.getRealGasFromString(search); //searching with the name
		
		this.setName(real.getName());
		this.setSymbol(real.getSymbol());
		this.setMass(real.getMass());
		this.setAConst(real.getAConst());
		this.setBConst(real.getBConst());
		this.setMolecularRepresentation(real.getMolecularRepresentation());
		this.atoms=molecularRepresentation.getAtoms();
	}
	
	public void setAConst(double a)
	{
		this.a=a;
	}
	
	public void setBConst(double b)
	{
		this.b=b;
	}
	
	public void setMolecularRepresentation(Molecule molecularRepresentation)
	{
		this.molecularRepresentation=molecularRepresentation;
	}
	
	public double getAConst()
	{
		return a;
	}
	
	public double getBConst()
	{
		return b;
	}
	
	public Molecule getMolecularRepresentation()
	{
		return molecularRepresentation;
	}
	
	public ArrayList<Atom> getAtoms()
	{
		return atoms;
	}
	
	public int compareTo(RepresentativeUnits other)
	{
		return (int)Math.signum(this.getMass()-other.getMass());
	}
	
	public static String getRealGasEntry()
	{
		boolean solved=false;
		int index=0;
		List<String> results=null;
		
		String search=Use.nextLine(">>> Enter search query: ");
		while(!solved)
		{
			results=VanDerWaalsSearcher.getSearchResultsByString(search);
			index=VanDerWaalsSearcher.confirmResult(results);
			if(index==-1)
			{
				search=Use.nextLine(">>> Enter search query: "); //this allows user to search again if the initial input was incorrect
				continue;
			}
			solved=true;
		}
		return results.get(index);
	}
}