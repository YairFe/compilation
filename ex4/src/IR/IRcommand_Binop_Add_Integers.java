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

public class IRcommand_Binop_Add_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Add_Integers(TEMP dst,TEMP t1,TEMP t2)
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
		// #TODO need to define max / min values
		String label_end_max = getFreshLabel("label_end_max");
		String label_end_min = getFreshLabel("label_end_min");
		MIPSGenerator.getInstance().add(dst,t1,t2);
		// if the division product greater than max assign max to dst
		MIPSGenerator.getInstance().ble(dst,max,label_end_max);
		MIPSGenerator.getInstance().li(dst,max);
		MIPSGenerator.getInstance().label(label_end_max);
		// if the division product less than min assign min to dst
		MIPSGenerator.getInstance().bge(dst,min,label_end_min);
		MIPSGenerator.getInstance().li(dst,min);
		MIPSGenerator.getInstance().label(label_end_min);
	}
}
