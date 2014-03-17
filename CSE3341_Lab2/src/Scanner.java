import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class Scanner is called by the parser and creates a Token
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class Scanner {
	
	private Token token;
	private BufferedReader program = null;
	private StringBuffer input = null;
	
	
	/**
	 * Enumerated Token Types of the Core Language  
	 * @author Sid
	 *
	 */
	public enum TokenType
	{
		PROGRAM, BEGIN, END, INT, ID, CONST, SEMICOL, COMMA, ASSIGN, INPUT, OUTPUT, IF, THEN, ENDIF, DO, WHILE, ENDDO, AND,
		OR, COLON, BAR, ADD_OP, SUBT_OP, MULT_OP, GREATER_THAN, GREATER_OR_EQ_TO, EQUAL, NOT, NOT_EQUAL, LESS_THAN, LESS_THAN_OR_EQ_TO,
		LPAREN, RPAREN, LBRACKET, RBRACKET, OF
	}
	/**
	 * Constructor for the Scanner class
	 * 
	 * @param program is the incoming program coming in from the CoreInterpreter
	 * @throws IOException
	 */
	public Scanner(BufferedReader program)
	{
		//defensive copying
		this.program = new BufferedReader(program);
		
		/*
		 * Move all input into one line
		 */
		String read_line = null;
		this.input = new StringBuffer();
		try{
			while((read_line = this.program.readLine())!=null)
			{
				this.input.append(read_line);
			}
			
			//Test and see if everything is printed on one line
			
		}
		catch (IOException e)
		{
			System.out.println("General IO Exception: " + e.getMessage());
		}
	}
	
	//Test and see if everything is printed on one line
	public void printString()
	{
		System.out.println(this.input.toString());
		System.out.println();
	}
	
	/**
	 * This method creates the Token the
	 */
	private void createToken()
	{
		
	}
	
	public Token getToken()
	{
		Token t = null;
		
		return t;
	}
	
	
	
	/**
	 * nested Class Token is used to create a Token
	 * @author Sid Gowda
	 * 
	 *
	 */
	
	private class Token
	{
		private TokenType t;
		private String token_value;
		
		private Token()
		{
			
		}
	}
	
	
	
	
	
}
