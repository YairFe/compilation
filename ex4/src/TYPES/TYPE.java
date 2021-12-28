package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}

	/*************/
	/* isFunc() */
	/*************/
	public boolean isFunc(){ return false;}
	
	/*************/
	/* isError() */
	/*************/
	public boolean isError(){ return false;}

	/*************/
	/* isNil()	 */
	/*************/
	public boolean isNil() {return false;}
}
