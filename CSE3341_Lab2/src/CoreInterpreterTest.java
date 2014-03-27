import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;


public class CoreInterpreterTest {

	boolean flag = false;
	CoreInterpreter core = null;

	/**
	 * The parser and scanner does the tests for the Constructor
	 * @throws FileNotFoundException
	 */
	
	@Test
	public void testPrinter() throws FileNotFoundException {
		
		//test 1
		core = new CoreInterpreter(new BufferedReader(new FileReader("t1.code")));
		
		String s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		
		//test 2
		core = new CoreInterpreter(new BufferedReader(new FileReader("t2.code")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 3
		core = new CoreInterpreter(new BufferedReader(new FileReader("t3.code")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 4
		core = new CoreInterpreter(new BufferedReader(new FileReader("t4.code")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 5
		core = new CoreInterpreter(new BufferedReader(new FileReader("goodDeclSeq")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 6
		core = new CoreInterpreter(new BufferedReader(new FileReader("nestedConditions.txt")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 6
		core = new CoreInterpreter(new BufferedReader(new FileReader("nestedIf.txt")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 7
		core = new CoreInterpreter(new BufferedReader(new FileReader("nestedIfandCaseLoops.txt")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 8
		core = new CoreInterpreter(new BufferedReader(new FileReader("nestedLoop")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 9
		core = new CoreInterpreter(new BufferedReader(new FileReader("prettyIf.txt")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);
		
		//test 10
		
		core = new CoreInterpreter(new BufferedReader(new FileReader("nestedExpressions.txt")));
		
		s = core.printer();
		
		flag = s == null;
		
		System.out.println(s);
		assertFalse(flag);	
	
	}

	@Test
	public void testExecutor() throws FileNotFoundException 
	{
	
		boolean flag = false;	
		List<Integer> test = null;
		
		//test 1
		core = new CoreInterpreter(new BufferedReader(new FileReader("t1.code")));
		
		test = core.executor(new BufferedReader(new FileReader("t1.data")));
		
		flag = test == null;
		
		System.out.println(test);
		assertFalse(flag);
		
		
		//test 2
		core = new CoreInterpreter(new BufferedReader(new FileReader("t2.code")));
		
		test = core.executor(new BufferedReader(new FileReader("t2.data")));
			
		flag = test == null;
		
		System.out.println();
		System.out.println(test);
		assertFalse(flag);
		
		
		//test 3
		core = new CoreInterpreter(new BufferedReader(new FileReader("t3.code")));
			
		test = core.executor(new BufferedReader(new FileReader("t3.data")));
			
		flag = test == null;
			
		System.out.println();
		System.out.println(test);
		assertFalse(flag);
	
		//test 4
		core = new CoreInterpreter(new BufferedReader(new FileReader("t4.code")));
		
		test = core.executor(new BufferedReader(new FileReader("t4.data")));
			
		flag = test == null;
			
		System.out.println();
		System.out.println(test);
		assertFalse(flag);
		
		//test 5 - test case
		core = new CoreInterpreter(new BufferedReader(new FileReader("caseExec")));
		
		test = core.executor(new BufferedReader(new StringReader("2")));
			
		flag = test == null;
			
		System.out.println();
		System.out.println(test);
		assertFalse(flag);
		
		
		
	
	}

}
