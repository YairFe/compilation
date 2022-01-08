package AST; import TYPES.*; import TEMP.*; import IR.*; import SYMBOL_TABLE.*;

public class AST_CLASS_DEC extends AST_Node {

	public String id1;
	public String id2;
	public AST_CLASS_CONT cont;
	public TYPE_CLASS decClass;

	public AST_CLASS_DEC(int line, String id1, AST_CLASS_CONT cont) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== classDec -> CLASS ID( %s ) LBRACE classCont RBRACE", id1);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.id1 = id1;
		this.id2 = null;
		this.cont = cont;
	}
	
	public AST_CLASS_DEC(int line, String id1, String id2, AST_CLASS_CONT cont) 
	{
		super(line);
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== classDec -> CLASS ID( %s ) EXTENDS ID( %s ) LBRACE classCont RBRACE", id1, id2);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.id1 = id1;
		this.id2 = id2;
		this.cont = cont;
	}
	
	/***********************************************/
	/* The default message for a classDec AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = CLASS DEC NODE */
		/************************************/
		System.out.print("AST NODE CLASS DEC\n");

		/*****************************/
		/* RECURSIVELY PRINT CONT ... */
		/*****************************/
		if (cont != null) cont.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		if( id2 == null) AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC( %s )\n", id1));
		if( id2 != null) AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC( %s ) EXTENDS ( %s )\n", id1, id2));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(cont != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cont.SerialNumber);
		
	}
	
	public TYPE SemantMe()
	{	
		
		/*************************/
		/* [0] Check Class Name */
		/*************************/
		if(SYMBOL_TABLE.getInstance().existInScope(id1)) { return new TYPE_ERROR(line); /* name already exists */ }
		
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		TYPE parent = null;	
		
		if(id2 != null) {
			// find parent class object
			parent = SYMBOL_TABLE.getInstance().find(id2);
			
			if ((parent == null) || !(parent.isClass())) return new TYPE_ERROR(line); // parent does not exist
		}
		
		

		/*************************************************************/
		/* [2] Temporarily enter class object into the class scope   */
		/*  (will be flushed on endClassScope()               
		 * NOTE: entering with null 3rd parameter, 
		 * because cont should be processed inside class context.     */
		/*************************************************************/
		TYPE_CLASS t = new TYPE_CLASS((TYPE_CLASS) parent, id1, null);
		SYMBOL_TABLE.getInstance().beginClassScope(t);
		SYMBOL_TABLE.getInstance().enter(id1, t);
		/*****************************/
		/* [4] Semant Data Members */
		/*****************************/
		TYPE c = cont.SemantMe();
		if(c.isError()) return c;

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endClassScope();
		this.decClass = t;
		// need to implement a function to return a list of method names
		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return TYPE_VOID.getInstance();		
	}
	
	public TEMP IRme() {
		// declare class ir command
		IR.getInstance().Add_IRcommand(new IRcommand_Label(this.id1));
		if(this.cont != null) this.cont.IRme();
		return null;
	}
}
