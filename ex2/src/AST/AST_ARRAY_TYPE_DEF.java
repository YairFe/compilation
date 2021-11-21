package AST;

public class AST_ARRAY_TYPE_DEF extends AST_Node {
	public String id;
	public AST_Type type;
	
	
	public AST_ARRAY_TYPE_DEF(String id, AST_Type type) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== arrayTypedef -> ARRAY ID( %s ) EQ type LBRACK RBRACK SEMICOLON", id);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.id = id;
		this.type = type;
	}
	
	/***********************************************/
	/* The default message for an arrayTypedef AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = ARRAY TYPE DEF NODE */
		/************************************/
		System.out.print("AST NODE ARRAY TYPE DEF\n");

		/*****************************/
		/* RECURSIVELY PRINT TYPE ... */
		/*****************************/
		if (type != null) type.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ARRAY TYPE DEF( %s )\n", id));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		
	}
	
	
}
