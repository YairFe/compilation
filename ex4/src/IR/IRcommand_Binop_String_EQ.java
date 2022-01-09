package IR;
import MIPS.*;
import TEMP.*;

public class IRcommand_Binop_String_EQ extends IRcommand {

	TEMP s1;
	TEMP s2;
	TEMP dst; 
	
	public IRcommand_Binop_String_EQ(TEMP s1, TEMP s2, TEMP dst) {
		this.s1 = s1;
		this.s2 = s2;
		this.dst = dst;
	}
	
	public void MIPSme() {
		String neq_label = getFreshLabel("neq_label");
		String str_eq_end = getFreshLabel("str_eq_end");
		String str_eq_loop = getFreshLabel("str_eq_loop");
		
		MIPSGenerator.getInstance().li(String.format("Temp_%d", dst.getSerialNumber()), 1);
		MIPSGenerator.getInstance().mov("$s0", String.format("Temp_%d", s1.getSerialNumber()));
		MIPSGenerator.getInstance().mov("$s1", String.format("Temp_%d", s2.getSerialNumber()));
		MIPSGenerator.getInstance().label(str_eq_loop);
		MIPSGenerator.getInstance().lb("$s2", "$s0", 0);
		MIPSGenerator.getInstance().lb("$s3", "$s1", 0);
		MIPSGenerator.getInstance().bne("$s2", "$s3", neq_label);
		MIPSGenerator.getInstance().beqz("$s2", str_eq_end);
		MIPSGenerator.getInstance().addu("$s0", "$s0", 1);
		MIPSGenerator.getInstance().addu("$s1", "$s1", 1);
		MIPSGenerator.getInstance().jump(str_eq_loop);
		MIPSGenerator.getInstance().label(neq_label);
		MIPSGenerator.getInstance().li(String.format("Temp_%d", dst.getSerialNumber()), 0);
		MIPSGenerator.getInstance().label(str_eq_end);
	}

}
