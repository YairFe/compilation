package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(int line,AST_EXP cond,AST_STMT_LIST body)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		
		System.out.format("====================== stmt -> WHILE LPAREN exp RPAREN LBRACE multiStmt RBRACE\n");

		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.cond = cond;
		this.body = body;
	}
	
	/*********************************************************/
	/* The printing message for a while statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST WHILE STATEMENT */
		/********************************************/
		System.out.print("AST NODE WHILE STMT\n");

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
			"WHILE\n( cond ) { body }");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}

	public TYPE SemantMe(){
        SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		
		TYPE t = cond.SemantMe();
		if (t.isError()) return t;
		else if(!t.name.equals("int")) return new TYPE_ERROR(cond.line);
		s.beginScope();
		TYPE body_type = body.SemantMe();
		if(body_type.isError()) return body_type;
		s.endScope();
		return TYPE_VOID.getInstance();
    }

	public TEMP IRme(){
		IR.getInstance().Add_IRcommand(new IRcommand_Label("loop_start"));
		TEMP t = cond.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(t, "loop_end"));
		if(body != null) body.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label("loop_start"));
		IR.getInstance().Add_IRcommand(new IRcommand_Label("loop_end"));
		
		return null;
	}
}