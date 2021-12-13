package AST;

public class AST_FUNC_DEC extends AST_Node {

	public AST_Type type;
	String id;
	AST_VAR_LIST vars;
	AST_STMT_LIST stmts;
	
	public AST_FUNC_DEC(AST_Type type, String id, AST_VAR_LIST vars, AST_STMT_LIST stmts) 
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (multiVar == null) System.out.format("====================== funcDec -> type ID( %s ) LPAREN RPAREN LBRACE multiStmt RBRACE\n", id);
		if (multiVar != null) System.out.format("====================== funcDec -> type ID( %s ) LPAREN multiVar RPAREN LBRACE multiStmt RBRACE\n", id);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		
		this.type = type;
		this.id = id;
		this.vars = vars;
		this.stmts = stmts;
	}
	
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST FUNC DEC */
		/**************************************/
		System.out.print("AST NODE FUNC DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT FIELDS ... */
		/*************************************/
		if (type != null) type.PrintMe();
		if (vars != null) vars.PrintMe();
		if (stmts != null) stmts.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FUNC DEC ( %s )\n", id));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if (vars != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,vars.SerialNumber);
		if (stmts != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,stmts.SerialNumber);
	}
	
}
