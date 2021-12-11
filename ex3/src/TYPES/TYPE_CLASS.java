package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_CLASS_VAR_DEC_LIST data_members;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_CLASS_VAR_DEC_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	public TYPE findInClassScope(String name){
		if (data_members == null)
			return null;
		for (TYPE_CLASS_VAR_DEC_LIST e=this.data_members;e!=null;e=e.tail){
			if (e.head.name.equals(name)){
				return e.head.t;
			}
		}
		if (this.father != null)
			return this.father.findInClassScope(name);
		return null;
	}
	
	public boolean isFatherOf(TYPE_CLASS son){
		while(son != null){
			if(this.name.equals(son.name))
				return true;
			son = son.father;
		}
		return false;
	}
	
	public boolean isClass () { return true; }
}
