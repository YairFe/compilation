package IR; import TEMP.*;

public class IRcommand_Dec_Func extends IRcommand {

	public String label;
	public TEMP_LIST args;

	public IRcommand_Dec_Func(String label, TEMP_LIST args) {
		this.label = label;
		this.args = args;
	}
	
	public void MIPSme() { /* TODO */ }

}
