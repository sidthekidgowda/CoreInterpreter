import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Class Executor executes the execute method in the Core Interpreter class and returns a list of output values
 *
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class Executor {
	
	private List<Integer> output_values = null;
	private List<Integer> input_values = null;
	private ParseTree parse_tree = null;
	private StringBuffer hold_input = null;
	private SymbolTable sym_t = null;
	
	
	/**
	 * Constructor creates
	 * 
	 * @param pt
	 * @param input
	 */
	public Executor(ParseTree pt, BufferedReader input)
	{
		this.output_values = new ArrayList<Integer>();
		this.input_values = new ArrayList<Integer>();
		
		//create the inner symbol table reference
		this.sym_t = new SymbolTable();
		
		try{
			/**
			 * Move all input into one line
			 */
			
			String read_line = null;
			this.hold_input = new StringBuffer("");
			while((read_line = input.readLine())!= null)
			{
				//do not add empty lines
				if(!read_line.matches("\\s*"))
				{
					this.hold_input.append(read_line.trim());
				}
				
			}
		}
		catch(IOException e)
		{
			System.out.println("Could not read in the input values" + e.getMessage());
		}
		
		//now get the variables
		//split the string buffer into a string into a string array
		String input_values_string[] = this.hold_input.toString().split("\\s+");
		
		//store in the input_values list
		for(int i = 0; i < input_values_string.length; i++)
		{
			
			try{
				int x = Integer.parseInt(input_values_string[i]);
				this.input_values.add(x);
			}
			catch(NumberFormatException n)
			{
				throw new RuntimeException("The value of " + input_values_string[i] +
						" is not an integer that can be read.");
			}
		}
	
		this.parse_tree = pt;
		
		//move the ParseTree to the top
		this.parse_tree.moveCursorToRoot();
	}
	
	
	/**
	 * This method returns the List which contains the output values of the excecuted program
	 * 
	 * @return outputValues
	 */
	public List<Integer> outputValues()
	{
		return this.output_values;
	}
	
	/**
	 * This method executes and runs the program and creates the List of outputValues
	 */
	public void executeProgram()
	{
		this.parse_tree.moveCursorToChild(1);
		
		//execute the declaration sequence
		this.executeDeclSeq();
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(2);
		
		//exceute the statement sequences
		this.executeStmtSeq();
		
		this.parse_tree.moveCursorUp();
	}
	
	/**
	 * This method executes the declaration sequence
	 */
	private void executeDeclSeq()
	{
		this.parse_tree.moveCursorToChild(1);
		
		this.executeDecl();
		
		this.parse_tree.moveCursorUp();
		
		if(this.parse_tree.getAlternativeNumber() == 2)
		{
			this.parse_tree.moveCursorToChild(2);
			this.executeDeclSeq();
			this.parse_tree.moveCursorUp();
		}
	}
	
	/**
	 * This method executes the declaration
	 */
	private void executeDecl()
	{
		this.parse_tree.moveCursorToChild(1);
		
		this.executeIdList();
		
		this.parse_tree.moveCursorUp();
	}
	
	private void executeIdList()
	{
		this.parse_tree.moveCursorToChild(1);
		
		//check and see if the symbol table has the value already
		if(!this.sym_t.containsKey(this.parse_tree.getId()))
			this.sym_t.setValue(this.parse_tree.getId(), null);
		
		this.parse_tree.moveCursorUp();
		
		if(this.parse_tree.getAlternativeNumber() == 2)
		{
			this.parse_tree.moveCursorToChild(2);
			this.executeIdList();
			this.parse_tree.moveCursorUp();
		}
	}
	
	private void executeStmtSeq()
	{
		
	}
	
	private void executeStmt()
	{
		
	}
	
	private void executeAssign()
	{
		
	}
	
	private void executeIf()
	{
		
	}
	
	private void executeLoop()
	{
		
	}
	
	private void executeIn()
	{
		
	}
	
	private void executeOut()
	{
		
	}
	private void exceuteCase()
	{
		
	}
	private void exceuteCond()
	{
		
	}
	private void exceuteCmpr()
	{
		
	}
	private void exceuteCmprOp()
	{
		
	}
	private void exceuteExpr()
	{
		
	}
	private void exceuteTerm()
	{
		
	}
	private void exceuteFactor()
	{
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Parser p1 = new Parser(new Scanner(new BufferedReader(new FileReader("t1.code"))));
		p1.makeParseTree();
		
		Executor ex = new Executor(p1.getParseTree(), new BufferedReader(new FileReader("t1.data")));
		
	}
	
	
	/**
	 * This private class SymbolTable manages the available variables for the executor
	 * 
	 * @author Sid Gowda
	 *
	 */
	private class SymbolTable
	{
		// insert instance variable(s) here, if any
		
		//create a Map interface and set to null
		private Map<String, Integer> table = null;
		
	    /**
	     * Creates a SymbolTable.
	     * Empty Constructor
	     */
	    private SymbolTable()
	    {
	    	//use a HashMap implementation
	    	table = new HashMap<String,Integer>();
	    }
	    
	    /**
	     * Sets the value of the given variable.
	     * 
	     * @param name the name
	     * @param value the value
	     */
	    private void setValue(String name, Integer value)
	    {
	    	//put into the HashMap
	    	table.put(name, value);
	    }
	    
	    /**
	     * Returns the value of the given variable.
	     * 
	     * @param name the name
	     * @return the value
	     */
	    private int getValue(String name)
	    {
	    	int result = 0;
	    	result = table.get(name);
	  
	        return result;
	    }
	    
	    /**
	     * Returns true or false if the symbol table has the identifier defined
	     */
	    private boolean containsKey(String key)
	    {
	    	return this.table.containsKey(key);
	    }
		
	}

}
