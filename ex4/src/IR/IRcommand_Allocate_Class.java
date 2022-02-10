/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.util.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;
import TYPES.*;

public class IRcommand_Allocate_Class extends IRcommand
{
	TYPE_CLASS my_class;

	public IRcommand_Allocate_Class(TYPE_CLASS my_class)
	{
		this.my_class = my_class;
	}
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{	
		// build virtual table
		List<String> funcList = my_class.getFuncList();
		System.out.format("class:%s list:%s\n",my_class.name,funcList.toString());
		if(!funcList.isEmpty()){
			MIPSGenerator.getInstance().label_data(String.format("vt_%s",my_class.name));
			for(String func : funcList){
				String declared_in_class = my_class.getClassNameWithAttribute(func);
				MIPSGenerator.getInstance().allocate_func(String.format("%s_%s",declared_in_class,func));
			}
		}
		// build class allocation function
		MIPSGenerator.getInstance().label_text(String.format("class_%s",my_class.name));
		MIPSGenerator.getInstance().malloc((my_class.getNumOfAttribute()+1)*4);

		// saves virtual table to class pointer
		MIPSGenerator.getInstance().push_to_stack("$s0");
		if(!funcList.isEmpty())
			MIPSGenerator.getInstance().la("$s0",String.format("vt_%s",my_class.name));
		else
			MIPSGenerator.getInstance().mov("$s0", "$zero");
		MIPSGenerator.getInstance().sw("$s0","$v0",0);
		MIPSGenerator.getInstance().popStackTo("$s0");
		MIPSGenerator.getInstance().push_to_stack("$v0");
		
		// saves pointer to stack
		MIPSGenerator.getInstance().mov("$a0", "$v0");
		MIPSGenerator.getInstance().label(String.format("initialize_class_%s",this.my_class.name));
		MIPSGenerator.getInstance().push_to_stack("$ra");
		if(this.my_class.father != null){
			MIPSGenerator.getInstance().jal(String.format("initialize_class_%s",this.my_class.father.name));
		}

	}
}
