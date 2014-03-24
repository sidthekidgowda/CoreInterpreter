import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Class Printer prints the program in a pretty print format and stores it in a String called prettyProgram
 * 
 * @author Sid Gowda
 * @version 3/17/2014
 *
 */
public class Printer {

	private ParseTree parse_tree = null;
	private StringBuffer prettyProgram = null;
	
	/**
	 * Constructor takes in a parse tree and
	 * @param pt
	 */
	public Printer(ParseTree pt)
	{
		this.parse_tree = pt;
		this.prettyProgram = new StringBuffer("");
	}
	
	/**
	 * Makes the program in a pretty print format
	 */
	public void makePrettyProgam()
	{
		this.prettyProgram.append(this.printProgram());
		System.out.println(this.prettyProgram.toString());
	}
	/**
	 * Does the program production rule in a pretty print format
	 * 
	 * @return the program as a string
	 */
	private String printProgram()
	{
		StringBuffer program = new StringBuffer();
		
		program.append("program\n ");
		
		this.parse_tree.moveCursorToChild(1);
		
		program.append(this.printDeclSeq());
		
		this.parse_tree.moveCursorToRoot();
		
		
		program.append("begin\n");
		/*
		this.parse_tree.moveCursorToChild(2);
		
		this.prettyProgram.append(this.printStmtSeq());
		
		this.prettyProgram.append("end\n");
		*/
		return program.toString();
	}
	/**
	 * Does the declSeq production rule in a pretty print format
	 * 
	 * @return the declSeq as a string
	 */
	private String printDeclSeq()
	{
		StringBuffer declSeq = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		declSeq.append(this.printDecl());
		declSeq.append("\n");
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1:
			//do nothing
			break;
		default://2
			this.parse_tree.moveCursorToChild(2);
			declSeq.append(this.printDeclSeq());
			break;	
		}
		return declSeq.toString();
		
	}
	private String printDecl()
	{
		StringBuffer decl = new StringBuffer();
		
		decl.append("int");
		
		this.parse_tree.moveCursorToChild(1);
		
		decl.append(" ");
		decl.append(this.printIdList());
		decl.append(";");
		
		this.parse_tree.moveCursorUp();
	
		return decl.toString();
	}
	private String printIdList()
	{
		StringBuffer id_list = new StringBuffer();
		this.parse_tree.moveCursorToChild(1);
		
		id_list.append(this.parse_tree.getId());
		
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1:
			//do nothing
			break;
		default://2
			id_list.append(",");
			this.parse_tree.moveCursorToChild(2);
			id_list.append(this.printIdList());
			this.parse_tree.moveCursorUp();
			break;	
		}
		
		return id_list.toString();
		
	}
	/*
	private String printStmtSeq()
	{
		switch(this.PT.getAlternativeNumber(my_row))
		{
		case 1:
			break;
		default://2
			break;
				
		}
	}
	private void printStmt()
	{
		switch(this.PT.getAlternativeNumber(my_row))
		{
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		default://2
			break;
				
		}
	}
	private void printAssign()
	{
		
	}
	private void printIf()
	{
		
	}
	private void printLoop()
	{
		
	}
	private void printInput()
	{
		
	}
	private void printOutput()
	{
		
	}
	private void printCase()
	{
		
	}
	private void printCaseListBlock()
	{
		
	}
	private void printCaseList()
	{
		
	}
	private void printCond()
	{
		
	}
	private void printCmpr()
	{
		
	}
	private void printCmprOp()
	{
		
	}
	private void printExpr()
	{
		
	}
	private void printTerm()
	{
		
	}
	private void printFactor()
	{
		
	}
	*/
	public static void main(String[] args)
	{
		Parser p1 = new Parser(new Scanner(new BufferedReader(new StringReader("program int x      , y    , xy; int z, a    , b; begin input x; end"))));
		p1.makeParseTree();
		
		Printer print = new Printer(p1.getParseTree());
		print.makePrettyProgam();
	}
}
