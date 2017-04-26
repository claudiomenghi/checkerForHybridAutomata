package solvers.mitli;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import com.google.common.io.ByteStreams;

import formulae.mitli.MITLIFormula;
import formulae.mitli.parser.MITLILexer;
import formulae.mitli.parser.MITLIParser;
import solvers.MITLIsolver;
import zotrunner.ZotException;

public class MITLISocsolverTest {

	private final PrintStream out = new PrintStream(ByteStreams.nullOutputStream());

	@Test
	public void testExample1() throws IOException, ZotException {
		String path = ClassLoader.getSystemResource("solvers/mitli/example1.mitli").getPath();

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		MITLILexer lexer = new MITLILexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MITLIParser parser = new MITLIParser(tokens);
		parser.setBuildParseTree(true);
		MITLIFormula formula = parser.mitli().formula;
		
		
		MITLIsolver solver=new MITLIsolver(formula, new PrintStream(System.out),  10);
		
		boolean result=solver.solve();
		
		assertTrue( "An ap should be solvable", result);
	}

	
	@Test
	public void testExample2() throws IOException, ZotException {
		String path = ClassLoader.getSystemResource("solvers/mitli/example2.mitli").getPath();

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		MITLILexer lexer = new MITLILexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MITLIParser parser = new MITLIParser(tokens);
		parser.setBuildParseTree(true);
		MITLIFormula formula = parser.mitli().formula;
		
		
		MITLIsolver solver=new MITLIsolver(formula, new PrintStream(System.out),  10);
		
		boolean result=solver.solve();
		
		assertTrue( "An ap should be solvable", result);
	}
}
