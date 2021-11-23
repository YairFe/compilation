package AST;

public class AST_EXP_FUNC extends AST_EXP {

	public String fn;
	public AST_EXP_LIST exps;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_FUNC(String fn, AST_EXP_LIST exps) 
	{
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
	
}
