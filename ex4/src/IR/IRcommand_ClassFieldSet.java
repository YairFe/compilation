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
			
		if(my_class != null){
			MIPSGenerator.getInstance().beqz(my_class.toString(), "abort_pointer");
			MIPSGenerator.getInstance().sw(value.toString(), my_class.toString(), offset);
		} else{ // my_class is null only when initiallizing class attributes
			if(value != null){
				MIPSGenerator.getInstance().sw(value.toString(),"$a0",offset);
			} else {
				MIPSGenerator.getInstance().sw("$zero","$a0",offset);
			}
		}
		
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(my_class);
		result.add(value);
		return result;
	}
}
