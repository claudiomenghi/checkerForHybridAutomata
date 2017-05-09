/**
 * 
 */
package formulae.mitli.converters;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import formulae.cltloc.CLTLocFormula;
import formulae.mitli.MITLIFormula;
import formulae.mitli.atoms.MITLIAtom;
import formulae.mitli.atoms.MITLIPropositionalAtom;
import formulae.mitli.atoms.MITLIRelationalAtom;
import formulae.mitli.parser.MITLILexer;
import formulae.mitli.parser.MITLIParser;

/**
 * @author Claudio1
 *
 */
public class MITLI2CLTLocTest {

	@Test
	public void test() {
		
		MITLIAtom a=new MITLIPropositionalAtom("a");
		
		
		MITLI2CLTLoc converter=new MITLI2CLTLoc(a, 5);
		
		converter.apply();
		converter.getTheta(a);
	}

	@Test
	public void test2() throws FileNotFoundException, IOException {
		
		String path = ClassLoader.getSystemResource("formulae/mitli/converters/formula1.mitli").getPath();

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		MITLILexer lexer = new MITLILexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MITLIParser parser = new MITLIParser(tokens);
		parser.setBuildParseTree(true);
		MITLIFormula formula = parser.mitli().formula;
		
		assertTrue(formula instanceof MITLIRelationalAtom);
		
		CLTLocFormula f=	new MITLI2CLTLoc(formula, 5).apply();
	}
	
	@Test
	public void test3() throws FileNotFoundException, IOException {
		
		String path = ClassLoader.getSystemResource("formulae/mitli/converters/formula2.mitli").getPath();

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		MITLILexer lexer = new MITLILexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MITLIParser parser = new MITLIParser(tokens);
		parser.setBuildParseTree(true);
		MITLIFormula formula = parser.mitli().formula;
		
		assertTrue(formula instanceof MITLIRelationalAtom);
	}

	@Test
	public void test4() throws FileNotFoundException, IOException {
		
		String path = ClassLoader.getSystemResource("formulae/mitli/converters/formula2.mitli").getPath();

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		MITLILexer lexer = new MITLILexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MITLIParser parser = new MITLIParser(tokens);
		parser.setBuildParseTree(true);
		MITLIFormula formula = parser.mitli().formula;
		
		assertTrue(formula instanceof MITLIRelationalAtom);
	}
	
	
}
