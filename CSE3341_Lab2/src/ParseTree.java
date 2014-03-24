import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * ParseTree represented by a Parse Table with a List of Strings for Non-Terminals
 * 
 * The Parse Table will be represented by a List<Non Terminals>, Map of Alternatives<Key = Row Number, Value = Alternative Rule No>, 
 * and Map<Key = Row Number, Value = List<Children of Row Number>>
 * 
 * NonTerminals, Alternatives, Children of NonTerminal
 * 
 * Symbol Table will hold the values of the Identifiers
 * 
 * Constants Table will hold the values of the Constants
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
	private Set<String> decl_table = null;
	private List<Integer> id_index = null;
	private Scanner scanner = null;
	private LinkedList<Integer> parents = null;
	private List<Integer> alternativesList = null;
	private static int row_num;
	
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
		this.decl_table = new HashSet<String>();
		this.id_index = new ArrayList<Integer>();
		this.parents = new LinkedList<Integer>();
		this.scanner = s;
	}
	static
	{
		ParseTree.row_num = 0;
	}
	
	/**
	 * Add a NonTerminal type to the ParseTree at the specified position
	 * @param nt is a NonTerminal type added to the list of NonTerminals
	 */
	public void addNonTerminal(NonTerminals nt)
	{
		this.non_terminals.add(nt);
	}
	
	/**
	 * addAlternativeNumber uses the row_number as the key and the rule as the value
	 * @param row_number
	 * @param rule
	 */
	public void addAlternativeNumber(int row_number, int rule)
	{
		this.alternatives.put(row_number, rule);
	}
	
	/**
	 * This checks and see if an identifier is declared more than once in the DeclSeq
	 * @return true or false
	 */
	public boolean containsMutlipleDeclaredVariable()
	{
		if(this.symbol_table.containsValue(this.scanner.getTokenValue()))
			return true;
		return false;
	}
	
	/**
	 * This method checks and see if a variable is declared before it is used.
	 * @param identifier
	 */
	public boolean checkIfVariableIsDeclared(String identifier)
	{
		if(this.decl_table.contains(identifier))
			return true;
		return false;
	}
	
	/**
	 * Add child to the List of List of Integers
	 * @param myRow - tells which row to access in the ParseTree Table
	 * @param value of the child
	 * @param s - tells if the child is a non-terminal, identifier, or a constant
	 */
	public void addChild(int myRow, int value, String s)
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
		case "identifier":
			this.children.get(myRow).add(value);
			//make sure you don't create duplicate entries in the symbol table
			if(!this.symbol_table.containsValue(this.scanner.getTokenValue()))
			{
				this.symbol_table.put(value, this.scanner.getTokenValue());
				if(Parser.isProgramInDeclSeq())
				{
					this.decl_table.add(this.scanner.getTokenValue());
					this.id_index.add(Parser.getParserIdIndex());
				}
				//decrement the ID index
				Parser.decrementIDIndexBy2();
			}
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
	
	/**
	 * The method moveCursorToChild moves to the row number of the child
	 * @param int child tells which child to move to
	 */
	public void moveCursorToChild(int child)
	{
		//add the parent row number
		this.parents.add(ParseTree.row_num);
		//move ParseTree to the child
		ParseTree.row_num = this.children.get(ParseTree.row_num).get(--child);	
	}
	
	/**
	 * The method moveCursorUp moves the cursor to the parent of the child
	 * precondition: not at root
	 */
	public void moveCursorUp()
	{
		if(ParseTree.row_num != 0)
			ParseTree.row_num = this.parents.pollLast();
		
	}
	
	/**
	 * The method moveCursorToRoot moves the cursor to the root
	 */
	public void moveCursorToRoot()
	{
		ParseTree.row_num = this.parents.getFirst();
	}
	
	/**
	 * The method checks if the cursor is at Root
	 * @return True if the cursor is at the root, False if the cursor is not at the root
	 */
	public boolean isCursorAtRoot()
	{
		return ParseTree.row_num == 0;
	}
	
	/**
	 * Get the alternative number of the node
	 * 
	 * @return the alternative number
	 */
	public int getAlternativeNumber()
	{
		return this.alternatives.get(ParseTree.row_num);
	}
	
	/**
	 * The method Returns the String Representation of the non-terminal
	 * @return the non-terminal as a string
	 */
	public String getNonTerminal()
	{
		return this.non_terminals.get(ParseTree.row_num).toString();
	}
	
	
	/**
	 * Gets the id of the Node from the symbol table
	 * @return the string representation of the id
	 */
	public String getId()
	{
		return this.symbol_table.get(ParseTree.row_num);
	}
	
	/**
	 * Gets the constant of the Node from the symbol table
	 * @return the string representation of the constant
	 */
	public String getConstant()
	{
		return this.constants_table.get(ParseTree.row_num);
	}
	
	/**
	 * This method returns the index of the identifier for the symbol table described by a Hash Map
	 * @return the id index of the Id
	 */
	public int getIDindex()
	{
		
		/**
		 * loop until the values in the id_index list and token_value match the symbol_table 
		 * that has all the variables declared and stored
		 */
		int key = 0;
		
		for(int i = 0; i < this.id_index.size(); i++)
		{
			key = this.id_index.get(i);
			
			if(this.symbol_table.get(key).equals(this.scanner.getTokenValue()))
				break;
		}
	
		return key;
	}
	
	
	

}
