package TYPES;
import SYMBOL_TABLE.*;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
	}
	public boolean isSameArgs(TYPE_LIST args){
		TYPE_LIST tmp = this.params;
		while(tmp != null && args != null){
			// shouldnt be null but check anyway
			if(tmp.head != null){
				if(!(SYMBOL_TABLE.getInstance().canAssignValueToVar(tmp.head,args.head)))
					return false;
			tmp = tmp.tail;
			args = args.tail;
			}
		}
		if(tmp == null && args == null)
			return true;
		return false;
	}
	/*************/
	/* isFunc() */
	/*************/
	public boolean isFunc(){ return true;}
}
