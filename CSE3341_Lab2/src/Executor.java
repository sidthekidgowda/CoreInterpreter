import java.io.BufferedReader;
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
	
	private static boolean InDeclSeq;
	private static boolean ReadInput;
	private static boolean OutputVariable;
	
	private List<Integer> output_values = null;
	private List<Integer> input_values = null;
	private ParseTree parse_tree = null;
	private StringBuffer hold_input = null;
	private SymbolTable sym_t = null;
	
	
	static
	{
		Executor.InDeclSeq = true;
		Executor.ReadInput = false;
		Executor.OutputVariable = false;
	}
	
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
					this.hold_input.append(read_line.trim() + " ");
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
		
		Executor.InDeclSeq = false;
		
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
	
	/**
	 * This method executes the id-list production rule
	 */
	private void executeIdList()
	{	
		if(Executor.InDeclSeq)
		{
			this.parse_tree.moveCursorToChild(1);
			this.sym_t.setValue(this.parse_tree.getId(), null);
			this.parse_tree.moveCursorUp();
			
			if(this.parse_tree.getAlternativeNumber() == 2)
			{
				this.parse_tree.moveCursorToChild(2);
				this.executeIdList();
				this.parse_tree.moveCursorUp();
			}
		}
		else//in stmt-seq phase
		{
			
			if(Executor.ReadInput)
			{
				this.parse_tree.moveCursorToChild(1);
				int value = this.input_values.remove(0);
				this.sym_t.setValue(this.parse_tree.getId(), value);
				this.parse_tree.moveCursorUp();
				
				if(this.parse_tree.getAlternativeNumber() == 2)
				{
					this.parse_tree.moveCursorToChild(2);
					this.executeIdList();
					this.parse_tree.moveCursorUp();
				}
			}
			
			if(Executor.OutputVariable)
			{
				this.parse_tree.moveCursorToChild(1);
				int value = this.sym_t.getValue(this.parse_tree.getId());
				this.output_values.add(value);
				this.parse_tree.moveCursorUp();
				
				if(this.parse_tree.getAlternativeNumber() == 2)
				{
					this.parse_tree.moveCursorToChild(2);
					this.executeIdList();
					this.parse_tree.moveCursorUp();
				}
				
			}
		}
	}
	
	
	/**
	 * This method executes the statement sequence
	 */
	private void executeStmtSeq()
	{
		this.parse_tree.moveCursorToChild(1);
		
		this.executeStmt();
		
		this.parse_tree.moveCursorUp();
		
		if(this.parse_tree.getAlternativeNumber() == 2)
		{
			this.parse_tree.moveCursorToChild(2);
			this.executeStmtSeq();
			this.parse_tree.moveCursorUp();
		}
	}
	
	/**
	 * This method executes the statement
	 */
	private void executeStmt()
	{	
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://assign
			this.parse_tree.moveCursorToChild(1);
			this.executeAssign();
			break;
		case 2://if
			this.parse_tree.moveCursorToChild(1);
			this.executeIf();
			break;
		case 3://loop
			this.parse_tree.moveCursorToChild(1);
			this.executeLoop();
			break;
		case 4: //in
			this.parse_tree.moveCursorToChild(1);
			this.executeIn();
			break;
		case 5://out
			this.parse_tree.moveCursorToChild(1);
			this.executeOut();
			break;
		default://case
			this.parse_tree.moveCursorToChild(1);
			this.exceuteCase();
			break;
		}
		
		this.parse_tree.moveCursorUp();
	}
	
	/**
	 * This method executes the assign statement
	 */
	private void executeAssign()
	{
		//evaluate the expression and set that equal to the identifier
		
		this.parse_tree.moveCursorToChild(2);
		
		int value = this.evaluateExpression();
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(1);
		
		this.sym_t.setValue(this.parse_tree.getId(), value);
		
		this.parse_tree.moveCursorUp();
	}
	
	/**
	 * This method executes the if-then or if-else-then method
	 */
	private void executeIf()
	{
		this.parse_tree.moveCursorToChild(1);
		
		boolean result = this.evaluateCondition();
		
		this.parse_tree.moveCursorUp();
		
		if(result)
		{
			this.parse_tree.moveCursorToChild(2);
			
			this.executeStmtSeq();
			
			this.parse_tree.moveCursorUp();
		}
		
		if(this.parse_tree.getAlternativeNumber() == 2)
		{
			this.parse_tree.moveCursorToChild(3);
			this.executeStmtSeq();
			this.parse_tree.moveCursorUp();
		}
		
	}
	
	/**
	 * This method executes the do while loop
	 * 
	 * This method will run at least once
	 */
	private void executeLoop()
	{
		boolean loop_again = false;
		
		do
		{
			this.parse_tree.moveCursorToChild(1);
			this.executeStmtSeq();
			this.parse_tree.moveCursorUp();
			
			this.parse_tree.moveCursorToChild(2);
			
			loop_again = this.evaluateCondition();
			
			this.parse_tree.moveCursorUp();
		}
		while(loop_again);
		
	}
	
	/**
	 * This method executes the Input method
	 */
	private void executeIn()
	{
		this.parse_tree.moveCursorToChild(1);
		
		Executor.ReadInput = true;
		
		this.executeIdList();
		
		this.parse_tree.moveCursorUp();
		
		Executor.ReadInput = false;
		
	}
	
	/**
	 * This method executes the Output method and adds to the list of output variables
	 */
	private void executeOut()
	{
		this.parse_tree.moveCursorToChild(1);
		
		Executor.OutputVariable = true;
		
		this.executeIdList();
		
		this.parse_tree.moveCursorUp();
		
		Executor.OutputVariable = false;
	}
	
	/**
	 * This method executes the Case Statement
	 */
	private void exceuteCase()
	{
		this.parse_tree.moveCursorToChild(1);
		
		int id = this.sym_t.getValue(this.parse_tree.getId());
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(2);
		
		if(this.evaluateListBlock(id))
		{
			id = this.executeListBlock();
			
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(1);
			
			this.sym_t.setValue(this.parse_tree.getId(), id);
			
			this.parse_tree.moveCursorUp();
		}
		else
		{
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(3);
			
			id = this.evaluateExpression();
			
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(1);
			this.sym_t.setValue(this.parse_tree.getId(), id);
			this.parse_tree.moveCursorUp();
		}
	
	}
	
	/**
	 * This method executes the List Block and executes the expression
	 * 
	 */
	private int executeListBlock()
	{
		this.parse_tree.moveCursorToChild(2);
		
		int result = this.evaluateExpression();
		
		this.parse_tree.moveCursorUp();
		
		return result;
	}
	
	/**
	 * This method evaluates the List Block and returns true or false
	 * @return true or false
	 */
	private boolean evaluateListBlock(int id)
	{
		boolean result = false;
		
		this.parse_tree.moveCursorToChild(1);
		
		if(this.evaluateList(id))
		{
			result = true;
		}
		else
		{
			this.parse_tree.moveCursorUp();
			
			if(this.parse_tree.getAlternativeNumber() == 2)
			{
				this.parse_tree.moveCursorToChild(2);
				result = this.evaluateListBlock(id);
			}
		}
		
		return result;
	}
	
	/**
	 * This method evaluates the individual list and sees if the identifier is matched
	 */
	private boolean evaluateList(int id)
	{
		boolean result = false;
		
		this.parse_tree.moveCursorToChild(1);
		
		int constant = Integer.parseInt(this.parse_tree.getConstant());
		
		this.parse_tree.moveCursorUp();
		
		if(id == constant)
			result = true;
		else
		{
			if(this.parse_tree.getAlternativeNumber() == 2)
			{
				this.parse_tree.moveCursorToChild(2);
				result = this.evaluateList(id);
				this.parse_tree.moveCursorUp();
			}
		}
		
		return result;
	}
	
	/**
	 * This method evaluates the condition and returns true or false
	 * 
	 * @return true or false
	 */
	private boolean evaluateCondition()
	{
		boolean result = false;
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://cmpr
			this.parse_tree.moveCursorToChild(1);
			result = this.evaluateCmpr();
			this.parse_tree.moveCursorUp();
			break;
		case 2:// !<cond>
			this.parse_tree.moveCursorToChild(1);
			result = !this.evaluateCondition();
			this.parse_tree.moveCursorUp();
			break;
		case 3:// (<cond> AND <cond>)
			this.parse_tree.moveCursorToChild(1);
			result = this.evaluateCondition();
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(2);
			result = result && this.evaluateCondition();
			this.parse_tree.moveCursorUp();
			break;
		default:
			this.parse_tree.moveCursorToChild(1);
			result = this.evaluateCondition();
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(2);
			result = result || this.evaluateCondition();
			this.parse_tree.moveCursorUp();
			break;
		}
		
		return result;
	}
	
	/**
	 * This method evaluates the comparison and returns true or false
	 * 
	 * @return true or false
	 */
	private boolean evaluateCmpr()
	{
		
		boolean result = false;
		
		this.parse_tree.moveCursorToChild(1);
		
		int expr1 = this.evaluateExpression();
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(3);
		
		int expr2 = this.evaluateExpression();
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(2);
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<
			result = expr1 < expr2;
			break;
		case 2://=
			result = expr1 == expr2;
			break;
		case 3://!=
			result = expr1 != expr2;
			break;
		case 4://>
			result = expr1 > expr2;
			break;
		case 5://>=
			result = expr1 >= expr2;
			break;
		default://<=
			result = expr1 <= expr2;
			break;
		}
		this.parse_tree.moveCursorUp();
	
		return result;
		
	}
	
	/**
	 * This method evaluates the expression and returns the value of the expression
	 * @return the value of the expression
	 */
	private int evaluateExpression()
	{
		int value = 0;
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://term
			this.parse_tree.moveCursorToChild(1);
			value = this.evaluateTerm();
			this.parse_tree.moveCursorUp();
			break;
		case 2://term + factor
			this.parse_tree.moveCursorToChild(1);
			value = this.evaluateTerm();
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(2);
			value = value + this.evaluateExpression();
			this.parse_tree.moveCursorUp();
			break;
		default://term - expr
			this.parse_tree.moveCursorToChild(1);
			value = this.evaluateTerm();
			this.parse_tree.moveCursorUp();
			this.parse_tree.moveCursorToChild(2);
			value = value - this.evaluateExpression();
			this.parse_tree.moveCursorUp();
			break;
		}
		
		return value;
	}
	
	/**
	 * This method evaluates the term and returns the value of the term
	 * 
	 * @return the value of the term
	 */
	private int evaluateTerm()
	{
		int value = 0;
		
		//id
		if(ParseTree.getParseTreeRowNum() < 0 && ParseTree.getParseTreeRowNum() % 2 != 0)
		{
			return this.sym_t.getValue(this.parse_tree.getId());
		}
		//constant
		if(ParseTree.getParseTreeRowNum() < 0 && ParseTree.getParseTreeRowNum()%2 == 0)
		{
			return Integer.parseInt(this.parse_tree.getConstant());
		}
		
		
		this.parse_tree.moveCursorToChild(1);
		
		value = this.evaluateFactor();
		
		this.parse_tree.moveCursorUp();
		
		if(this.parse_tree.getAlternativeNumber() == 2)
		{
			this.parse_tree.moveCursorToChild(2);
			value = value * this.evaluateTerm();
			this.parse_tree.moveCursorUp();
		}
		
		return value;
	}
	
	/**
	 * This method evaluates the factor and returns the value of the factor
	 * 
	 * @return the value of the factor
	 */
	private int evaluateFactor()
	{
		int value = 0;
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://constant
			this.parse_tree.moveCursorToChild(1);
			value = Integer.parseInt(this.parse_tree.getConstant());
			this.parse_tree.moveCursorUp();
			break;
		case 2://id
			this.parse_tree.moveCursorToChild(1);
			
			if(this.sym_t.isIdNotInitialized(this.parse_tree.getId()))
				throw new RuntimeException("The variable " + this.parse_tree.getId() + 
						" has not been initialized.");
	
			value = this.sym_t.getValue(this.parse_tree.getId());
			this.parse_tree.moveCursorUp();
			break;
		case 3://-<factor>
			this.parse_tree.moveCursorToChild(1);
			value = this.evaluateFactor();
			value = value * -1;
			this.parse_tree.moveCursorUp();
			break;
		default://(<expr)
			this.parse_tree.moveCursorToChild(1);
			value = this.evaluateExpression();
			this.parse_tree.moveCursorUp();
			break;
		}
		
		return value;
	}
	
	/**
	 * This inner private class SymbolTable manages the available variables for the executor
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
	     * This method checks and see if an identifier is initialized or not
	     * 
	     * @param id
	     * 
	     * @return true or false
	     */
	    public boolean isIdNotInitialized(String id) {
			
	    	if(this.table.get(id) == null)
	    		return true;
	    	else
	    		return false;
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
	}

}
