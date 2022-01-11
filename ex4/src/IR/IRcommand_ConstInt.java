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

public class IRcommand_ConstInt extends IRcommand
{
	TEMP t;
	int value;
	
	public IRcommand_ConstInt(TEMP t,int value)
	{
		this.t = t;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().li(t.toString(),value);
	}
}
