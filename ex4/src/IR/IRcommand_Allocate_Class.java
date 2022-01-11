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
		MIPSGenerator.getInstance().label_data(String.format("vt_%s",my_class.name));
		List<String> funcList = my_class.getFuncList();
		for(String func : funcList){
			String father_name = my_class.getClassNameWithAttribute(func);
			MIPSGenerator.getInstance().allocate_func(String.format("%s_%s",father_name,func));
		}
		// build class allocation function
		MIPSGenerator.getInstance().label_text(String.format("class_%s",my_class.name));
		MIPSGenerator.getInstance().malloc(my_class.getNumOfAttribute()+1);

		// saves virtual table to class pointer
		MIPSGenerator.getInstance().push_to_stack("$s0");
		MIPSGenerator.getInstance().la("$s0",String.format("vt_%s",my_class.name));
		MIPSGenerator.getInstance().sw("$s0","$v0",0);
		MIPSGenerator.getInstance().popStackTo("$s0");
		// saves pointer to stack
		MIPSGenerator.getInstance().push_to_stack("$v0");

	}
}
