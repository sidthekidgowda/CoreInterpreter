import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


import org.junit.Test;


public class ParserTest {
	
	@Test
	public void testMakeParseTree() throws FileNotFoundException {
		
		// Test on bad program - program
        boolean flag = false;
        Parser p = null;
       
        //no program token
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("bad1.code"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - no end
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("bad2.code"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - no matching right bracket
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("bad3.code"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        //Test on bad program - x is not declared
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("bad4.code"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - no begin
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("bad5noBegin.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - no semi colon in input
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badnoSemiColon.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - "a" is declared more than once
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badMultipleDeclaredVariables.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - bad declSeq
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badDeclSeq.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - badStmtSeq - empty stm-seq is an error
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badStmtSeq.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        //Test on bad program - badStmt- empty stm-seq is an error
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badStmt.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        
        //Test on bad program - invalid Assign statement, Y is not declared
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("invalidAssign.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        
        //Test on bad program - nested if statement does not matching endif
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badIf.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        
        //Test on bad program - invalid loop, no enddo
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badLoop.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        //Test on bad program - bad condition statement, AND is ANDD
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badCondition.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //Test on bad program - bad comparison operator, x <! y
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badCmprOp.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        //Test on bad program - bad Expression, x:= 10 + -20 - -20 * --20;
        //--20 is illegal by itself
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badExpr.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        flag = false; //-10 - --100 is illegal;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badExpr2.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        //bad case statement - no end;
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badCase.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
      //bad case statement - multiple declared constant. 2 is duplicated
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badCase2.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        //bad nested statements
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("badNestedStatements.txt"))));
        	p.makeParseTree();
        } 
        catch (IllegalArgumentException e) 
        {
        	flag = true;
            System.out.println(e);
        } 
        assertTrue(flag);
        
        
        /**
         * now testing good programs
         */
        
        //first test
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("t1.code"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //second test
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("t2.code"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //third test
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("t3.code"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //fourth test
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("t4.code"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //fifth test
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("goodDeclSeq"))));
        	p.makeParseTree();
        	flag = true;
        	
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //now nested if statements and case statements and test the loops
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("nestedIfandCaseLoops.txt"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        
        //nested conditions
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("nestedConditions.txt"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
       
      
        //nested expressions
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("nestedExpressions.txt"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //nested do while
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("nestedLoop"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);
        
        //nested if statements
        flag = false;
        try 
        {
        	p = new Parser(new Scanner(new BufferedReader(new FileReader("nestedIf.txt"))));
        	p.makeParseTree();
        	flag = true;
        } 
        catch (IllegalArgumentException e) 
        {
        	//do nothing
        } 
        assertTrue(flag);

	}


	
	@Test
	public void testGetParseTree() throws FileNotFoundException {
		
		Parser p = null;
		//ask for null parse tree
		boolean flag = false;
		try
		{
			p = new Parser(new Scanner(null));
			p.makeParseTree();
			
			p.getParseTree();
		}
		catch(NullPointerException e)
		{
			flag = true;
		}
		assertTrue(flag);
		
		//ask for a parse tree that gets made
		
		flag = false;
		
		try
		{
			p = new Parser(new Scanner(new BufferedReader(new FileReader("nestedIf.txt"))));
			p.makeParseTree();
			
			p.getParseTree();
			flag = true;
		}
		catch(NullPointerException e)
		{
			//do nothing
		}
		assertTrue(flag);
		
		
		
	}
	

	
}
