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

		// push temps to stack
		MIPSGenerator.getInstance().funcPrologue();
		// arguments should be pushed in reverse order, so cycle to the end of the list
		while((argsp != null) && (argsp.next != null)) { args_num += 1; argsp = argsp.next; }
		
		while(argsp != null) {
			// push arguments to stack
			MIPSGenerator.getInstance().push_to_stack(argsp.value.toString());
			argsp = argsp.prev;
		}
		
		// jump to function
		MIPSGenerator.getInstance().jal(String.format("func_%s",name));
		
		if(dst != null)
			// save return value
			MIPSGenerator.getInstance().mov(dst.toString(),"$v0");
		
		// clear arguments from stack
		MIPSGenerator.getInstance().addu("$sp","$sp",4*args_num);
		
		// pop temps from stack
		MIPSGenerator.getInstance().funcEpilogue();
		
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		for(TEMP_LIST e=args;e!=null;e=e.next){
			result.add(e.value);
		}
		if(dst != null) result.remove(dst);
		if(result.value == null) return null;
		return result;
	}

}
