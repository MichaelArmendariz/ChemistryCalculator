package linear_algebra;

/*
 * Code: class MatrixOperations
 * Author: Michael Armendariz
 * Date: 7/22/20
 * Code Version: 1.1
 * Revisions:
 * 
 * (8/10/20) Added methods sum(), sub(), and scale(),
 * 			 moved dotProd() to a new class, VectorOperations, and updated calls to the method
 * 
 * (8/11/20) Moved the transpose() method over from the Transpose class,
 * 			 added deaugment to easily separate augmented matrices
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * Provides methods to perform arithmetic operations on matrices
 * @author Michael Armendariz
 */

public class MatrixOperations
{
	/**
	 * Adds two input matrices as A + B = C
	 * 
	 * @param matrixA
	 * @param matrixB
	 * @return the sum {@code Matrix}
	 */
	
	public static Matrix sum(Matrix matrixA,Matrix matrixB)
	{
		double[][] a=matrixA.getArr(),b=matrixB.getArr();
		int mA=a.length,nA=a[0].length,mB=b.length,nB=b[0].length;
		if(mA!=mB||nA!=nB) throw new IllegalArgumentException("Matrices are not compatible!"); //check to see if dimensions are the same
		
		double[][] ret=new double[mA][nA];
		for(int i=0;i<mA;i++) //rows
			for(int j=0;j<nA;j++) //cols
				ret[i][j]=a[i][j]+b[i][j]; //add
		
		return new Matrix(ret);
	}
	
	/**
	 * Subtracts two input matrices as A - B = C, where A is the initial matrix and B is the difference
	 * 
	 * @param matrixA
	 * @param matrixB
	 * @return the sum {@code Matrix}
	 */
	
	public static Matrix sub(Matrix matrixA,Matrix matrixB)
	{
		double[][] a=matrixA.getArr(),b=matrixB.getArr();
		int mA=a.length,nA=a[0].length,mB=b.length,nB=b[0].length;
		if(mA!=mB||nA!=nB) throw new IllegalArgumentException("Matrices are not compatible!"); //check to see if dimensions are the same
		
		double[][] ret=new double[mA][nA];
		for(int i=0;i<mA;i++) //rows
			for(int j=0;j<nA;j++) //cols
				ret[i][j]=a[i][j]-b[i][j]; //subtract
		
		return new Matrix(ret);
	}
	
	/**
	 * Scales matrix by factor of scalar value
	 * 
	 * @param matrix
	 * @param scalar
	 * @return the sum {@code Matrix}
	 */
	
	public static Matrix scale(Matrix matrix,double scalar)
	{
		double[][] mat=matrix.getArr();
		int m=mat.length,n=mat[0].length;
		
		double[][] ret=new double[m][n];
		for(int i=0;i<m;i++) //rows
			for(int j=0;j<n;j++) //cols
				ret[i][j]=mat[i][j]*scalar; //multiply individual terms
		
		return new Matrix(ret);
	}
	
	/**
	 * Multiplies two input matrices as AB = C, where B is the initial matrix and A is its linear transformation
	 * 
	 * @param a
	 * @param b
	 * @return the product {@code Matrix}
	 */
	
	public static Matrix mult(Matrix matrixA,Matrix matrixB)
	{
		double[][] mat_A=matrixA.getArr(),mat_BT=transpose(matrixB).getArr(),product=new double[mat_A.length][mat_BT.length];
		int vectNum=mat_A.length;
		if(mat_A[0].length!=mat_BT[0].length) throw new IllegalArgumentException("Matrices are not compatible!");
		double[] rowA,colB;
		
		for(int row=0;row<vectNum;row++) //will move down rows of A matrix
		{
			rowA=mat_A[row]; //vector 1
			for(int col=0;col<mat_BT.length;col++) //moves down rows of B^T matrix
			{
				colB=mat_BT[col]; //vector 2
				product[row][col]=VectorOperations.dotProd(rowA,colB);
			}
		}
		return new Matrix(product);
	}
	
	/**
	 * Transposes any matrix A sent to it and returns the corresponding transposed matrix A^T
	 * 
	 * @return A^T {@code Matrix}
	 */
	
	public static Matrix transpose(Matrix matrix)
	{
		double[][] m=matrix.getArr(),transposed=new double[m[0].length][m.length];
		for(int i=0;i<m.length;i++)
			for(int j=0;j<m[0].length;j++)
				transposed[j][i]=m[i][j];
		return new Matrix(transposed);
	}
	
	/**
	 * Takes any augmented matrix and returns the {@code Matrix} object at the designated index
	 * 
	 * @param augmented
	 * @param m
	 * @param n1
	 * @param n2
	 * @param matrixIndex
	 * @return the matrix at the designated index of [1|2]
	 */
	
	public static Matrix deaugment(Matrix augmented,int matrixIndex,int m,int nA,int nB)
	{
		double[][] aug=augmented.getArr(),arrA=new double[m][nA],arrB=new double[m][nB];
		for(int i=0;i<m;i++) //loops down rows
		{
			for(int j=0;j<nA;j++) //loop across first cols
				arrA[i][j]=aug[i][j]; //there will be left over cols not copied
			for(int j=nA;j<nB+nA;j++)
				arrB[i][j-nA]=aug[i][j]; //adds n of the first onto the index of j to shift
		}
		Matrix a,b;
		a=new Matrix(arrA);
		b=new Matrix(arrB);
		
		switch(matrixIndex)
		{
			case 1: return a;
			case 2: return b;
			default: throw new IllegalArgumentException("Augmented matrix holds only 2 matrices between 1 and 2");
		}
	}
	
	//add method for finding determinants of square matrices
}