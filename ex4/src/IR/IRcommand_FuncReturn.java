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
	static int count = 0;
	
	public IRcommand_FuncReturn(TEMP value)
	{
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().label(String.format("IR_FuncReturn_%d", count));
		count++;
		// move value to $v0
		if(value != null) MIPSGenerator.getInstance().mov("$v0",value.toString());	
		// pop out all local func variables from stack
		MIPSGenerator.getInstance().mov("$sp","$fp");
		// pop fp from stack
		MIPSGenerator.getInstance().popStackTo("$fp");
		// pop return address from stack
		MIPSGenerator.getInstance().popStackTo("$ra");
		// jump to the return address
		MIPSGenerator.getInstance().jr("$ra");
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(value);
		return result;
	}
}
