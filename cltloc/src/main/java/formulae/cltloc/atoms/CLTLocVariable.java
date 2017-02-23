package formulae.cltloc.atoms;

import com.google.common.base.Preconditions;

import formulae.cltloc.visitor.CLTLocVisitor;

public class CLTLocVariable extends CLTLocAtom {

	private final int hash;
	private final String variableName;

	public CLTLocVariable(String name) {
		super();
		Preconditions.checkNotNull(name, "The clock name cannot be null");
		this.variableName = name;
		this.hash = variableName.hashCode();
	}

	@Override
	public int hashCode() {
		return this.hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CLTLocVariable other = (CLTLocVariable) obj;
		if (variableName == null) {
			if (other.variableName != null)
				return false;
		} else if (!variableName.equals(other.variableName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return variableName;
	}

	@Override
	public <T> T accept(CLTLocVisitor<T> t) {
		return t.visit(this);
	}

}