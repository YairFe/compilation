package AST;

public class AST_BINOP extends AST_Node
{
	int OP;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_BINOP(int OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

        
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.OP = OP;

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== BINOP -> %s \n",translateOP());
	}
	public String translateOP(){
        String sOP=null;
        if (this.OP == 1)  {sOP = "+";}
		if (this.OP == 2)  {sOP = "-";}
		if (this.OP == 3)  {sOP = "*";}
		if (this.OP == 4)  {sOP = "/";}
		if (this.OP == 5)  {sOP = "=";}
		if (this.OP == 6)  {sOP = "<";}
		if (this.OP == 7)  {sOP = ">";}
        return sOP;
    }
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		
		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE BINOP\n");
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP( %s )",translateOP()));
	}
}
