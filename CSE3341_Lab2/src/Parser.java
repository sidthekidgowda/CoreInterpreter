
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
	private ParseTree parse_tree = null;
	private static int nextRow;//stores the global variable for the next available row

	
	/**
	 * static constructor initializes the global variable nextRow
	 */
	static{
		Parser.nextRow = 0;
	}
	
	
	/**
	 * Constructor for the Parser
	 * Takes a Scanner reference so it can ask for Tokens
	 * @param Scanner s
	 */
	public Parser(Scanner s)
	{
		this.scanner = s;
		this.parse_tree = new ParseTree();
		//create the first token for the lookahead
		this.scanner.nextToken();
	}
	/**
	 * method makeParseTree creates a ParseTree object
	 */
	public void makeParseTree()
	{
		
		int store_value = this.program();
	}
	/**
	 * Main Method test
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}
	
	private int program()
	{
		int myRow = this.nextRow;
		nextRow++;
		this.parse_tree.addNonTerminal(NonTerminals.PROG);
		this.parse_tree.addAlternativeNumber(1);
		
		//match Program Token
		if(!this.scanner.matchToken(TokenType.PROGRAM))
			throw new IllegalArgumentException("Input does not have the word \"program\"");
	
	
		this.scanner.nextToken();
		
		this.parse_tree.addChildren(myRow, this.declSeq(), "non-terminal");
		
		//match BEGIN token
		if(!this.scanner.matchToken(TokenType.BEGIN))
			throw new IllegalArgumentException("Input does not have the word \"begin\"");
		
		
		this.parse_tree.addChildren(myRow, this.stmtSeq(), "non-terminal");
		
		//match the END token
		if(!this.scanner.matchToken(TokenType.END))
			throw new IllegalArgumentException("Input does not have the word \"end\"");
		
		this.scanner.nextToken();
		//match the EOF token
		if(!scanner.matchToken(TokenType.EOF))
			throw new IllegalArgumentException("EOF token not passed");
		
		return myRow;
	
	
	}
	private int declSeq()
	{
		int declRow = this.decl();
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.DECL_SEQ);
	
		
		if(this.scanner.getTokenType() == TokenType.BEGIN)
		{
			this.parse_tree.addAlternativeNumber(1);
		}
		if(this.scanner.getTokenType() == TokenType.INT)
		{
			this.parse_tree.addAlternativeNumber(2);
		}
		
		return 0;
	}
	private int decl()
	{
		
		//match INT token
		if(!this.scanner.matchToken(TokenType.INT))
			throw new IllegalArgumentException("Input does not have the word \"int\"");
		
		int id_list = this.idList();
		
		return id_list;
	}
	private int idList()
	{
		
		
		//match ID token
		if(!this.scanner.matchToken(TokenType.ID))
			throw new IllegalArgumentException("Input does not have an identifier in the DeclSeq");
		
		if(this.scanner.currentToken() == TokenType.COMMA)
		{
			int this.idList();
		}
		
	}
	private int stmtSeq()
	{
		return 0;
	}
	
	
	
	
	
	
	
}
