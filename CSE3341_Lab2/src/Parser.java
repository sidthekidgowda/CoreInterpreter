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
	/**
	 * This method decrements the Identifier index by 2
	 */
	public static void decrementIDIndexBy2()
	{
		Parser.ID_index-=2;
	}
	/**
	 * This method decrements the Constant index by 2
	 */
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
	 * 
	 * <prog> ::= program <decl-seq> begin <stmt-seq> end
	 * 
	 * One Lookahead Token coming in.
	 * 
	 * Incoming Token (PROGRAM)
	 * 
	 */
	private void program()
	{
		int myRow = Parser.nextRow;
		nextRow++;
		
		//match Program Token
		if(!this.scanner.matchToken(TokenType.PROGRAM))
			throw new IllegalArgumentException("Error: expecting the word \"program\"");
	
		this.parse_tree.addNonTerminal(NonTerminals.PROG);
		this.parse_tree.addAlternativeNumber(myRow, 1);
	
		this.scanner.nextToken();
		int declSeq = this.declSeq();
		
		this.parse_tree.addChild(myRow, declSeq, "non-terminal");
		
		//match BEGIN token
		if(!this.scanner.matchToken(TokenType.BEGIN))
			throw new IllegalArgumentException("Error: expecting the word \"begin\"");
		
		this.scanner.nextToken();
		int stmtSeq = this.stmtSeq();
		this.parse_tree.addChild(myRow, stmtSeq, "non-terminal");
		
		//match the END token
		if(!this.scanner.matchToken(TokenType.END))
			throw new IllegalArgumentException("Error: expecting the word \"end\"");
		
		this.scanner.nextToken();
		
		//match the EOF token
		if(!scanner.matchToken(TokenType.EOF))
			throw new IllegalArgumentException("Error: EOF token not passed");
		
	}
	/**
	 * Parse the Decl Seq method
	 * 
	 * <decl-seq> ::= <decl>|<decl><decl-seq>
	 * 
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token (INT)
	 * Outgoing Token (BEGIN or INT)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int declSeq()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
	
		this.parse_tree.addNonTerminal(NonTerminals.DECL_SEQ);
		int decl = this.decl();
		
		if(this.scanner.getTokenType() == TokenType.BEGIN)
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
			this.parse_tree.addChild(myRow, decl, "non-terminal");
			
		}
		else //TokenType is INT
		{
			this.parse_tree.addAlternativeNumber(myRow, 2);
			int declSeq = declSeq();
			this.parse_tree.addChild(myRow, declSeq, "non-terminal");
		
		}
		return myRow;
	}
	/**
	 * Parse the decl seq method
	 * <decl> ::= int <id-list>;
	 * 
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(INT)
	 * Outgoing Token (BEGIN or INT)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int decl()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		//match INT token
		if(!this.scanner.matchToken(TokenType.INT))
			throw new IllegalArgumentException("Error: expecting the word \"int\"");
		
		this.scanner.nextToken();

		this.parse_tree.addNonTerminal(NonTerminals.DECL);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		int id_list = this.idList();
		this.parse_tree.addChild(myRow, id_list, "non-terminal");
		
		//match SemiColon Token
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
		
		this.scanner.nextToken();
		return myRow;
	}
	/**
	 * Parse the Id List Method
	 * 
	 * <id-list> ::= id | id, <id-list>
	 * 
	 * One Lookahead Token coming in, One Lookahead going coming out
	 * 
	 * Incoming Token(ID)
	 * Outgoing Token(BEGIN)
	 * 
	 * @return myRow
	 */
	private int idList()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		//match ID token
		if(!this.scanner.matchToken(TokenType.ID))
			throw new IllegalArgumentException("Error: expecting an identifier");
		
		this.parse_tree.addNonTerminal(NonTerminals.ID_LIST);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		this.parse_tree.addChild(myRow, Parser.ID_index, "id");
		
		this.scanner.nextToken();
		
		if(this.scanner.getTokenType() == TokenType.COMMA)
		{
			//call next Token
			this.scanner.nextToken();
			int id_list = this.idList();
			//change the alternative number to the second alternative
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, id_list, "non-terminal");
		}
		return myRow;
		
	}
	/**
	 * Parse the StmtSeq method
	 * 
	 * <stmt-seq> ::= <stmt><stmt-seq>
	 * One Lookahead Token coming in, One Lookahead going out
	 * 
	 * Incoming Token(ID or INPUT or OUTPUT or IF or DO or CASE)
	 * Outgoing Token(END)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int stmtSeq()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.STMT_SEQ);
		int stmt = this.stmt();
		this.parse_tree.addChild(myRow, stmt, "non-terminal");
		
		if(this.scanner.getTokenType() == TokenType.END)
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		else // recursive descent again
		{
			int stmt_seq = this.stmtSeq();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, stmt_seq, "non-terminal");
		}
		
		return myRow;
	}
	/**
	 * Parse the stmt() method
	 * 
	 * <stmt> ::= <assign>|<if>|<loop>|<in>|<out>
	 * One Lookahead token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(ID or INPUT or OUTPUT or IF or DO or CASE)
	 * Outgoing Token(END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	
	private int stmt()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.STMT);
		
		if(this.scanner.getTokenType() == TokenType.ID)
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
			int assign = this.assign();
			this.parse_tree.addChild(myRow, assign, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.IF)
		{
			this.parse_tree.addAlternativeNumber(myRow, 2);
			int if_row = this.if_parse();
			this.parse_tree.addChild(myRow, if_row, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.DO)
		{
			this.parse_tree.addAlternativeNumber(myRow,3);
			int do_while = this.do_while();
			this.parse_tree.addChild(myRow, do_while, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.INPUT)
		{
			this.parse_tree.addAlternativeNumber(myRow,4);
			int in = this.in();
			this.parse_tree.addChild(myRow, in, "non-terminal");
			
		}
		else if(this.scanner.getTokenType() == TokenType.OUTPUT)
		{
			this.parse_tree.addAlternativeNumber(myRow, 4);
			int out = this.out();
			this.parse_tree.addChild(myRow, out, "non-terminal");
		}
		else //Case statement
		{
			this.parse_tree.addAlternativeNumber(myRow, 5);
			int case_stmt = this.case_stmt();
			this.parse_tree.addChild(myRow, case_stmt, "non-terminal");
		}
		
		return myRow;
	}
	
	/**
	 * Parse the assign statement
	 * 
	 * <assign> ::= id := <expr>;
	 * 
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(ID)
	 * Outgoing Token(END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int assign()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		if(!this.scanner.matchToken(TokenType.ID))
			throw new IllegalArgumentException("Error: expecting an indentifier");
		
		this.scanner.nextToken();
		
		if(!this.scanner.matchToken(TokenType.ASSIGN))
			throw new IllegalArgumentException("Error: expecting an assignment sign \":=\"");
		
		this.scanner.nextToken();
		
		this.parse_tree.addNonTerminal(NonTerminals.ASSIGN);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		int expr = this.expr();
		this.parse_tree.addChild(myRow, expr, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
		
		this.scanner.nextToken();
		return myRow;
	}
	/**
	 * Parse the if statement
	 *
	 * <if> ::= if<cond> then <stmt-seq> endif; 
	 * 			| if <cond> then <stmt-seq> else <stmt-seq> endif;
	 * 
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(IF)
	 * Outgoing Token (END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int if_parse()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;

		if(!this.scanner.matchToken(TokenType.IF))
			throw new IllegalArgumentException("Error: expecting the word \"if\"");
	
		this.scanner.nextToken();
		
		this.parse_tree.addNonTerminal(NonTerminals.IF);
		int cond = this.cond();
		this.parse_tree.addChild(myRow, cond, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.THEN))
			throw new IllegalArgumentException("Error: expecting the word \"then\"");
		
		this.scanner.nextToken();
		int then_stmt = this.stmtSeq();
		this.parse_tree.addChild(myRow, then_stmt, "non-terminal");
		
		
		if(this.scanner.getTokenType() == TokenType.ELSE)
		{
			//match else
			if(!this.scanner.matchToken(TokenType.ELSE))
				throw new IllegalArgumentException("Error: expecting the word \"else\"");
			
			this.scanner.nextToken();
			int else_stmt = this.stmtSeq();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, else_stmt, "non-terminal");
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		
		if(!this.scanner.matchToken(TokenType.ENDIF))
			throw new IllegalArgumentException("Error: expecting the word \"endif\"");
		
		this.scanner.nextToken();
		
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
	
		this.scanner.nextToken();
		return myRow;
	}
	
	/**
	 * Parse the do_while statement
	 * 
	 * <do-while> ::= do <stmt-seq> while <cond> enddo;
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(DO)
	 * Outgoing Token(END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int do_while()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		if(!this.scanner.matchToken(TokenType.DO))
			throw new IllegalArgumentException("Error: expecting the word\"do\"");
		
		this.scanner.nextToken();
		
		this.parse_tree.addNonTerminal(NonTerminals.LOOP);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		int stmtSeq = this.stmtSeq();
		this.parse_tree.addChild(myRow, stmtSeq, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.WHILE))
			throw new IllegalArgumentException("Error: expecting the word \"while\"");
		
		this.scanner.nextToken();
		int cond = this.cond();
		
		this.parse_tree.addChild(myRow, cond, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.ENDDO))
			throw new IllegalArgumentException("Error: expecting the word \"enddo\"");
		
		this.scanner.nextToken();
		
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
		
		this.scanner.nextToken();
		
		return myRow;
	}
	
	/**
	 * Parse the in statement
	 * 
	 * <in> ::= input <id-list>;
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(INPUT)
	 * Outgoing Token(END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int in()
	{
		
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		if(!this.scanner.matchToken(TokenType.INPUT))
			throw new IllegalArgumentException("Error: expecting the word \"input\"");
		
		this.parse_tree.addNonTerminal(NonTerminals.IN);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		this.scanner.nextToken();
		int id_list = this.idList();
		
		this.parse_tree.addChild(myRow, id_list, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
		
		this.scanner.nextToken();
		
		return myRow;
	}
	
	/**
	 * Parse the out statement
	 * 
	 * <out> ::= output <id-list>;
	 * 
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(OUTPUT)
	 * Outgoing Token(END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 *@return the row number of the Parse Tree table
	 */
	private int out()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		if(!this.scanner.matchToken(TokenType.OUTPUT))
			throw new IllegalArgumentException("Error: expecting the word \"output\"");
		
		this.scanner.nextToken();
		
		this.parse_tree.addNonTerminal(NonTerminals.OUT);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		
		int id_list = this.idList();
		
		this.parse_tree.addChild(myRow, id_list, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
		
		this.scanner.nextToken();
		
		return myRow;
	}
	
	/**
	 * Parse the case statement
	 * 
	 * <case> ::= case id of <list-block> else <expr> end;
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(CASE)
	 * Outgoing Token(END or ID or INPUT or OUTPUT or IF or DO or CASE)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	
	private int case_stmt()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		if(!this.scanner.matchToken(TokenType.CASE))
			throw new IllegalArgumentException("Error: expecting the word \"case\"");
		
		this.scanner.nextToken();
		
		if(!this.scanner.matchToken(TokenType.ID))
			throw new IllegalArgumentException("Error: expecting an identifier");
		
		this.parse_tree.addNonTerminal(NonTerminals.CASE);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		this.parse_tree.addChild(myRow, Parser.ID_index, "id");
		
		this.scanner.nextToken();
		int list_block = this.list_block();
		this.parse_tree.addChild(myRow, list_block, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.ELSE))
			throw new IllegalArgumentException("Error: expecting the word \"else\"");
		
		this.scanner.nextToken();
		int expr = this.expr();
		this.parse_tree.addChild(myRow, expr, "non-terminal");
	
		
		if(!this.scanner.matchToken(TokenType.SEMICOLON))
			throw new IllegalArgumentException("Error: expecting a semicolon \";\"");
		
		this.scanner.nextToken();
		
		return myRow;
	}
	
	/**
	 * Parse the list block inside the Case Statement
	 * 
	 * <list-block> ::= <list>COLON<expr> | <list>COLON <expr> BAR <list-block>
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token(CONSTANT)
	 * Outgoing Token(ELSE or BAR)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	
	private int list_block()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.LIST_BLOCK);
		int list = this.list();
		this.parse_tree.addChild(myRow, list, "non-terminal");
		
		if(!this.scanner.matchToken(TokenType.COLON))
			throw new IllegalArgumentException("Error: expecting a colon \":\"");
		
		this.scanner.nextToken();
		int expr = this.expr();
		
		this.parse_tree.addChild(myRow, expr, "non-terminal");
		
		if(this.scanner.getTokenType() == TokenType.BAR)
		{
			//match the bar
			if(!this.scanner.matchToken(TokenType.BAR))
				throw new IllegalArgumentException("Error: expecting a bar \"|\"");
		
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.scanner.nextToken();
			int list_block = this.list_block();
			this.parse_tree.addChild(myRow, list_block, "non-terminal");
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		
		return myRow;
	}
	/**
	 * Parse the list statement part of the Case statement
	 * 
	 * <list> ::= const, <list>| const
	 * 
	 * One Lookahead token coming in, One Lookahead token going out
	 * 
	 * Incoming Token(CONSTANT)
	 * Outgoing Token(COLON)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int list()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		if(!this.scanner.matchToken(TokenType.CONSTANT))
			throw new IllegalArgumentException("Error: expecting a constant");
		
		this.parse_tree.addNonTerminal(NonTerminals.LIST);
		this.parse_tree.addChild(myRow, Parser.Const_index, "constant");
		
		this.scanner.nextToken();
		
		if(this.scanner.getTokenType() == TokenType.COMMA)
		{
			//match Comma
			if(!this.scanner.matchToken(TokenType.COMMA))
				throw new IllegalArgumentException("Error: expecting a comma \";\"");
			
			this.scanner.nextToken();
			int list = this.list();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, list, "non-terminal");
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		
		return myRow;
	} 
	
	/**
	 * Parse an expression
	 * 
	 * <expr> ::= <term> | <term> + <expr> | <term> - <expr>
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token (CONSTANT or ID or MINUS or LEFT_PAREN)
	 * Outgoing Token (SEMICOLON or < or = or != or > or >= or <= or RIGHT_BRACKET)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int expr()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.EXPR);
		int term = this.term();
		this.parse_tree.addChild(myRow, term, "non-terminal");
		
		
		if(this.scanner.getTokenType() == TokenType.ADD_OP)
		{
			//match add op
			if(!this.scanner.matchToken(TokenType.ADD_OP))
				throw new IllegalArgumentException("Error: expecting a add operation \"+\"");
			
			this.scanner.nextToken();
			
			int expr = this.expr();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, expr, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.SUBT_OP)
		{
			//match subtract op
			if(!this.scanner.matchToken(TokenType.SUBT_OP))
				throw new IllegalArgumentException("Error: expecting a subtraction operation \"-\"");
			
			this.scanner.nextToken();
			int expr = this.expr();
			this.parse_tree.addAlternativeNumber(myRow, 3);
			this.parse_tree.addChild(myRow, expr, "non-terminal");
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		return myRow;
	}
	
	/**
	 * Parse the conditional statement
	 * 
	 * <cond> ::= <cmpr> | !<cond> | ( <cond> AND <cond> ) | ( <cond> OR <cond> )
	 * One Lookahead Token coming in, One Lookahead Token going out
	 *  
	 * Incoming Token( [ or ! or ( )
	 * Outgoing Token(ENDDO)
	 *  
	 * @return the row number of the Parse Tree table
	 */
	private int cond()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.COND);
		
		if(this.scanner.getTokenType() == TokenType.LBRACKET)
		{
			int cmpr = this.cmpr();
			this.parse_tree.addAlternativeNumber(myRow, 1);
			this.parse_tree.addChild(myRow, cmpr, "non-terminal");
		}
		else if(this.scanner.getTokenType() == TokenType.NOT)
		{
			//match not operator
			if(!this.scanner.matchToken(TokenType.NOT))
				throw new IllegalArgumentException("Error: expecting the not operator \"!\"");
			
			this.scanner.nextToken();
			int cond = this.cond();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, cond, "non-terminal");

		}
		else //Token is ( or LEFT_PAREN
		{
			//match left parenthesis
			if(!this.scanner.matchToken(TokenType.LPAREN))
				throw new IllegalArgumentException("Error: expecting a left parenthesis \"(\"");
			
			this.scanner.nextToken();
			int cond = this.cond();
			this.parse_tree.addChild(myRow, cond, "non-terminal");
			if(this.scanner.getTokenType() == TokenType.AND)
			{
				//match and
				if(!this.scanner.matchToken(TokenType.AND))
					throw new IllegalArgumentException("Error: expecting the keyword \"AND\"");
				
				this.parse_tree.addAlternativeNumber(myRow, 3);
			}
			else if(this.scanner.getTokenType() == TokenType.OR)
			{
				//match or
				if(!this.scanner.matchToken(TokenType.OR))
					throw new IllegalArgumentException("Error: expecting the keyword \"AND\"");
				this.parse_tree.addAlternativeNumber(myRow, 4);
			}
			
			this.scanner.nextToken();
			cond = this.cond();
			this.parse_tree.addChild(myRow, cond, "non-terminal");
			
			//match right parenthesis
			if(!this.scanner.matchToken(TokenType.RPAREN))
				throw new IllegalArgumentException("Error: expecting a right parenthesis \")\"");
			
			this.scanner.nextToken();
		}
		
		return myRow;
	}
	/**
	 * Parse the comparison statement inside the conditional statement
	 * 
	 * <cmpr> ::= [ <expr><cmpr-op><expr> ]
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token([)
	 * Outgoing Token()
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int cmpr()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		//match Left Bracket
		if(!this.scanner.matchToken(TokenType.LBRACKET))
			throw new IllegalArgumentException("Error: Left Bracket expected \"[\"");
		
		this.scanner.nextToken();
		this.parse_tree.addNonTerminal(NonTerminals.CMPR);
		this.parse_tree.addAlternativeNumber(myRow, 1);
		int expr = this.expr();
		this.parse_tree.addChild(myRow, expr, "non-terminal");
		
		
		int cmpr_op = this.cmpr_op();
		this.parse_tree.addChild(myRow, cmpr_op, "non-terminal");
		
		expr = this.expr();
		this.parse_tree.addChild(myRow, expr, "non-terminal");
		
		//match Right Bracket
		if(!this.scanner.matchToken(TokenType.RBRACKET))
			throw new IllegalArgumentException("Error: Right Bracket expected \"]\"");
		
		this.scanner.nextToken();
		
		return myRow;
	}
	
	/**
	 * Parse the comparsion operation statement inside the comparison statement
	 * 
	 * <cmpr-op> ::= < | = | != | > | >= | <=
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * 
	 * Incoming Token (< or = or != or > or >= or <=)
	 * Outgoing Token (CONST or ID or - or LEFT_PAREN)
	 * 
	 * @return the row number of the Parse Tree table
	 */
	private int cmpr_op()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		this.parse_tree.addNonTerminal(NonTerminals.CMPR_OP);
		
		if(this.scanner.getTokenType() == TokenType.LESS_THAN)
		{
			//match less than
			if(!this.scanner.matchToken(TokenType.LESS_THAN))
				throw new IllegalArgumentException("Error: Less than symbol expected \"<\"");
			
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		else if(this.scanner.getTokenType() == TokenType.EQUAL)
		{
			//match equal sign
			if(!this.scanner.matchToken(TokenType.EQUAL))
				throw new IllegalArgumentException("Error: Equal sign expected \"=\"");
			
			this.parse_tree.addAlternativeNumber(myRow, 2);
		}
		else if(this.scanner.getTokenType() == TokenType.NOT_EQUAL)
		{
			//match not equal
			if(!this.scanner.matchToken(TokenType.NOT_EQUAL))
				throw new IllegalArgumentException("Error: Not Equal to sign expected \"!=\"");
			
			this.parse_tree.addAlternativeNumber(myRow, 3);
		}
		else if(this.scanner.getTokenType() == TokenType.GREATER_THAN)
		{
			//match greater than
			if(!this.scanner.matchToken(TokenType.GREATER_THAN))
				throw new IllegalArgumentException("Error: Less than symbol expected \"<\"");
			
			this.parse_tree.addAlternativeNumber(myRow, 4);
		}
		else if(this.scanner.getTokenType() == TokenType.GREATER_OR_EQ_TO)
		{
			//match less than
			if(!this.scanner.matchToken(TokenType.GREATER_OR_EQ_TO))
				throw new IllegalArgumentException("Error: Less than symbol expected \"<\"");
			
			this.parse_tree.addAlternativeNumber(myRow, 5);
		}
		else //<=
		{
			//match less than
			if(!this.scanner.matchToken(TokenType.LESS_THAN_OR_EQ_TO))
				throw new IllegalArgumentException("Error: Less than or equal to symbol expected \"<=\"");
			
			this.parse_tree.addAlternativeNumber(myRow, 6);
		}
		
		return myRow;
	}
	
	/**
	 * Parse the term in the expression
	 * 
	 * <term> ::= <factor> | <factor> * <term>
	 * One Lookahead token coming in, One Lookahead token going out
	 * 
	 * Incoming Token (CONSTANT or ID or MINUS or LEFT_PAREN)
	 * Outgoing Token (SEMICOLON or < or = or != or > or >= or <= or RIGHT_BRACKET)
	 * 
	 * 
	 * @return
	 */
	private int term()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		
		this.parse_tree.addNonTerminal(NonTerminals.TERM);
		
		int factor = this.factor();
		
		this.parse_tree.addChild(myRow, factor, "non-terminal");
		
		if(this.scanner.getTokenType() == TokenType.MULT_OP)
		{
			//match mult operation
			if(!this.scanner.matchToken(TokenType.MULT_OP))
				throw new IllegalArgumentException("Error: expecting a multiplication operation \"*\"");
			
			this.scanner.nextToken();
			int term = this.term();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, term, "non-terminal");
			
		}
		else
		{
			this.parse_tree.addAlternativeNumber(myRow, 1);
		}
		
		return myRow;
	}
	/**
	 * Parse the factor in the term
	 * 
	 * <factor> ::= const | id | - <factor> | (<expr>)
	 * 
	 * One Lookahead Token coming in, One Lookahead Token going out
	 * Incoming Token(CONSTANT, ID, -, RIGHT_PAREN)
	 * Outgoing Token(SEMICOLON, <, = , != , >, >=, <=, RIGHT_BRACKET)
	 * 
	 * @return
	 */
	private int factor()
	{
		int myRow = Parser.nextRow;
		Parser.nextRow++;
		this.parse_tree.addNonTerminal(NonTerminals.FACTOR);
		
		if(this.scanner.getTokenType() == TokenType.CONSTANT)
		{
			//match constant
			if(!this.scanner.matchToken(TokenType.CONSTANT))
				throw new IllegalArgumentException("Error: expecting a constant");
			
			this.scanner.nextToken();
			this.parse_tree.addAlternativeNumber(myRow, 1);
			this.parse_tree.addChild(myRow, Parser.Const_index, "constant");
			
		}
		else if(this.scanner.getTokenType() == TokenType.ID)
		{
			//match id
			if(!this.scanner.matchToken(TokenType.ID))
				throw new IllegalArgumentException("Error: expecting an identifier");
			
			this.scanner.nextToken();
			this.parse_tree.addAlternativeNumber(myRow, 2);
			this.parse_tree.addChild(myRow, Parser.ID_index, "identifier");
		}
		else if(this.scanner.getTokenType() == TokenType.SUBT_OP)
		{
			//match minus op
			if(!this.scanner.matchToken(TokenType.SUBT_OP))
				throw new IllegalArgumentException("Error: expecting a minus \"-\"");
			
			this.scanner.nextToken();
			
			int factor = this.factor();
			
			this.parse_tree.addAlternativeNumber(myRow, 3);
			this.parse_tree.addChild(myRow, factor, "non-terminal");
		}
		else //Left Parenthesis
		{
			//match left parenthesis
			if(!this.scanner.matchToken(TokenType.LPAREN))
				throw new IllegalArgumentException("Error: expecting a left parenthesis \"(\"");
			
			this.scanner.nextToken();
			
			int expr = this.expr();
			
			this.parse_tree.addAlternativeNumber(myRow, 4);
			this.parse_tree.addChild(myRow, expr, "non-terminal");
			
			//match right parenthesis
			if(!this.scanner.matchToken(TokenType.RPAREN))
				throw new IllegalArgumentException("Error: expecting a right parenthesis \")\"");
			
			this.scanner.nextToken();
		}
		
		return myRow;
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
