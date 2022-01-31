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
		MIPSGenerator.getInstance().label(String.format("IR_ArrayAccess_%d", this.dst.getSerialNmber()));
		MIPSGenerator.getInstance().push_to_stack("$s0");
		// put in dst the size of the array
		MIPSGenerator.getInstance().bltz(index.toString(), "abort_array");
		MIPSGenerator.getInstance().lw("$s0",array.toString(),0);
		MIPSGenerator.getInstance().bge(index.toString(),"$s0","abort_array");
		// put in dst the offset value
		MIPSGenerator.getInstance().addi("$s0",index.toString(),1);
		MIPSGenerator.getInstance().sll("$s0","$s0",2);
		// put in dst the address value
		MIPSGenerator.getInstance().add("$s0",array.toString(),"$s0");
		MIPSGenerator.getInstance().lw(dst.toString(),"$s0",0);
		
		MIPSGenerator.getInstance().popStackTo("$s0");
	}
	
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(array);
		result.add(index);
		result.remove(dst);
		return result;
	}
}
