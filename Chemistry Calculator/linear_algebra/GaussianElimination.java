package linear_algebra;

/*
 * Code: class GaussianElimination
 * Author: Michael Armendariz
 * Date: 8/9/20
 * Code Version: 1.2
 * Revisions:
 * 
 * (8/10/20) Removed deep copies from methods because implementation is now provided from Matrix
 * 
 * (8/11/20) Made invert() more intuitive after adding augmentation methods to Matrix and MatrixOperations
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * Provides functions to perform row eliminations of matrices
 * 
 * @author Michael Armendariz
 */

public class GaussianElimination
{
	/**
	 * Takes any matrix and returns its row echelon form; that is, an upper triangular matrix if the input is a square matrix
	 * 
	 * @param matrix
	 * @return {@code Matrix} object made into upper triangular matrix by Gaussian elimination
	 */
	
	public static Matrix rowEchelon(Matrix matrix)
	{
		double[][] access=matrix.getArr();
		int m=access.length,n=access[0].length;
		
		double[] temp;
		int rank=m<n?m:n; //m.length is m and m[0].length is n (lesser of the two)
		
		for(int i=0;i<rank;i++) //loops by rank (square out of matrix)
		{
			//find largest term and swap row with row i
			int largestIndex=i;
			for(int j=i;j<rank;j++) //starts at diagonal rank term and searches down col
				if(access[j][i]>access[largestIndex][i]) //goes down rows but same col (j,i) and largestIndex is in col as well
					largestIndex=j;
			temp=access[i]; //current top row (rank term)
			access[i]=access[largestIndex]; //duplicate largestIndex row into top row
			access[largestIndex]=temp; //replace with unneeded row
			
			//move down current col from rank value performing operation
			double rankValue=access[i][i];
			for(int j=i+1;j<m;j++) //loop travels down entire col from below current rank value
			{
				if(rankValue==0) continue;
				double factor=access[j][i]/rankValue; //will make 0 out of term under rank value
				for(int k=i;k<n;k++) //loop travels across row from rank term
					access[j][k]-=access[i][k]*factor; //multiply by factor, subtract value in i row from current row
			}
		}
		round(access,m,n); //rounds in one step
		return new Matrix(access);
	}
	
	public static Matrix reducedRowEchelon(Matrix matrix)
	{
		boolean rowEchelon=true;
		double[][] access=matrix.getArr();
		int m=access.length,n=access[0].length;
		int rank=m<n?m:n; //m.length is m and m[0].length is n (lesser of the two)
		
		for(int i=0;i<rank;i++) //check to see if already row echelon
			for(int j=i+1;j<rank;j++)
				if(access[j][i]!=0)
					rowEchelon=false;
		if(!rowEchelon) throw new IllegalArgumentException("Matrix is not in row echelon form");
		
		//DOES NOT PROPERLY FIND REF WHEN COLS ARE NOT ARRANGED
		
		for(int i=rank-1;i>=0;i--) //moves across cols
		{
			double rankValue=access[i][i];
			for(int j=i-1;j>=0;j--) //moves up rows
			{
				if(rankValue==0) continue;
				double factor=access[j][i]/rankValue; //will make 0 out of term under rank value
				for(int k=i;k<n;k++) //loop travels across row from rank term
					access[j][k]=access[j][k]-access[i][k]*factor; //multiply by factor, subtract value in i row from current row
			}
		}
		diagonalNormalize(access,m,n); //makes rank 1.0
		
		round(access,m,n); //takes care of decimal bugs in one step
		return new Matrix(access);
	}
	
	private static void diagonalNormalize(double[][] mat,int m,int n)
	{
		double diagValue;
		for(int i=0;i<m;i++)
		{
			diagValue=mat[i][i];
			for(int j=0;j<n;j++)
				mat[i][j]/=diagValue;
		}
	}
	
	//the code below is momentarily defunct until I can make better methods for finding right, left, and complete 
	//inverses of matrices. While this Gaussian elimination can work, it doesn't provide enough mathematical precision for my liking
	
	/**
	 * Inverts a given matrix using Gauss-Jordan elimination and augmented matrices
	 * 
	 * @param matrix
	 * @return {@code Matrix} object M inverse, M^-1
	 */
	
//	public static Matrix invert(Matrix matrix)
//	{
//		double[][] terms=matrix.getArr();
//		int m=terms.length;
//		double[][] id=new double[m][m];
//		
//		for(int i=0;i<m;i++)
//			for(int j=0;j<m;j++)
//				id[i][j]=i==j?1:0; //creates identity matrix of right size
//		
//		Matrix identity,augmented,gauss_jordan,inverse; //puts the matrix and identity matrix together
//		identity=new Matrix(id);
//		augmented=new Matrix(matrix,identity);
//		
//		gauss_jordan=GaussianElimination.rowEchelon(augmented); //makes input matrix into inverse with I matrix tacked on
//		gauss_jordan=GaussianElimination.reducedRowEchelon(gauss_jordan);
//		
//		int gjM,augmentedN1,augmentedN2;
//		gjM=gauss_jordan.getM();
//		augmentedN1=augmented.getAugmentedN(1);
//		augmentedN2=augmented.getAugmentedN(2);
//		
//		inverse=MatrixOperations.deaugment(gauss_jordan,2,gjM,augmentedN1,augmentedN2); //takes gauss-jordan matrix, its height, then the indeces of the augmented matrix, then chooses the second matrix
//		
//		return inverse;
//	}
	
	private static void round(double[][] unrounded,int m,int n)
	{
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				unrounded[i][j]=round(unrounded[i][j]); //fixes rounding errors
	}
	
	private static double round(double num) //reduces rounding error
	{
		String str=num+"";
		if(str.contains("E-")) return 0; //sees if exponential to negative power
		if(str.contains(".000000")) return Double.parseDouble(str.substring(0,str.indexOf(".")));
		return num;
	}
}