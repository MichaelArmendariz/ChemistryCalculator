package linear_algebra;

import java.util.*;

/*
 * Code: class Matrix
 * Author: Michael Armendariz
 * Date: 7/22/20
 * Code Version: 1.2
 * Revisions:
 * 
 * (8/10/20) Method getArr() has been revised to properly return a deep copy of the matrix field
 * 
 * (8/11/20) Constructor added to create augmented matrices,
 * 			 added fields for m and n and methods to get them in client classes, as well as isAugmented field
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code Matrix} class creates a 2D array that holds the values of each cell of a matrix
 * 
 * @author Michael Armendariz
 */

public class Matrix
{
	private double[][] matrix;
	private int nA,nB,n,m; //either stores values of individual matrices or a single matrix
	private boolean isAugmented;
	
	/**
	 * Instantiates a new {@code Matrix} object using a string of terms separated by spaces; 
	 * each row separated by a semicolon
	 * 
	 * @param values
	 */
	
	public Matrix(String values)
	{
		values=values.replace("; ",";").replace(" ;",";");
		String[] rows=values.split(";"),temp;
		ArrayList<ArrayList<String>> pre=new ArrayList<>();
		
		for(int i=0;i<rows.length;i++) //first loop through all rows and save as Strings/split by rows
		{
			temp=rows[i].split(" ");
			pre.add(new ArrayList<String>());
			for(int j=0;j<temp.length;j++)
				pre.get(i).add(temp[j]);
		}
		
		double[][] matrix=new double[rows.length][pre.get(0).size()];
		for(int i=0;i<rows.length;i++) //go through all and parse into doubles
			for(int j=0;j<matrix[0].length;j++)
				matrix[i][j]=Double.parseDouble(pre.get(i).get(j));
		
		int m=matrix.length,n=matrix[0].length;
		this.m=m;
		this.n=n;
		this.matrix=matrix;
	}
	
	/**
	 * Instantiates a new {@code Matrix} object using a prepared 2D array
	 * 
	 * @param matrix
	 */
	
	public Matrix(double[][] matrix)
	{
		int m=matrix.length,n=matrix[0].length;
		this.m=m;
		this.n=n;
		this.matrix=new double[m][n];
		
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				this.matrix[i][j]=matrix[i][j];
	}
	
	/**
	 * Instantiates a new augmented {@code Matrix} object as the augmented matrix [A|B]
	 * 
	 * @param {@code Matrix} a
	 * @param {@code Matrix} b
	 */
	
	public Matrix(Matrix a,Matrix b)
	{
		isAugmented=true;
		double[][] arrA=a.getArr(),arrB=b.getArr(),augmented;
		int mA=arrA.length,nA=arrA[0].length,mB=arrB.length,nB=arrB[0].length;

		this.m=mA; //sets both fields
		this.nA=nA;
		this.nB=nB;
		
		if(mA!=mB) throw new IllegalArgumentException("Matrices are incompatible!");
		augmented=new double[mA][nA+nB]; //length is n of A plus n of B
		
		for(int i=0;i<mA;i++) //creates augmented matrix
		{
			for(int j=0;j<nA;j++) //includes A matrix
				augmented[i][j]=arrA[i][j];
			for(int j=nA;j<nB+nA;j++) //includes B matrix
				augmented[i][j]=arrB[i][j-nA];
		}
		matrix=augmented;
	}
	
	/**
	 * @return an array corresponding to the data held by the instance
	 */
	
	public double[][] getArr()
	{
		int m=matrix.length,n=matrix[0].length;
		double[][] ret=new double[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				ret[i][j]=matrix[i][j];
		return ret;
	}
	
	/**
	 * Will return the m value of the matrix
	 * 
	 * @return total number of rows in matrix
	 */
	
	public int getM()
	{
		return m;
	}
	
	/**
	 * Will return the full n value of the matrix, the sum of both matrices' n values if an augmented matrix
	 * 
	 * @return total number of columns in the matrix
	 */
	
	public int getN()
	{
		if(isAugmented) return nA+nB; //the length will not be the same, so add the lengths
		return n;
	}
	
	/**
	 * Will return a partial n value of the matrix, as specified by either a 1 or a 2 index for the value of the A or B component matrices
	 * 
	 * @param matrixIndex
	 * @return the specific n value of either matrix in an augmented matrix
	 */
	
	public int getAugmentedN(int matrixIndex)
	{
		if(!isAugmented) throw new IllegalArgumentException("Matrix is not augmented");
		
		switch(matrixIndex)
		{
			case 1: return nA;
			case 2: return nB;
			default: throw new IllegalArgumentException("Augmented matrix holds only 2 matrices between 1 and 2");
		}
	}
	
	public boolean isAugmented()
	{
		return isAugmented;
	}
	
	public String toString() //┌,│,┐,└,┘
	{
		String n="";
		for(double c:matrix[0])
			n+=c+"";
		System.out.print("┌");
		for(int i=0;i<n.length()+matrix[0].length-1;i++)
			System.out.print(" ");
		System.out.println("┐");
		for(int i=0;i<matrix.length;i++)
		{
			System.out.print("│");
			for(int j=0;j<matrix[0].length;j++)
			{
				System.out.print(matrix[i][j]);
				if(j!=matrix[0].length-1) System.out.print(" ");
			}
			System.out.println("│");
		}
		System.out.print("└");
		for(int i=0;i<n.length()+matrix[0].length-1;i++)
			System.out.print(" ");
		System.out.print("┘");
		return "";
	}
}