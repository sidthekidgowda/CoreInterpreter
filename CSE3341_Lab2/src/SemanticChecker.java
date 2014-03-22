/**
 * Class Semantic Checker makes semantic checks on the Parse Tree
 * 
 * This class makes sure all variables are declared before they are used.
 * Variables can't be declared more than once
 * 
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class SemanticChecker {

	private ParseTree pt;
	
	/**
	 * Creates the SemanticChecker object with a reference to the built ParseTree
	 * @param pt
	 */
	public SemanticChecker(ParseTree pt)
	{
		this.pt = pt;
	}
	
	/**
	 * checks and see if the ParseTree makes sense
	 */
	public void check()
	{
		
	}
}
