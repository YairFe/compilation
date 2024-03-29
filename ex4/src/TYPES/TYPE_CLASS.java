package TYPES;
import java.util.*;
import MIPS.*;

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
		for (TYPE_CLASS_VAR_DEC_LIST e=this.data_members;e!=null;e=e.tail){
			if (e.head.name.equals(name)){
				return e.head.t;
			}
		}
		if (this.father != null)
			return this.father.findInClassScope(name);
		return null;
	}
	
	public boolean existInFatherScope(String name){
		if(this.father != null){
			TYPE_CLASS fatherClass = this.father;
			for (TYPE_CLASS_VAR_DEC_LIST e=fatherClass.data_members;e!=null;e=e.tail){
				if (e.head.name.equals(name)){
					return true;
				}
			}
			if (fatherClass.father != null)
				return fatherClass.father.existInFatherScope(name);
		}
		return false;
	}

	public boolean isFatherOf(TYPE_CLASS son){
		while(son != null){
			if(this.name.equals(son.name))
				return true;
			son = son.father;
		}
		return false;
	}
	public int getNumOfAttribute(){
		return this.getAttributeList().size();
	}
	/* 
	function to get the index of an attribute inside a class
	the class frame start with father attributes and then son attributes
	*/
	public int getAttributeIndex(String name){
		return this.getAttributeList().indexOf(name);
	}
	public int getFuncIndex(String name){
		return this.getFuncList().indexOf(name);
	}
	public String getClassNameWithAttribute(String name){
		for(TYPE_CLASS_VAR_DEC_LIST e=this.data_members;e!=null;e=e.tail){
			if(e.head.name.equals(name))
				return this.name;
		}
		return this.father.getClassNameWithAttribute(name);
	}
	
	public List<String> getFuncList(){
		List<String> lst;
		if(father != null){
			lst = father.getFuncList();
		} else {
			lst = new LinkedList<String>();
		}
		TYPE_CLASS_VAR_DEC_LIST tmp_list = null;
		for(TYPE_CLASS_VAR_DEC_LIST e=this.data_members;e!=null;e=e.tail){
			if(e.head.t.isFunc() && !lst.contains(e.head.name)){
				tmp_list = new TYPE_CLASS_VAR_DEC_LIST(e.head,tmp_list);
			}
		}
		for(TYPE_CLASS_VAR_DEC_LIST e=tmp_list;e!=null;e=e.tail){
			lst.add(String.format("%s",e.head.name));
		}
		return lst;
	}
	public LinkedList<String> getAttributeList(){
		LinkedList<String> lst;
		if(father != null){
			lst = father.getAttributeList();
		} else {
			lst = new LinkedList<String>();
		}
		TYPE_CLASS_VAR_DEC_LIST tmp_list = null;
		for(TYPE_CLASS_VAR_DEC_LIST e=this.data_members;e!=null;e=e.tail){
			if(!e.head.t.isFunc() && !lst.contains(e.head.name)){
				tmp_list = new TYPE_CLASS_VAR_DEC_LIST(e.head,tmp_list);
			}
		}
		for(TYPE_CLASS_VAR_DEC_LIST e=tmp_list;e!=null;e=e.tail){
			lst.add(String.format("%s",e.head.name));
		}
		System.out.println(lst.toString());
		return lst;
	}

	public boolean isClass () { return true; }
}
