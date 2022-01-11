package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;

public class IRcommand_Binop_Div_Integers extends IRcommand {

	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Div_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public void MIPSme() { 
		int max = 32767;
		int min = -32768;
		String label_end_max = getFreshLabel("label_end_max");
		String label_end_min = getFreshLabel("label_end_min");
		
		MIPSGenerator.getInstance().push_to_stack("$s0");
		// if t2 equal to zero print message and exit
		MIPSGenerator.getInstance().beqz(t2,label_division_by_zero);
		MIPSGenerator.getInstance().div(dst,t1,t2);
		// if the division product greater than max assign max to dst
		MIPSGenerator.getInstance().li("$s0",max);
		MIPSGenerator.getInstance().ble(dst,"$s0",label_end_max);
		MIPSGenerator.getInstance().mov(dst,"$s0");
		MIPSGenerator.getInstance().jump(label_end_min);
		MIPSGenerator.getInstance().label(label_end_max);
		// if the division product less than min assign min to dst
		MIPSGenerator.getInstance().li("$s0",min);
		MIPSGenerator.getInstance().bge(dst,"$s0",label_end_min);
		MIPSGenerator.getInstance().mov(dst,"$s0");
		MIPSGenerator.getInstance().label(label_end_min);
		MIPSGenerator.getInstance().popStackTo("$s0");
	}

}
