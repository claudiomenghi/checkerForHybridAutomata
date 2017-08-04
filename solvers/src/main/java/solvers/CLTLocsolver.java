package solvers;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.BiMap;

import formulae.cltloc.CLTLocFormula;
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
	private final BiMap<Integer, String> vocabulary;
	private final Map<String, Float> initValues;
	private final Set<String> flows;
	
	public String getZotEncoding() {
		return zotEncoding;
	}

	public CLTLocsolver(CLTLocFormula formula, PrintStream out, int bound, BiMap<Integer, String> vocabulary, Map<String, Float> initValues, Set<String> flows) {

		this.formula = formula;
		this.out = out;
		this.bound = bound;
		this.vocabulary=vocabulary;
		this.initValues=initValues;
		this.flows=flows;

	}

	public boolean solve() throws IOException, ZotException {
		out.println("Converting the following CLTLoc formula in zot");
		out.println(formula.accept(new CLTLoc2StringVisitor()).getKey());
		//String zotEncoding = new CLTLoc2Ae2zot(bound).apply(formula);
		String zotEncoding = new CLTLoc2ZotDReal(bound, vocabulary, initValues, flows).apply(formula);

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
