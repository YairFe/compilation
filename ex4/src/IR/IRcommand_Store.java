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

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TEMP src;
	String scope_type;
	int index;

	public IRcommand_Store(String var_name,TEMP src, String scope_type, int index)
	{
		this.src      = src;
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
			MIPSGenerator.getInstance().store(var_name,src.toString());
		else if(scope_type.equals("param"))
			MIPSGenerator.getInstance().sw(src.toString(),"$fp",(index+2)*4);
		else if(scope_type.equals("local_func"))			
			MIPSGenerator.getInstance().sw(src.toString(),"$fp",(index+1)*-4);
		else if(scope_type.equals("local_class")){
			// the first param of a method is the class pointer
			MIPSGenerator.getInstance().push_to_stack("$s0");
			MIPSGenerator.getInstance().lw("$s0","$fp",8);
			MIPSGenerator.getInstance().sw(src.toString(),"$s0",(index+1)*4);
			MIPSGenerator.getInstance().popStackTo("$s0");
		}

	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(src);
		return result;
	}
}
