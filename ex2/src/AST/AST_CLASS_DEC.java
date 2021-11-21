package AST;

public class AST_CLASS_DEC extends AST_Node {

	public String id1;
	public String id2;
	public AST_CLASS_CONT cont;
	
	public AST_CLASS_DEC(String id1, AST_CLASS_CONT cont) 
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== classDec -> CLASS ID( %s ) LBRACE classCont RBRACE", id1);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.id1 = id1;
		this.id2 = null;
		this.cont = cont;
	}
	
	public AST_CLASS_DEC(String id1, String id2, AST_CLASS_CONT cont) 
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== classDec -> CLASS ID( %s ) EXTENDS ID( %s ) LBRACE classCont RBRACE", id1, id2);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.id1 = id1;
		this.id2 = id2;
		this.cont = cont;
	}
	
	/***********************************************/
	/* The default message for a classDec AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = CLASS DEC NODE */
		/************************************/
		System.out.print("AST NODE CLASS DEC\n");

		/*****************************/
		/* RECURSIVELY PRINT CONT ... */
		/*****************************/
		if (cont != null) cont.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		if( id2 == null) AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC( %s )\n", id1));
		if( id2 != null) AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC( %s ) EXTENDS ( %s )\n", id1, id2));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(cont != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cont.SerialNumber);
		
	}
	
}
