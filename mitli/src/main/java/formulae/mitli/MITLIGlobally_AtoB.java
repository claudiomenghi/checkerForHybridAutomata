package formulae.mitli;

import formulae.mitli.TemporizedFormula;

import formulae.mitli.visitors.MITLIVisitor;

public class MITLIGlobally_AtoB extends MITLIGlobally implements TemporizedFormula {

	private final int a;
	private final int b;

	private final String operator = "G";

	public MITLIGlobally_AtoB(MITLIFormula subformula, int a, int b) {

		super(subformula);
		this.a = a;
		this.b = b;

		subformula.maxIntComparedto(b - a);
		this.maxIntComparedto(b);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int upperbound() {
		return b;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int lowerbound() {
		return a;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T accept(MITLIVisitor<T> visitor) {
		return visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + this.operator + "(" + String.valueOf(a) + "," + String.valueOf(b) + ")" + this.getChild() + ")";
	}
}
