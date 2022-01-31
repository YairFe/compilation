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

public class IRcommand_ArrayNew extends IRcommand
{
	TEMP dst;
	TEMP size;
	
	public IRcommand_ArrayNew(TEMP dst,TEMP size)
	{
		this.dst = dst;
		this.size = size;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().label(String.format("IR_ArrayNew_%d", this.dst.getSerialNumber()));
		MIPSGenerator.getInstance().addi("$a0",size.toString(),1);
		MIPSGenerator.getInstance().sll("$a0","$a0",2);
		MIPSGenerator.getInstance().malloc();
		MIPSGenerator.getInstance().mov(dst.toString(),"$v0");
		MIPSGenerator.getInstance().sw(size.toString(),dst.toString(),0);
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(size);
		result.add(dst);
		return result;
	}
}
