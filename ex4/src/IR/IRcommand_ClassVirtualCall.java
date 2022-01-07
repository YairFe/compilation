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
	TEMP class;
    String method_name;
    TEMP_LIST args;
	
	public IRcommand_ClassVirtualCall(TEMP dst, TEMP class, String name, TEMP_LIST args)
	{
		this.dst = dst;
		this.class = class;
        this.method_name = name;
        this.args = args;
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
		// save ra to stack
		MIPSGenerator.getInstance().subStack(4);
		stack_offset += 4;
		MIPSGenerator.getInstance().saveReturnAddressToStack();
		// save fp to stack
		MIPSGenerator.getInstance().subStack(4);
		stack_offset += 4;
		MIPSGenerator.getInstance().saveToStack(class);
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		MIPSGenerator.getInstance().lw(t,class,0);
		MIPSGenerator.getInstance().lw(t,t,func_offset); // missing func offset
		
		MIPSGenerator.getInstance().jalr(t);


		if(dst != null){
			getFuncResult(dst);
		}
		// pop back fp value
		MIPSGenerator.getInstance().lw("$fp","$sp",0);
		// pop back return address value
		MIPSGenerator.getInstance().lw("$ra","$sp",-4);
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
