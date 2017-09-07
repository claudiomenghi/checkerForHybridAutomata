package formulae.cltloc.converters;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;

import formulae.cltloc.CLTLocFormula;
import formulae.cltloc.atoms.CLTLocClock;
import formulae.cltloc.atoms.Signal;
import formulae.cltloc.atoms.Variable;
import formulae.cltloc.visitor.CLTLoc2ZotVisitor;
import formulae.cltloc.visitor.GetClocksVisitor;
import formulae.cltloc.visitor.GetSignalVisitor;
import formulae.cltloc.visitor.GetVariablesVisitor;

public class CLTLoc2ZotDReal implements Function<CLTLocFormula, String> {

	private final int bound;
	
	private final BiMap<Integer, String> vocabulary;
	private final Map<String, Float> initValues;
	private final Set<String> flows;
	
	public CLTLoc2ZotDReal(int bound, BiMap<Integer, String> vocabulary, Map<String, Float> initValues, Set<String> flows) {
		Preconditions.checkArgument(bound > 0, "The bound must be grather than zero");
		this.bound = bound;
		this.vocabulary=vocabulary;
		this.initValues=initValues;
		this.flows=flows;
	}

	public String apply(CLTLocFormula formula) {
		final StringBuilder builder = new StringBuilder();
		builder.append("(asdf:operate 'asdf:load-op 'ae2zotdreal)");
		builder.append("(use-package :trio-utils)\n");

		Set<CLTLocClock> clocks = formula.accept(new GetClocksVisitor());
		clocks.forEach(clock -> builder.append("(define-tvar " + clock.toString() + " *real*)\n"));

		Set<Signal> signals = formula.accept(new GetSignalVisitor());
		signals.forEach(signal -> builder.append("(define-tvar " + signal.toString() + " *real*)\n"));

		Set<Variable> variables = formula.accept(new GetVariablesVisitor());
		variables.forEach(variable -> builder.append("(define-tvar " + variable.toString() + " *real*)\n"));

		
		final StringBuilder footerBuilder = new StringBuilder();
		footerBuilder.append(":signals '(" + StringUtils.join(signals, ' ') + ")\n");
		
		footerBuilder.append(":discrete-counters '(" + StringUtils.join(variables, ' ') + ")\n");

		final StringBuilder signalsFootBuilder = new StringBuilder();
		
		signalsFootBuilder.append(":signals '(" + StringUtils.join(signals, ' ') + ")");
		
		final StringBuilder intervalsBuilder = new StringBuilder();
		
		intervalsBuilder.append(":mtl-intervals '(" );
		for(Entry<Integer, String> e: vocabulary.entrySet()){
			intervalsBuilder.append("(H_"+e.getKey().toString()+" "+e.getValue()+")");
		}
		intervalsBuilder.append(")");
		
		final StringBuilder initBuilder = new StringBuilder();
		initBuilder.append(":init-signals '( ");
		for(Entry<String, Float> e: initValues.entrySet()){
			initBuilder.append("("+e.getKey()+" "+e.getValue().toString()+ ")");
		}
		initBuilder.append(")");
		
		final StringBuilder flowsBuilder = new StringBuilder();
		flowsBuilder.append(":flows '( ");
		for(String flow: flows){
			flowsBuilder.append(flow);
		}
		flowsBuilder.append(" )");
		
		builder.append("(ae2zotdreal:zot " + bound + "(yesterday (&&" + formula.accept(new CLTLoc2ZotVisitor()) + "))\n\n"
				+ ":smt-lib :smt2 \n" 
				+ ":gen-symbolic-val nil\n"
				+ ":over-clocks 0\n"
				+ signalsFootBuilder.toString()+"\n"
				+ intervalsBuilder.toString()+"\n"
				+ initBuilder.toString()+"\n"
				+ flowsBuilder.toString()+"\n"
				+ footerBuilder.toString() + " \n"
				  + ")\n");

		builder.append("\n");

		return builder.toString();
	}
	
	
	public String apply(Set<CLTLocFormula> set){
		final StringBuilder builder = new StringBuilder();
		builder.append("(asdf:operate 'asdf:load-op 'ae2zotdreal)");
		builder.append("(use-package :trio-utils)\n");

		Set<CLTLocClock> clocks = new HashSet<CLTLocClock>();
		for (CLTLocFormula formula: set){
			clocks.addAll(formula.accept(new GetClocksVisitor()));
		}
		clocks.forEach(clock -> builder.append("(define-tvar " + clock.toString() + " *real*)\n"));

		//Set<Signal> signals = formula.accept(new GetSignalVisitor());
		Set<Signal> signals = new HashSet<Signal>();
		for (CLTLocFormula formula: set){
			signals.addAll(formula.accept(new GetSignalVisitor()));
		}
		// This might happen when the formula does not include signals
		if (signals.isEmpty()){
			initValues.keySet().forEach(signal -> signals.add(new Signal(signal.toString())));
		}
		signals.forEach(signal -> builder.append("(define-tvar " + signal.toString() + " *real*)\n"));

		
		final StringBuilder formulaBuilder = new StringBuilder();
		formulaBuilder.append("(&&");
		
		int i=0;
		for (CLTLocFormula formula: set){
			
			// Write the definition of Lisp constant fn (defconstant fn '(...))
			builder.append("(defconstant f" + String.valueOf(i));
			builder.append(formula.accept(new CLTLoc2ZotVisitor()));
			builder.append(")\n\n");
			
			// Write the final formula with fn instances
			formulaBuilder.append(" f" + String.valueOf(i));
			i++;
		}
		formulaBuilder.append(")");
		
		
		final StringBuilder signalsFootBuilder = new StringBuilder();		
		signalsFootBuilder.append(":signals '(" + StringUtils.join(signals, ' ') + ")");
		
		final StringBuilder intervalsBuilder = new StringBuilder();		
		intervalsBuilder.append(":mtl-intervals '(" );
		for(Entry<Integer, String> e: vocabulary.entrySet()){
			intervalsBuilder.append("(H_"+e.getKey().toString()+" "+e.getValue()+")");
		}
		intervalsBuilder.append(")");
		
		final StringBuilder initBuilder = new StringBuilder();
		initBuilder.append(":init-signals '( ");
		for(Entry<String, Float> e: initValues.entrySet()){
			initBuilder.append("("+e.getKey()+" "+e.getValue().toString()+ ")");
		}
		initBuilder.append(")");
		
		final StringBuilder flowsBuilder = new StringBuilder();
		flowsBuilder.append(":flows '( ");
		for(String flow: flows){
			flowsBuilder.append(flow);
		}
		flowsBuilder.append(" )");
		
		
		
		builder.append("(ae2zotdreal:zot " + bound + " " + formulaBuilder.toString() + "\n\n"
				+ ":smt-lib :smt2 \n" 
				+ ":gen-symbolic-val nil\n"
				+ ":over-clocks 0\n"
				+ signalsFootBuilder.toString()+"\n"
				+ intervalsBuilder.toString()+"\n"
				+ initBuilder.toString()+"\n"
				+ flowsBuilder.toString()+"\n"
				+ ")\n");

		builder.append("\n");

		return builder.toString();
	}
}
