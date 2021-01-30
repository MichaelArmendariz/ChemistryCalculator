package chemistry;

import java.io.*;

/*
 * Code: class RepresentativeUnits
 * Author: Michael Armendariz
 * Since: 3/28/20
 * Code Version: 1.6
 * Revisions:
 *
 * (3/29/20) Changed int multiplicity to double moles;
 * added getMoles() overloaded method
 * (4/3/20) Added implements Comparable for compareTo method and sorts
 * (4/6/20) Added final method descriptors to ensure subclasses can't override
 * (4/15/20) Added static File objects readAtom and readMolecule
 * (7/20/20) Added setMoles method to directly set molar amounts (so RealGas can have proper gas ratios)
 * (1/8/21) Added fields periodicTableDatabase and vanDerWaalsConstantsDatabases
 *
 * Availability: public, Eclipse IDE
 */

/**
 *
 * The {@code RepresentativeUnits} class saves the values of a particle's name,
 * symbol, mass, and number of particles and returns them when needed
 * <p>
 * {@code RepresentativeUnits} also ensures that all child classes set these fields on instantiation
 * 
 * @author Michael Armendariz
 */

public abstract class RepresentativeUnits implements Comparable<RepresentativeUnits>
{
	private String name,symbol;
	private double mass,moles=1.0;
	
	protected static File readMolecule=new File("C:\\Users\\micha\\Desktop\\Molecule Database.txt");

	/**
	 * Initializes a newly created {@code RepresentativeUnits} object
	 */

	public RepresentativeUnits(){}

	/**
	 * Initializes a newly created {@code RepresentativeUnits}
	 * object and sets the number of moles of that particle present
	 * 
	 * @param moles : the number of moles of a given substance
	 */

	public RepresentativeUnits(double moles)
	{
		this.moles=moles;
	}

	/**
	 * Ensures that all subclasses must set the fields of the {@code RepresentativeUnits} class
	 * 
	 * @param line : {@code String} object of the
	 *             current line being viewed from text databases on the desktop
	 * @throws FileNotFoundException
	 */

	/**
	 * Sets the name of the particle
	 * 
	 * @param name : name of the particle
	 */

	public final void setName(String name)
	{
		this.name=name;
	}
	
	/**
	 * Directly sets the amount of the particle in moles
	 * 
	 * @param moles : amount of the particle in moles
	 */

	public final void setMoles(double moles)
	{
		this.moles=moles;
	}
	
	/**
	 * Sets the symbol of the particle
	 * 
	 * @param symbol : symbol of the particle
	 */

	public final void setSymbol(String symbol)
	{
		this.symbol=symbol;
	}

	/**
	 * Sets the molar mass of the particle
	 * 
	 * @param mass : mass of the particle in amu
	 */

	public final void setMass(double mass)
	{
		this.mass=mass;
	}

	/**
	 * Gets the name of the particle
	 * 
	 * @return {@code String} name of the particle
	 */

	public final String getName()
	{
		return name;
	}

	/**
	 * Gets the symbol of the particle
	 * 
	 * @return {@code String} symbol of the paricle
	 */

	public final String getSymbol()
	{
		return symbol;
	}

	/**
	 * Gets the molar mass of the particle
	 * 
	 * @return {@code double} mass of the particle in amu
	 */

	public final double getMass()
	{
		return mass*moles;
	}

	/**
	 * Gets the mass of a given number of moles of a particle
	 * 
	 * @param moles : moles of a particle
	 * @return {@code double} mass of the adjusted number of moles
	 */

	public final double getMass(double moles)
	{
		return mass*this.moles*moles;
	}

	/**
	 * Gets the number of moles of a particle at instantiation, not including
	 * any prior adjustments made to molar masses through the {@code getMass()} method
	 * 
	 * @return {@code double} moles of a particle
	 */

	public final double getMoles()
	{
		return moles;
	}

	/**
	 * Gets the number of moles of a particle at instantiation, not including
	 * any prior adjustments made to molar masses through the {@code getMass()} method,
	 * while also accepting an adjusting parameter of the number of moles of the particle
	 * 
	 * @return {@code double} moles of a particle
	 */

	public final double getMoles(double moles)
	{
		return this.moles*moles;
	}
}