import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;


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
	private static int ID_index; //used to reference ID symbol table
	private static int Const_index;//used to reference Constant symbol table

	
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
	 * method makeParseTree creates a ParseTree object
	 */
	public void makeParseTree()
	{
		this.program();
	}
	
	/**
	 * Parses the Program Method
	 * <prog> ::= program <decl-seq> begin <stmt-seq> end
	 * One Lookahead Token coming in.
	 * Incoming Token (PROGRAM)
	 * 
	 */
	private void program()
	{
		int myRow = Parser.nextRow;
		nextRow++;
		this.parse_tree.addNonTerminal(myRow,NonTerminals.PROG);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		//match Program Token
		if(!this.scanner.matchToken(TokenType.PROGRAM))
			throw new IllegalArgumentException("Input does not have the word \"program\"");
	
	
		this.scanner.nextToken();
		int declSeq = this.declSeq();
		
		this.parse_tree.addChildren(myRow, declSeq, "non-terminal");
		/*
		//match BEGIN token
		if(!this.scanner.matchToken(TokenType.BEGIN))
			throw new IllegalArgumentException("Input does not have the word \"begin\"");
		
		this.scanner.nextToken();
		int stmtSeq = this.stmtSeq();
		this.parse_tree.addChildren(myRow, stmtSeq, "non-terminal");
		
		//match the END token
		if(!this.scanner.matchToken(TokenType.END))
			throw new IllegalArgumentException("Input does not have the word \"end\"");
		
		this.scanner.nextToken();
		//match the EOF token
		if(!scanner.matchToken(TokenType.EOF))
			throw new IllegalArgumentException("EOF token not passed");
		*/
	}
	/**
	 * Parse the Decl Seq method
	 * <decl-seq> ::= <decl>|<decl><decl-seq>
	 * 
	 * One Lookahead Token coming in, One Lookahead Token coming out
	 * Incoming Token (INT), Outgoing Token (BEGIN or INT)
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
	 * One Lookahead Token coming in, One Lookahead Token coming out
	 * Incoming Token(INT), Outgoing Token (BEGIN or INT)
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
	 * Incoming Token(ID), Outgoing Token(BEGIN)
	 * @return myRow
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
			//call next Token
			this.scanner.nextToken();
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
	 * Parse the StmtSeq method
	 * <stmt-seq> ::= <stmt><stmt-seq>
	 * One Lookahead Token coming in, One Lookahead Token coming out
	 * @return myRow
	 */
	private int stmtSeq()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(myRow, NonTerminals.STMT_SEQ);
		int stmt = this.stmt();
		
		if(this.scanner.getTokenType() == TokenType.END)
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
			this.parse_tree.addChildren(myRow, stmt, "non-terminal");
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 2);
			int stmtSeq = this.stmtSeq();
			this.parse_tree.addChildren(myRow, stmtSeq, "non-terminal");
		}
		
		return myRow;
	}
	/**
	 * Parse the stmt() method
	 * <stmt> ::= <assign>|<if>|<loop>|<in>|<out>
	 * One Lookahead token coming in, One Lookahead Token coming out
	 * @return my Row
	 */
	
	private int stmt()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(myRow, NonTerminals.STMT);
		if(this.scanner.getTokenType() == TokenType.ID)
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
			int assign = this.assign();
			this.parse_tree.addChildren(myRow, assign, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.IF)
		{
			this.parse_tree.addAlternativeNumber(myRow, 2);
			int if_row = this.if_parse();
			this.parse_tree.addChildren(myRow, if_row, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.DO)
		{
			this.parse_tree.addAlternativeNumber(myRow,3);
			int do_while = this.do_while();
			this.parse_tree.addChildren(myRow, do_while, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.INPUT)
		{
			this.parse_tree.addAlternativeNumber(myRow,4);
			int in = this.in();
			this.parse_tree.addChildren(myRow, in, "non-terminal");
			
		}
		else if(this.scanner.getTokenType() == TokenType.OUTPUT)
		{
			this.parse_tree.addAlternativeNumber(myRow, 4);
			int out = this.out();
			this.parse_tree.addChildren(myRow, out, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.CASE)
		{
			this.parse_tree.addAlternativeNumber(myRow, 5);
			int case_stmt = this.case_stmt();
			this.parse_tree.addChildren(myRow, case_stmt, "non-terminal");
		}
		else
		{
			//error case
			throw new IllegalArgumentException("Illegal stmt word \""+this.scanner.getTokenValue()+"\"");
		}
		
		return myRow;
	}
	
	/**
	 * Parse the assign statement
	 * <assign> ::= id:=<expr>;
	 * 
	 * One Lookahead Token coming in, One Lookahead Token coming out
	 * @return myRow
	 */
	private int assign()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(myRow, NonTerminals.ASSIGN);
		
		return myRow;
	}
	/**
	 * Parse the if statement
	 * <if> ::= if<cond> then <stmt-seq> endif; 
	 * 			| if <cond> then <stmt-seq> else <stmt-seq> endif;
	 * 
	 * One Lookahead Token coming in, One Lookeahead Token coming out
	 * @return myRow
	 */
	private int if_parse()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(myRow, NonTerminals.IF);
		return myRow;
	}
	
	/**
	 * Parse the do_while statement
	 * <do-while> ::= do <stmt-seq> while <cond> enddo;
	 * One Lookahead Token coming in, One Lookahead Token coming out
	 * @return myRow
	 */
	private int do_while()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		return myRow;
	}
	private int in()
	{
		return 0;
	}
	private int out()
	{
		return 0;
	}
	private int case_stmt()
	{
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Main Method test
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		Parser p1 = new Parser(new Scanner(new BufferedReader(new StringReader("program int x, y, xy; begin"))));
		p1.makeParseTree();
	}
	
	
	
	
	
	
}
