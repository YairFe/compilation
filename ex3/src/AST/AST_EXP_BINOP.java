package AST; import TYPES.*;


public class AST_EXP_BINOP extends AST_EXP
{
	AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(int line, AST_EXP left,AST_EXP right,AST_BINOP OP)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		
		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (OP != null) OP.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nBINOP");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (OP  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,OP.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
	
	/*************************************************/
	/*          Semantic analysis function           */
	/*************************************************/
	public TYPE SemantMe()
	{
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE t1 = null;
		TYPE t2 = null;
		
		t1 = left.SemantMe();
		if(t1.isError()) return t1;
		t2 = right.SemantMe();
		if(t2.isError()) return t2;
		// case: int binop
		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
		{
			if((right instanceof AST_EXP_INT) && (((AST_EXP_INT)right).value == 0) && (OP.OP == 4))
				return new TYPE_ERROR(line);
			return TYPE_INT.getInstance();
		}
		
		// case: string concatenation
		if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()))
		{
			if(OP == 1)
				return TYPE_STRING.getInstance();
		}
		
		// case: equality testing
		if(OP.OP == 5) 
		{
			/* check that the compared values have similar types */
			if(s.canAssignValueToVar(t1,t2) || s.canAssignValueToVar(t2, t1)) return TYPE_INT.getInstance();
		}
		// otherwise, error
		return new TYPE_ERROR(line);
	}
}
