package IR; import TEMP.*; import MIPS.*;

public class IRcommand_Call_Func extends IRcommand {

	public String name;
	public TEMP_LIST args;
	
	public IRcommand_Call_Func(String name, TEMP_LIST args) {
		this.name = name;
		this.args = args;
	}
	
	public void MIPSme() { /* TODO Auto-generated method stub */ }

}
