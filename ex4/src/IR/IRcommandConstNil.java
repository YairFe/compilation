package IR;
import TEMP.*;
import MIPS.*;

public class IRcommandConstNil extends IRcommand {

	TEMP t;
	
	public IRcommandConstNil(TEMP t) { this.t = t; }
	public void MIPSme() { 
		MIPSGenerator.getInstance().label(String.format("IR_ConstNil_%d", this.t.getSerialNumber()));
		MIPSGenerator.getInstance().mov(t.toString(),"$zero");	
	}
	public TEMP_LIST getLiveTemp(TEMP_LIST input){
		TEMP_LIST result = input.clone();
		result.remove(t);
		return result;
	}

}
