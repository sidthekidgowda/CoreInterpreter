import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * CoreInterpreter
 * 
 * @author Sid Gowda
 * @version 3/17/2014
 */

public class CoreInterpreter
{   
	// insert instance variable(s) here, if any
	
	private Scanner lex_analyzer = null;
	private Parser parser = null;


	/**
     * Explicit constructor to perform lexical analysis and parsing
     * 
     * @param program the input program text
	 * @throws IllegalArgumentException if encounter syntax and parsing errors
     */
    public CoreInterpreter(BufferedReader program)
    {
    	/* Insert your code here */
 
    	//Send the input program into the lexical analyzer
    	this.lex_analyzer = new Scanner(program);
    	
    	//create the LL(1) Parser and send in the scanner reference
    	this.parser = new Parser(this.lex_analyzer);
    	
    	//parse and create the Parse Tree
    	this.parser.makeParseTree();
    	
    	//make semantic check on the Parse Tree
    	
    	
    	
    	
    	
    }

    /**
     * Printer
     * 
	 * @return the input program in pretty print format (should never return null)
     */
    public String printer()
    {
		/* Replace with your code */
		/* Remember, this method should never return null */
    	return null;
    }
    
    
    /**
     * Executor
     * 
     * @param input program input for execution
     * @return list of output values (Should never return null)
     * @throws RuntimeException if a run-time error occurs during execution
     */
    public List<Integer> executor(BufferedReader input)
    {
		/* Replace with your code */
		/* Remember, this method should never return null */
        return null;
    }
    
    /**
     * Delete This Main method used only for testing
     * @throws FileNotFoundException 
     */
    
    public static void main(String[] args) throws FileNotFoundException
    {
    	CoreInterpreter c = new CoreInterpreter(new BufferedReader(new FileReader("t1.code")));
    	CoreInterpreter c2 = new CoreInterpreter(new BufferedReader(new FileReader("t3.code")));
    	CoreInterpreter c3 = new CoreInterpreter(new BufferedReader(new FileReader("t4.code")));
        
        
    }
}