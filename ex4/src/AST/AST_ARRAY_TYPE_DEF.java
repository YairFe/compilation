package AST; import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_ARRAY_TYPE_DEF extends AST_Node {
	public String id;
	public AST_Type type;
	
	
	public AST_ARRAY_TYPE_DEF(int line, String id, AST_Type type) {
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== arrayTypedef -> ARRAY ID( %s ) EQ type LBRACK RBRACK SEMICOLON", id);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.id = id;
		this.type = type;
	}
	
	/***********************************************/
	/* The default message for an arrayTypedef AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = ARRAY TYPE DEF NODE */
		/************************************/
		System.out.print("AST NODE ARRAY TYPE DEF\n");

		/*****************************/
		/* RECURSIVELY PRINT TYPE ... */
		/*****************************/
		if (type != null) type.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ARRAY TYPE DEF( %s )\n", id));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		
	}
	
	public TYPE SemantMe() {
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		/* check that the array name is available.
		 * Note that we should only check the local scope,
		 * since arrays can only be defined in the global scope. */
		if (s.existInScope(id)) return new TYPE_ERROR(type.line);
		// if (s.curClass != null) return null; // arrays cannot be defined in class context
		
		TYPE t = type.SemantMe();
		if (t.isError()) return t;
		else if(t.name.equals("void")) return new TYPE_ERROR(type.line);
		// semantic analysis successful, create symbol table entry
		TYPE_ARRAY t1 = new TYPE_ARRAY(id, t);
		s.enter(id, t1);
		return t1; // can return void as well
	}
}
