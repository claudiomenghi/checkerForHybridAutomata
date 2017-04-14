# Checker for Hybrid automata
## Running the checker 
Assuming ZOT installed and working, to run the checker 

- open the *solvers* folder
- run gradle run -PappArgs="['exampleProperty.mitli','20']" where *exampleProperty.mitli* is the property to be checked and *20* is the bound to be used.

## Analyzing the output
The solver produces the following files
- *exampleProperty.cltloc* the CLTLoc encoding of the formula;
- *exampleProperty.vocabulary* the vocabulary that assigns each subformula to the corresponding propositions;
- *exampleProperty.lisp* contains the encodin that is passed to the zot solver





## Input File
The input file should contain an MITLI formula that is compliant with the following grammar:

#### MITLI grammar

An brief overview of the grammar
* `&& (f1) (f2)`: conjunction. `f1` and `f2`;
* `-> (f1) (f2)`: implication. `f1` implies `f2`;
* `G_ei a b (f)`: globally excluded, included, corresponds to the operator `G(a,b] f`, where the a and b are the lower and the upper bound of the formula and `f` is the sub-furmula;
* `G_ii a b (f)`: globally included, excluded, corresponds to the operator `G[a,b] f`, where the `a` and `b` are the lower and the upper bound of the formula and `f` is the sub-furmula;
* `G_i+ a (f)`: globally included infinite, corresponds to the operator `G[a,+inf] f`, where `a` is the lower bound and `f` is the sub-furmula;





## Developers corner

### Implementation details
The QLTLoc framework is made by several modules:

* [cltloc](/cltloc) module that contains the description of CLTLoc formulae
* [mitli](/mitli) module that contains the description of  MITLI formulae
* [solvers](/solvers) module allows the verification of MITLI formulae


#### MITLI module
Contains the classes that are used to represent an MITLI formula and to convert the MITLI formula in CLTLoc. 

* The class *MITLI2CLTLoc.java* (in the package *convertes*) is used to transform an MITLI formula in CLTLoc.
* The class *MITLI2CLTLocVisitor.java* (in the package *visitors*) contains the logic (implemented in a visitor) that it is used to transform every MITLI formula in CLTLoc. 
If a new convertion is required it is sufficient to implement a new visitor.

#### CLTLoc module
Contains the classes that are used to represent a CLTLoc formula and to convert the CLTLoc formula in zot.
* The classes *CLTLoc2ZotBvzot.java* (*CLTLoc2ZotDReal.java*) which are contained in the package *convertes* the are used to convert the CLTLoc formula into a zot formula (dreal) formula, respectively.
* The class *CLTLoc2ZotVisitor.java* (in the package *visitors*) is used to transform every CLTLoc formula in zot.


### Developer workflow
### Running the tests
To run the tests run
`gradle test`

