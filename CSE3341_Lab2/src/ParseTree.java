import java.util.List;
import java.util.ArrayList;

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
	private List<Integer> alternatives = null;
	private List<String> symbol_table = null;
	private List<Integer> constants = null;
	
	/**
	 * Constructor creates the abstract Parse Tree Representation
	 */
	public ParseTree()
	{
		this.non_terminals = new ArrayList<NonTerminals>();
		this.children = new ArrayList<List<Integer>>();
		this.alternatives = new ArrayList<Integer>();
		this.symbol_table = new ArrayList<String>();
		this.constants = new ArrayList<Integer>();
	
		
		
	}
	
	
	
	/**
	 * Add a NonTerminal type to the ParseTree
	 * @param NonTerminal 
	 */
	public void addNonTerminal(NonTerminals nt)
	{
		this.non_terminals.add(nt);
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
		 * else
		 * 	if the number is "constant", add to the list of Constants, this is a constant
		 * else
		 * 	add to the List of List of Integers, this is a non-terminal
		 */
		
		switch(s)
		{
		case "id":
			break;
		case "constant":
			break;
		default:
			break;
		}
		
		
		
		
	}
	/**
	 * Adds the Alternative Rule Number to the List
	 */
	public void addAlternativeNumber(int rule)
	{
		this.alternatives.add(rule);
	}
	
}
