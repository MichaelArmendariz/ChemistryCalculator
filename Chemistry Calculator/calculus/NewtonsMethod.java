package calculus;

import java.util.*;

/*
 * Code: class NewtonsMethod
 * Author: Michael Armendariz
 * Date: 12/23/20
 * Code Version: 1.1
 * Revisions:
 * 
 * (12/28/20) Overloaded getZeros() and findZeros() methods to take in a reasonable estimation of the solution
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code NewtonsMethod} class calculates the zero of a client-defined function on a given interval (<i>a</i>,<i>b</i>)
 * 
 * @author Michael Armendariz
 */

public abstract class NewtonsMethod extends SolutionIntervals
{
	private final double PRECISION=0.0000001;
	private ArrayList<Double> zeros=new ArrayList<>();
	
	private void findZeros(ArrayList<double[]> intervals) //intervals on which there is a zero
	{
		for(int i=0;i<intervals.size();i++)
		{
			double[] interval=intervals.get(i);
			double a=interval[0],b=interval[1];
			double curr=(b-a)/2+a,prev=b,slope=0,y=0;
			
			while(!(curr<=prev+PRECISION&&curr>=prev-PRECISION))
			{
				slope=getDerivative(curr); //middle of the interval
				if(slope==0)
					slope=PRECISION;
				y=function(curr);
				prev=curr;
				curr=curr-(y/slope);
			}
			
			zeros.add(curr);
		}
	}
	
	private void findZeros(double estimate) //estimation of the zero
	{
		double curr=estimate,prev=estimate-1,slope=0,y=0;
		
		while(!(curr<=prev+PRECISION&&curr>=prev-PRECISION))
		{
			slope=getDerivative(curr);
			if(slope==0)
				slope=PRECISION;
			y=function(curr);
			prev=curr;
			curr=curr-(y/slope);
		}
		
		zeros.add(curr);
	}
	
	/**
	 * @param a,b
	 * @return Zeros of function within (<i>a</i>,<i>b</i>)
	 */
	
	public ArrayList<Double> getZeros(double a,double b,int precision) //this is for functions where the zeros are relatively unknown
	{
		ArrayList<double[]> intervals=getIntervals(a,b,precision);
		findZeros(intervals);
		return this.zeros;
	}
	
	/**
	 * @param estimate
	 * @return Zero of function near reasonable estimate of the zero
	 */
	
	public ArrayList<Double> getZeros(double estimate) //this is for functions where a zero is generally known
	{
		findZeros(estimate);
		return this.zeros;
	}
}
