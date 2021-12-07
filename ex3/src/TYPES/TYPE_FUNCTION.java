package TYPES;

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
		while(tmp != null){
			if(tmp.head != null && !tmp.head.name.equals(args.head.name))
				return false;
			tmp = tmp.tail;
		}
		if(params == null && args == null)
			return true;
		return false;
	}
	/*************/
	/* isFunc() */
	/*************/
	public boolean isFunc(){ return true;}
}
