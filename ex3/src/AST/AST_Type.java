package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_Type extends AST_Node {

	public int t;
	public String id;

	public AST_Type(int line, int t) {
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		switch(t) {
		case 1: this.id = "TYPE_INT"; break;
		case 2: this.id = "TYPE_STRING"; break;
		case 3: this.id = "TYPE_VOID"; break;
		}
		System.out.format("====================== type -> %s \n", id);
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.t = t;
	}
	
	public AST_Type(int line, String id) {
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== type -> ID( %s )\n", id);
		
		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.t = 4;
		this.id = id;
	}
	
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = TYPE NODE */
		/************************************/
		System.out.format("AST NODE TYPE ( %s )\n", id);
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"TYPE\n");
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		switch(t){
			case 1: return TYPE_INT.getInstance();
			case 2: return TYPE_STRING.getInstance();
			case 3: return TYPE_VOID.getInstance();
			case 4: 
				TYPE t = s.find(id);
				if(t != null && (t.isClass() || t.isArray()))
					return t;
		}
		return new TYPE_ERROR(line);
	}
}
