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

public class IRcommand_ArraySet extends IRcommand
{
	TEMP arr;
	TEMP index;
    TEMP value;
	
	public IRcommand_ArraySet(TEMP arr, TEMP index, TEMP value)
	{
		this.arr = arr;
		this.index = index;
        this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO
	}
}
