package IR; import TEMP.*; import MIPS.*;

public class IRcommand_Call_Func extends IRcommand {

	public String name;
	public TEMP_LIST args;
	public TEMP dst;
	
	public IRcommand_Call_Func(TEMP dst, String name, TEMP_LIST args) {
		this.name = name;
		this.args = args;
		this.dst = dst;
	}
	
	public void MIPSme() { 
		TEMP_LIST argsp = this.args;
		int args_num = 0;

		// arguments should be pushed in reverse order, so cycle to the end of the list
		while((argsp != null) && (argsp.next != null)) { args_num += 1; argsp = argsp.next; }
		
		while(argsp != null) {
			// push arguments to stack
			MIPSGenerator.getInstance().push_to_stack(argsp.value.toString(),0, 0);
			argsp = argsp.prev;
		}
		
		// jump to function
		MIPSGenerator.getInstance().jal(name);
		
		// save return value
		MIPSGenerator.getInstance().getFuncResult(dst);
		
		// clear arguments from stack
		MIPSGenerator.getInstance().dec_sp(4*args_num);
		
		
		
	}

}
