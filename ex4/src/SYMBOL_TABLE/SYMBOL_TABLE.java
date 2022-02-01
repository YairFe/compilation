/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;
import AST.*;
/****************/
/* SYMBOL TABLE */
/****************/
public class SYMBOL_TABLE
{
	private int hashArraySize = 100;
	
	/**********************************************/
	/* The actual symbol table data structure ... */
	/**********************************************/
	private SYMBOL_TABLE_ENTRY[] table = new SYMBOL_TABLE_ENTRY[hashArraySize];
	private SYMBOL_TABLE_ENTRY top;
	private int top_index = 0;
	private int cur_scope_depth = 0;
	public TYPE_CLASS curClass = null;
	private TYPE returnType = null;
	public int func_local_index; // the local index of a var inside a function
	private int param_local_index; // the local index of a param in a function 

	/**************************************************************/
	/* A very primitive hash function for exposition purposes ... */
	/**************************************************************/
	private int hash(String s)
	{
		return Math.abs(s.hashCode()) % hashArraySize;
	}

	/****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */
	/****************************************************************************/
	public void enter(String name,TYPE t)
	{
		if (this.cur_scope_depth == 0) this.enter(name,t,"global");
		else this.enter(name,t,"local");
	}
	/****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */
	/* var_scope can be one of this values: "global", "local", "param"			*/
	/****************************************************************************/
	public void enter(String name,TYPE t,String var_scope)
	{
		/*************************************************/
		/* [1] Compute the hash value for this new entry */
		/*************************************************/
		int hashValue = hash(name);

		/******************************************************************************/
		/* [2] Extract what will eventually be the next entry in the hashed position  */
		/*     NOTE: this entry can very well be null, but the behaviour is identical */
		/******************************************************************************/
		SYMBOL_TABLE_ENTRY next = table[hashValue];
	
		/**************************************************************************/
		/* [3] Prepare a new symbol table entry with name, type, next and prevtop */
		/**************************************************************************/
		int local_index = 0;
		if(var_scope.equals("param"))
			local_index = param_local_index++;
		else if(var_scope.equals("global"))
			local_index = 0;
			// the index doesn't matter if this is global var
		else if(var_scope.equals("local")){
			if(returnType != null){
				var_scope = "local_func";
				if(!t.isFunc() && !(name.equals("SCOPE-BOUNDARY")))
					local_index = func_local_index++;
			} else {
				var_scope = "local_class";
			}
		}

		SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(local_index,var_scope,name,t,hashValue,next,cur_scope_depth,top,top_index++);

		/**********************************************/
		/* [4] Update the top of the symbol table ... */
		/**********************************************/
		top = e;
		
		/****************************************/
		/* [5] Enter the new entry to the table */
		/****************************************/
		table[hashValue] = e;
		
		if(curClass != null && cur_scope_depth==1 && !curClass.name.equals(name) && !(t instanceof TYPE_FOR_SCOPE_BOUNDARIES)){
			curClass.data_members = new TYPE_CLASS_VAR_DEC_LIST(
												new TYPE_CLASS_VAR_DEC(t,name),
												curClass.data_members);
		}

		/**************************/
		/* [6] Print Symbol Table */
		/**************************/
		PrintMe();
	}
	/*****************************************/
	/* return true if the variable is global */
	/*****************************************/
	public String getVarScope(String name){
		for (SYMBOL_TABLE_ENTRY e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name))
			{	
				return e.var_scope;
			}
		}
		return null;
	}
	/****************************************************/
	/* get the local index of the variable              */
	/****************************************************/
	public int getLocalIndex(String name){
		for (SYMBOL_TABLE_ENTRY e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name))
			{	
				return e.local_index;
			}
		}
		return -1;
	}
	public String getClassNameWithAttribute(String funcName){
		return this.curClass.getClassNameWithAttribute(funcName);
	}
	/****************************************************/
	/* get the attribute index in a class               */
	/****************************************************/
	public int getAttributeIndex(String name){
		return curClass.getAttributeIndex(name);
	}
	/****************************************************/
	/* get the func index in a class               */
	/****************************************************/
	public int getFuncIndex(String name){
		return curClass.getFuncIndex(name);
	}
	/****************************************************/
	/* Find the inner-most scope element with name 		*/
	/* if we are in class context then look at 			*/
	/* child scope then fathers scope then Global 		*/
	/****************************************************/
	public TYPE find(String name) 
	{
		SYMBOL_TABLE_ENTRY e;
		TYPE result_type = null;
		for (e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name))
			{	
				/* scope_depth will be zero if there is only 	**
				** a global variable with this name				*/
				if ((curClass != null) && (e.scope_depth == 0)){
					break;
				}
				return e.type;
			}
		}
		if (curClass != null){
			/* look for the name in the class scope	*/
			if (curClass != null)
				result_type = curClass.findInClassScope(name);
			if (result_type != null){
				return result_type;
			} else if (e != null){
				return e.type;
			}
		}
		return null;
	}
	/************************************************************/
	/* function that check if value can be assign to var		*/
	/* with the following conditions							*/
	/* nil can be assign to both array and class type			*/
	/* value class is a child class of var class				*/
	/* var and value are array type with the same type			*/
	/* value is a function with return value of var type		*/
	/* value and var are of the same type						*/
	/************************************************************/
	public boolean canAssignValueToVar(TYPE var, TYPE value){
		if(value.isNil())
			if(var.isClass() || var.isArray() || var.isNil())
				return true;
			else
				return false;
		if(var.isClass() && value.isClass())
			return ((TYPE_CLASS) var).isFatherOf((TYPE_CLASS) value);
		if(var.isArray() && value.isArray()){
			TYPE_ARRAY arr1 = (TYPE_ARRAY) var;
			TYPE_ARRAY arr2 = (TYPE_ARRAY) value;
			if(arr2.name == null){
				if(arr1.array_type.isClass() && arr2.array_type.isClass())
					return ((TYPE_CLASS) arr1.array_type).isFatherOf((TYPE_CLASS) arr2.array_type);
				return arr1.array_type.name.equals(arr2.array_type.name);
			}
			return arr1.name.equals(arr2.name);
		}
		if(value.isFunc())
			value = ((TYPE_FUNCTION) value).returnType;
		return var.name.equals(value.name);
	}

	public boolean shadowingVariable(String id, TYPE id_type){
		if(curClass != null && cur_scope_depth == 1) {
			TYPE d = curClass.findInClassScope(id);
			if(d != null) {
				// found match with a different type name
				if(!d.name.equals(id_type.name)) return true; 
			}
		}
		return false;
	}
	/************************************************************/
	/* function that check if exp can be assign to var			*/
	/* when we are at class scope								*/
	/* with the following conditions							*/
	/* class type vaiables can only be assign with nil			*/
	/* int type variables can only be assign with const			*/
	/* string type can only be assign with string const			*/
	/* array cant be declared in class so not implemented		*/
	/************************************************************/
	public boolean canAssignExpToVar(TYPE var, AST_Node exp){
		// this policy is only for class variables declarations
		if(curClass != null && cur_scope_depth == 1){
			// cant use new exp at the class declaration scope
			if(exp instanceof AST_NEW_EXP) return false;
			if(var.isClass())
				return exp instanceof AST_EXP_NIL;
			if(var == TYPE_INT.getInstance())
				return exp instanceof AST_EXP_INT;
			if(var == TYPE_STRING.getInstance())
				return exp instanceof AST_EXP_STRING;
		}
		return true;	
	}
	/************************************************************/
	/* function which check if the type provided can be 		*/
	/* returned by the function in the scope					*/
	/* if not in function scope return false					*/
	/************************************************************/
	public boolean canReturnType(TYPE other){
		if (this.returnType == null)
			return false;
		return canAssignValueToVar(this.returnType, other);
	}
	/************************************************************/
	/* function to look if the name exist in current scope		*/
	/* use to check if we can declare the variable on the scope	*/
	/************************************************************/
	public boolean existInScope(String name)
	{
		SYMBOL_TABLE_ENTRY  e;
		for (e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name))
			{	
				return e.scope_depth == cur_scope_depth;
			}
		}
		return false;
	}
	/***************************************************************************/
	/* begin scope = Enter the <SCOPE-BOUNDARY> element to the data structure */
	/***************************************************************************/
	public void beginScope()
	{
		/************************************************************************/
		/* Though <SCOPE-BOUNDARY> entries are present inside the symbol table, */
		/* they are not really types. In order to be ablt to debug print them,  */
		/* a special TYPE_FOR_SCOPE_BOUNDARIES was developed for them. This     */
		/* class only contain their type name which is the bottom sign: _|_     */
		/************************************************************************/
		enter(
			"SCOPE-BOUNDARY",
			new TYPE_FOR_SCOPE_BOUNDARIES("NONE"));
		cur_scope_depth++;
		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}
	/********************************************************************/
	/* begin class scope 												*/
	/* Enter the <SCOPE-BOUNDARY> element to the data structure 		*/
	/* initializing class context parameters: 							*/
	/* curClass an instance of the type of the father				*/
	/* classContext value which indicate that we're in class scope		*/
	/********************************************************************/
	public void beginClassScope(TYPE_CLASS curClass)
	{
		beginScope();
		this.curClass = curClass;

	}
	/********************************************************************/
	/* begin function scope												*/
	/* Enter the <SCOPE-BOUNDARY> element to the data structure 		*/
	/* initializing function type parameters: 							*/
	/* returnType an instance of the return type of the function		*/
	/********************************************************************/
	public void beginFuncScope(TYPE returnType)
	{
		// we can do it because there isn't nested functions
		this.func_local_index = 0; 
		// if we are in class scope then class pointer is the first parameter
		this.param_local_index = curClass != null ? 1 : 0; 
		beginScope();
		this.returnType = returnType;

	}
	/********************************************************************************/
	/* end scope = Keep popping elements out of the data structure,                 */
	/* from most recent element entered, until a <NEW-SCOPE> element is encountered */
	/********************************************************************************/
	public void endScope()
	{
		/**************************************************************************/
		/* Pop elements from the symbol table stack until a SCOPE-BOUNDARY is hit */		
		/**************************************************************************/
		while (top.name != "SCOPE-BOUNDARY")
		{
			table[top.index] = top.next;
			top_index = top_index-1;
			top = top.prevtop;
		}
		/**************************************/
		/* Pop the SCOPE-BOUNDARY sign itself */		
		/**************************************/
		table[top.index] = top.next;
		top_index = top_index-1;
		top = top.prevtop;
		cur_scope_depth--;
		/*********************************************/
		/* Print the symbol table after every change */		
		/*********************************************/
		PrintMe();
	}
	/************************************************************/
	/* end class scope and enter the class to symbol table   	*/
	/************************************************************/
	public void endClassScope()
	{
		endScope();
		enter(curClass.name,curClass);
		this.curClass = null;
	}
	public void endFuncScope(){
		endScope();
		this.returnType = null;
	}

	public static int n=0;
	
	public void PrintMe()
	{
		int i=0;
		int j=0;
		String dirname="./output/";
		String filename=String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

		try
		{
			/*******************************************/
			/* [1] Open Graphviz text file for writing */
			/*******************************************/
			PrintWriter fileWriter = new PrintWriter(dirname+filename);

			/*********************************/
			/* [2] Write Graphviz dot prolog */
			/*********************************/
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			/*******************************/
			/* [3] Write Hash Table Itself */
			/*******************************/
			fileWriter.print("hashTable [label=\"");
			for (i=0;i<hashArraySize-1;i++) { fileWriter.format("<f%d>\n%d\n|",i,i); }
			fileWriter.format("<f%d>\n%d\n\"];\n",hashArraySize-1,hashArraySize-1);
		
			/****************************************************************************/
			/* [4] Loop over hash table array and print all linked lists per array cell */
			/****************************************************************************/
			for (i=0;i<hashArraySize;i++)
			{
				if (table[i] != null)
				{
					/*****************************************************/
					/* [4a] Print hash table array[i] -> entry(i,0) edge */
					/*****************************************************/
					fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n",i,i);
				}
				j=0;
				for (SYMBOL_TABLE_ENTRY it=table[i];it!=null;it=it.next)
				{
					/*******************************/
					/* [4b] Print entry(i,it) node */
					/*******************************/
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
						it.name,
						it.type.name,
						it.prevtop_index);

					if (it.next != null)
					{
						/***************************************************/
						/* [4c] Print entry(i,it) -> entry(i,it.next) edge */
						/***************************************************/
						fileWriter.format(
							"node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
							i,j,i,j+1);
						fileWriter.format(
							"node_%d_%d:f3 -> node_%d_%d:f0;\n",
							i,j,i,j+1);
					}
					j++;
				}
			}
			fileWriter.print("}\n");
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static SYMBOL_TABLE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected SYMBOL_TABLE() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static SYMBOL_TABLE getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new SYMBOL_TABLE();

			/*****************************************/
			/* [1] Enter primitive types int, string */
			/*****************************************/
			instance.enter("int",   TYPE_INT.getInstance());
			instance.enter("string",TYPE_STRING.getInstance());

			/*************************************/
			/* [2] How should we handle void ??? */
			/*************************************/

			/***************************************/
			/* [3] Enter library function PrintInt */
			/***************************************/
			instance.enter(
				"PrintInt",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintInt",
					new TYPE_LIST(
						TYPE_INT.getInstance(),
						null)));
			instance.enter(
				"PrintString",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintString",
					new TYPE_LIST(
						TYPE_STRING.getInstance(),
						null)));
			instance.enter(
				"PrintTrace",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintTrace",
					null));
			
		}
		return instance;
	}
}
