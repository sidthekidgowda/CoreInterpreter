
/**
 * Class Parser creates a Parse Tree with a given Scanner to create Tokens
 * 
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class Parser 
{
	private Scanner scanner = null;
	private ParseTree pt = null;
	
	
	/**
	 * Constructor for the Parser
	 * Takes a Scanner reference so it can ask for Tokens
	 * @param s
	 */
	public Parser(Scanner s)
	{
		this.scanner = s;
		this.pt = new ParseTree();
	}
	/**
	 * method makeParseTree creates a ParseTree object
	 */
	public void makeParseTree()
	{
		Node p = new Program();
		p.parse();
	}
	
	/**
	 * Private Class Program performs a
	 * @author Sid Gowda
	 * 
	 *
	 */
	private class Program implements Node
	{
		
		private Node decl_seq = null;
		private Node stmt_seq = null;
		private int alternative = 1;
		
		
		/**
		 * Default Constructor
		 */
		private Program()
		{
			
		}
		/**
		 * Parse the Program non-terminal and store in the Parse Tree
		 */
		public void parse()
		{
			
			if(!scanner.matchToken(TokenType.PROGRAM))
				throw new IllegalArgumentException("Input does not have PROGRAM or PROGRAM is formatted incorrectly");
			
			this.decl_seq = new DeclSeq();
			this.decl_seq.parse();
			
			//move the scanner to the next token
			scanner.nextToken();
			if(!scanner.matchToken(TokenType.BEGIN))
				throw new IllegalArgumentException("Input does not have BEGIN or BEGIN is formatted incorrectly");
			
			this.stmt_seq = new StmtSeq();
			this.stmt_seq.parse();
			
			scanner.nextToken();
			if(!scanner.matchToken(TokenType.END))
				throw new IllegalArgumentException("Input does not have END or END is formatted incorrectly");
			
			scanner.nextToken();
			if(!scanner.matchToken(TokenType.EOF))
				throw new IllegalArgumentException();
			
		}
	}
	private class DeclSeq implements Node
	{
		private DeclSeq()
		{
			
		}
		public void parse()
		{
			
		}
	}
	private class StmtSeq implements Node
	{
		private StmtSeq()
		{
			
		}
		public void parse()
		{
			
		}
	}
	private class Stmt implements Node
	{
		private Stmt()
		{
			
		}
		public void parse()
		{
			
		}
	}
}
