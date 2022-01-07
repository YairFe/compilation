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
	TEMP class;
	String field_name;
    TEMP value;
	
	public IRcommandConstInt(TEMP class, String field_name, TEMP value)
	{
		this.class = class;
		this.field_name = field_name;
        this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().sw(value,field_name /* offset */, class);
	}
}
