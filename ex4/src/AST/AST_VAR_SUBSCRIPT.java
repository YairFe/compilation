package AST;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(int line, AST_VAR var,AST_EXP subscript)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var LBRACK exp RBRACK\n");

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.var = var;
		this.subscript = subscript;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}

	public TYPE SemantMe(){
		TYPE var_type = var.SemantMe();
		if(var_type.isError())
			return var_type;
		else if(!(var_type.isArray()))
			return new TYPE_ERROR(line);
		TYPE exp_type = subscript.SemantMe();
		if(exp_type.isError())
			return exp_type;
		else if(!exp_type.name.equals("int"))
			return new TYPE_ERROR(line);
		return ((TYPE_ARRAY) var_type).array_type;
	}

	public TEMP IRme(){
		TEMP offset = exp.IRme();
		TEMP arr = var.IRme();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		if(arr == null){
			
		}
		IR.getInstance.Add_IRcommand(new IRcommand_ArrayAccess(dst,arr,offset));
		return dst;
	}
}
