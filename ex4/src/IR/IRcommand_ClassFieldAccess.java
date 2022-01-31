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
		MIPSGenerator.getInstance().label(String.format("IR_ClassFieldAccess_%s_%d", this.field_name, this.dst.getSerialNumber()));
		MIPSGenerator.getInstance().beqz(my_class.toString(), "abort_pointer");
		MIPSGenerator.getInstance().lw(dst.toString(), my_class.toString(),(index+1)*4);
	}

	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(my_class);
		result.remove(dst);
		return result;
	}
}
