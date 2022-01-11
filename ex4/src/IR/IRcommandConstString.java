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
	
	public void MIPSme() { 
		String str_name = getFreshLabel(value);
		// allocate new constant string in the data segment
		MIPSGenerator.getInstance().allocate_string(str_name,value);
		// move back to text segment
		MIPSGenerator.getInstance().text_segment();
		// load the address of the string to register
		MIPSGenerator.getInstance().la(t.toString(),str_name);
		
	}

}
