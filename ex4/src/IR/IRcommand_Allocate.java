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
	String scope_type;
	String var_name = null;
	int var_value_word = 0;
	String var_value_string = null;

	public IRcommand_Allocate(String scope_type){
		this.scope_type = scope_type;
	}
	public IRcommand_Allocate(String scope_type,String var_name,int var_value)
	{
		this(scope_type);
		this.var_name = var_name;
		this.var_value_word = var_value;
	}
	public IRcommand_Allocate(String scope_type,String var_name,String var_value)
	{
		this(scope_type);
		this.var_name = var_name;
		this.var_value_string = var_value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().label("IR_Allocate");
		if(scope_type.equals("global")){
			if(var_value_string != null){
				MIPSGenerator.getInstance().allocate_string(String.format("global_%s",var_name),var_value_string);
			} else {
				MIPSGenerator.getInstance().allocate(var_name,var_value_word);
			}
		}
	}
}
