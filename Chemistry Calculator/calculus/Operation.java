package calculus;

/*
 * Code: interface Operation
 * Author: Michael Armendariz
 * Date: 12/21/20
 * Code Version: 1.0
 * Revisions:
 * 
 * (N/A)
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code Operation} interface defines a class as an operation acting on a function and ensures the function is provided
 * 
 * @author Michael Armendariz
 */

public interface Operation
{
	/**
	 * Client-defined single-variable function of <i>x</i>
	 * 
	 * @param x
	 * @return
	 */
	public abstract double function(double x);
}