package AST;

public class AST_STMT_VAR_FUNC extends AST_STMT {
	
	public AST_VAR var;
	public String fn;
	public AST_EXP_LIST exps;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_VAR_FUNC(AST_VAR var, String fn, AST_EXP_LIST exps) 
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(var==null)
			if(exps==null) System.out.format("====================== stmt -> ID(%s) LPAREN RPAREN SEMICOLON\n", fn);
			else System.out.format("====================== stmt -> ID(%s) LPAREN multiExp RPAREN SEMICOLON\n", fn);
		if(var!=null) 
			if(exps==null) System.out.format("====================== stmt -> var DOT ID(%s) LPAREN RPAREN SEMICOLON\n", fn);
			else System.out.format("====================== stmt -> var DOT ID(%s) LPAREN multiExp RPAREN SEMICOLON\n", fn);
		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.var = var;
		this.fn = fn;
		this.exps = exps;
	}

	/*********************************************************/
	/* The printing message for a variable function AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = STMT VAR FIELD AST node */
		/********************************************/
		System.out.print("AST NODE STMT VAR FUNC\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXPS ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (fn != null) System.out.format("FUNC NAME( %s )\n",fn);
		if (exps != null) exps.PrintMe();
		
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STMT\n VAR\n FUNC( %s )", fn));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exps != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exps.SerialNumber);
	}
	
	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE id_type;
		TYPE exp_type;
		if(!var){
			TYPE var_type = var.SemantMe();
			if(!var_type || !var_type.isClass()) return null;
			id_type = ((TYPE_CLASS) var_type).findInClassScope(id);
		} else {
			s.find(id);
		}
		if(!id_type || !id_type.isFunc()) return null;
		if(!exps){
			exp_type = exps.SemantMe();
			if(!exp_type) return null;
		} else {
			exp_type = null;
		}
		if(!((TYPE_FUNCTION) id_type).isSameArgs(exp_type)) return null;
		return ((TYPE_FUNCTION) id_type).returnType;
	}
	
}
