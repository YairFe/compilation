package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_VAR_DEC extends AST_Node {

	public AST_Type type;
	public String id;
	public AST_EXP exp;
	public AST_NEW_EXP newexp;
	
	public AST_VAR_DEC(int line, AST_Type type, String id) {
		super(line);
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
	
	public AST_VAR_DEC(int line, AST_Type type, String id, AST_EXP exp) {
		super(line);
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
	
	public AST_VAR_DEC(int line, AST_Type type, String id, AST_NEW_EXP newexp) {
		super(line);
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
		TYPE exp_type = null;
		TYPE var_type = type.SemantMe();
		if(var_type.isError()){
			return var_type;
		} else if(var_type.name.equals("void")){
			return new TYPE_ERROR(type.line);
		} 
		if(s.existInScope(id)) return new TYPE_ERROR(type.line);

		s.enter(id,var_type);

		if(exp != null) exp_type = exp.SemantMe();
		else if(newexp != null) exp_type = newexp.SemantMe();

		if(exp_type != null){
			if(exp_type.isError()) return exp_type;
			if(!s.canAssignValueToVar(var_type,exp_type)) return new TYPE_ERROR(type.line);
		} 
		return new TYPE_CLASS_VAR_DEC(var_type,id);
	}
	
}
