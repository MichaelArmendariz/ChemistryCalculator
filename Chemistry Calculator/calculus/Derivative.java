package calculus;

/*
 * Code: class Derivative
 * Author: Michael Armendariz
 * Date: 12/21/20
 * Code Version: 1.0
 * Revisions:
 * 
 * N/A
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code Derivative} class calculates the derivative of a client-defined function at a given <i>x</i> value
 * 
 * @author Michael Armendariz
 */

public abstract class Derivative implements Operation
{
	private final double DX=0.000001;
	private double derivative;
	
	private void derive(double x)
	{
		double dy=function(x+DX)-function(x);
		derivative=dy/DX;
	}
	
	/**
	 * @param x
	 * @return Derivative of function at <i>x</i>
	 */
	
	public double getDerivative(double x)
	{
		derive(x);
		return this.derivative;
	}
}