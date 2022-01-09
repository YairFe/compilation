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

public class IRcommand_Allocate_VT_Entry extends IRcommand
{
	String funcNames;

	public IRcommand_Allocate_VT_Entry(String funcName)
	{
		this.funcNames = funcNames;
	}
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocate_func(funcNames);
	}
}
