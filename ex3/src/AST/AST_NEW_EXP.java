package AST; import TYPES.*; 

public class AST_NEW_EXP extends AST_Node {

	public AST_Type type;
	public AST_EXP exp;
	
	public AST_NEW_EXP(int line, AST_Type type, AST_EXP exp) {
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(exp == null) System.out.print("====================== newExp -> NEW type\n");
		if(exp != null) System.out.print("====================== newExp -> NEW type RBRACK exp LBRACK\n");
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.type = type;
		this.exp = exp;
	}
	
	/***********************************************/
	/* The default message for a newExp AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = NEW EXP NODE */
		/************************************/
		System.out.print("AST NODE NEW EXP\n");

		/*****************************/
		/* RECURSIVELY PRINT EXP AND TYPE ... */
		/*****************************/
		if (exp != null) exp.PrintMe();
		if (type != null) type.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"NEW EXP\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
			
	}
	
	public TYPE SemantMe() 
	{
		TYPE t1 = type.SemantMe();
		if(t1.isError()) return t1;
		
		TYPE t2 = null;
		if(exp != null) 
		{ 
			// index can only be an integer
			t2 = exp.SemantMe();
			if(t2 != TYPE_INT.getInstance()) return new TYPE_ERROR(line);
			return new TYPE_ARRAY(null,t1);
		}
		return t1;

	}
	
}
