package solvers;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import formulae.cltloc.CLTLocFormula;
import formulae.mitli.MITLIFormula;
import formulae.mitli.atoms.MITLIRelationalAtom;
import formulae.mitli.converters.MITLI2CLTLoc;
import formulae.mitli.parser.MITLILexer;
import formulae.mitli.parser.MITLIParser;
import zotrunner.ZotException;

public class MITLIsolver {

	private final MITLIFormula formula;
	private final PrintStream out;
	private final int bound;

	private CLTLocFormula cltlocFormula;
	private Set<CLTLocFormula> cltlocFormulae;
	private Map<Integer, MITLIFormula> vocabulary;
	private String zotEncoding;
	private final Map<String, Float> initValues;
	private final Set<String> flows;
	

	public MITLIsolver(MITLIFormula formula, PrintStream out, int bound, Map<String, Float> initValues, Set<String> flows) {

		this.formula = formula;
		this.out = out;
		this.bound = bound;
		this.initValues=initValues;
		this.flows=flows;

	}

	public boolean solve() throws IOException, ZotException {

		out.println("Transforming the MITLI formula in CLTLoc");
		out.println("Formula: " + formula);
		MITLI2CLTLoc converted = new MITLI2CLTLoc(formula, bound);

		cltlocFormula = converted.apply();
		converted.printFancy(out);

		this.vocabulary = converted.getVocabulary();
		
		BiMap<Integer, String> vocabularyForSolver=HashBiMap.create();
		
		for(Entry<Integer, MITLIFormula> e: vocabulary.entrySet()){
			if(e.getValue() instanceof MITLIRelationalAtom){
				vocabularyForSolver.put(e.getKey(), ((MITLIRelationalAtom)e.getValue()).getString());
			}
		}
		
		CLTLocsolver solver=new CLTLocsolver(cltlocFormula, out, bound, vocabularyForSolver, this.initValues, this.flows);
		boolean result=solver.solve();
		this.zotEncoding=solver.getZotEncoding();
		
		
		StringBuilder vocabularyBuilder = new StringBuilder();
		this.vocabulary.entrySet().forEach(e -> vocabularyBuilder.append(e.getValue() + "\t" + e.getKey() + "\n"));
		
		out.println("Vocabulary:");
		out.println(vocabularyBuilder.toString());
		FileUtils.writeStringToFile(new File("vocabulary.txt"), vocabularyBuilder.toString());

		return result;

	}
	
	
	public boolean solve2() throws IOException, ZotException {

		out.println("Transforming the MITLI formula in CLTLoc");
		out.println("Formula: " + formula);
		MITLI2CLTLoc converted = new MITLI2CLTLoc(formula, bound);

		cltlocFormulae = converted.getCLTLocFormulae();
		converted.printFancy(out);

		this.vocabulary = converted.getVocabulary();
		
		BiMap<Integer, String> vocabularyForSolver=HashBiMap.create();
		
		for(Entry<Integer, MITLIFormula> e: vocabulary.entrySet()){
			if(e.getValue() instanceof MITLIRelationalAtom){
				vocabularyForSolver.put(e.getKey(), ((MITLIRelationalAtom)e.getValue()).getString());
			}
		}
		
		CLTLocsolver solver=new CLTLocsolver(cltlocFormulae, out, bound, vocabularyForSolver, this.initValues, this.flows);
		boolean result=solver.solve();
		this.zotEncoding=solver.getZotEncoding();
		
		
		StringBuilder vocabularyBuilder = new StringBuilder();
		this.vocabulary.entrySet().forEach(e -> vocabularyBuilder.append(e.getValue() + "\t" + e.getKey() + "\n"));
		
		out.println("Vocabulary:");
		out.println(vocabularyBuilder.toString());
		FileUtils.writeStringToFile(new File("vocabulary.txt"), vocabularyBuilder.toString());

		return result;

	}

	public String getZotEncoding() {
		return zotEncoding;
	}

	public static void main(String[] args) throws Exception {
		PrintStream out = System.out;

		out.println(args[0]);
		out.println(args[1]);

		String fileOutName = args[0];
		Preconditions.checkArgument(args.length > 1,
				"you must specify the file that contains the MITLI formula and the bound to be used");
		Preconditions.checkArgument(Files.exists(Paths.get(args[0])), "The file: " + args[0] + " does not exist");
		Preconditions.checkArgument(Files.exists(Paths.get(args[2])), "The file: " + args[2] + " does not exist");
		Preconditions.checkArgument(Files.exists(Paths.get(args[3])), "The file: " + args[3] + " does not exist");
		// Preconditions.checkArgument(args[1]!=null, "The second parameter mus
		// be the bound to be used");
		// Preconditions.checkArgument( Integer.getInteger(args[1])!=null,
		// "Parameter "+args[1]+ " not valid");
		out.println("Quantitative - Metric Temporal Logic Solver");
		out.println("v. 2.0 - 19/4/2013\n");
		out.println("Analizing the file: " + args[0]);

		if (new File(args[0]).isFile()) {
			ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
			MITLILexer lexer = new MITLILexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MITLIParser parser = new MITLIParser(tokens);
			parser.setBuildParseTree(true);

			MITLIFormula formula = parser.mitli().formula;

			
			MITLIsolver solver = new MITLIsolver(formula, System.out, Integer.parseInt(args[1]), readInitValues(args[2]), readFlows(args[3]));
			solver.solve2();

			out.print("************************************************************************************\n");
			// Writing the CLTLoc formula
			String cltlocFile;
			if (fileOutName.contains(".mitli")) {
				cltlocFile = fileOutName.replaceAll(".mitli", ".cltloc");
			} else {
				cltlocFile = fileOutName.concat(".cltloc");
			}

			FileUtils.writeStringToFile(new File(cltlocFile),
					solver.getCltlocFormula().toString());
			out.println("CLTLoc formula written in the file " + cltlocFile);

			// Writing the vocabulary
			StringBuilder vocabularyBuilder = new StringBuilder();
			for (Entry<Integer, MITLIFormula> entry : solver.getVocabulary().entrySet()) {
				vocabularyBuilder.append(entry.getKey() + ": " + entry.getValue() + "\n");
			}
			String vocabulary;
			if (fileOutName.contains(".mitli")) {
				vocabulary = fileOutName.replaceAll(".mitli", ".vocabulary");
			} else {
				vocabulary = fileOutName.concat(".vocabulary");
			}
			FileUtils.writeStringToFile(new File(vocabulary), vocabularyBuilder.toString());
			out.println("Vocabulary written in the file " + vocabulary);

			// Writing the zot encoding

			String zotEncoding = solver.getZotEncoding();
			String lispFile;
			if (fileOutName.contains(".mitli")) {
				lispFile = fileOutName.replaceAll(".mitli", ".lisp");
			} else {
				lispFile = fileOutName.concat(".lisp");
			}
			FileUtils.writeStringToFile(new File(lispFile), zotEncoding);
			out.println("Zot encoding written in the file " + lispFile);

		} else {
			System.out.println(
					"File " + (new File("").getAbsolutePath()) + File.pathSeparator + args[0] + " does not exist");
		}
	}

	public Map<Integer, MITLIFormula> getVocabulary() {
		return vocabulary;
	}

	public CLTLocFormula getCltlocFormula() {
		return cltlocFormula;
	}

	public void setCltlocFormula(CLTLocFormula cltlocFormula) {
		this.cltlocFormula = cltlocFormula;
	}
	
	public static Map<String, Float> readInitValues(String filename) throws IOException{
		Map<String, Float> m = new HashMap<String, Float>();
		
		List<String> contents = FileUtils.readLines(new File(filename));
		for (String line : contents) 
			m.put(line.split("=")[0], new Float(line.split("=")[1]));
		
		return m;
	}
	
	public static Set<String> readFlows(String filename) throws IOException{
		
		List<String> contents = FileUtils.readLines(new File(filename));
		
		return new HashSet<String>(contents);
	}
	
}