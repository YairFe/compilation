/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.util.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;
import TYPES.*;

public class IRcommand_Allocate_Func extends IRcommand
{
	String func_name;
    String class_name;
	int num_of_local_vars;

	public IRcommand_Allocate_Func(String class_name, String func_name, int num_of_local_vars)
	{
		this.func_name = func_name;
        this.class_name = class_name;
		this.num_of_local_vars = num_of_local_vars;
	}
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
        if(class_name != null)
            MIPSGenerator.getInstance().label_text(String.format("%s_%s",class_name,func_name));
        else if(func_name.equals("main")){
			MIPSGenerator.getInstance().label_text("main");
		} else
            MIPSGenerator.getInstance().label_text(String.format("func_%s",func_name));
        MIPSGenerator.getInstance().push_to_stack("$ra");
        MIPSGenerator.getInstance().push_to_stack("$fp");
        MIPSGenerator.getInstance().mov("$fp","$sp");
		MIPSGenerator.getInstance().subu("$sp","$sp",this.num_of_local_vars*4);

	}
}
