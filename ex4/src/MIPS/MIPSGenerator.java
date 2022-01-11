/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_string(String error_msg){
		fileWriter.format("\tla %s\n",error_msg);
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
	}
	public void print_int(String t)
	{
		fileWriter.format("\tmove $a0,%s\n",t);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
	}
	public void funcPrologue(){
		// there is 10 temp registers we want to save
		for(int i=0;i<10;i++){
			fileWriter.format("subu $sp,$sp,4\n");
			fileWriter.format("sw $t%d,0($sp)\n", i);
		}
	}
	public void funcEpilogue(){
		// there is 10 temp registers we want to load
		for(int i=0;i<10;i++){
			fileWriter.format("lw $t%d,0($sp)\n", i);
			fileWriter.format("addu $sp,$sp,4\n");			
		}
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	
	public void dec_sp(int offset) {
		fileWriter.format("addu $sp,$sp,%d\n",offset);
	}
	
	public void push_to_stack(String src) {
		fileWriter.format("subu $sp,$sp,4\n");
		fileWriter.format("sw %s,0($sp)\n", src);
	}
	public void popStackTo(String src) {
		fileWriter.format("lw %s,0($sp)\n", src);
		fileWriter.format("addu $sp,$sp,4\n");
		
	}
	
	public void allocate_func(String var_name)
	{
		fileWriter.format("\t.word %s\n",var_name);
	}
	public void allocate(String var_name,int value)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word %d\n",var_name, value);
	}
	public void allocate_string(String var_name, String value)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\t%s:\n",var_name);
		fileWriter.format("\t .word %d\n",value.length());
		fileWriter.format("\t .asciiz \"%s\"\n",value);
	}
	public void text_segment(){
		fileWriter.format(".text\n");
	}
	public void malloc()
	{
		fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tsyscall\n");
	}
	public void malloc(int numOfBytes)
	{
		fileWriter.format("\tli $a0,%d\n",numOfBytes);
		fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tsyscall\n");
	}
	public void load(TEMP dst,String var_name)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,global_%s\n",idxdst,var_name);
	}
	public void store(String var_name,String src)
	{
		fileWriter.format("\tsw %s,global_%s\n",src,var_name);		
	}
	public void la(String dst,String src)
	{
		fileWriter.format("\tla %s, %s\n",dst,src);
	}
	public void li(String t,int value)
	{
		fileWriter.format("\tli %s, %d\n",t,value);
	}
	
	public void lw(String dst,String src, int offset)
	{
		fileWriter.format("\tlw %s, %d(%s)",dst,offset,src);
	}
	public void sw(String src,String dst, int offset)
	{
		fileWriter.format("\tsw %s, %d(%s)",src,offset,dst);
	}
	
	public void lb(String dst,String src,int offset)
	{
		fileWriter.format("\tlb %s, %d(%s)",dst,offset,src);
	}
	
	public void mov(String dst,String src)
	{
		fileWriter.format("\tmov %s, %s",dst,src);
	}
	
	public void getFuncResult(String dst)
	{
		fileWriter.format("\tmov %s, $v0",dst);
	}
	
	public void setFuncResult(TEMP dst)
	{
		int idx1=dst.getSerialNumber();
		fileWriter.format("\tmov $v0, Temp_%d",idx1);
	}
	public void add(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\tadd %s,%s,%s\n",dst,oprnd1,oprnd2);
	}
	public void mul(String dst,String oprnd1,String oprnd2)
	{
		fileWriter.format("\tmul %s,%s,%s\n",dst,oprnd1,oprnd2);
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void addu(String dst, String src, int val) {
		fileWriter.format("\taddu %s,%s,%d\n", dst, src, val);
	}
	public void subu(String dst,String src,int offset)
	{
		fileWriter.format("\tsubu %s,%s,$d\n",dst,src,offset);
	}
	public void label(String inlabel)
	{
		fileWriter.format("%s:\n",inlabel);
	}	
	public void label_text(String inlabel)
	{
		fileWriter.format(".text\n");
		fileWriter.format("%s:\n",inlabel);
	}	
	public void label_data(String inlabel)
	{
		fileWriter.format(".data\n");
		fileWriter.format("%s:\n",inlabel);
	}	
	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}	
	public void blt(String oprnd1,String oprnd2,String label)
	{
		fileWriter.format("\tblt %s,%s,%s\n",oprnd1,oprnd2,label);				
	}
	
	public void bge(String oprnd1,String oprnd2,String label)
	{
		fileWriter.format("\tbge %s,%s,%s\n",oprnd1,oprnd2,label);				
	}
	
	public void ble(String oprnd1,String oprnd2,String label)
	{
		fileWriter.format("\tble %s,%s,%s\n",oprnd1,oprnd2,label);				
	}
	
	public void bne(String oprnd1,String oprnd2,String label)
	{
		fileWriter.format("\tbne %s,%s,%s\n",oprnd1,oprnd2,label);				
	}

	public void beq(String oprnd1,String oprnd2,String label)
	{
		fileWriter.format("\tbeq %s,%s,%s\n",oprnd1,oprnd2,label);				
	}
	public void beqz(String oprnd1,String label)
	{		
		fileWriter.format("\tbeqz %s,%s\n",oprnd1,label);				
	}

	public void bltz(String oprnd1,String label)
	{		
		fileWriter.format("\tbltz %s,%s\n",oprnd1,label);				
	}
	
	public void jalr(String dst)
	{
		fileWriter.format("\tjalr %s\n",dst);				
	}
	public void jal(String label)
	{
		fileWriter.format("\tjal %s\n",label);				
	}
	public void jr(String dst)
	{
		fileWriter.format("\tjr %s\n",dst);				
	}
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static MIPSGenerator instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected MIPSGenerator() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new MIPSGenerator();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./output/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
			instance.fileWriter.print(String.format("max: .word %d\n",32767));
			instance.fileWriter.print(String.format("min: .word %d\n",-32768));
		}
		return instance;
	}
}
