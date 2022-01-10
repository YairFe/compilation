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

public class IRcommand_Binop_EQ_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_EQ_Strings(TEMP dst,TEMP t1,TEMP t2)
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
        String label_start      = getFreshLabel("start");
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
        // assume not equal at the start
        MIPSGenerator.getInstance().li(String.format("Temp_%d", dst.getSerialNumber()),0);
        // label_start
        MIPSGenerator.getInstance().label(label_start);

		/* 
		#TODO compare between char and not words
		by lb $s0,$t1 and lb $s1,$t2 and then compare them 
		*/

        // if not equal end
		MIPSGenerator.getInstance().bne(String.format("Temp_%d", t1.getSerialNumber()),String.format("Temp_%d", t2.getSerialNumber()),label_end);

        // assign 1 because end of both the string and they are equals
        MIPSGenerator.getInstance().beqz(String.format("Temp_%d", t1.getSerialNumber()),label_AssignOne);

		MIPSGenerator.getInstance().jump(label_start);

        // label_AssignOne
		MIPSGenerator.getInstance().label(label_AssignOne);
		MIPSGenerator.getInstance().li(String.format("Temp_%d", dst.getSerialNumber()),1);

        

		MIPSGenerator.getInstance().label(label_end);
	}
}
