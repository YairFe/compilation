package AST; import TYPES.*; import TEMP.*;

public class AST_C_FIELD extends AST_Node {

	public AST_VAR_DEC varDec;
	public AST_FUNC_DEC funcDec;
	
	public AST_C_FIELD(int line, AST_VAR_DEC varDec) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== cField -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.varDec = varDec;
		this.funcDec = null;
	}
	
	public AST_C_FIELD(int line, AST_FUNC_DEC funcDec) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== cField -> funcDec\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.varDec = null;
		this.funcDec = funcDec;
	}
	
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = CLASS FIELD NODE */
		/************************************/
		System.out.print("AST NODE CLASS FIELD\n");

		/*****************************/
		/* RECURSIVELY PRINT FIELDS ... */
		/*****************************/
		if (varDec != null) varDec.PrintMe();
		if (funcDec != null) funcDec.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CLASS FIELD\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
		if(funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
		
	}
	
	public TYPE SemantMe() 
	{ 
		if (varDec != null) return varDec.SemantMe();
		if (funcDec != null) return funcDec.SemantMe();
		
		return new TYPE_ERROR(line);
	}
	
	public TEMP IRme() {
		if (varDec != null) return varDec.IRme();
		if (funcDec != null) return funcDec.IRme();
		
		return null;
	}
}
