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

public class IRcommand_ClassFieldSet extends IRcommand
{
	TEMP my_class;
	String field_name;
    TEMP value;
	int index;

	public IRcommand_ClassFieldSet(TEMP my_class, String field_name, TEMP value, int index)
	{
		this.my_class = my_class;
		this.field_name = field_name;
        this.value = value;
		this.index = index;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int offset = (index+1)*4;
		String abort = getFreshLabel("abort");
		String end_label = getFreshLabel("end_label");

		MIPSGenerator.getInstance().beqz(my_class.toString(), abort);

		if(my_class != null)
			MIPSGenerator.getInstance().sw(value.toString(), my_class.toString(), offset);
		else{ // my_class is null only when initiallizing class attributes
			MIPSGenerator.getInstance().push_to_stack("$s0");
			// we expect the sp to point to class pointer
			MIPSGenerator.getInstance().lw("$s0","$sp",4);
			if(value != null){
				MIPSGenerator.getInstance().sw(value.toString(),"$s0",offset);
			} else {
				MIPSGenerator.getInstance().sw("$zero","$s0",offset);
			}
			MIPSGenerator.getInstance().popStackTo("$s0");
		}
		// abort function
		MIPSGenerator.getInstance().jump(end_label);
		MIPSGenerator.getInstance().label(abort);
		MIPSGenerator.getInstance().la("$a0","string_invalid_ptr_dref");
		MIPSGenerator.getInstance().print_string();
		MIPSGenerator.getInstance().exit();
		MIPSGenerator.getInstance().label(end_label);
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(my_class);
		result.add(value);
		return result;
	}
}
