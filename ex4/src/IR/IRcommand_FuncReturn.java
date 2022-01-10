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
			MIPSGenerator.getInstance().mov("$v0",value);
		// pop out all local func variables from stack
		MIPSGenerator.getInstance().mov("$sp","$fp");
		// pop fp from stack
		MIPSGenerator.getInstance().popStackTo("$fp");
		// pop return address from stack
		MIPSGenerator.getInstance().popStackTo("$ra");
		// jump to the return address
		MIPSGenerator.getInstance().jr();
	}
}
