package AST;
import SYMBOL_TABLE.*

public class AST_VAR_DEC extends AST_Node {

	public AST_Type type;
	public String id;
	public AST_EXP exp;
	public AST_NEW_EXP newexp;
	
	public AST_VAR_DEC(AST_Type type, String id) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== varDec -> type ID( %s ) SEMICOLON\n", id);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.type = type;
		this.id = id;
		this.exp = null;
		this.newexp = null;
	}
	
	public AST_VAR_DEC(AST_Type type, String id, AST_EXP exp) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== varDec -> type ID( %s ) ASSIGN exp SEMICOLON\n", id);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.type = type;
		this.exp = exp;
		this.id = id;
		this.newexp = null;
	}
	
	public AST_VAR_DEC(AST_Type type, String id, AST_NEW_EXP newexp) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== varDec -> type ID( %s ) ASSIGN newExp SEMICOLON\n", id);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.type = type;
		this.newexp = newexp;
		this.id = id;
		this.exp = null;
	}
	
	/***********************************************/
	/* The default message for a varDec AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = VAR DEC NODE */
		/************************************/
		System.out.print("AST NODE VAR DEC\n");

		/*****************************/
		/* RECURSIVELY PRINT EXP AND TYPE ... */
		/*****************************/
		if (type != null) type.PrintMe();
		if (exp != null) exp.PrintMe();
		if (newexp != null) newexp.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VAR DEC( %s )\n", id));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		if (newexp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,newexp.SerialNumber);
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE exp_type;
		TYPE var_type = type.SemantMe();
		if(!var_type || var_type.name.equals("void")) return null;
		if(s.existInScope(id)) return null;

		s.enter(id,var_type);

		if(exp) exp_type = exp.SemantMe();
		else exp_type = newExp.SemantMe();

		if(!exp_type) return null;

		if(!s.canAssignValueToVar(var_type,exp_type)) return null;
		return exp_type; //can return Void as the type
	}
	
}
