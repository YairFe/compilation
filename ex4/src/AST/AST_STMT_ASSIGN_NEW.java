package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_STMT_ASSIGN_NEW extends AST_STMT 
{
	public AST_VAR var;
	public AST_NEW_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN_NEW(int line, AST_VAR var, AST_NEW_EXP exp)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN newExp SEMICOLON\n");
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for a new exp assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST NEW EXP ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN NEW STMT\n");
		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nvar := newexp\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null ) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE var_type = var.SemantMe();
		if(var_type.isError()) return var_type;
		TYPE exp_type = exp.SemantMe();
		if(exp_type.isError()) return exp_type;
		if(s.canAssignValueToVar(var_type,exp_type))
			return TYPE_VOID.getInstance();
		return new TYPE_ERROR(var.line);
	}

	public TEMP IRme(){
		TEMP t = null;
		TEMP dst;
		if(var instanceof AST_VAR_SIMPLE){
			t = exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Store(var.name,t,var.scope_type,var.index));
		} else if(var instanceof AST_VAR_FIELD){
			dst = ((AST_VAR_FIELD) var).var.IRme();
			t = exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_ClassFieldSet(dst,var.name,t,var.index));
		} else if(var instanceof AST_VAR_SUBSCRIPT){
			dst = ((AST_VAR_SUBSCRIPT) var).var.IRme(); 
			TEMP index = ((AST_VAR_SUBSCRIPT) var).subscript.IRme();
			t = exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_ArraySet(dst,index,t));
		}
		return null;

	}
	
}
