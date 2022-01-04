package IR; import TEMP.*;

public class IRcommand_Dec_Func extends IRcommand {

	public String label;
	public TEMP_LIST args;
	public IRcommandList body;
	
	public IRcommand_Dec_Func(String label, TEMP_LIST args, IRcommandList body) {
		this.label = label;
		this.args = args;
		this.body = body;
	}
	
	public void MIPSme() { /* TODO */ }

}
