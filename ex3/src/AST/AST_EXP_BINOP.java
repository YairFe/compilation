package AST; import TYPES.*;


public class AST_EXP_BINOP extends AST_EXP
{
	AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,AST_BINOP OP)
	{
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
		TYPE t1 = null;
		TYPE t2 = null;
		
		t1 = left.SemantMe();
		t2 = right.SemantMe();
		
		// case: int binop
		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
		{
			if((((AST_EXP_INT)right).value == 0) && (OP.OP == 4)) { /*	TODO: return division-by-0 error */ }
			return TYPE_INT.getInstance();
		}
		
		// case: string concatenation
		if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()))
		{
			return TYPE_STRING.getInstance();
		}
		
		// case: equality testing
		if(OP.OP == 5) 
		{
			boolean hasString = (t1 == TYPE_STRING.getInstance()) || (t2 == TYPE_STRING.getInstance());
			boolean hasNil = (t1 == TYPE_NIL.getInstance()) || (t2 == TYPE_NIL.getInstance());
			if (hasNil) {
				if (hasString) return null;
				return TYPE_INT.getInstance();
			}
			/* check that the compared values have similar types */
			if(canAssignValueToVar(t1,t2) && canAssignValueToVar(t2, t1)) return TYPE_INT.getInstance();
		}
		// otherwise, error
		return null;
	}
}
