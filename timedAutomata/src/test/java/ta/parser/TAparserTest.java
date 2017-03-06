package ta.parser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import ta.SystemDecl;
import ta.TA;

public class TAparserTest {

	

	@Test
	public void testParseTA() throws IOException {
	
		ANTLRInputStream input = new ANTLRFileStream(ClassLoader
				.getSystemResource("ta/parser/bocdp.ta").getPath());
	    TALexer lexer = new TALexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
      
        TAParser parser = new TAParser(tokens);
        parser.setBuildParseTree(true);
        SystemDecl system= parser.ta().systemret;
        
        TA ta=system.getTimedAutomata().iterator().next();
      
        assertNotNull(ta);
    }
}
