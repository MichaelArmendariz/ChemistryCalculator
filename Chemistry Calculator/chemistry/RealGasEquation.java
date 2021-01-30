package chemistry;

import java.util.*;
import use.*;

/*
 * Code: class RealGasEquation
 * Author: Michael Armendariz
 * Date: 4/7/20
 * Code Version: 1.2
 * Revisions:
 *
 * (12/24/20) Integrated NewtonsMethod inheritance and fixed massive essay of an equation in solve();
 * 			added function() method and switched roles of solve() and function()
 * 
 * (12/28/20) Patched bug in solve() where multiple solutions to the gas equation were being found, causing an error
 *
 * Availability: public, Eclipse IDE
 */

/**
 * The {@code RealGasEquation} class accepts the necessary parameters to return a solution
 * for some missing value of a gas sample based on the real gas equation (Van Der Waals equation)
 * 
 * @author Michael Armendariz
 */

public class RealGasEquation extends IdealGasEquation
{
	private double a,b;

	/**
	 * Initializes a newly created {@code RealGasEquation} object
	 *
	 * @param a constant <i>A</i> of the species
	 * @param b constant <i>B</i> of the species
	 */

	public RealGasEquation(RealGasMolecule m)
	{
		this.a=m.getAConst();
		this.b=m.getBConst();
	}

	/*
	 * Because I don't want to make the assumption that the law of partial pressures works for all cases (it doesn't, since it fails 
	 * for gas mixtures that are significantly nonideal), I wanted to be able to make gas mixtures with unique Van Der Waals constants.
	 * This requires a double sum of all the elements in the set of mole ratios with the elements of the set of constants in the form:
	 * 
	 * ΣΣ[x_i*x_j*sqrt(w_i*w_j)]; i = [i,n] and j = [1,i = n]
	 */
	
	/**
	 * Initializes a newly created {@code RealGasEquation} object, as well as accepting a {@code Molecule[]} for mole ratios and constants
	 * of a gas mixture
	 *
	 * @param array of {@code RealGasMolecule} objects
	 */

	public RealGasEquation(RealGasMolecule[] arr) //possibly implement a HashSet instead
	{	
		double molTotal=0,curr=0,adjustedA=0,adjustedB=0;
		for(int i=0;i<arr.length;i++) //each molecule
			molTotal+=arr[i].getMoles(); //sums all moles into total
		for(int i=0;i<arr.length;i++) //repeats per molecule
		{
			for(int j=0;j<arr.length;j++) //compares each molecule to each other one (n^2)
				curr+=(arr[i].getMoles()/molTotal)*(arr[j].getMoles()/molTotal)
						*Math.sqrt(arr[i].getAConst()*arr[j].getAConst()); //current partial constant A
			adjustedA+=curr; //adjusted constant is sum of partials
			curr=0;
			for(int j=0;j<arr.length;j++) //compares each molecule to each other one (n^2)
				curr+=(arr[i].getMoles()/molTotal)*(arr[j].getMoles()/molTotal)
						*Math.sqrt(arr[i].getBConst()*arr[j].getBConst()); //current partial constant B
			adjustedB+=curr; //adjusted constant is sum of partials
			curr=0;
		}
		
		a=adjustedA;
		b=adjustedB;
	}
	
	/**
	 * Initializes a newly created {@code RealGasEquation} object, as well as accepting a {@code ArrayList<Molecule>} for mole ratios and
	 * constants of a gas mixture
	 *
	 * @param array {@code ArrayList} of {@code RealGasMolecule} objects
	 */
	
	public RealGasEquation(ArrayList<RealGasMolecule> arr)
	{
		double molTotal=0,curr=0,adjustedA=0,adjustedB=0;
		for(int i=0;i<arr.size();i++)
			molTotal+=arr.get(i).getMoles();
		for(int i=0;i<arr.size();i++)
		{
			for(int j=0;j<arr.size();j++)
				curr+=(arr.get(i).getMoles()/molTotal)*(arr.get(i).getMoles()/molTotal)
						*Math.sqrt(arr.get(i).getAConst()*arr.get(i).getAConst());
			adjustedA+=curr;
			curr=0;
			for(int j=0;j<arr.size();j++)
				curr+=(arr.get(i).getMoles()/molTotal)*(arr.get(i).getMoles()/molTotal)
						*Math.sqrt(arr.get(i).getBConst()*arr.get(i).getBConst());
			adjustedB+=curr;
			curr=0;
		}
		a=adjustedA;
		b=adjustedB;
	}

	public double function(double x) //overrides super.function(), making the value from the Van Der Waals equation difficult to estimate
	{
		double variable=0;
		if(p==null)
			variable=(x+a*Math.pow(n/v,2))*(v-n*b)-n*R*t;
		else if(v==null)
			variable=(p+a*Math.pow(n/x,2))*(x-n*b)-n*R*t;
		else if(n==null)
			variable=(p+a*Math.pow(x/v,2))*(v-x*b)-x*R*t;
		else if(t==null)
			variable=(p+a*Math.pow(n/v,2))*(v-n*b)-n*R*x;
		
		return variable;
	}

	public double solve()
	{
		if(check!=3)
			throw new IllegalArgumentException("Cannot find solution with "+check+" known parameters"); // errors if user did not enter all necessary values to generate a solution
		
		//because of the bug to do with function() inheritance, another instance must be made within this one to get an estimate
		IdealGasEquation ideal=new IdealGasEquation();
		if(p!=null)
			ideal.setPressure(p);
		if(v!=null)
			ideal.setVolume(v);
		if(n!=null)
			ideal.setMoles(n);
		if(t!=null)
			ideal.setTemperature(t);
		double estimate=ideal.solve();
		
		ArrayList<Double> zeros=this.getZeros(estimate); 
		double solution=Double.parseDouble((zeros+"").replace("[","").replace("]",""));
		return Use.round(solution,6);
	}
}