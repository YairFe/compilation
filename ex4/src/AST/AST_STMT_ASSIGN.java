package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(int line, AST_VAR var,AST_EXP exp)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

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
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE var_type = var.SemantMe();
		if(var_type.isError()) return var_type;

		TYPE exp_type = exp.SemantMe();
		if(exp_type.isError()) return exp_type;
		
		if(!s.canAssignValueToVar(var_type,exp_type))
			return new TYPE_ERROR(var.line);
		return TYPE_VOID.getInstance();
	}
	 public TEMP IRme(){
		 TEMP t = exp.IRme();
		 TEMP dst;
		 if(var instanceof AST_VAR_SIMPLE){
			// need to enter the offset if var is not global
			IR.getInstance().Add_IRcommand(new IRcommand_Store((AST_VAR_SIMPLE) var).name,t);
		} else if(var instanceof AST_VAR_FIELD){
			dst = ((AST_VAR_FIELD) var).var.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_ClassFieldSet(dst,((AST_VAR_FIELD) var).name,t));
		} else if(var instanceof AST_VAR_SUBSCRIPT){
			dst = ((AST_VAR_SUBSCRIPT) var).var.IRme(); 
			TEMP index = ((AST_VAR_SUBSCRIPT) var).exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_ArraySet(dst,index,t));
		}
		return null;
	 }
}
