package chemistry;

import java.util.*;
import linear_algebra.*;

/*
 * Code: class StoichiometryBalancer
 * Author: Michael Armendariz
 * Date: 12/27/20
 * Code Version: 1.1
 * Revisions:
 * 
 * (12/28/20) Finished balance(); added commonMultiple(), isAllInts(), doubleIsInt(), and getBalancedEquation()
 * (12/29/20) Added rearrangeMolecules() and changed portion of balance() to patch bug
 * 
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code StoichiometryBalancer} class determines the coefficients required to balance a chemical equation, 
 * given the input of reactants and products as {@code Molecule} objects
 * @author Michael Armendariz
 */

public class StoichiometryBalancer
{
	private ArrayList<Molecule> reactants,products;
	private Matrix equations;
	private ArrayList<Integer> elementsList;
	private int[] coefficients;
	
	/**
	 * Initializes a newly created {@code StoichiometryBalancer}
	 * object and accepts the reactants and products in the chemical reaction as {@code ArrayList}s of {@code Molecule}s
	 * @param reactants
	 * @param products
	 */
	
	public StoichiometryBalancer(ArrayList<Molecule> reactants,ArrayList<Molecule> products)
	{
		this.reactants=reactants;
		this.products=products;
	}
	
	private void populateElements()
	{
		//Px_1 = Rx_2 --> [P|-R]x = 0 --> Ax = 0
		//find null space of matrix
		
		HashSet<Integer> allReactantsElements=deconstructEquations(); //method below: takes apart the molecules from the equation
		elementsList=new ArrayList<>(allReactantsElements);
		
		double[][] r_arr=new double[elementsList.size()][reactants.size()]; //matrices are molecules by column, elements by row
		double[][] p_arr=new double[elementsList.size()][products.size()];
		
		fill2DArray(reactants,r_arr);
		fill2DArray(products,p_arr);
		
		setEquations(r_arr,p_arr); //formally sets stoichiometric matrix as an object
	}
	
	private void fill2DArray(ArrayList<Molecule> molecules,double[][] arr) //accesses arrays directly without making copies
	{
		Molecule currMolecule=null;
		ArrayList<Atom> atoms=null;
		
		for(int i=0;i<molecules.size();i++)
		{
			currMolecule=molecules.get(i); //increments by molecules
			atoms=currMolecule.getAtoms();
			for(int j=0;j<elementsList.size();j++) //increment by total number of elements in equations
				for(Atom a:atoms)
					if(a.getAtomicNumber()==elementsList.get(j))
						arr[j][i]=a.getMoles();
		}
	}
	
	private HashSet<Integer> deconstructEquations()
	{
		ArrayList<ArrayList<Atom>> reactantsAtoms=new ArrayList<>(),productsAtoms=new ArrayList<>();
		HashSet<Integer> allReactantsElements=new HashSet<>(),allProductsElements=new HashSet<>(); //set of all unique atomic numbers
		int atomicNumber=0;
		ArrayList<Atom> atoms=null;
		
		for(Molecule m:reactants) //adds all atoms in individual arrays, then finds the unique elements for both sides of the equation
		{
			atoms=m.getAtoms();
			reactantsAtoms.add(atoms);
			for(Atom a:atoms)
			{
				atomicNumber=a.getAtomicNumber();
				allReactantsElements.add(atomicNumber);
			}
		}
		
		for(Molecule m:products)
		{
			atoms=m.getAtoms();
			productsAtoms.add(atoms);
			for(Atom a:atoms)
			{
				atomicNumber=a.getAtomicNumber();
				allProductsElements.add(atomicNumber);
			}
		}
		
		if(!allReactantsElements.equals(allProductsElements))
			throw new IllegalArgumentException("Cannot violate Law of Conservation of Matter!"); //guarantees that it can be balanced
		
		return allReactantsElements;
	}
	
	private void setEquations(double[][] r_arr,double[][] p_arr)
	{
		Matrix r_matrix=new Matrix(r_arr);
		Matrix p_matrix=new Matrix(p_arr);
		p_matrix=MatrixOperations.scale(p_matrix,-1.0);
		
		Matrix augmented=new Matrix(r_matrix,p_matrix);
		
		equations=augmented;
	}
	
	private void balance()
	{
		populateElements(); //creates new Matrix object
		
		Matrix rowEchelon=null,reducedRowEchelon=null;
		boolean ref=false;
		
		while(!ref) //to fix errors from matrix not being in row-echelon form
		{
			try
			{
				ref=true;
				rowEchelon=GaussianElimination.rowEchelon(equations); //convert equation to row echelon form
				reducedRowEchelon=GaussianElimination.reducedRowEchelon(rowEchelon); //reduced row echelon form (1's on diagonals)
			}
			catch(IllegalArgumentException e)
			{
				ref=false;
				rearrangeMolecules();
			}
		}
		
		Matrix col=MatrixOperations.deaugment(reducedRowEchelon,2,reducedRowEchelon.getM(),reducedRowEchelon.getN()-1,1); //take out solution vector
		col=MatrixOperations.scale(col,-1); //convert answer to positive doubles
		col=MatrixOperations.transpose(col); //convert to row vector
		Matrix improperSolutions=new Matrix(col,new Matrix("1")); //tack on 1 for free variable
		improperSolutions=MatrixOperations.transpose(improperSolutions); //transpose back
		
		double[][] vec=improperSolutions.getArr();
		double[] nums=new double[vec.length];
		for(int i=0;i<vec.length;i++)
			nums[i]=vec[i][0]; //convert to array
		
		int scalar=commonMultiple(nums); //find the scalar multiple of the decimal coefficients to convert them to the lowest integers
		for(int i=0;i<nums.length;i++)
			nums[i]=nums[i]*scalar;
		
		coefficients=new int[nums.length]; //instantiate coefficients and deep-copy nums
		for(int i=0;i<nums.length;i++)
			coefficients[i]=(int)nums[i];
	}
	
	private void rearrangeMolecules()
	{
		Molecule first=products.get(0);
		for(int i=1;i<products.size();i++)
			products.set(i-1,products.get(i));
		products.set(products.size()-1,first);
		balance();
	}
	
	private int commonMultiple(double[] nums)
	{
		for(int i=1;i<=1000;i++)
			if(!isAllInts(nums,i))
				continue;
			else
				return i;
		return -1;
	}
	
	private boolean isAllInts(double[] nums,int scalar)
	{
		for(int i=0;i<nums.length;i++)
			if(!doubleisInt(nums[i]*scalar))
				return false;
		return true;
	}
	
	private boolean doubleisInt(double d)
	{
	    double tolerance=1E-5; //range of tolerance for integer
	    return Math.abs(d-Math.floor(d))<tolerance;
	}
	
	public String getBalancedEquation()
	{
		balance();
		String equation="",co="";
		
		for(int i=0;i<reactants.size();i++)
		{
			if(coefficients[i]==1)
				co="";
			else
				co=coefficients[i]+"";
			equation+=co+reactants.get(i).getSymbol()+" + ";
		}
		
		equation=equation.substring(0,equation.length()-3);
		equation+=" --> ";
		int inc=reactants.size();
		
		for(int i=0;i<products.size();i++)
		{
			if(coefficients[i+inc]==1)
				co="";
			else
				co=coefficients[i+inc]+"";
			equation+=co+products.get(i).getSymbol()+" + ";
		}

		equation=equation.substring(0,equation.length()-3);
		
		return equation;
	}
}
