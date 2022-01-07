package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_STMT_VAR_FUNC extends AST_STMT {
	
	public AST_VAR var;
	public String fn;
	public AST_EXP_LIST exps;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_VAR_FUNC(int line, AST_VAR var, String fn, AST_EXP_LIST exps) 
	{
		super(line);
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
		TYPE id_type = null;
		TYPE exp_type = null;
		if(var != null){
			TYPE var_type = var.SemantMe();
			if(var_type.isError()){
				return var_type;
			} else if(!var_type.isClass()){
				return new TYPE_ERROR(line);
			}
			id_type = ((TYPE_CLASS) var_type).findInClassScope(fn);
		} else {
			id_type = s.find(fn);
		}
		if(id_type == null || !id_type.isFunc()) return new TYPE_ERROR(line);
		if(exps != null){
			exp_type = exps.SemantMe();
			if(exp_type.isError()) return exp_type;
		} else {
			exp_type = null;
		}
		if(!((TYPE_FUNCTION) id_type).isSameArgs((TYPE_LIST) exp_type)) return new TYPE_ERROR(line);
		return ((TYPE_FUNCTION) id_type).returnType;
	}

	public TEMP IRme(){
		// statement will never be assigned to var so no need assign temp
		if(var == null){
			if(multiExp == null) {
				IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(null,fn,null));
			} else {
				TEMP_LIST args = multiExp.IRme();
				IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(null,fn,args));
			}
		} else {
			TEMP class = var.IRme();
			if(multiExp == null) {
				IR.getInstance().Add_IRcommand(new IRcommand_ClassVirtualCall(null,class,fn,null));
			} else {
				TEMP_LIST args = multiExp.IRme();
				IR.getInstance().Add_IRcommand(new IRcommand_ClassVirtualCall(null,class,fn,args));
			}
		}
		return null;
	}
	
}
