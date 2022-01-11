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
		MIPSGenerator.getInstance().lw(dst.toString(),array.toString(),0);
		// #TODO need to define abort
		MIPSGenerator.getInstance().bge(dst.toString(),index.toString(),"abort");
		// put in dst the offset value
		MIPSGenerator.getInstance().addi(dst.toString(),index.toString(),1);
		MIPSGenerator.getInstance().sll(dst.toString(),dst.toString(),2);
		// put in dst the address value
		MIPSGenerator.getInstance().add(dst.toString(),array.toString(),dst.toString());
		MIPSGenerator.getInstance().lw(dst.toString(),dst.toString(),0);
		
	}
}
