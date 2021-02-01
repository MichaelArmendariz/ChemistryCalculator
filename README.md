# chemistry-calculator
A place for the files doing the work behind my chemistry/cheminformatics calculator project

##  INSTRUCTIONS:

The program will take a moment to accumulate data from a few different webpages. After this pause, a menu will appear allowing the user to input the index of one of the listed headings:

![Menu Screen](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/menu.png)

This description will focus on the use of each one at a time.

### _Information_

Enter a 1. The choice of viewing information on either elements or molecules is given; for the purpose of this demo, I will start with the elements.
<p>
  Enter "element". When prompted with a search query, type in any element that might come to mind. Note that while the entry does not need to be spelled correctly, close-to-correct spelling will ensure that the ranking of the true result is found in at least the matches printed to the screen. The input can be either the name or the symbol of the element. If the desired element is located in the list, enter the index listed along the side. If it is not, search again by entering a -1 or other nonsensical index:

![Element Search 1](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/element%20search%20(1).png)

![Element Search 2](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/element%20search%20(2).png)

![Element Search 3](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/element%20search%20(3).png)

![Element Search 4](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/element%20search%20(4).png)

<p>
  Once back to the menu, enter a 1 for the Information heading again. Now input "molecule". The program may take a moment to connect to the database API, eventually presenting a similar prompt as before. Of course, anything can be searched for, so this demo is limited in its scope. As long as the connection between the API and the Chemistry Calculator code has not been smoothed out, there remain some bugs that fail to correctly parse some of the molecular data from the more unusual biochemical entries, something to keep in mind when trying to search for any particuarly obscure substances.
<p>
  I will demonstrate the comparable usage under this subheading with a few examples:

![Molecule Search 1](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/molecule%20search%20(1).png)

![Molecule Search 2](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/molecule%20search%20(2).png)

![Molecule Search 3](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/molecule%20search%20(3).png)

![Molecule Search 4](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/molecule%20search%20(4).png)

Again, not all compounds are compatible with the interface and may cause the program to crash. This will be amended some time in the near future in an upcoming patch.

### _Balance Equations_

  Type a 2. The user interface works similarly here to the information section. However, the formatting is slightly different in that a running list is kept while the molecules are input, and the user has to tell the program when to stop the reactants list and start the products list. Be careful as well: if the products and reactants are not correct (there is a missing element on one side or the other) the program will exit to prevent the linear equations generated from crashing it in the following step of the algorithm. If the Law of Conservation of Matter is met, any failures are on the part of the developer, not the user (see **Issues**).
<p>
  Here is an example of its usage (note that the screenshots have been clipped and placed side-by-side, not top-to-bottom):

![Balance Equations 1](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/balance%20equations%20(1).png)

![Balance Equations 2](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/balance%20equations%20(2).png)

![Balance Equations 3](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/balance%20equations%20(3).png)

### _Gas Laws_
<p>
  Type a 3. The program will introduce a prompt to choose which kind of gas law is required: real or ideal. Both choices will be covered here:
<p>
  Enter "ideal". A prompt will announce that one of the four values entered must be a variable. This means that, from pressure, volume, moles, or temperature, all but one of the values need to be numbers. The variable to be solved for, one of the four fields, can be anything that isn't a number. Preferably, this would be "x", although any nonnumerical symbol is acceptable. After entering each of the values and one variable, the result should be the solution to the ideal gas equation PV = nRT for the values entered, in units of atm, L, mol, or K.

![Ideal Gas 1](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/gas%20laws%20(1).png)

![Ideal Gas 2](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/gas%20laws%20(2).png)

![Ideal Gas 3](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/gas%20laws%20(3).png)

![Ideal Gas 4](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/gas%20laws%20(4).png)

<p>
  Enter "real". The following output will be in the same format as the molecule and element searches; it functions the same as well. Enter a search for a real gas species. The set of substances with experimentally-derived Van Der Waals constants is smaller than the set of database molecules, so no results for "Tetracarbonylbis(hexamethylphosphorous Triamide)molybdenum gas" are present, but enough gases present in a typical lab environment are.
<p>
  After the desired molecule has been located and selected, the code will ask if another gas molecule needs to be added to the list. If this is true, type "yes", then repeat the same steps as before for searching and entering. It should be noted that the molecules entered for a gas mixture will require the subsequent input of their mole ratios in the mixture.
<p>
  Once enough molecules have been entered, type "n" when prompted. The program will, as aforementioned, ask for the mole ratio of each gas in the set. After this step, the instructions are the same as the ideal gas calculator.

![Real Gas](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/gas%20laws%20(5).png)

**NOTE:** The gas calculators are not intended to predict gaseous reactions (not yet, anyway), meaning that the program is not responsible for checking if two or more gases present might change the pressure/moles/temperature of substance in a vessel. Use the gas mixture feature only if no reaction will occur to ensure accurate results.
  
### _Degree of Unsaturation_
<p>
  Enter a 4. A prompt will print asking for the input of a molecule. As per usual, enter the name of whatever molecule may come to mind and locate the desired index. At this point, a message will print with the degree of unsaturation of the molecule. That's it.

![Degree of Unsaturation 1](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/degree%20of%20unsaturation%20(1).png)

![Degree of Unsaturation 2](https://github.com/MichaelArmendariz/ChemistryCalculator/blob/main/images/degree%20of%20unsaturation%20(2).png)

## OUTSIDE FILES:
<p>
  While creating these programs, a big goal of mine was to orchestrate the code so I could just find an outside database API and plug it in directly with minimal changes. Starting by reading text files, I slowly started to build code around data management and parsing. Recently, I discovered the ChEBI biochemical database while doing research and was very excited to find a Java API to connect it to my code. I'm deeply thankful to the development team behind libChEBI for making this code and much, much more to follow possible.
<p>
  I'm also using an HTML parser open-sourced package called JSoup, which allows me to open URL connections and parse HTML text. It is used in the parser classes (I call them "Tables") in order to acquire data from sources on the web rather than lugging around lengthy text files. For this reason, a huge thanks goes out to the development team behind JSoup as well.
<p>
  An extensive and comprehensive database-search program called Apache Lucene also serves as the backbone to all of this code. It was a somewhat tricky procedure, but I managed to track down the correct version of Apache Lucene being used in the libChEBI package: version 7.7.3. If you want to run any of my code yourself, make sure to find the libChEBI, JSoup, and Apache Lucene 7.7.3 programs as well. I have also included my own Use class, which I designed with a friend of mine a long time ago, since I use it extensively throughout all of my programs.

## BACKGROUND:
<p>
  This project started at the beginning of my junior year of high school under a slightly different name and theme: BIOLOGY CALCULATOR. I was beginning to learn Java and I let my enthusiasm seep into other subjects as well. At some point, we were assigned the task of memorizing a formula sheet in AP Biology and, well, I saw an opportunity.
<p>
  The prototype code focused more on printing ASCII art and mastering nested for-loops than providing helpful computation, but as my knowledge grew in both subjects, I recognized the usefulness of Java as a tool to represent biological concepts. At some point, I tried to write code that translated mRNA sequences into their respective lines of amino acids; this was likely the point of my epitome. I shifted my focus towards searching for better mathematical and computational representations of the biochemistry I was so interested in, causing me to quickly outgrow the AP Computer Science class I started in.
<p>
  Halfway through the school year, I found myself designing code that would solve the pesky Van Der Waals equation for me, reading from a text file. I soon set out to create an application that was both useful, efficient, and comprehensive, and although there are many "chemistry calculators" that already exist on the internet, this was less of a lesson in pragmatism and more of a lesson in advancing my Java skills and understanding of applied mathematics, cheminformatics, and obviously computation. While this project is not perfect by any means, I'm intent on improving it as time goes by. I've come to understand that this project will likely never be "finished", just as scientific progress is never truly finished, but that's not a problem for me.
