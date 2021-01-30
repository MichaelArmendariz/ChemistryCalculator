package linear_algebra;

/*
 * Code: class VectorOperations
 * Author: Michael Armendariz
 * Date: 8/10/20
 * Code Version: 1.0
 * Revisions:
 * 
 * N/A
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * Provides methods to perform arithmetic operations on vectors
 * 
 * @author Michael Armendariz
 */

public class VectorOperations
{
	/**
	 * The sum of the products of the corresponding terms of two vectors
	 * 
	 * @param v
	 * @param w
	 * @return the scalar v•w
	 */
	
	public static double dotProd(double[] v,double[] w) //vectors must be represented as arrays
	{
		if(v.length!=w.length) throw new IllegalArgumentException("Vectors are not compatible!");
		int sum=0;
		for(int i=0;i<v.length;i++)
				sum+=v[i]*w[i];
		return sum;
	}
}