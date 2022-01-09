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

public class IRcommand_Label extends IRcommand
{
	String label_name;
	int switchDataText;

	public IRcommand_Label(String label_name)
	{
		this(label_name,0);
	}
	public IRcommand_Label(String label_name, int switchDataText)
	{
		this.label_name = label_name;
		/*
		0 for label without data / text assign
		1 for label of data
		2 for label of text
		*/
		this.switchDataText = switchDataText;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		switch(switchDataText){
			case 0:
				MIPSGenerator.getInstance().label(label_name);
				break;
			case 1:
				MIPSGenerator.getInstance().label_data(label_name);
			 	break;
			case 2:
				MIPSGenerator.getInstance().label_text(label_name);
				break;
		}
	}	
}
