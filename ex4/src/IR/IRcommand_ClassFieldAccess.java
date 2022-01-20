/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;

public class IRcommand_ClassFieldAccess extends IRcommand
{
	TEMP my_class;
	String field_name;
	TEMP dst;
	int index;

	public IRcommand_ClassFieldAccess(TEMP dst, TEMP my_class, String field_name,int index)
	{
		this.dst = dst;
		this.my_class = my_class;
		this.field_name = field_name;
		this.index = index;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String abort = getFreshLabel("abort");
		String end_label = getFreshLabel("end_label");

		MIPSGenerator.getInstance().beqz(my_class.toString(), abort);
		MIPSGenerator.getInstance().lw(dst.toString(), my_class.toString(),(index+1)*4);
		// abort function
		MIPSGenerator.getInstance().jump(end_label);
		MIPSGenerator.getInstance().label(abort);
		MIPSGenerator.getInstance().print_string("string_invalid_ptr_dref");
		MIPSGenerator.getInstance().exit();
		MIPSGenerator.getInstance().label(end_label);
	}

	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(my_class);
		result.remove(dst);
		return result;
	}
}
