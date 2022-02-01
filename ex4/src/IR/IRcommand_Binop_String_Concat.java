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
		String first_loop = getFreshLabel("first_loop");
		String end_first_loop = getFreshLabel("end_first_loop");
		String second_loop = getFreshLabel("second_loop");
		String end_second_loop = getFreshLabel("end_second_loop");
		String assign_first = getFreshLabel("assign_first");
		String assign_second = getFreshLabel("assign_second");
		String end_label = getFreshLabel("end_label");
		
		// saves $s0-$s2 to stack
		MIPSGenerator.getInstance().push_to_stack("$s0");
		MIPSGenerator.getInstance().push_to_stack("$s1");
		MIPSGenerator.getInstance().push_to_stack("$s2");
		MIPSGenerator.getInstance().push_to_stack("$s3");
		// calculate the size of the strings
		MIPSGenerator.getInstance().li("$s0",0);
		MIPSGenerator.getInstance().mov("$s2",t1.toString());
		MIPSGenerator.getInstance().label(first_loop);
		MIPSGenerator.getInstance().lb("$s3","$s2",0);
		MIPSGenerator.getInstance().beqz("$s3",end_first_loop);
		MIPSGenerator.getInstance().addu("$s0","$s0",1);
		MIPSGenerator.getInstance().addu("$s2","$s2",1);
		MIPSGenerator.getInstance().jump(first_loop);
		MIPSGenerator.getInstance().label(end_first_loop);
		MIPSGenerator.getInstance().mov("$s2",t2.toString());
		MIPSGenerator.getInstance().label(second_loop);
		MIPSGenerator.getInstance().lb("$s3","$s2",0);
		MIPSGenerator.getInstance().beqz("$s3",end_second_loop);
		MIPSGenerator.getInstance().addu("$s0","$s0",1);
		MIPSGenerator.getInstance().addu("$s2","$s2",1);
		MIPSGenerator.getInstance().jump(second_loop);
		MIPSGenerator.getInstance().label(end_second_loop);
		// add size for null and the size of the string
		MIPSGenerator.getInstance().addu("$a0","$s0",1);
		// allocate memory
		MIPSGenerator.getInstance().malloc();
		// get the pointer from $v0
		MIPSGenerator.getInstance().mov("$s2","$v0");

		MIPSGenerator.getInstance().mov("$s0",t1.toString());
		MIPSGenerator.getInstance().mov("$s1",t2.toString());
		// label assign_first
		MIPSGenerator.getInstance().label(assign_first);
		MIPSGenerator.getInstance().lb("$s3","$s0",0);
		MIPSGenerator.getInstance().beqz("$s3",assign_second);
		// store the char
		MIPSGenerator.getInstance().sb("$s3","$s2",0);
		// set the pointer to the next char
		MIPSGenerator.getInstance().addu("$s0","$s0",1);
		MIPSGenerator.getInstance().addu("$s2","$s2",1);
		MIPSGenerator.getInstance().jump(assign_first);
		// label assign second
		MIPSGenerator.getInstance().label(assign_second);
		MIPSGenerator.getInstance().lb("$s3","$s1",0);
		MIPSGenerator.getInstance().beqz("$s3",end_label);
		// store the char
		MIPSGenerator.getInstance().sb("$s3","$s2",0);
		// set the pointer to the next char
		MIPSGenerator.getInstance().addu("$s1","$s1",1);
		MIPSGenerator.getInstance().addu("$s2","$s2",1);
		MIPSGenerator.getInstance().jump(assign_second);

		// label end
		MIPSGenerator.getInstance().label(end_label);
		// set the last char to null
		MIPSGenerator.getInstance().sb("$zero","$s2",0);
		MIPSGenerator.getInstance().mov(dst.toString(),"$v0");
		MIPSGenerator.getInstance().popStackTo("$s3");
		MIPSGenerator.getInstance().popStackTo("$s2");
		MIPSGenerator.getInstance().popStackTo("$s1");
		MIPSGenerator.getInstance().popStackTo("$s0");
		
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(t1);
		result.add(t2);
		result.remove(dst);
		return result;
	}
}
