package AST; import TYPES.*; import TEMP.*; import IR.*;

public class AST_EXP_STRING extends AST_EXP {
	
	public String value;
	public int length;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(int line, String value) 
	{	
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> STRING( %s )\n", value);
		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.value = value.substring(1, value.length()-1);
		this.length = this.value.length();
	}
	
	/************************************************/
	/* The printing message for a STRING EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST STRING EXP */
		/*******************************/
		System.out.format("AST NODE STRING( %s )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING( %s )",value));
	}
	
	/*************************************************/
	/*          Semantic analysis function           */
	/*************************************************/
	public TYPE SemantMe()
	{
		return TYPE_STRING.getInstance();
	}
	
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstString(t,value));
		return t;
	}
}
