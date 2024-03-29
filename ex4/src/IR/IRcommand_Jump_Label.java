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

public class IRcommand_Jump_Label extends IRcommand
{
	String label_name;
	
	public IRcommand_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(label_name.equals("back")){
			MIPSGenerator.getInstance().popStackTo("$ra");	
			MIPSGenerator.getInstance().jr("$ra");	
		} else {
			MIPSGenerator.getInstance().jump(label_name);
		}
	}
}
