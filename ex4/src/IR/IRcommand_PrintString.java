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

public class IRcommand_PrintString extends IRcommand
{
	TEMP t;
	
	public IRcommand_PrintString(TEMP t)
	{
		this.t = t;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().mov("$a0",t.toString());
		MIPSGenerator.getInstance().print_string();
	}
}
