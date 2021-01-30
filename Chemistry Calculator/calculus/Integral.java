package calculus;

/*
 * Code: class Integral
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
 * The {@code Integral} class calculates the integral of a client-defined function on a given interval (<i>a</i>,<i>b</i>)
 * 
 * @author Michael Armendariz
 */

public abstract class Integral implements Operation
{
	private final double DX=0.000001;
	private double integral;
	
	private void integrate(double a,double b)
	{
		double sum=0;
		double n=b-a;
		
		for(double i=0;i<n;i+=DX)
			sum+=function(i+a);
		
		integral=sum*DX;
	}

	/**
	 * @param a,b
	 * @return Integral of function along (<i>a</i>,<i>b</i>)
	 */
	
	public double getIntegral(double a,double b)
	{
		integrate(a,b);
		return this.integral;
	}
}