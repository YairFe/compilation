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
	static int count = 0;

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
			
		MIPSGenerator.getInstance().label(String.format("IR_ClassFieldSet_%s_%d", this.field_name, count));
		count++;
		if(my_class != null){
			MIPSGenerator.getInstance().beqz(my_class.toString(), "abort_pointer");
			MIPSGenerator.getInstance().sw(value.toString(), my_class.toString(), offset);
		} else{ // my_class is null only when initiallizing class attributes
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
		
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(my_class);
		result.add(value);
		return result;
	}
}
