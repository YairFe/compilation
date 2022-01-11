package AST; import TYPES.*; import TEMP.*; import IR.*; import SYMBOL_TABLE.*;

public class AST_EXP_BINOP extends AST_EXP
{
	AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;
	boolean stringConcat = false;
	boolean stringEQ = false;
	
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
			if(OP.OP == 1)
				this.stringConcat = true;
				return TYPE_STRING.getInstance();
		}
		
		// case: equality testing
		if(OP.OP == 5) 
		{	
			/* check for string equality testing */
			if((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance())) { this.stringEQ = true;}
			/* check that the compared values have similar types */
			if(s.canAssignValueToVar(t1,t2) || s.canAssignValueToVar(t2, t1) || (t1 == TYPE_VOID.getInstance() && t2 == TYPE_VOID.getInstance())) return TYPE_INT.getInstance();
			
		}
		// otherwise, error
		return new TYPE_ERROR(line);
	}
	
	/**********************************/
	/*          IR function           */
	/**********************************/
	public TEMP IRme()
	{
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
				
		if (left  != null) t1 = left.IRme();
		if (right != null) t2 = right.IRme();
		
		switch(OP.OP) {
			case 1: {
				// case: addition
				if (this.stringConcat) { IR.getInstance().Add_IRcommand(new IRcommand_Binop_String_Concat(dst, t1, t2)); }
				else { IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(dst,t1,t2)); }
				break;
			}
			case 2: {
				// case: subtraction
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Sub_Integers(dst,t1,t2));
				break;
			}
			case 3: {
				// case: multiplication
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Mul_Integers(dst,t1,t2));
				break;
			}
			case 4: {
				// case: division
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Div_Integers(dst,t1,t2));
				break;
			}
			case 5: {
				// case: equality testing
				if(this.stringEQ) { 
					// string equality testing
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_EQ_Strings(dst,t1,t2)); 
				}
				else { 
					// NOTE: equality testing between arrays and class instances is similar to integer equality testing
					// (we compare pointers instead of values)
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_EQ_Integers(dst,t1,t2)); 
				}
				break;
			}
			case 6: {	
				// case: LT testing
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t1,t2));
				break;
			}
			case 7: {	
				// case: GT testing (equivalent to LT testing with swapped parameters)
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t2,t1));
				break;
			}
		}
		
		return dst;
	}
}
