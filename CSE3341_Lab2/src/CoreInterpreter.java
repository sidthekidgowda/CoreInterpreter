import java.io.BufferedReader;
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
	private Printer printer = null;
	private Executor executor = null;


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
    	
    	//done, if parse tree is syntactically and semantically correct, printer and executor can be called
    	
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
    	
    	this.printer = new Printer(this.parser.getParseTree());
    	
    	return this.printer.getPrettyProgram();
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
    	
    	this.executor = new Executor(this.parser.getParseTree(), input);
    	
    	//execute the program
    	this.executor.executeProgram();
    	
    	
    	return this.executor.outputValues();
    }
    
}