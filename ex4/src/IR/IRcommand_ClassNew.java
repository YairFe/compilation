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

public class IRcommand_ClassNew extends IRcommand
{
	TEMP dst;
	String name;
	
	public IRcommand_ClassNew(TEMP dst, String name)
	{
		this.dst = dst;
		this.name = name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{		
		// jump to class allocation function
		MIPSGenerator.getInstance().jal(name);
		// move the pointer of the new class to dst
		MIPSGenerator.getInstance().mov(dst,"$v0");
	}
}
