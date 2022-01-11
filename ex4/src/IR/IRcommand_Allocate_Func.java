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
import TYPEs.*;

public class IRcommand_Allocate_Func extends IRcommand
{
	String func_name;
    String class_name;

	public IRcommand_Allocate_Func(String class_name, String func_name)
	{
		this.func_name = func_name;
        this.class_name = class_name;
	}
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
        if(class_name != null)
            MIPSGenerator.getInstance().label_text(String.format("%s_%s",class_name,func_name));
        else
            MIPSGenerator.getInstance().label_text(String.format("func_%s",func_name));
        MIPSGenerator.getInstance().push_to_stack("$ra");
        MIPSGenerator.getInstance().push_to_stack("$fp");
        MIPSGenerator.getInstance().mov("$fp","$sp");

	}
}
