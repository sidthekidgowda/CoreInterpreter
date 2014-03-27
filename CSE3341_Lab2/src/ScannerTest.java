import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;


public class ScannerTest {

	@Test
	public void testScanner() {
		
		boolean flag = false;
		
		Scanner s = null;
		
		String good_string = "program\n\nint x; begin x:= 0; end";
		
		//good token - program
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			flag = s.getTokenType() == TokenType.PROGRAM;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - int
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.INT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - x
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.ID;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - semicolon
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.SEMICOLON;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - begin
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.BEGIN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - x
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.ID;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - assign :=
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.ASSIGN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - 0
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.CONSTANT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - semicolon
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.SEMICOLON;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - end
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.END;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - EOF
		try
		{
			s = new Scanner(new BufferedReader(new StringReader(good_string)));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.EOF;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - int
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.INT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - input
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program input")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.INPUT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - output
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; output")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();
			flag = s.getTokenType() == TokenType.OUTPUT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - if
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; if")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();
			flag = s.getTokenType() == TokenType.IF;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - then
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; if then")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();//if
			s.nextToken();
			flag = s.getTokenType() == TokenType.THEN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - else
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; if then else")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();//if
			s.nextToken();//then
			s.nextToken();//else
			flag = s.getTokenType() == TokenType.ELSE;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		
		//good token - endif
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; if then endif")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();//if
			s.nextToken();//then
			s.nextToken();//endif;
			flag = s.getTokenType() == TokenType.ENDIF;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - do
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; do x:= while")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();
			flag = s.getTokenType() == TokenType.DO;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - while
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; do while")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();//do
			s.nextToken();//while
			flag = s.getTokenType() == TokenType.WHILE;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - ! not
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x; !x ")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//;
			s.nextToken();//do
			flag = s.getTokenType() == TokenType.NOT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		
		assertTrue(flag);
		
		//good token - Left Parenthesis
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program (x)")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.LPAREN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		
		assertTrue(flag);
		
		//good token - Right Parenthesis
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program (x)")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.RPAREN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		
		assertTrue(flag);
		
		//good token - Left Bracket
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program [x]")));
			s.currentToken();
			s.nextToken();
			
			flag = s.getTokenType() == TokenType.LBRACKET;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - Right Bracket
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program [x]")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.RBRACKET;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - <
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x < - 10")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//<
			flag = s.getTokenType() == TokenType.LESS_THAN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - <=
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x <= 10")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.LESS_THAN_OR_EQ_TO;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - =
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x = 10")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.EQUAL;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		
		//good token !=
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x != 10")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.NOT_EQUAL;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token >
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x > 10")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.GREATER_THAN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token >=
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x >= 10")));
			s.currentToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.GREATER_OR_EQ_TO;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token +
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x:= 5 + 10")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//:=
			s.nextToken();//5
			s.nextToken();//+
			flag = s.getTokenType() == TokenType.ADD_OP;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token -
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x:= 5 - 10")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//:=
			s.nextToken();//5
			s.nextToken();//+
			flag = s.getTokenType() == TokenType.SUBT_OP;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		//good token - *
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program int x:= 5 * 10")));
			s.currentToken();
			s.nextToken();//int
			s.nextToken();//x
			s.nextToken();//:=
			s.nextToken();//5
			s.nextToken();//+
			flag = s.getTokenType() == TokenType.MULT_OP;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		
		/**
		 * Now testing bad tokens
		 */
		
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("progRAM")));
			s.currentToken();
			flag = s.getTokenType() == TokenType.PROGRAM;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - bad int
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program Int x;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.INT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - begin
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program beGin;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.BEGIN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - end
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program eND;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.END;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - input
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program inPUt;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.INPUT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - output
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program outPUt;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.OUTPUT;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token -if
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program iF then;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.IF;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - then
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program THen;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.THEN;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - endif
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program endIF;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.ENDIF;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - do
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program dO;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.DO;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - while
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program whILE;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.WHILE;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - endWHile
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program endDO;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.ENDDO;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - AND
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program aNd;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.AND;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - OR
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program oR;")));
			s.currentToken();
			s.nextToken();
			flag = s.getTokenType() == TokenType.OR;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		//bad token - bad constant - this test fails but the parser doesn't allow this
		//the token is a constant but the grammar won't allow this
		try
		{
			s = new Scanner(new BufferedReader(new StringReader("program 192aeb;")));
			s.currentToken();
			s.nextToken();
			//the 
			flag = s.getTokenType() == TokenType.SCANNER_ERROR;
			
		}
		catch (IllegalArgumentException e)
		{
			//do nothing
		}
		assertFalse(flag);
		
		

		
		
		
		
	}

}
