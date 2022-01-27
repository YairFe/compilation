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
		// push temps to stack
		MIPSGenerator.getInstance().funcPrologue();
		// save args to stack from last to first
		int stack_offset = this.saveToStack(this.args);
		// jump to function
		MIPSGenerator.getInstance().jal(String.format("func_%s",name));
		// clear arguments from stack
		MIPSGenerator.getInstance().subu("$sp","$sp",stack_offset);
		// pop temps from stack
		MIPSGenerator.getInstance().funcEpilogue();
		if(dst != null)
			// save return value
			MIPSGenerator.getInstance().mov(dst.toString(),"$v0");
		
		
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
		for(TEMP_LIST e=args;e!=null;e=e.next){
			result.add(e.value);
		}
		if(dst != null) result.remove(dst);
		return result;
	}

}
