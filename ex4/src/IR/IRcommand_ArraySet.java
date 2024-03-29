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
		// save $s0
		MIPSGenerator.getInstance().push_to_stack("$s0");
		MIPSGenerator.getInstance().bltz(index.toString(), "abort_array");
		MIPSGenerator.getInstance().lw("$s0", arr.toString(), 0);
		MIPSGenerator.getInstance().bge(index.toString(), "$s0", "abort_array");
		MIPSGenerator.getInstance().mov("$s0", index.toString());
		MIPSGenerator.getInstance().add("$s0", "$s0", "1");
		MIPSGenerator.getInstance().mul("$s0", "$s0", "4");
		MIPSGenerator.getInstance().add("$s0", arr.toString(), "$s0");
		// here, $s0 has the address of the requested array element
		MIPSGenerator.getInstance().sw(value.toString(), "$s0", 0);
		// after saving the value, restore $s0
		MIPSGenerator.getInstance().popStackTo("$s0");
		
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(arr);
		result.add(index);
		result.add(value);
		return result;
	}
}
