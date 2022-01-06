package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_VAR_SIMPLE extends AST_VAR
{
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(int line, String name)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n( %s )",name));
	}

	public TYPE SemantMe(){
		// might need to add a check to id type is not func
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE id_type = s.find(name);
		if(id_type == null) return new TYPE_ERROR(line);
		if(s.isGlobal(name)) this.scope_type = "global";
		else { this.scope_type = "local"; this.index = s.getLocalIndex(name); }
		return id_type;
	}

	public TEMP IRme(){
		// doesn't have any ir command
		return null;
	}
}
