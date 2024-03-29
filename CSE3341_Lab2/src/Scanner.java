import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class Scanner is called by the parser and creates a Token
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class Scanner {
	
	private Token token = null;
	private BufferedReader program = null;
	private StringBuffer input = null;
	private static List<String> words = null;
	private static String keywords[] = {"program", "begin", "end", "int", "input", "output", "if", "then", "else", "endif", "do",
			"while", "enddo", "case", "of", "AND", "OR"};
	
	/**
	 * Static Constructor creates the list of keywords
	 */
	static {
		Scanner.words = new ArrayList<String>(Arrays.asList(keywords));
	}
	
	/**
	 * Constructor for the Scanner class
	 * Takes in the Buffered Reader and creates a String Buffer of the program in one line
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
		this.input = new StringBuffer("");
		try{
			while((read_line = this.program.readLine())!=null)
			{
				//do not add empty lines
				if(!read_line.matches("\\s*"))
				{
					this.input.append(read_line.trim() + " ");
				}
				
			}
			//Test and see if everything is printed on one line
		}
		catch (IOException e)
		{
			System.out.println("Could not read the input program: " + e.getMessage());
		}
	}
	
	
	/**
	 * This method uses the StringBuffer to create a Token
	 */
	private void createToken()
	{	
		boolean token_made = false;
		StringBuffer make_token = new StringBuffer("");
		
		//create End of File Token
		if(this.isInputEmpty())
		{
			this.token = new Token("",TokenType.EOF);
			token_made = true;
		}
		
		if(!token_made)
		{
			//Ignore Empty Spaces, tabs, or new lines
			while(this.input.charAt(0) == ' ' || this.input.charAt(0)=='\t')
			{
				this.input.deleteCharAt(0);
			}
			
			/*
			 * match keywords or identifiers or constants
			 * 
			 * if a letter is read, keep reading characters until the first non-letter
			 * or digit character is read
			 * 
			 * if x is a digit, keep reading characters until the first non-digit character is read
			 */
			
			
			String s = Character.toString(this.input.charAt(0));
			//match a digit first
			if(s.matches("[0-9]"))
			{
				//if 0 is read, then create the Token
				if(s.equals("0"))
				{
					make_token.append(this.input.charAt(0));
					this.input.deleteCharAt(0);
				}
				else
				{
					while(s.matches("[0-9]"))
					{
						make_token.append(this.input.charAt(0));
						this.input.deleteCharAt(0);
						s = Character.toString(this.input.charAt(0));
					}
				}
				this.token = new Token(make_token.toString(), TokenType.CONSTANT);
				token_made = true;
			}
			//match keywords and identifiers
			if(s.matches("[a-zA-Z]") && !token_made)
			{
				//read character until the first non-letter-or-digit character
				make_token.append(this.input.charAt(0));
				this.input.deleteCharAt(0);
				s = Character.toString(this.input.charAt(0));
				while(s.matches("[a-zA-Z0-9]"))
				{
					make_token.append(this.input.charAt(0));
					this.input.deleteCharAt(0);
					s = Character.toString(this.input.charAt(0));
				}
				//check and see if a keyword is matched
				
				if(this.isKeywordMatched(make_token.toString()))
				{
					this.token = new Token(make_token.toString(), this.getKeywordToken(make_token.toString()));
				}
				else
				{
					//see if a keyword is matched but that is capitalized or formatted incorrectly
					if(this.isErrorKeywordMatched(make_token.toString()))
						this.token = new Token(make_token.toString(), TokenType.SCANNER_ERROR);
					else
						this.token = new Token(make_token.toString(), TokenType.ID);
				}
				
				token_made = true;
			}
			
			//match other tokens
			if(!token_made)
			{
				switch(this.input.charAt(0))
				{
				case ';':
					this.token = new Token(this.input.charAt(0), TokenType.SEMICOLON);
					this.input.deleteCharAt(0);
					break;
				case ',':
					this.token = new Token(this.input.charAt(0), TokenType.COMMA);
					this.input.deleteCharAt(0);
					break;
				case '(':
					this.token = new Token(this.input.charAt(0), TokenType.LPAREN);
					this.input.deleteCharAt(0);
					break;
				case ')':
					this.token = new Token(this.input.charAt(0), TokenType.RPAREN);
					this.input.deleteCharAt(0);
					break;
				case '[':
					this.token = new Token(this.input.charAt(0), TokenType.LBRACKET);
					this.input.deleteCharAt(0);
					break;
				case ']':
					this.token = new Token(this.input.charAt(0), TokenType.RBRACKET);
					this.input.deleteCharAt(0);
					break;
				case '=':
					this.token = new Token(this.input.charAt(0), TokenType.EQUAL);
					this.input.deleteCharAt(0);
					break;
				case '+':
					this.token = new Token(this.input.charAt(0), TokenType.ADD_OP);
					this.input.deleteCharAt(0);
					break;
				case '-':
					this.token = new Token(this.input.charAt(0), TokenType.SUBT_OP);
					this.input.deleteCharAt(0);
					break;
				case '*':
					this.token = new Token(this.input.charAt(0), TokenType.MULT_OP);
					this.input.deleteCharAt(0);
					break;
				case '|':
					this.token = new Token(this.input.charAt(0), TokenType.BAR);
					this.input.deleteCharAt(0);
					break;
				case ':':
					make_token.append(this.input.charAt(0));
					this.input.deleteCharAt(0);
					if(this.input.charAt(0) == '=')//:= assign token
					{
						make_token.append(this.input.charAt(0));
						this.token = new Token(make_token.toString(), TokenType.ASSIGN);
						this.input.deleteCharAt(0);
					}
					else
						this.token = new Token(make_token.toString(), TokenType.COLON);
					break;
				case '!':
					make_token.append(this.input.charAt(0));
					this.input.deleteCharAt(0);
					if(this.input.charAt(0) == '=')//!= not equal
					{
						make_token.append(this.input.charAt(0));
						this.token = new Token(make_token.toString(), TokenType.NOT_EQUAL);
						this.input.deleteCharAt(0);
					}
					else 
						this.token = new Token(make_token.toString(), TokenType.NOT);
					break;
					
				case '<':
					make_token.append(this.input.charAt(0));
					this.input.deleteCharAt(0);
					if(this.input.charAt(0) == '=')//<=
					{
						make_token.append(this.input.charAt(0));
						this.token = new Token(make_token.toString(), TokenType.LESS_THAN_OR_EQ_TO);
						this.input.deleteCharAt(0);
					}
					else
						this.token = new Token(make_token.toString(), TokenType.LESS_THAN);
					break;
				case '>':
					make_token.append(this.input.charAt(0));
					this.input.deleteCharAt(0);
					if(this.input.charAt(0) == '=')
					{
						make_token.append(this.input.charAt(0));
						this.token = new Token(make_token.toString(), TokenType.GREATER_OR_EQ_TO);
						this.input.deleteCharAt(0);
					}
					else
						this.token = new Token(make_token.toString(), TokenType.GREATER_THAN);
						
					break;
				default://error cases
					throw new IllegalArgumentException("Illegal Core Grammar Character entered: " + this.input.charAt(0));
					
				}
			}
		}
		
	}
	
	/**
	 * See if the keyword is in the List of Keywords
	 * 
	 * @param String s is the incoming string
	 * @return true if the keyword is in the list or false if the keyword is not in the list
	 */
	private boolean isKeywordMatched(String s)
	{
		if(Scanner.words.contains(s))
			return true;
		else
			return false;
		
	}
	/**
	 * See if a keyword is in the List of Keywords but formatted incorrectly
	 * 
	 * @param String s is the incoming string
	 * @return true if the keyword is formatted incorrectly or false if it is an ID
	 */
	private boolean isErrorKeywordMatched(String s)
	{
		if(Scanner.words.contains(s.toLowerCase()))
			return true;
		else
			return false;
	}
	/**
	 * Creates a Token associated to the keyword and returns it
	 * 
	 * @param String s is the incoming string
	 * @return Token Type associated with the incoming keyword
	 */
	private TokenType getKeywordToken(String s)
	{
		TokenType t = null;
		switch(s)
		{
		case "program":
			t = TokenType.PROGRAM;
			break;
		case "begin":
			t = TokenType.BEGIN;
			break;
		case "end":
			t = TokenType.END;
			break;
		case "int":
			t = TokenType.INT;
			break;
		case "input":
			t = TokenType.INPUT;
			break;
		case "output":
			t = TokenType.OUTPUT;
			break;
		case "if":
			t = TokenType.IF;
			break;
		case "then":
			t = TokenType.THEN;
			break;
		case "else":
			t = TokenType.ELSE;
			break;
		case "endif":
			t = TokenType.ENDIF;
			break;
		case "do":
			t = TokenType.DO;
			break;
		case "while":
			t = TokenType.WHILE;
			break;
		case "enddo":
			t = TokenType.ENDDO;
			break;
		case "case":
			t = TokenType.CASE;
			break;
		case "of":
			t = TokenType.OF;
			break;
		case "AND":
			t = TokenType.AND;
			break;
		default://OR
			t = TokenType.OR;
			break;
		}
		return t;
		
	}
	
	
	/**
	 * This method checks if there is any Input Left to be read
	 * The StringBuffer counts
	 * @return true or false
	 */
	private boolean isInputEmpty()
	{
		//StringBuffer counts empty string as part of the StringBuffer
		return this.input.length() == 1;
	}
	
	/**
	 * This method gets the current Token
	 * @return the currentToken that is made
	 */
	public Token currentToken()
	{		
		if(this.token == null)
			this.createToken();
		
		return this.token;
		
	}
	/**
	 * Checks and see if the Token passed in matches the TokenType
	 * @param TokenType t
	 * @return true or false
	 */
	public boolean matchToken(TokenType t)
	{
		if(this.token == null)
			this.createToken();
		
		if(this.currentToken().token_type == t)
			return true;
		
		return false;
	}
	/**
	 * get TokenType gets the token type
	 * @return the token type of the Token
	 */
	public TokenType getTokenType()
	{
		return this.currentToken().token_type;
	}
	
	/**
	 * Gets the Value of the Token
	 * @return Token Value of the Token
	 */
	public String getTokenValue()
	{
		return this.token.token_value;
	}
	
	
	/**
	 * Creates a new Token
	 */
	public void nextToken()
	{
		this.createToken();
	}
	
	
	/**
	 * Method allows you to create and print out Tokens
	 */
	public void printTokens()
	{
		while(!this.isInputEmpty())
		{
			this.createToken();
			System.out.println(this.toString());
			System.out.println();
		}
		//print EOF token
		this.createToken();
		System.out.println(this.toString());
		System.out.println();
		
	}
	
	/**
	 * Nested Class Token is used to create a Token
	 * Stores the token type and token value of the associated token
	 * @author Sid Gowda
	 * 
	 *
	 */
	
	private class Token
	{
		private TokenType token_type;
		private String token_value;
		
		/**
		 * Constructor creates a Token
		 * 
		 * @param c is the char value
		 * @param t is the Token Type
		 */
		private Token(char c, TokenType t)
		{
			this.token_type = t;
			this.token_value = Character.toString(c);
			
		}
		/**
		 * Constructor creates a Token
		 * 
		 * @param s is the string value
		 * @param t is the Token Type
		 */
		private Token(String s, TokenType t)
		{
			this.token_type = t;
			this.token_value = s;
		}
	}
	
}
