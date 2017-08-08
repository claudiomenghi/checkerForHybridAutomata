package formulae.cltloc.visitor;

import com.google.common.base.Strings;

import formulae.cltloc.atoms.CLTLocAP;
import formulae.cltloc.atoms.CLTLocClock;
import formulae.cltloc.atoms.Constant;
import formulae.cltloc.atoms.Signal;
import formulae.cltloc.atoms.Variable;
import formulae.cltloc.operators.binary.CLTLocConjunction;
import formulae.cltloc.operators.binary.CLTLocDisjunction;
import formulae.cltloc.operators.binary.CLTLocIff;
import formulae.cltloc.operators.binary.CLTLocImplies;
import formulae.cltloc.operators.binary.CLTLocRelease;
import formulae.cltloc.operators.binary.CLTLocSince;
import formulae.cltloc.operators.binary.CLTLocUntil;
import formulae.cltloc.operators.unary.CLTLocEventually;
import formulae.cltloc.operators.unary.CLTLocGlobally;
import formulae.cltloc.operators.unary.CLTLocNegation;
import formulae.cltloc.operators.unary.CLTLocNext;
import formulae.cltloc.operators.unary.CLTLocYesterday;
import formulae.cltloc.relations.CLTLocRelation;

/**
 * Translates a CLTLoc formula into a Zot formula. Each method specifies how a
 * specific type of formula must be converted.
 * 
 * @author Claudio Menghi
 */
public class CLTLoc2ZotVisitor implements CLTLocVisitor<String> {
	
	private int level;
	private boolean unaryParent;
	
	public CLTLoc2ZotVisitor(){
		level = 1;
		unaryParent = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocConjunction formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result = "\n" + Strings.repeat("\t", level-1) + "(&&" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocNegation formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = true;
		String result = "\n" + Strings.repeat("\t", level-1) + "(!!" + formula.getChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocUntil formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result =  "\n" + Strings.repeat("\t", level) + "(until" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocImplies formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(->" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocIff formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(<->" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocNext formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = true;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(next" + formula.getChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocGlobally formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = true;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(alwf" + formula.getChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocEventually eventually) {
		level++;
		boolean old = unaryParent;
 		unaryParent = true;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(somf" + eventually.getChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocSince formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(since" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocYesterday formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = true;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(yesterday" + formula.getChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocRelease formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(release" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocRelation formula) {
		level++;
		String result = "\n" + Strings.repeat("\t", level-1) + "([" + formula.getRelation() + "] " + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocDisjunction formula) {
		level++;
		boolean old = unaryParent;
 		unaryParent = false;
		String result =  "\n" + Strings.repeat("\t", level-1) + "(||" + formula.getLeftChild().accept(this) + " " + formula.getRightChild().accept(this) + ")";
		unaryParent = old;
		level--;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocAP cltLocAP) {
		if(cltLocAP.equals(CLTLocAP.TRUE)){
			return " 'true";
		}
		if(cltLocAP.equals(CLTLocAP.FALSE)){
			return " 'false";
		}
		if (unaryParent)
			return " " + "(-P- " + cltLocAP.toString() + ")";
		else
			return "\n" + Strings.repeat("\t", level) + "(-P- " + cltLocAP.toString() + ")";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(CLTLocClock cltlClock) {
		return "(-V- "+cltlClock.toString()+")";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(Constant cltlConstantAtom) {
		return cltlConstantAtom.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(Signal formula) {
		return "(-V- "+formula.toString()+")";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String visit(Variable formula) {
		return "(-V- "+formula.toString()+")";
	}
}
