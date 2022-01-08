package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*; import IR.*;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(int line, AST_VAR var,String name)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.var = var;
		this.name = name;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",name);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",name));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}

	public TYPE SemantMe(){
		TYPE var_type = var.SemantMe();
		if (var_type.isError())
			return var_type;
		else if(!var_type.isClass()) return new TYPE_ERROR(line);
		TYPE t = ((TYPE_CLASS) var_type).findInClassScope(name);
		if(t == null) return new TYPE_ERROR(line);
		this.scope_type = SYMBOL_TABLE.getInstance().getVarScope(name); 
		this.index = SYMBOL_TABLE.getInstance().getAttributeIndex(name);
		return t;
	}

	public TEMP IRme(){
		TEMP t = var.IRme();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_ClassFieldAccess(dst,t,name,index));
		return dst;
	}
}
