package calculus;

import java.util.*;

/*
 * Code: class SolutionIntervals
 * Author: Michael Armendariz
 * Date: 12/23/20
 * Code Version: 1.0
 * Revisions:
 * 
 * (N/A)
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code SolutionIntervals} class finds intervals of a function in which there is a zero for the client-defined function
 * @author Michael Armendariz
 */

public abstract class SolutionIntervals extends Derivative
{
	private double precision;
	private ArrayList<double[]> intervals=new ArrayList<>();
	
	private void findIntervals(double boundary_a,double boundary_b)
	{
		if(boundary_a>=boundary_b) throw new IllegalArgumentException("Boundaries are not applicable");
		
		double dx=(boundary_b-boundary_a)/precision,curr=function(boundary_a),prev=curr;
		
		for(double x=boundary_a;x<=boundary_b;x+=dx)
		{
			prev=curr;
			curr=function(x);
			if(-Math.signum(curr)==Math.signum(prev))
				intervals.add(new double[] {x-dx,x});
		}
	}
	
	/**
	 * Finds the intervals of a function containing zeros between general boundaries to a certain precision. 
	 * If the precision is too low, some zeros may not be found.
	 * 
	 * @param boundary_a
	 * @param boundary_b
	 * @param precision
	 * @return {@code ArrayList<double[]>} intervals containing zeros
	 */
	
	public ArrayList<double[]> getIntervals(double boundary_a,double boundary_b,int precision)
	{
		this.precision=precision;
		findIntervals(boundary_a,boundary_b);
		return this.intervals;
	}
}