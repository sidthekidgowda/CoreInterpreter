import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		ParseTree.clearParseTreeRowNum();
		this.prettyProgram = new StringBuffer("");
		this.prettyProgram.append(this.printProgram());
	}
	
	/**
	 * Makes the program in a pretty print format
	 */
	public void printPrettyProgram()
	{
		System.out.println(this.prettyProgram.toString());
	}
	
	/**
	 * 
	 * @return the prettyProgram as a String
	 */
	public String getPrettyProgram()
	{
		return this.prettyProgram.toString();
	}
	
	/**
	 * Does the program production rule in a pretty print format
	 * 
	 * @return the program as a StringBuffer
	 */
	private StringBuffer printProgram()
	{
		StringBuffer program = new StringBuffer();
		
		//move cursor to the root
		
		this.parse_tree.moveCursorUp();
		
		program.append("program\n");
		
		this.parse_tree.moveCursorToChild(1);
		
		program.append(this.printDeclSeq());
		
		program.append("begin\n");
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(2);
		
		program.append(this.printStmtSeq());
		
		this.parse_tree.moveCursorUp();

		program.append("end\n");
		
		return program;
	}
	
	/**
	 * Does the declSeq production rule in a pretty print format
	 * 
	 * @return the declSeq as a StringBuffer
	 */
	private StringBuffer printDeclSeq()
	{
		StringBuffer declSeq = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		
		declSeq.append(" ");
		declSeq.append(" ");
		declSeq.append(this.printDecl());
		declSeq.append("\n");
		
		this.parse_tree.moveCursorUp();
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<decl>
			//do nothing
			break;
		default://<decl><decl-seq>
			this.parse_tree.moveCursorToChild(2);
			declSeq.append(this.printDeclSeq());
			this.parse_tree.moveCursorUp();
			break;	
		}
		return declSeq;
		
	}
	
	/**
	 * Does the decl production rule in a pretty print format
	 * 
	 * @return the decl as a StringBuffer
	 */
	private StringBuffer printDecl()
	{
		StringBuffer decl = new StringBuffer();
		
		decl.append("int");
		
		this.parse_tree.moveCursorToChild(1);
		
		decl.append(" ");
		decl.append(this.printIdList());
		decl.append(";");
		
		this.parse_tree.moveCursorUp();
	
		return decl;
	}
	
	/**
	 * Does the id-list production rule in a pretty print format
	 * 
	 * @return the id-list as a StringBuffer
	 */
	private StringBuffer printIdList()
	{
		StringBuffer id_list = new StringBuffer();
		this.parse_tree.moveCursorToChild(1);
		
		id_list.append(this.parse_tree.getId());
		
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://id
			//do nothing
			break;
		default://id, <id-list>
			id_list.append(",");
			this.parse_tree.moveCursorToChild(2);
			id_list.append(this.printIdList());
			this.parse_tree.moveCursorUp();
			break;	
		}
		
		return id_list;
		
	}
	
	/**
	 * Does the stmt-sequence production rule in a pretty print format
	 * 
	 * @return the stmt-sequence as a StringBuffer
	 */
	private StringBuffer printStmtSeq()
	{
		StringBuffer stmt_seq = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		
		stmt_seq.append(" ");
		stmt_seq.append(" ");
		stmt_seq.append(this.printStmt());
		stmt_seq.append("\n");
		
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<stmt>
			//do nothing
			break;
		default://<stmt><stmt-seq>
			this.parse_tree.moveCursorToChild(2);
			stmt_seq.append(this.printStmtSeq());
			this.parse_tree.moveCursorUp();
			break;	
		}
		
		return stmt_seq;
	}
	
	/**
	 * Does the stmt production rule in a pretty print format
	 * 
	 * @return the stmt as a StringBuffer
	 */
	private StringBuffer printStmt()
	{
		StringBuffer stmt = new StringBuffer();
	
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<assign>
			this.parse_tree.moveCursorToChild(1);
			stmt.append(this.printAssign());
			break;
		case 2://<if>
			this.parse_tree.moveCursorToChild(1);
			stmt.append(this.printIf());
			break;
		case 3://<loop>
			this.parse_tree.moveCursorToChild(1);
			stmt.append(this.printLoop());
			break;
		case 4://<in>
			this.parse_tree.moveCursorToChild(1);
			stmt.append(this.printInput());
			break;
		case 5://<out>
			this.parse_tree.moveCursorToChild(1);
			stmt.append(this.printOutput());
			break;
		default://<case>
			this.parse_tree.moveCursorToChild(1);
			stmt.append(this.printCase());
			break;
				
		}
		
		this.parse_tree.moveCursorUp();
		return stmt;
	}
	
	/**
	 * Does the assign production rule in a pretty print format
	 * 
	 * @return the assignment as a StringBuffer
	 */
	private StringBuffer printAssign()
	{
		StringBuffer assign = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		
		assign.append(this.parse_tree.getId());
		assign.append(":=");
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(2);
		
		assign.append(this.printExpr());
		
		this.parse_tree.moveCursorUp();
		assign.append(";");
		
		return assign;
	}
	
	/**
	 * Does the if production rule in a pretty print format
	 * 
	 * @return the if rule as a StringBuffer
	 */
	private StringBuffer printIf()
	{
		StringBuffer if_print = new StringBuffer();
	
		if_print.append("if");
		
		this.parse_tree.moveCursorToChild(1);
	
		if_print.append(this.printCond());
		
		this.parse_tree.moveCursorUp();
		
		if_print.append("then\n");
		
		this.parse_tree.moveCursorToChild(2);
		
		if_print.append(this.addSpaces(this.printStmtSeq()));
		
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://if <cond> then <stmt-seq> endif;
			//do nothing
			break;
		default: //if <cond> then <stmt-seq> else <stmt-seq> endif;
			this.parse_tree.moveCursorToChild(3);
			if_print.append("else\n");
			if_print.append(this.addSpaces(this.printStmtSeq()));
			this.parse_tree.moveCursorUp();
			break;
		}
		if_print.append("  endif;");
		
		return if_print;
		
	}
	
	/**
	 * Does the do-while loop in a pretty print format
	 * 
	 * @return the do-while loop as a StringBuffer
	 */
	private StringBuffer printLoop()
	{
		StringBuffer loop = new StringBuffer();
		
		loop.append("do\n");
		this.parse_tree.moveCursorToChild(1);
		

		//add spaces into each line of the stmtSeq
		
		loop.append(this.addSpaces(this.printStmtSeq()));
		
		this.parse_tree.moveCursorUp();
		
		loop.append("  while");
		
		this.parse_tree.moveCursorToChild(2);
		
		loop.append(this.printCond());
		
		this.parse_tree.moveCursorUp();
		
		loop.append("enddo;");
		
		return loop;
	}
	
	/**
	 * Does the input production loop in a pretty print format
	 * 
	 * @return the input as a StringBuffer
	 */
	private StringBuffer printInput()
	{
		StringBuffer input = new StringBuffer();
		
		input.append("input ");
		
		this.parse_tree.moveCursorToChild(1);
		
		input.append(this.printIdList());
		
		this.parse_tree.moveCursorUp();
		
		input.append(";");
		
		return input;
	}
	
	/**
	 * Does the output production rule in a pretty print format
	 * 
	 * @return the output as a StringBuffer
	 */
	private StringBuffer printOutput()
	{
		StringBuffer output = new StringBuffer();
		
		output.append("output ");
		
		this.parse_tree.moveCursorToChild(1);
		
		output.append(this.printIdList());
		
		this.parse_tree.moveCursorUp();
		
		output.append(";");
		
		return output;
		
	}
	
	/**
	 * Does the output production rule in a pretty print format
	 * 
	 * <case> ::= case id of <list-block> else <expr> end;
	 * 
	 * @return the output as a StringBuffer
	 */
	private StringBuffer printCase()
	{
		StringBuffer case_stmt = new StringBuffer();
		
		case_stmt.append("case ");
		
		this.parse_tree.moveCursorToChild(1);
		
		case_stmt.append(this.parse_tree.getId());
		
		this.parse_tree.moveCursorUp();
		case_stmt.append(" of\n");
		
		this.parse_tree.moveCursorToChild(2);
		
		case_stmt.append(this.printCaseListBlock());
		
		this.parse_tree.moveCursorUp();
		
		this.parse_tree.moveCursorToChild(3);
		
		case_stmt.append("\telse ");
		
		case_stmt.append(this.printExpr());
		
		this.parse_tree.moveCursorUp();
		
		case_stmt.append("\n  end;\n");
		
		return case_stmt;
	}
	
	/**
	 * Does the case list block production rule in a pretty print format
	 * 
	 * <list-block> ::= <list>COLON<expr> | <list>COLON <expr> BAR <list-block>
	 * 
	 * @return the output as a StringBuffer
	 */
	private StringBuffer printCaseListBlock()
	{
		StringBuffer list_block = new StringBuffer();
		
		list_block.append("\t");
		
		this.parse_tree.moveCursorToChild(1);
		list_block.append(this.printCaseList());
		this.parse_tree.moveCursorUp();
		
		list_block.append(":");
		
		this.parse_tree.moveCursorToChild(2);
		list_block.append(this.printExpr());
		
		list_block.append("\n");
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<list>COLON<expr>
			//do nothing
			break;
		default://<list>COLON<expr>BAR<list-block>
			list_block.append("  |");
			this.parse_tree.moveCursorToChild(3);
			list_block.append(this.printCaseListBlock());
			this.parse_tree.moveCursorUp();
			break;
		}
		return list_block;
	}
	
	/**
	 * Does the case list production rule in a pretty print format
	 * 
	 * <list> ::= const, <list>| const
	 * 
	 * @return the output as a string
	 */
	private StringBuffer printCaseList()
	{
		StringBuffer list = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		list.append(this.parse_tree.getConstant());
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://const, <list>
			break;
		default://const, <list> | const
			list.append(",");
			this.parse_tree.moveCursorToChild(2);
			list.append(this.printCaseList());
			this.parse_tree.moveCursorUp();
		}
		
		return list;	
	}
	
	/**
	 * Does the condition production rule in a pretty print format
	 * 
	 * @return the condition as a StringBuffer
	 */
	private StringBuffer printCond()
	{
		StringBuffer cond = new StringBuffer();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<cmpr>
			this.parse_tree.moveCursorToChild(1);
			cond.append(this.printCmpr());
			break;
		case 2://!<cond>
			this.parse_tree.moveCursorToChild(1);
			cond.append("!");
			cond.append(this.printCond());
			break;
		case 3://(<cond>AND<cond>)
			cond.append("(");
			this.parse_tree.moveCursorToChild(1);
			cond.append(this.printCond());
			this.parse_tree.moveCursorUp();
			cond.append(" AND ");
			this.parse_tree.moveCursorToChild(2);
			cond.append(this.printCond());
			cond.append(")");
			break;
		default://(<cond>OR<cond>)
			cond.append("(");
			this.parse_tree.moveCursorToChild(1);
			cond.append(this.printCond());
			this.parse_tree.moveCursorUp();
			cond.append("OR");
			this.parse_tree.moveCursorToChild(2);
			cond.append(this.printCond());
			cond.append(")");
			break;
		
		}
		this.parse_tree.moveCursorUp();
		return cond;
	}
	
	/**
	 * Does the Comparison condition rule in a pretty print format
	 * 
	 * @return the comparison as a StringBuffer
	 */
	private StringBuffer printCmpr()
	{
		StringBuffer cmpr = new StringBuffer();
		
		cmpr.append("[");
		this.parse_tree.moveCursorToChild(1);
		
		cmpr.append(this.printExpr());
		this.parse_tree.moveCursorUp();
		this.parse_tree.moveCursorToChild(2);
		
		cmpr.append(this.printCmprOp());
		this.parse_tree.moveCursorUp();
		this.parse_tree.moveCursorToChild(3);
		
		cmpr.append(this.printExpr());
		this.parse_tree.moveCursorUp();
		cmpr.append("]");
		
		return cmpr;
	}
	
	/**
	 * Does the Comparison operator rule in a pretty print format
	 * 
	 * @return the comparison operator as a StringBuffer
	 */
	private StringBuffer printCmprOp()
	{
		StringBuffer cmpr_op = new StringBuffer();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<
			cmpr_op.append("<");
			break;
		case 2://=
			cmpr_op.append("=");
			break;
		case 3://!=
			cmpr_op.append("!=");
			break;
		case 4://>
			cmpr_op.append(">");
			break;
		case 5://>=
			cmpr_op.append(">=");
			break;
		default://<=
			cmpr_op.append("<=");
			break;
		}
		return cmpr_op;
	}
	
	/**
	 * Does the expression in a pretty print format
	 * 
	 * @return the expression as a StringBuffer
	 */
	private StringBuffer printExpr()
	{
		StringBuffer expr = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		
		expr.append(this.printTerm());
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<term>
			break;
		case 2://<term> + <expr>
			expr.append("+");
			this.parse_tree.moveCursorToChild(2);
			expr.append(this.printExpr());
			this.parse_tree.moveCursorUp();
			break;
		default://<term> - <expr>
			expr.append("-");
			this.parse_tree.moveCursorToChild(2);
			expr.append(this.printExpr());
			this.parse_tree.moveCursorUp();
			break;
		}
		
		return expr;
	}
	
	/**
	 * Does the term in a pretty print format
	 * 
	 * @return the term as a StringBuffer
	 */
	private StringBuffer printTerm()
	{
		StringBuffer term = new StringBuffer();
		
		this.parse_tree.moveCursorToChild(1);
		
		term.append(this.printFactor());
		
		this.parse_tree.moveCursorUp();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://<factor>
			break;
		default://<factor> * <term>
			term.append("*");
			this.parse_tree.moveCursorToChild(2);
			term.append(this.printTerm());
			this.parse_tree.moveCursorUp();
			break;
		}
		return term;
	}
	
	/**
	 * Does the factor conditional rule in a pretty print format
	 * @return
	 */
	private StringBuffer printFactor()
	{
		StringBuffer factor = new StringBuffer();
		
		switch(this.parse_tree.getAlternativeNumber())
		{
		case 1://const
			this.parse_tree.moveCursorToChild(1);
			factor.append(this.parse_tree.getConstant());
			break;
		case 2://id
			this.parse_tree.moveCursorToChild(1);
			factor.append(this.parse_tree.getId());
			break;
		case 3://-<factor>
			this.parse_tree.moveCursorToChild(1);
			factor.append("-");
			factor.append(this.printFactor());
			break;
		default://(<expr>)
			this.parse_tree.moveCursorToChild(1);
			factor.append("(");
			factor.append(this.printExpr());
			factor.append(")");
			break;
		}
		this.parse_tree.moveCursorUp();
		
		return factor;
		
	}
	
	private StringBuffer addSpaces(StringBuffer s)
	{
		
		String strArray[] = s.toString().split("\n");
		
		
		//add spaces
		for(int i = 0; i < strArray.length; i++)
		{
			strArray[i] = "  " + strArray[i];
		}
		
		s = new StringBuffer();
		
		for(int i = 0; i < strArray.length; i++)
		{
			s.append(strArray[i] + "\n");
		}
		return s;
		
	}
	
	
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		Parser p1 = new Parser(new Scanner(new BufferedReader(new StringReader("program int x; begin if [x < 10] then if [x < 10] then if [x < 20] then input x; endif; endif; endif; end"))));
		
		p1.makeParseTree();
		Printer print = new Printer(p1.getParseTree());
		print.printPrettyProgram();
	}
}
