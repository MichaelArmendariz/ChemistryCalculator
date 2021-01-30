package chemistry;

import calculus.*;
import use.*;
import java.util.*;

/*
 * Code: class IdealGasEquation
 * Author: Michael Armendariz
 * Date: 4/6/20
 * Code Version: 1.2
 * Revisions:
 *
 * (4/7/20) Created Equations interface and implementation statement;
 * 			changed access of double variables to no access classifier;
 *			removed unnecessary gasName variable
 *
 * (12/24/20) Added extends NewtonsMethod and function method;
 * 			moved function to function() method instead of solve();
 * 			integrated NewtonsMethod from solve();
 * 			changed p,v,n,t global variables to Double data type;
 * 			fixed bug with creating new instance of same class within solve()
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code IdealGasEquation} class accepts the necessary parameters to return a solution for some missing value of a gas sample based on the 
 * ideal gas equation.
 * 
 * @author Michael Armendariz
 */

public class IdealGasEquation extends NewtonsMethod implements Equation
{
	Double p=null,v=null,n=null,t=null;
	int check=0;
	public final double R=0.082057366080960; //units of L*atm*mol^-1*K^-1
	//I don't want to use m^3*Pas*mol^-1*K^-1, even though this is standard SI units, because lab measurements aren't normally large quantities
	
	/**
	 * @param pressure
	 */
	
	public final void setPressure(double p)
	{
		this.p=p;
		check++;
	}

	/**
	 * @param volume
	 */
	
	public final void setVolume(double v)
	{
		this.v=v;
		check++;
	}

	/**
	 * @param moles
	 */
	
	public final void setMoles(double n)
	{
		this.n=n;
		check++;
	}

	/**
	 * @param temperature
	 */
	
	public final void setTemperature(double t)
	{
		this.t=t;
		check++;
	}

	public double function(double x)
	{
		double variable=0;
		
		if(p==null)
			variable=(x*v)-(n*R*t);
		else if(v==null)
			variable=(p*x)-(n*R*t);
		else if(n==null)
			variable=(p*v)-(x*R*t);
		else if(t==null)
			variable=(p*v)-(n*R*x);
		
		return variable;
	}
	
	public double solve()
	{
		if(check!=3)
			throw new IllegalArgumentException("Cannot find solution with "+check+" known parameters"); // errors if user did not enter all necessary values to generate a solution
		
		ArrayList<Double> zeros=this.getZeros(0,1E4,(int)1E5); //arbitrarily small precision and large boundary --> should be honed down with a better algorithm later (use second derivative?)
		double solution=Double.parseDouble((zeros+"").replace("[","").replace("]",""));
		return Use.round(solution,6);
	}
}