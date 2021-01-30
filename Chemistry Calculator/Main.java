import uk.ac.manchester.libchebi.*;
import java.io.*;
import java.text.*;
import java.util.*;
import chemistry.*;
import use.*;

public class Main
{
	static
	{
		PeriodicTable.initialize();
		VanDerWaalsConstantsTable.initialize();
	}
	
	enum Category
	{
		INFORMATION,BALANCE_EQUATIONS,GAS_LAWS,DEGREE_OF_UNSATURATION; //headings
		
		public String print()
		{
			String ret="";
			String[] arr=this.name().toLowerCase().split("_"); //convert to lowercase and split on '_'
			for(String str:arr)
				ret+=Character.toUpperCase(str.charAt(0))+str.substring(1)+" "; //add capitalized string to return
			return ret;
		}
		
		public static Category getEnum(int num)
		{
			return Category.values()[num];
		}
	}
	
	public static void main(String[] args) throws IOException, ParseException, ChebiException, org.apache.lucene.queryparser.classic.ParseException
	{
		titlize("Chemistry Calculator"); //prints title
		while(true)
		{
			System.out.println("\n-----------------------");
			for(Category e:Category.values()) //prints entire array of enums
				System.out.print(e.ordinal()+1+") "+e.print()+"\n");
			
			System.out.println("-----------------------");
			Category category=null;
			boolean correctEntry=false;
			
			while(!correctEntry)
				try
				{
					String entry=Use.nextLine("\n>>> Enter heading (\"done\" to exit program): ");
					try
					{
						if(entry.equalsIgnoreCase("done")) System.exit(0);
						category=Category.valueOf(entry.replaceAll(" ","_").toUpperCase());
						correctEntry=true;
					}
					catch(IllegalArgumentException e)
					{
						category=Category.getEnum(Integer.parseInt(entry)-1);
						correctEntry=true;
					}
				}
				catch(RuntimeException e)
				{
					System.out.println("\nInvalid entry, please try again:");
				}
			
			switch(category) //UPDATE WITH ENUMS
			{
				case INFORMATION:
					getInformation();
					break;
				case BALANCE_EQUATIONS:
					getBalancedEquation();
					break;
				case GAS_LAWS:
					getGasLaws();
					break;
				case DEGREE_OF_UNSATURATION:
					getDegreeOfUnsaturation();
					break;
			}
		}
	}

	public static void getInformation() throws IOException, ParseException, ChebiException, org.apache.lucene.queryparser.classic.ParseException
	{
		boolean correctEntry=false;
		String result;
		while(!correctEntry) //loops while the entry is incorrect
		{
			String input=Use.next("\n>>> Element or molecule? ").toLowerCase();
			switch(input)
			{
				case "element":
					System.out.println();
					result=new Atom(Atom.getAtomEntryByString())+""; //calls toString
					result=Use.formatResult(result);
					System.out.println("\n"+result);
					correctEntry=true;
					break;
				case "molecule":
					System.out.println();
					result=new Molecule(Molecule.getMoleculeEntry())+""; //calls toString
					result=Use.formatResult(result);
					System.out.println("\n"+result);
					correctEntry=true;
					break;
				default:
					System.out.print("\n>>> Invalid entry, please try again: "); //does not change boolean, infinite loop until valid category
			}
		}
	}

	public static void getBalancedEquation() throws IOException, ParseException, ChebiException, org.apache.lucene.queryparser.classic.ParseException
	{
		ArrayList<Molecule> reactants=new ArrayList<Molecule>(),products=new ArrayList<Molecule>(); //list of molecules for both
		
		System.out.println("\nREACTANTS:");
		while(true) //input of reactant molecules
		{
			System.out.println();
			reactants.add(new Molecule(Molecule.getMoleculeEntry()));
			if(Use.nextLine(">>> More reactants (y/n)? ").toLowerCase().equals("n")) break; //if no more molecules, stop loop
		}
		
		System.out.println("\nPRODUCTS:");
		while(true) //input of product molecules
		{
			System.out.println();
			products.add(new Molecule(Molecule.getMoleculeEntry()));
			if(Use.nextLine(">>> More products (y/n)? ").toLowerCase().equals("n")) break; //same here
		}
		
		StoichiometryBalancer eb=new StoichiometryBalancer(reactants,products);
		String result=Use.formatResult("The balanced equation is\n\n"+eb.getBalancedEquation());
		System.out.println("\n"+result);
	}

	public static void getGasLaws() throws FileNotFoundException
	{
		boolean correctEntry=false;
		while(!correctEntry) // loops while the entry is incorrect
		{
			String unit="",input=Use.next("\n>>> Ideal or real? ").toLowerCase();
			switch(input)
			{
				case "ideal": //multiple try catches and overarching one for incorrect entry
					while(true)
					{
						IdealGasEquation ideal=new IdealGasEquation();
						System.out.println("\nEnter values, one of which must be a variable (x):");
						try
						{
							ideal.setPressure(Use.nextDouble("\n>>> Enter pressure (bar): "));
						}
						catch(InputMismatchException e)
						{
							unit="bar";
						}
						try
						{
							ideal.setVolume(Use.nextDouble(">>> Enter volume (L): "));
						}
						catch(InputMismatchException e)
						{
							unit="L";
						}
						try
						{
							ideal.setMoles(Use.nextDouble(">>> Enter moles (mol): "));
						}
						catch(InputMismatchException e)
						{
							unit="mol";
						}
						try
						{
							ideal.setTemperature(Use.nextDouble(">>> Enter temperature (K): "));
						}
						catch(InputMismatchException e)
						{
							unit="K";
						}
						try
						{
							String result=Use.formatResult("The solution is "+ideal.solve()+" "+unit);
							System.out.println("\n"+result);
							break;
						}
						catch(IllegalArgumentException e)
						{
							System.out.println("\nEnter one variable only!");
						}
					}
					correctEntry=true;
					break;
				case "real":
					while(true)
					{
						String in="";
						ArrayList<RealGasMolecule> gases=new ArrayList<>();
						while(true)
						{
							System.out.println();
							gases.add(new RealGasMolecule(RealGasMolecule.getRealGasEntry()));
							in=Use.nextLine("\n>>> More entries (y/n)?: ");
							if(in.equalsIgnoreCase("n")) break;
						}
						if(gases.size()>1)
							for(RealGasMolecule m:gases)
								m.setMoles(Use.nextDouble("\n>>> Mole ratio of "+m.getName().toLowerCase()+" present in sample: "));
						RealGasEquation real=new RealGasEquation(gases);
						System.out.println("\nEnter values, one of which must be a variable (x):");
						try
						{
							real.setPressure(Use.nextDouble("\n>>> Enter pressure (bar): "));
						}
						catch(InputMismatchException e)
						{
							unit="bar";
						}
						try
						{
							real.setVolume(Use.nextDouble(">>> Enter volume (L): "));
						}
						catch(InputMismatchException e)
						{
							unit="L";
						}
						try
						{
							real.setMoles(Use.nextDouble(">>> Enter moles (mol): "));
						}
						catch(InputMismatchException e)
						{
							unit="mol";
						}
						try
						{
							real.setTemperature(Use.nextDouble(">>> Enter temperature (K): "));
						}
						catch(InputMismatchException e)
						{
							unit="K";
						}
						try
						{
							String result=Use.formatResult("The solution is "+real.solve()+" "+unit);
							System.out.println("\n"+result);
							break;
						}
						catch(IllegalArgumentException e)
						{
							System.out.println(e);
						}
					}
					correctEntry=true;
					break;
				default:
					System.out.print("\n>>> Invalid entry, please try again: "); // does not change boolean, infinite loop until valid category
			}
		}
	}

	public static void getDegreeOfUnsaturation() throws IOException, ParseException, ChebiException, org.apache.lucene.queryparser.classic.ParseException
	{
		System.out.println();
		DegreeOfUnsaturation ihd=new DegreeOfUnsaturation(new Molecule(Molecule.getMoleculeEntry()));
		try
		{
			String result=Use.formatResult("The solution is "+(int)ihd.solve());
			System.out.println("\n"+result);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("\n"+e.getLocalizedMessage());
		}
	}
	
	public static void titlize(String str)
	{
		str+=" =====";
		str="===== "+str;
		for(int i=0;i<str.length();i++)
			System.out.print("=");
		System.out.println("\n"+str);
		for(int i=0;i<str.length();i++)
			System.out.print("=");
		System.out.println();
	}
}