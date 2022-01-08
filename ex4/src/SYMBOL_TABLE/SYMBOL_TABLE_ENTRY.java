/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/**********************/
/* SYMBOL TABLE ENTRY */
/**********************/
public class SYMBOL_TABLE_ENTRY
{
	/*********/
	/* index */
	/*********/
	int index;
	
	/********/
	/* name */
	/********/
	public String name;

	/******************/
	/* TYPE value ... */
	/******************/
	public TYPE type;

	/*********************************************/
	/* prevtop and next symbol table entries ... */
	/*********************************************/
	public SYMBOL_TABLE_ENTRY prevtop;
	public SYMBOL_TABLE_ENTRY next;

	/****************************************************/
	/* The prevtop_index is just for debug purposes ... */
	/****************************************************/
	public int prevtop_index;
	
	int scope_depth;
	int local_index;
	String var_scope;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public SYMBOL_TABLE_ENTRY(int local_index,String var_scope, String name, TYPE type, int index, SYMBOL_TABLE_ENTRY next, int scope_depth, SYMBOL_TABLE_ENTRY prevtop,
    int prevtop_index)
	{
		this.index = index;
		this.name = name;
		this.type = type;
		this.next = next;
		this.scope_depth = scope_depth;
		this.prevtop = prevtop;
		this.prevtop_index = prevtop_index;
		this.local_index = local_index;
		this.var_scope = var_scope;
	}
}
