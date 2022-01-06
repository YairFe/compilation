package IR;
import TEMP.*;

public class IRcommandConstString extends IRcommand {

	TEMP t;
	String value;
	
	public IRcommandConstString(TEMP t,String value)
	{
		this.t = t;
		this.value = value;
	}
	
	public void MIPSme() { /* TODO Auto-generated method stub */ }

}
