import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * ParseTree represented by a Parse Table with a List of Strings for Non-Terminals
 * List of List of Integers for children
 * and List of Integers for alternatives
 * 
 * Each Row in the Parse Table will be looked as List<nonterminals>, List<Alternatives>, 
 * and List<List<Children>>
 * 
 * Symbol Table will hold the values of the Identifiers
 * 
 * List of Constants will hold the values of the Constants
 * 
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class ParseTree {

	private List<NonTerminals> non_terminals = null;
	private List<List<Integer>> children = null;
	private static List<Integer> child = null;
	private static int nextRow;
	
	private List<Integer> alternatives = null;
	private Map<Integer, String> symbol_table = null;
	private Map<Integer, String> constants_table =null;
	private List<Integer> constants = null;
	private Scanner scanner = null;
	

	/**
	 * Constructor creates the abstract Parse Tree Representation
	 */
	public ParseTree(Scanner s)
	{
		this.non_terminals = new ArrayList<NonTerminals>(5000);
		this.children = new ArrayList<List<Integer>>(5000);
		this.alternatives = new ArrayList<Integer>(5000);
		this.symbol_table = new HashMap<Integer,String>();
		this.constants_table = new HashMap<Integer, String>();
		this.scanner = s;
	}
	
	static
	{
		ParseTree.child = new ArrayList<Integer>();
		ParseTree.nextRow = Parser.getParseRow();
	}
	
	/**
	 * Add a NonTerminal type to the ParseTree at the specified position
	 * @param NonTerminal 
	 */
	public void addNonTerminal(int row_number, NonTerminals nt)
	{
		this.non_terminals.add(row_number, nt);
	}
	/**
	 * Adds the Alternative Rule Number to the List
	 */
	public void addAlternativeNumber(int row_number, int rule)
	{
		this.alternatives.add(row_number, rule);
	}
	
	
	/**
	 * Add child to the List of List of Integers
	 * @param myRow
	 * @param value
	 * @param s
	 */
	public void addChildren(int myRow, int value, String s)
	{
		/**
		 * if the string is "id", add to a symbol table, this is a identifier
		 * 	if the number is "constant", add to the list of Constants, this is a constant
		 * 	the value is stored in the List of Children, but these negative values are keys to the HashMap
		 *  with the ID value or Constant Value
		 * 
		 * else
		 * 	add to the List of List of Integers, this is a non-terminal
		 */
		
	
		//if the ParseTree index is not lined up with the index of the Parser,
		//Clear the List
		if(ParseTree.nextRow != myRow)
			ParseTree.clearChild();
		
		switch(s)
		{
		case "id":
			ParseTree.child.add(value);
			this.children.add(myRow, ParseTree.child);
			this.symbol_table.put(value, this.scanner.getTokenValue());
			//decrement the ID index
			Parser.decrementIDIndexBy2();
			break;
		
		case "constant":
			ParseTree.child.add(value);
			this.children.add(myRow, ParseTree.child);
			this.constants_table.put(value, this.scanner.getTokenValue());
			break;
		
		default://non-terminal
			ParseTree.child.add(value);
			
			this.children.add(myRow, ParseTree.child);
			
			break;
		}
		
		//increment 
		ParseTree.nextRow++;
		
	}
	private static void clearChild()
	{
		ParseTree.child.clear();
	}

}
