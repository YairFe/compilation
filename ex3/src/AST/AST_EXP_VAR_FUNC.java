package AST; import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_EXP_VAR_FUNC extends AST_EXP {

	public AST_VAR var;
	public String fn;
	public AST_EXP_LIST exps;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_FUNC(AST_VAR var, String fn, AST_EXP_LIST exps) 
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		
		System.out.format("====================== exp -> var DOT ID(%s) LPAREN multiExp RPAREN\n", fn);
		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.var = var;
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
		System.out.print("AST NODE EXP VAR FUNC\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXPS ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (fn != null) System.out.format("FUNC NAME( %s )\n",fn);
		if (exps != null) exps.PrintMe();
		
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("EXP/n VAR FUNC( %s )", fn));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exps != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exps.SerialNumber);
	}
	
	public TYPE SemantMe() {
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		
		/* TODO: check that fn is a class method of var's class or one of its parent classes */
		
		
		// analyze var
		TYPE t1 = var.SemantMe();
		if(t1 == null) return null; 
		
		// analyze exps
		if(exps != null) 
		{
			TYPE t2 = exps.SemantMe();
			if(t2 == null) return null;
		}
		
		/* TODO: check that exps represents a correct set of parameters.
		 * TODO: find the object of fn and return its return type */
		
		return null;
	}
	
}
