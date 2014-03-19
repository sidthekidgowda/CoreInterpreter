import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author Sid
 *
 */
public class ParseTree {

	private List<String> non_terminals = null;
	private List<Integer> children = null;
	private List<Integer> alternatives = null;
	
	/**
	 * Constructor creates the abstract Parse Tree Representation
	 */
	public ParseTree()
	{
		this.non_terminals = new ArrayList<String>();
		this.children = new ArrayList<Integer>();
		this.alternatives = new ArrayList<Integer>();
	}
	
	/**
	 * add A NonTerminal String type
	 */
	public void addNonTerminal()
	{
		
	}
	/**
	 * Add Children assoicated to the Grammar
	 */
	public void addChildren()
	{
		
	}
	/**
	 * 
	 */
	public void addAlternativeRuleNumber()
	{
		
	}
	
}
