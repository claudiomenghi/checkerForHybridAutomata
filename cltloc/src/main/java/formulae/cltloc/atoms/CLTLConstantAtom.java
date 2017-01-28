package formulae.cltloc.atoms;

import formulae.cltloc.visitor.CLTLocVisitor;

public class CLTLConstantAtom extends CLTLocAtom {

	public CLTLConstantAtom(int value) {
		super(Integer.toString(value));
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T accept(CLTLocVisitor<T> t) {
		return t.visit(this);
	}
}