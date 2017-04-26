package solvers;

import java.io.IOException;
import java.io.PrintStream;

import formulae.cltloc.CLTLocFormula;
import formulae.cltloc.converters.CLTLoc2Ae2zot;
import formulae.cltloc.converters.CLTLoc2ZotDReal;
import formulae.cltloc.visitor.CLTLoc2StringVisitor;
import zotrunner.ZotException;
import zotrunner.ZotRunner;

public class CLTLocsolver {
	private final CLTLocFormula formula;
	private final PrintStream out;
	private final int bound;


	private float checkingtime;
	
	private long checkingspace;
	
	private String zotEncoding;
	
	public String getZotEncoding() {
		return zotEncoding;
	}

	public CLTLocsolver(CLTLocFormula formula, PrintStream out, int bound) {

		this.formula = formula;
		this.out = out;
		this.bound = bound;

	}

	public boolean solve() throws IOException, ZotException {
		out.println("Converting the following CLTLoc formula in zot");
		out.println(formula.accept(new CLTLoc2StringVisitor()).getKey());
		String zotEncoding = new CLTLoc2Ae2zot(bound).apply(formula);

		out.println("************************************************");
		out.println("ZOT ENCODING");
		out.println(zotEncoding);
		out.println("************************************************");

		out.println("Formula converted in zot");
		ZotRunner zotRunner=new ZotRunner(zotEncoding, out);
		boolean sat = zotRunner.run();
		this.checkingtime=zotRunner.getCheckingtime();
		this.checkingspace=zotRunner.getCheckingspace();
		this.zotEncoding=zotRunner.getZotEncoding();
		return sat;
	}

	public float getCheckingtime() {
		return checkingtime;
	}

	public long getCheckingspace() {
		return checkingspace;
	}
}
