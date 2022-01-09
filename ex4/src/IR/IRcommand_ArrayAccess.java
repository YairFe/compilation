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

public class IRcommand_ArrayAccess extends IRcommand
{
	TEMP dst;
    TEMP array;
    TEMP index;
	
	public IRcommand_ArrayAccess(TEMP dst, TEMP array, TEMP index)
	{
		this.dst = dst;
        this.array = array;
        this.index = index;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// put in dst the size of the array
		MIPSGenerator.getInstance().lw(dst,array,0);
		MIPSGenerator.getInstance().bge(dst,index,"abort");
		// put in dst the offset value
		MIPSGenerator.getInstance().addi(dst,index,1);
		MIPSGenerator.getInstance().sll(dst,dst,2);
		// put in dst the address value
		MIPSGenerator.getInstance().add(dst,array,dst);
		MIPSGenerator.getInstance().lw(dst,dst,0);
		
	}
}
