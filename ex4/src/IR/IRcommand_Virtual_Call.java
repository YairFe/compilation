package IR;
import TEMP.*;

public class IRcommand_Virtual_Call extends IRcommand {

	TEMP dst;
	String fn;
	TEMP_LIST args;
	TEMP var;
	
	public IRcommand_Virtual_Call(TEMP dst, TEMP var, TEMP_LIST args, String fn) {
		this.dst = dst;
		this.fn = fn;
		this.args = args;
		this.var = var;
	}
	
	
	public void MIPSme() {
		/* TODO */

	}

}
