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

public class IRcommand_Load extends IRcommand
{
	TEMP dst;
	String var_name;
	String scope_type;
	int index;

	public IRcommand_Load(TEMP dst,String var_name, String scope_type, int index)
	{
		this.dst      = dst;
		this.var_name = var_name;
		this.scope_type = scope_type;
		this.index = index;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(scope_type.equals("global"))
			MIPSGenerator.getInstance().load(dst,var_name);
		else if(scope_type.equals("param"))
			MIPSGenerator.getInstance().lw(dst,"$fp",(index+2)*4);
		else if(scope_type.equals("local_func"))
			MIPSGenerator.getInstance().lw(dst,"$fp",(index+1)*-4);
		else if(scope_type.equals("local_class")){
			// first argument of a method is the class pointer
			MIPSGenerator.getInstance().lw(dst,"$fp",8);
			MIPSGenerator.getInstance().lw(dst,dst,(index+1)*4);
		}
	}
}
