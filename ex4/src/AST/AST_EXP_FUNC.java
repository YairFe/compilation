package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*; import IR.*;

public class AST_EXP_FUNC extends AST_EXP {

	public String fn;
	public AST_EXP_LIST exps;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_FUNC(int line, String fn, AST_EXP_LIST exps) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		
		System.out.format("====================== exp -> ID(%s) LPAREN multiExp RPAREN\n", fn);
		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.fn = fn;
		this.exps = exps;
	}
	
	/*********************************************************/
	/* The printing message for a variable field AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = EXP VAR FUNC AST node */
		/********************************************/
		System.out.print("AST NODE EXP FUNC\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXPS ... */
		/***********************************/
		if (fn != null) System.out.format("FUNC NAME( %s )\n",fn);
		if (exps != null) exps.PrintMe();
		
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("EXP\n FUNC( %s )", fn));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exps != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exps.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE id_type = s.find(fn);
		if(id_type == null || !id_type.isFunc()) return new TYPE_ERROR(exps.line);
		TYPE exp_type = null;
		if(exps != null){
			exp_type = exps.SemantMe();
			if(exp_type.isError()) return exp_type;
		}
		if(!((TYPE_FUNCTION) id_type).isSameArgs((TYPE_LIST) exp_type)) return new TYPE_ERROR(exps.line);
		return ((TYPE_FUNCTION) id_type).returnType;
	}
	
	public TEMP IRme() {
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP_LIST args = null;
		if (exps != null) args = exps.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(dst, this.fn, args));
		return dst;
		
	}
	
}
