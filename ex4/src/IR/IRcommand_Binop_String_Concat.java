package IR;

import TEMP.*;
import MIPS.*;

public class IRcommand_Binop_String_Concat extends IRcommand {

	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_String_Concat(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public void MIPSme() { 
		String assign_first = getFreshLabel("assign_first");
		String assign_second = getFreshLabel("assign_second");
		String end_label = getFreshLabel("end_label");

		// saves $s0-$s2 to stack
		MIPSGenerator.getInstance().push_to_stack("$s0");
		MIPSGenerator.getInstance().push_to_stack("$s1");
		MIPSGenerator.getInstance().push_to_stack("$s2");
		// load the size of the strings
		MIPSGenerator.getInstance().lw("$s0",t1.toString(),0);
		MIPSGenerator.getInstance().lw("$s1",t2.toString(),0);
		// calculate the total size of the desired string
		// save the size to $s0
		MIPSGenerator.getInstance().add("$s0","$s0","$s1");
		// add size for null and the size of the string
		MIPSGenerator.getInstance().addu("$a0","$s0",5);
		// allocate memory
		MIPSGenerator.getInstance().malloc();
		// get the pointer from $v0
		MIPSGenerator.getInstance().mov("$s2","$v0");
		// save the size of the string
		MIPSGenerator.getInstance().sw("$s0","$s2",0);

		MIPSGenerator.getInstance().mov("$s0",t1.toString());
		MIPSGenerator.getInstance().mov("$s1",t2.toString());
		MIPSGenerator.getInstance().addu("$s0","$s0",4);
		MIPSGenerator.getInstance().addu("$s1","$s1",4);
		MIPSGenerator.getInstance().addu("$s2","$s2",4);
		// label assign_first
		MIPSGenerator.getInstance().label(assign_first);
		MIPSGenerator.getInstance().beqz("$s0",assign_second);
		// store the char
		MIPSGenerator.getInstance().sb("$s0","$s2",0);
		// set the pointer to the next char
		MIPSGenerator.getInstance().addu("$s0","$s0",1);
		MIPSGenerator.getInstance().addu("$s2","$s2",1);
		MIPSGenerator.getInstance().jump(assign_first);
		// label assign scond
		MIPSGenerator.getInstance().label(assign_second);
		MIPSGenerator.getInstance().beqz("$s0",end_label);
		// store the char
		MIPSGenerator.getInstance().sb("$s1","$s2",0);
		// set the pointer to the next char
		MIPSGenerator.getInstance().addu("$s1","$s1",1);
		MIPSGenerator.getInstance().addu("$s2","$s2",1);
		MIPSGenerator.getInstance().jump(assign_second);

		// label end
		MIPSGenerator.getInstance().label(end_label);
		// set the last char to null
		MIPSGenerator.getInstance().sb("$zero","$s2",0);
		MIPSGenerator.getInstance().mov(dst.toString(),"$v0");
		MIPSGenerator.getInstance().popStackTo("$s2");
		MIPSGenerator.getInstance().popStackTo("$s1");
		MIPSGenerator.getInstance().popStackTo("$s0");
		
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(t1);
		result.add(t2);
		result.remove(dst);
		if(result.value == null) return null;
		return result;
	}
}
