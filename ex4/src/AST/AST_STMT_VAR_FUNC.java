package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_STMT_VAR_FUNC extends AST_STMT {
	
	public AST_VAR var;
	public String fn;
	public AST_EXP_LIST exps;
	public TYPE_CLASS class_type;
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
			this.class_type = ((TYPE_CLASS) var_type);
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
	// statement will never be assigned to var so dont need to assign temp
	public TEMP IRme(){
		if(var == null){
			if(this.exps == null)
				IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(null,fn,null));
			else {
				TEMP_LIST args = this.exps.IRme();
				if(fn.equals("PrintInt") && args != null && args.next == null){
					IR.getInstance().Add_IRcommand(new IRcommand_PrintInt(args.value));
				}else if(fn.equals("PrintString") && args != null && args.next == null){
					IR.getInstance().Add_IRcommand(new IRcommand_PrintString(args.value));
				} else {
					IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(null,fn,args));
				}
			}
		} else {
			TEMP t1 = var.IRme();
			int func_index = class_type.getFuncIndex(fn);
			if(this.exps == null)
				IR.getInstance().Add_IRcommand(new IRcommand_ClassVirtualCall(null,t1,fn,null,func_index));
			else{
				TEMP_LIST args = this.exps.IRme();
				IR.getInstance().Add_IRcommand(new IRcommand_ClassVirtualCall(null,t1,fn,args,func_index));
			}
		}
		return null;
	}
}
