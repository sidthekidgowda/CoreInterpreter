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
	
	private StringBuffer input_program;

	/**
     * Explicit constructor to perform lexical analysis and parsing
     * 
     * @param program the input program text
	 * @throws IllegalArgumentException if encounter syntax and parsing errors
     */
    public CoreInterpreter(BufferedReader program)
    {
    	/* Insert your code here */
    	
    	/*
    	 * Take Buffered Reader input and put everything in one line for the Scanner.
    	 */
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
}