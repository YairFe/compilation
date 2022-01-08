package AST;
import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*; import IR.*;

public class AST_VAR_LIST extends AST_Node {
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_Type type;
	public String head;
	public AST_VAR_LIST tail;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_LIST(int line, String head, AST_VAR_LIST tail, AST_Type type)
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.format("====================== multiVar -> type ID( %s ) COMMA multiVar\n", head);
		if (tail == null) System.out.format("====================== multiVar -> type ID( %s )     \n", head);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
		this.type = type;
	}
	
	/******************************************************/
	/* The printing message for an expression list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST VAR LIST */
		/**************************************/
		System.out.print("AST NODE VAR LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (type != null) type.PrintMe();
		if (head != null) System.out.format("ID: %s\n", head); 
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE s = SYMBOL_TABLE.getInstance();
		TYPE head_type;
		TYPE tail_type = null;
		head_type = type.SemantMe();
		if(head_type.isError()){
			return head_type;
		} else if(head_type.name.equals("void")){
			return new TYPE_ERROR(line);
		}
		if(s.existInScope(head)) return new TYPE_ERROR(line);
		s.enter(head,head_type,"param");
		if(tail != null){
			tail_type = tail.SemantMe();
			if(tail_type.isError()) return tail_type;
		}
		return new TYPE_LIST(head_type,(TYPE_LIST) tail_type);
	}

	public TEMP IRme(){
		return null;
	}
}
