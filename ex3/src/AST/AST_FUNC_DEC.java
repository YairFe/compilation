package AST; import TYPES.*; import SYMBOL_TABLE.*;

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
		if (stmts == null) System.out.format("====================== funcDec -> type ID( %s ) LPAREN multiVar RPAREN LBRACE RBRACE\n", id);
		if (stmts != null) System.out.format("====================== multiVar -> type ID( %s ) LPAREN multiVar RPAREN LBRACE multiStmt RBRACE\n", id);

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
		if (s.existInScope(id)) return null;
		
		/* if a class method, check for method overloading (same name, different type or params) */
		if(s.curClass != null) {
			TYPE d = s.curClass.findInClassScope(id);
			if(d != null) {
				if(!d.isFunc()) return null;  // found match with a non-function name
				duplicate = (TYPE_FUNCTION) d; // otherwise, found match with a function name
			}
		}
		
		TYPE t1 = null;
		TYPE t2 = null;
		TYPE t3 = null;
		
		
		// analyze type
		t1 = type.SemantMe();
		if(t1 == null) return null;
		if((duplicate != null) && (duplicate.returnType != t1)) return null; // method overloading found

		// analyze parameters
		if(vars != null) {
		t2 = vars.SemantMe();
		if(t2 == null) return null;
		if((duplicate != null) && (!duplicate.isSameArgs((TYPE_LIST)t2))) return null; // method overloading found
		}
		
		TYPE_FUNCTION t = new TYPE_FUNCTION(t1, id, (TYPE_LIST)t2);
		
		// analyze statements
		if(stmts != null) {
		/* only define a function scope if the body of the function is not empty */
		s.beginScope();
		// temporarily enter t into the function scope - will be flushed on endScope()
		s.enter(id, t);
		t3 = stmts.SemantMe();
		if(t3 == null) return null;
		s.endScope();
		}
		
		t = new TYPE_FUNCTION(t1, id, (TYPE_LIST)t2);
		s.enter(id, t);
		
		/* return value is irrelevant, like with class declarations(?) */
		return null;
		
	}
	
}
