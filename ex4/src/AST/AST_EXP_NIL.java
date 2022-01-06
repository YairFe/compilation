package AST; import TYPES.*; import TEMP.*; import IR.*;

public class AST_EXP_NIL extends AST_EXP 
{
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	
	public AST_EXP_NIL(int line)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> NIL\n");

	}
	
	/************************************************/
	/* The printing message for an NIL EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST NIL EXP */
		/*******************************/
		System.out.print("AST NODE NIL\n");

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"NIL");
	}
	
	public TYPE SemantMe() { return TYPE_NIL.getInstance(); }
	
	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstNil(t));
		return t;
	}

}
