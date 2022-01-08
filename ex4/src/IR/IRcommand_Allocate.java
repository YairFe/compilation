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

public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	int var_value_word;
	String var_value_string;

	public IRcommand_Allocate(String var_name,int var_value)
	{
		this.var_name = var_name;
		this.var_value_word = var_value;
		this.var_value_string = null;
	}
	public IRcommand_Allocate(String var_name,String var_value)
	{
		this.var_name = var_name;
		this.var_value_word = 0;
		this.var_value_string = var_value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(var_value_string != null)
			MIPSGenerator.getInstance().allocate(var_name,var_value_string);
		else
			MIPSGenerator.getInstance().allocate(var_name,var_value_word);
	}
}
