# Checker for Hybrid automata

The QLTLoc framework is made by several modules:

* [cltloc](/cltloc) module that contains the description of CLTLoc formulae
* [mitli](/mitli) module that contains the description of  MITLI formulae
* [solvers](/solvers) module allows the verification of MITLI formulae


# Input

# Developers corner

## High level component description
### MITLI module
Contains the classes that are used to represent an MITLI formula and to convert the MITLI formula in CLTLoc. 

* The class *MITLI2CLTLoc.java* (in the package *convertes*) is used to transform an MITLI formula in CLTLoc.
* The class *MITLI2CLTLocVisitor.java* (in the package *visitors*) contains the logic (implemented in a visitor) that it is used to transform every MITLI formula in CLTLoc. 
If a new convertion is required it is sufficient to implement a new visitor.

### CLTLoc module
Contains the classes that are used to represent a CLTLoc formula and to convert the CLTLoc formula in zot.
* The classes *CLTLoc2ZotBvzot.java* (*CLTLoc2ZotDReal.java*) which are contained in the package *convertes* the are used to convert the CLTLoc formula into a zot formula (dreal) formula, respectively.
* The class *CLTLoc2ZotVisitor.java* (in the package *visitors*) is used to transform every CLTLoc formula in zot.


## Workflow
### Running the tests
To run the tests run
`gradle test`

