package IR; import TEMP.*; import MIPS.*;

public class IRcommand_Call_Func extends IRcommand {

	public String name;
	public TEMP_LIST args;
	
	public IRcommand_Call_Func(TEMP dst, String name, TEMP_LIST args) {
		this.name = name;
		this.args = args;
		this dst = dst;
	}
	
	public void MIPSme() { /* TODO Auto-generated method stub */ }

}
