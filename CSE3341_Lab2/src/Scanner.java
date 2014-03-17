/**
 * 
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class Scanner {

	private TokenMaker token;
	
	public enum TokenType
	{
		PROGRAM, BEGIN, END, INT, ID, CONST, SEMICOL, COMMA, ASSIGN, INPUT, OUTPUT, IF, THEN, ENDIF, DO, WHILE, ENDDO, AND,
		OR, COLON, BAR, ADD_OP, SUBT_OP, MULT_OP, GREATER_THAN, GREATER_OR_EQ_TO, EQUAL, NOT, NOT_EQUAL, LESS_THAN, LESS_THAN_OR_EQ_TO,
		LPAREN, RPAREN, LBRACKET, RBRACKET  	
	}
	
	public Scanner()
	{
		
	}
	
	private class TokenMaker
	{
		private TokenType t;
		private String value;
		
		private TokenMaker()
		{
			
		}
	}
	
	
	
	
	
}
