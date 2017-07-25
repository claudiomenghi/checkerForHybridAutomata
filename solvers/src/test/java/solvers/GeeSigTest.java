package solvers;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import com.google.common.collect.HashBiMap;

import formulae.mitli.MITLIFormula;
import formulae.mitli.parser.MITLILexer;
import formulae.mitli.parser.MITLIParser;
import zotrunner.ZotException;

public class GeeSigTest {

	@Test
	public void test() throws FileNotFoundException, IOException, ZotException {
		String path = ClassLoader.getSystemResource("solvers/geesig.mitli").getPath();

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		MITLILexer lexer = new MITLILexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MITLIParser parser = new MITLIParser(tokens);
		parser.setBuildParseTree(true);
		MITLIFormula formula = parser.mitli().formula;
		
		
		MITLIsolver solver=new MITLIsolver(formula, new PrintStream(System.out),  100, new HashMap<>(), new HashSet<>());
		
		boolean result=solver.solve();
		
		assertTrue( "An ap should be solvable", result);
		
	}

}
