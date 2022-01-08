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
		int offset = (index+1)*4
		if(my_class != null)
			MIPSGenerator.getInstance().sw(value, my_class, offset);
		else
			// my_class is null only when initiallizing class attributes
			MIPSGenerator.getInstance().sw(src,"$v0",offset);

	}
}
