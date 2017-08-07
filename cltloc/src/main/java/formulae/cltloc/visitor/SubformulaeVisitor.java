package formulae.cltloc.visitor;

import java.util.HashSet;
import java.util.Set;

import formulae.cltloc.CLTLocFormula;
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

public class SubformulaeVisitor implements CLTLocVisitor<Set<CLTLocFormula>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocDisjunction formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocConjunction formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocNegation formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocUntil formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocImplies formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocIff formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocSince formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocRelease formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getLeftChild().accept(this));
		formulae.addAll(formula.getRightChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocNext formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocGlobally formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocEventually formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocYesterday formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.addAll(formula.getChild().accept(this));
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocRelation formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.add(formula);
		return formulae;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<CLTLocFormula> visit(CLTLocAP formula) {
		Set<CLTLocFormula> formulae = new HashSet<>();
		formulae.add(formula);
		return formulae;
	}

	@Override
	public Set<CLTLocFormula> visit(CLTLocClock cltlClock) {
		return new HashSet<>();
	}

	@Override
	public Set<CLTLocFormula> visit(Constant cltlConstantAtom) {
		return new HashSet<>();
	}

	@Override
	public Set<CLTLocFormula> visit(Signal formula) {
		return new HashSet<>();
	}

	@Override
	public Set<CLTLocFormula> visit(Variable cltLocVariable) {
		return new HashSet<>();
	}
}
