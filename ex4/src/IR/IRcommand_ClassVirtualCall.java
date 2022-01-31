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

public class IRcommand_ClassVirtualCall extends IRcommand
{
	TEMP dst;
	TEMP my_class;
        String method_name;
        TEMP_LIST args;
	int index;
	
	public IRcommand_ClassVirtualCall(TEMP dst, TEMP my_class, String name, TEMP_LIST args, int index)
	{
		this.dst = dst;
		this.my_class = my_class;
                this.method_name = name;
                this.args = args;
		this.index = index;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().label(String.format("IR_ClassVirtualCall_%s_%d", this.method_name, this.dst.getSerialNumber()));
		String abort = getFreshLabel("abort");
		String end_label = getFreshLabel("end_label");
		MIPSGenerator.getInstance().beqz(my_class.toString(), abort);

		MIPSGenerator.getInstance().push_to_stack("$s0");
		// save temp register to stack
		MIPSGenerator.getInstance().funcPrologue();
		// save args to stack from last to first
		int stack_offset = this.saveToStack(this.args);
		// save class pointer to stack as first param
		MIPSGenerator.getInstance().push_to_stack(my_class.toString());
		stack_offset += 4;
		// get the function address from class pointer
		MIPSGenerator.getInstance().lw("$s0",my_class.toString(),0);
		MIPSGenerator.getInstance().lw("$s0","$s0",index*4);
		
		MIPSGenerator.getInstance().jalr("$s0");

		MIPSGenerator.getInstance().addu("$sp","$sp",stack_offset);

		if(dst != null){
			MIPSGenerator.getInstance().mov(dst.toString(),"$v0");
		}
		// poping out the temps back to their registers
		MIPSGenerator.getInstance().funcEpilogue();
		MIPSGenerator.getInstance().popStackTo("$s0");
		// abort function
		MIPSGenerator.getInstance().jump(end_label);
		MIPSGenerator.getInstance().label(abort);
		MIPSGenerator.getInstance().la("$a0","string_invalid_ptr_dref");
		MIPSGenerator.getInstance().print_string();
		MIPSGenerator.getInstance().exit();
		MIPSGenerator.getInstance().label(end_label);
	}

	/* recursive function to save the arguments from last to first*/
	public int saveToStack(TEMP_LIST lst){
		if(lst == null){
			return 0;
		}
		int stack_offset = this.saveToStack(lst.next);
		MIPSGenerator.getInstance().push_to_stack(lst.value.toString());
		return stack_offset + 4;
	}

	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.add(my_class);
		for(TEMP_LIST e=args;e!=null;e=e.next){
			result.add(e.value);
		}
		if(dst != null) result.remove(dst);
		return result;
	}
}
