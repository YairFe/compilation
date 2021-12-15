package AST; import TYPES.*; import SYMBOL_TABLE.*;

public class AST_FUNC_DEC extends AST_Node {

	public AST_Type type;
	String id;
	AST_VAR_LIST vars;
	AST_STMT_LIST stmts;
	
	public AST_FUNC_DEC(int line, AST_Type type, String id, AST_VAR_LIST vars, AST_STMT_LIST stmts) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (vars == null) System.out.format("====================== funcDec -> type ID( %s ) LPAREN RPAREN LBRACE multiStmt RBRACE\n", id);
		if (vars != null) System.out.format("====================== funcDec -> type ID( %s ) LPAREN multiVar RPAREN LBRACE multiStmt RBRACE\n", id);

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
	
	public TYPE SemantMe() {
		TYPE_FUNCTION duplicate = null;
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		/* check that the function name is available in current scope */
		/* NOTE: should insert library functions into the global scope beforehand */
		TYPE t1 = null;
		TYPE t2 = null;
		TYPE t3 = null;
		
		if (s.existInScope(id)) return new TYPE_ERROR(type.line);
		// analyze type
		t1 = type.SemantMe();
		if(t1.isError()) return t1;
		// analyze parameters
		s.beginFuncScope(t1);
		if(vars != null) {
			t2 = vars.SemantMe();
			if(t2.isError()) return t2;
		}
		if(s.curClass != null) {
			TYPE d = s.curClass.findInClassScope(id);
			if(d != null) {
				// found match with a non-function name
				if(!d.isFunc()) return new TYPE_ERROR(type.line);  
				// otherwise, found match with a function name
				duplicate = (TYPE_FUNCTION) d; 
				 // method overloading found with different type
				if((duplicate != null) && (duplicate.returnType != t1)) return new TYPE_ERROR(type.line);
				 // method overloading found with different args types
				if((duplicate != null) && (!duplicate.isSameArgs((TYPE_LIST)t2))) return new TYPE_ERROR(type.line);
			}
		}				

		TYPE_FUNCTION t = new TYPE_FUNCTION(t1, id, (TYPE_LIST)t2);
		// temporarily enter t into the function scope - will be flushed on endScope()
		s.enter(id, t);
		t3 = stmts.SemantMe();
		if(t3.isError()) return t3;
		s.endFuncScope();
		s.enter(id, t);
		
		return t;
		
	}
	
}
