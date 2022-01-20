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

public class IRcommand_Binop_Mul_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Mul_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int max = 32767;
		int min = -32768;
		String label_end_max = getFreshLabel("label_end_max");
		String label_end_min = getFreshLabel("label_end_min");
		MIPSGenerator.getInstance().push_to_stack("$s0");
		MIPSGenerator.getInstance().mul(dst.toString(),t1.toString(),t2.toString());
		// if the division product greater than max assign max to dst
		MIPSGenerator.getInstance().li("$s0",max);
		MIPSGenerator.getInstance().ble(dst.toString(),"$s0",label_end_max);
		MIPSGenerator.getInstance().mov(dst.toString(),"$s0");
		MIPSGenerator.getInstance().jump(label_end_min);
		MIPSGenerator.getInstance().label(label_end_max);
		// if the division product less than min assign min to dst
		MIPSGenerator.getInstance().li("$s0",min);
		MIPSGenerator.getInstance().bge(dst.toString(),"$s0",label_end_min);
		MIPSGenerator.getInstance().mov(dst.toString(),"$s0");
		MIPSGenerator.getInstance().label(label_end_min);
		MIPSGenerator.getInstance().popStackTo("$s0");
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(t1);
		result.add(t2);
		result.remove(dst);
		return result;
	}
}
