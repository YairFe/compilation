package AST; import TYPES.*; import TEMP.*; import IR.*;

public class AST_DEC extends AST_Node {

	public AST_VAR_DEC varDec;
	public AST_FUNC_DEC funcDec;
	public AST_CLASS_DEC classDec;
	public AST_ARRAY_TYPE_DEF arrayTypedef;
	
	public AST_DEC(int line, AST_VAR_DEC varDec) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.varDec = varDec;
		this.funcDec = null;
		this.classDec = null;
		this.arrayTypedef = null;
	}
	
	public AST_DEC(int line,AST_FUNC_DEC funcDec) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> funcDec\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.funcDec = funcDec;
		this.varDec = null;
		this.classDec = null;
		this.arrayTypedef = null;
	}
	
	public AST_DEC(int line, AST_CLASS_DEC classDec) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> classDec\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.classDec = classDec;
		this.varDec = null;
		this.funcDec = null;
		this.arrayTypedef = null;
	}
	
	public AST_DEC(int line, AST_ARRAY_TYPE_DEF arrayTypedef) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> arrayTypedef\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.arrayTypedef = arrayTypedef;
		this.varDec = null;
		this.funcDec = null;
		this.classDec = null;
	}
	
	/***********************************************/
	/* The default message for a dec AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = DEC NODE */
		/************************************/
		System.out.print("AST NODE DEC\n");

		/*****************************/
		/* RECURSIVELY PRINT FIELDS ... */
		/*****************************/
		if (varDec != null) varDec.PrintMe();
		if (funcDec != null) funcDec.PrintMe();
		if (classDec != null) classDec.PrintMe();
		if (arrayTypedef != null) arrayTypedef.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DEC");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
		if(funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
		if(classDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,classDec.SerialNumber);
		if(arrayTypedef != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,arrayTypedef.SerialNumber);
		
	}
	
	/*************************************************/
	/*          Semantic analysis function           */
	/*************************************************/
	public TYPE SemantMe()
	{
		if (varDec != null) return varDec.SemantMe();
		if (funcDec != null) return funcDec.SemantMe();
		if (classDec != null) return classDec.SemantMe();
		if (arrayTypedef != null) return arrayTypedef.SemantMe();
		return new TYPE_ERROR(line);
	}
	
	public TEMP IRme() {
		if (varDec != null) return varDec.IRme();
		if (funcDec != null) return funcDec.IRme();
		if (classDec != null) return classDec.IRme();
		if (arrayTypedef != null) return arrayTypedef.IRme();
		return null;
	}
}
