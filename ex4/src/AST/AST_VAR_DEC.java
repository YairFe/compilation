package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_VAR_DEC extends AST_Node {

	public AST_Type type;
	public String id;
	public AST_EXP exp;
	public AST_NEW_EXP newexp;
	public String scope_type;
	public int index;
	public TYPE_CLASS var_class;
	
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
		if(s.shadowingVariable(id,var_type)) return new TYPE_ERROR(type.line);
		if(s.existInScope(id)) return new TYPE_ERROR(type.line);
		if(exp != null) exp_type = exp.SemantMe();
		else if(newexp != null) exp_type = newexp.SemantMe();

		if(exp_type != null){
			if(exp_type.isError()) return exp_type;
			if(!s.canAssignValueToVar(var_type,exp_type)) return new TYPE_ERROR(type.line);
		}
		s.enter(id,var_type); 

		if(exp != null && !s.canAssignExpToVar(var_type,exp)) return new TYPE_ERROR(type.line);
		else if(newexp != null && !s.canAssignExpToVar(var_type,newexp)) return new TYPE_ERROR(type.line);

		this.scope_type = s.getVarScope(id);
		if(this.scope_type.equals("local_func")){
			this.index = s.getLocalIndex(id);
		} else if(this.scope_type.equals("local_class")){
			this.index = s.getAttributeIndex(id);
		}
		
		return new TYPE_CLASS_VAR_DEC(var_type,id);
	}
	public TEMP IRme(){
		/* if the variable is global need to set it in data section */
		if(this.scope_type.equals("global")){
			if(this.type.t == 1){ // type int
				// we expect contant int when initiallize global int
				int value = 0;
				if(exp != null)
					value = ((AST_EXP_INT) exp).value;
				IR.getInstance().Add_IRcommand(new IRcommand_Allocate(id,value));	
			} else if(this.type.t == 2)	{ // type string
				// we expect contant string when initiallize global string
				String value = "";
				if(exp != null)
					value = ((AST_EXP_STRING) exp).value;
				IR.getInstance().Add_IRcommand(new IRcommand_Allocate(id,value));	
			} else { // pointer
				// we expect nil value when initiallize global pointer
				IR.getInstance().Add_IRcommand(new IRcommand_Allocate(id,0));	
			}	
		} else{
			TEMP t1;
			if(exp != null) t1 = exp.IRme();
			else if (newexp != null) t1 = exp.IRme();
			else t1 = null;
			if(this.scope_type.equals("local_func")){
				// #TODO need to add IRcommand for declaring local func argument 
				// which increase the stack and by that declare local func var
				IR.getInstance().Add_IRcommand(new IRcommand_Store(id,t1,this.scope_type,this.index));
			} else if(this.scope_type.equals("local_class"))
				IR.getInstance().Add_IRcommand(new IRcommand_ClassFieldSet(null,id,t1,this.index));		
		}	
		return null;
	}
}
