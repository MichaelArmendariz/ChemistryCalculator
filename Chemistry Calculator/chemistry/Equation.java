package chemistry;

/*
 * Code: interface Equations
 * Author: Michael Armendariz
 * Date: 4/7/20
 * Code Version: 1.0
 * Revisions:
 *
 * N/A
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code Equations} interface ensures all subclasses can return a solution
 * 
 * @author Michael Armendariz
 */

public interface Equation
{
	/**
	 * @return The solution to the equation represented by the instance
	 */
	
	public double solve();
}