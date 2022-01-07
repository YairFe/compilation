package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(int line, AST_EXP cond,AST_STMT_LIST body)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		
		System.out.format("====================== stmt -> IF LPAREN exp RPAREN LBRACE multiStmt RBRACK\n");
		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.cond = cond;
		this.body = body;
	}
	
	/*********************************************************/
	/* The printing message for an if statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST IF STATEMENT */
		/********************************************/
		System.out.print("AST NODE IF STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT COND + BODY ... */
		/***********************************/
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"IF\n( cond ) { body }");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE exp_type = cond.SemantMe();
		if(exp_type.isError()){
			return exp_type;
		} else if(!exp_type.name.equals("int")){
			// might fail if exp_type is null
			return new TYPE_ERROR(cond.line);
		}
		s.beginScope();
		TYPE body_type = body.SemantMe();
		if(body_type.isError()) return body_type;
		s.endScope();
		return TYPE_VOID.getInstance();
	}

	public IRme(){
		TEMP t1 = cond.IRme();
		String end_label = IRcommand.getFreshLabel("end_label");
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(t1,end_label));
		if(body != null) body.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Label(end_label));
		return null;
	}
}
