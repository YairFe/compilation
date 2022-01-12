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

public class IRcommand_Binop_EQ_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	// change the name to equal values
	public IRcommand_Binop_EQ_Integers(TEMP dst,TEMP t1,TEMP t2)
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
		/*******************************/
		/* [1] Allocate 3 fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
		
		/******************************************/
		/* [2] if (t1==t2) goto label_AssignOne;  */
		/******************************************/
		MIPSGenerator.getInstance().beq(t1.toString(),t2.toString(),label_AssignOne);

		/*************************/
		/* [3] label_AssignZero: */
		/*                       */
		/*         t3 := 0       */
		/*         goto end;     */
		/*                       */
		/*************************/
		MIPSGenerator.getInstance().li(dst.toString(),0);
		MIPSGenerator.getInstance().jump(label_end);

		/************************/
		/* [4] label_AssignOne: */
		/*                      */
		/*         t3 := 1      */
		/*                      */
		/************************/
		MIPSGenerator.getInstance().label(label_AssignOne);
		MIPSGenerator.getInstance().li(dst.toString(),1);

		/******************/
		/* [5] label_end: */
		/******************/
		MIPSGenerator.getInstance().label(label_end);
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(t1);
		result.add(t2);
		result.remove(dst);
		if(result.value == null) return null;
		return result;
	}
}
