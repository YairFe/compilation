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

public class IRcommand_ClassVirtualCall extends IRcommand
{
	TEMP dst;
	TEMP class;
    String method_name;
    TEMP_LIST args;
	
	public IRcommand_ClassVirtualCall(TEMP dst, TEMP class, String name, TEMP_LIST args)
	{
		this.dst = dst;
		this.class = class;
        this.method_name = name;
        this.args = args;
	}
	
    public IRcommand_ClassVirtualCall(TEMP class, String name, TEMP_LIST args)
	{
		this.dst = null;
		this.class = class;
        this.method_name = name;
        this.args = args;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//TODO
	}
}
