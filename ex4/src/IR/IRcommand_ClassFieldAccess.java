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
	TEMP class;
	String field_name;
	TEMP dst;

	public IRcommand_ClassFieldAccess(TEMP dst, TEMP class, String field_name)
	{
		this.dst = dst;
		this.class = class;
		this.field_name = field_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO
	}
}
