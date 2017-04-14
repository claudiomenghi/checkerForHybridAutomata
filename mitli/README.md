# MITLI Module

The MITLI module contains 
* Java classes that describe a MITLI formula
* Parser for MITLI formulae

#### MITLI grammar

* `&& f1 f2`: conjunction. `f1` and `f2`;
* `-> f1 f2`: implication. `f1` implies `f2`;
* `G_ei`: globally excluded, included, corresponds to the operator `G(a,b] f`, where the a and b are the lower and the upper bound of the formula and `f` is the sub-furmula;
* `G_ii`: globally included, excluded, corresponds to the operator `G[a,b] f`, where the `a` and `b` are the lower and the upper bound of the formula and `f` is the sub-furmula;
* `G_i+ a f`: globally included infinite, corresponds to the operator `G[a,+inf] f`, where `a` is the lower bound and `f` is the sub-furmula;



