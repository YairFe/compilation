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

public class IRcommand_FuncReturn extends IRcommand
{
	TEMP value;
	
	public IRcommand_FuncReturn(TEMP value)
	{
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(value != null)
			// move value to $v0
			MIPSGenerator.getInstance().setFuncResult(value);
		// jump to the address in $ra
		MIPSGenerator.getInstance().lw("$ra","$fp",4);
		MIPSGenerator.getInstance().lw("$fp","$fp",0);
		MIPSGenerator.getInstance().jr();
	}
}
