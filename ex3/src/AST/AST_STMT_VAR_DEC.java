package AST;

public class AST_STMT_VAR_DEC extends AST_STMT {

	public AST_VAR_DEC varDec;
	
	public AST_STMT_VAR_DEC(AST_VAR_DEC varDec) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.varDec = varDec;
	}
	
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST VAR DEC STATEMENT */
		/********************************************/
		System.out.print("AST NODE VAR DEC STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR DEC... */
		/***********************************/
		if (varDec != null) varDec.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR DEC STMT\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
	}

	public TYPE SemantMe(){
		return varDec.SemantMe();
	}
}
