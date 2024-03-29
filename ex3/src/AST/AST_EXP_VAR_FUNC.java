package AST; import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_EXP_VAR_FUNC extends AST_EXP {

	public AST_VAR var;
	public String fn;
	public AST_EXP_LIST exps;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_FUNC(int line, AST_VAR var, String fn, AST_EXP_LIST exps) 
	{
		super(line);
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
		
		// analyze var
		TYPE t1 = var.SemantMe();
		if(t1.isError()) 
			return t1; 
		else if(!t1.isClass()) 
			return new TYPE_ERROR(line);
		TYPE t2 = ((TYPE_CLASS) t1).findInClassScope(fn);
		if(t2 == null || !t2.isFunc()) return new TYPE_ERROR(line);
		// analyze exps
		TYPE t3 = null;
		if(exps != null) 
		{
			t3 = exps.SemantMe();
			if(t3.isError()) return t3;
		}
		if(!(((TYPE_FUNCTION) t2).isSameArgs((TYPE_LIST) t3))) return new TYPE_ERROR(line);
		
		return ((TYPE_FUNCTION) t2).returnType;
	}
	
}
