
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
	private static int ID_index;
	private static int Const_index;

	
	/**
	 * static constructor initializes the global variable nextRow
	 */
	static{
		Parser.nextRow = 0;
		Parser.ID_index = -1;
		Parser.Const_index = -2;
	}
	
	
	/**
	 * Constructor for the Parser
	 * Takes a Scanner reference so it can ask for Tokens
	 * @param Scanner s
	 */
	public Parser(Scanner s)
	{
		this.scanner = s;
		this.parse_tree = new ParseTree(this.scanner);
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
	public static int getParseRow()
	{
		return Parser.nextRow;
	}
	public static void decrementIDIndexBy2()
	{
		Parser.ID_index-=2;
	}
	public static void decrementConstIndexBy2()
	{
		Parser.Const_index-=2;
	}
	/**
	 * Main Method test
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}
	/**
	 * Parses the Program Method
	 * <prog> ::= program <decl-seq> begin <stmt-seq> end
	 * One Lookahead Token coming In
	 * @return the row number
	 */
	private int program()
	{
		int myRow = Parser.nextRow;
		nextRow++;
		this.parse_tree.addNonTerminal(myRow,NonTerminals.PROG);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		//match Program Token
		if(!this.scanner.matchToken(TokenType.PROGRAM))
			throw new IllegalArgumentException("Input does not have the word \"program\"");
	
	
		this.scanner.nextToken();
		int declRow = this.declSeq();
		
		this.parse_tree.addChildren(myRow, declRow, "non-terminal");
		
		//match BEGIN token
		if(!this.scanner.matchToken(TokenType.BEGIN))
			throw new IllegalArgumentException("Input does not have the word \"begin\"");
		
		this.scanner.nextToken();
		int stmtRow = this.stmtSeq();
		this.parse_tree.addChildren(myRow, stmtRow, "non-terminal");
		
		//match the END token
		if(!this.scanner.matchToken(TokenType.END))
			throw new IllegalArgumentException("Input does not have the word \"end\"");
		
		this.scanner.nextToken();
		//match the EOF token
		if(!scanner.matchToken(TokenType.EOF))
			throw new IllegalArgumentException("EOF token not passed");
		
		return myRow;
	
	
	}
	/**
	 * Parse the Decl Seq method
	 * <decl-seq> ::= <decl>|<decl><decl-seq>
	 * 
	 * One Lookahead Token coming in
	 * @return the row number
	 */
	private int declSeq()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
	
		this.parse_tree.addNonTerminal(myRow, NonTerminals.DECL_SEQ);
		int decl = this.decl();
		
		if(this.scanner.getTokenType() == TokenType.BEGIN)
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
			this.parse_tree.addChildren(myRow, decl, "non-terminal");
			
		}
		if(this.scanner.getTokenType() == TokenType.INT)
		{
			this.parse_tree.addAlternativeNumber(myRow, 2);
			
			int declSeq = declSeq();
			this.parse_tree.addChildren(myRow, declSeq, "non-terminal");
		
		}
		return myRow;
	}
	/**
	 * Parse the decl seq method
	 * <decl> ::= int <id-list>;
	 * 
	 * One Lookahead Token coming in
	 * @return the row number
	 */
	private int decl()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		//match INT token
		if(!this.scanner.matchToken(TokenType.INT))
			throw new IllegalArgumentException("Input does not have the word \"int\"");
		
		this.scanner.nextToken();

		this.parse_tree.addNonTerminal(myRow, NonTerminals.DECL);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		int id_list = this.idList();
		this.parse_tree.addChildren(myRow, id_list, "non-terminal");
		
		//match SemiColon Token
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Input does not have a semicolon after the ID List");
		
		this.scanner.nextToken();
		return myRow;
	}
	/**
	 * Parse the Id List Method
	 * <id-list> ::= id | id, <id-list>
	 * 
	 * One Lookahead Token coming in, One Lookahead Token coming out
	 * @return
	 */
	private int idList()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		//match ID token
		if(!this.scanner.matchToken(TokenType.ID))
			throw new IllegalArgumentException("Input does not have an identifier in the DeclSeq");
		
		this.parse_tree.addNonTerminal(myRow, NonTerminals.ID_LIST);
		this.scanner.nextToken();
		
		if(this.scanner.getTokenType() == TokenType.COMMA)
		{
			int id_list = this.idList();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChildren(myRow, id_list, "non-terminal");
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
			//pass in the ID
			this.parse_tree.addChildren(myRow, Parser.ID_index, "id");
		}
		return myRow;
		
	}
	/**
	 * 
	 * @return
	 */
	private int stmtSeq()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		int stmtSeq = this.stmt();
		
		return myRow;
	}
	
	private int stmt()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		
		
		return 0;
	}
	
	
	
	
	
	
	
}
