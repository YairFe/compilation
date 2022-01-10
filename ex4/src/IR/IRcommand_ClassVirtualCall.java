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
		// save temp register to stack
		MIPSGenerator.getInstance().funcPrologue();
		// save args to stack from last to first
		int stack_offset = this.saveToStack(this.args);
		// save class pointer to stack as first param
		MIPSGenerator.getInstance().subStack(4);
		stack_offset += 4;
		MIPSGenerator.getInstance().saveToStack(my_class);
		// save ra to stack might need to do inside the function declartion
		MIPSGenerator.getInstance().subStack(4);
		stack_offset += 4;
		MIPSGenerator.getInstance().saveReturnAddressToStack();
		// save fp to stack might need to do inside the function declartion
		MIPSGenerator.getInstance().subStack(4);
		stack_offset += 4;
		MIPSGenerator.getInstance().sw("$fp","$sp",0);
		// update fp to the current stack position
		MIPSGenerator.getInstance().mov("$fp","$sp");
		// get the function address from class pointer
		MIPSGenerator.getInstance().lw(t,my_class,0);
		MIPSGenerator.getInstance().lw(t,t,index*4);
		
		MIPSGenerator.getInstance().jalr(t);


		if(dst != null){
			getFuncResult(dst);
		}
		// pop back return address value
		MIPSGenerator.getInstance().lw("$ra","$fp",-4);
		// pop back previous fp value
		MIPSGenerator.getInstance().lw("$fp","$fp",0);	
		// return the stack to his initial state before calling
		MIPSGenerator.getInstance().addStack(stack_offset);
		// poping out the temps back to their registers
		MIPSGenerator.getInstance().funcEpilogue();
	}

	/* recursive function to save the arguments from last to first*/
	public int saveToStack(TEMP_LIST lst){
		if(lst == null){
			return 0;
		}
		int stack_offset = this.saveToStack(lst.tail);
		MIPSGenerator.getInstance().subStack(4);
		MIPSGenerator.getInstance().saveToStack(lst.head);
		return stack_offset + 4;
	}
}
