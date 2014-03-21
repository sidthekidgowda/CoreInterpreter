import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * ParseTree represented by a Parse Table with a List of Strings for Non-Terminals
 * List of List of Integers for children
 * and List of Integers for alternatives
 * 
 * Each Row in the Parse Table will be represented by a List<nonterminal>1, Alternatives<Row, Alternative1>, 
 * and Map<Row Number, List<Children1>>
 * 
 * NonTerminals, Alternatives, Children of NonTerminal
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
	private Map<Integer, List<Integer>> children = null;
	private Map<Integer, Integer> alternatives = null;
	private Map<Integer, String> symbol_table = null;
	private Map<Integer, String> constants_table =null;
	
	private Scanner scanner = null;
	
	/**
	 * Constructor creates the abstract Parse Tree Representation
	 */
	public ParseTree(Scanner s)
	{
		this.non_terminals = new ArrayList<NonTerminals>();
		this.children = new HashMap<Integer,List<Integer>>();
		this.alternatives = new HashMap<Integer, Integer>();
		this.symbol_table = new HashMap<Integer,String>();
		this.constants_table = new HashMap<Integer, String>();
		this.scanner = s;
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
		this.alternatives.put(row_number, rule);
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
		

		//check and see if arrayList reference is there
		if(this.children.get(myRow) == null)
			this.children.put(myRow, new ArrayList<Integer>());
		
		
		switch(s)
		{
		case "id":
			this.children.get(myRow).add(value);
			this.symbol_table.put(value, this.scanner.getTokenValue());
			//decrement the ID index
			Parser.decrementIDIndexBy2();
			break;
		case "constant":
			this.children.get(myRow).add(value);
			this.constants_table.put(value, this.scanner.getTokenValue());
			Parser.decrementConstIndexBy2();
			break;
		
		default://non-terminal
			this.children.get(myRow).add(value);
			break;
		}
		
		
	}
	
	public static void main(String[] args)
	{
		Scanner s = new Scanner(new BufferedReader(new StringReader("program int x, y, xy; begin")));
		ParseTree t = new ParseTree(s);
		
		t.addNonTerminal(0, NonTerminals.PROG);
		t.addChildren(0, 1, "non-terminal");
		t.addChildren(0, 2, "non-terminal");
	}

}
