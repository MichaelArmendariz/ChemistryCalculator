package chemistry;

import java.util.*;

/*
 * Code: class DegreeOfUnsaturation
 * Author: Michael Armendariz
 * Date: 7/21/20
 * Code Version: 1.0
 * Revisions:
 * 
 * N/A
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code DegreeOfUnsaturation} class takes in a {@link Molecule} object and calculates the number of rings and pi-bonds present in the 
 * molecular structure.
 * 
 * @author Michael Armendariz
 */

public class DegreeOfUnsaturation implements Equation
{
	private ArrayList<Atom> atoms=new ArrayList<Atom>();
	
	/**
	 * Initializes a newly created {@code DegreeOfUnsaturation} object and accepts a {@link Molecule} object to calculate the IHD for
	 *
	 * @param molecule
	 */
	
	public DegreeOfUnsaturation(Molecule m)
	{
		this.atoms=m.getAtoms();
	}
	
	public double solve() //with the latest updates to Atom and Molecule, this can either be much more functional or completely unnecessary
	{
		int c=0,h=0,x=0,n=0;
		
		for(Atom a:atoms)
			switch(a.getAtomicNumber()) //currently only understands the most basic formulas of organic compounds (no organometallics)
			{
				case 1:
					h+=a.getMoles();
					break;
				case 6:
					c+=a.getMoles();
					break;
				case 7:
					n+=a.getMoles();
					break;
				case 8:
					break;
				case 16:
					break;
				case 9:
				case 17:
				case 35:
				case 53:
					x+=a.getMoles();
					break;
				default:
					throw new IllegalArgumentException("Molecule contains elements without known valencies!"); //fix this
			}
		
		return c-(h/2.0)-(x/2.0)+(n/2.0)+1;
	}
}