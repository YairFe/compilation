/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IR
{
	private IRcommand head=null;
	private IRcommandList tail=null;

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRcommand(IRcommand cmd)
	{
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
		System.out.println(cmd.getClass());
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}
	/********************/
	/*	optimize me		*/
	/********************/
	public void OPTme(){
		// using tmp to loop through the list
		IRcommandList tmp = new IRcommandList(head,tail);
		VertexList label_list = null;
		VertexList jump_list = null;
		VertexList func_list = null;
		VertexList return_list = null;
		// vertex for looping through the graph
		Vertex prevVertex = null; // pointer to the previous vertex we saw
		Vertex curVertex = null; // pointer to the current vertex we saw
		// looping through the ir command from start to end
		while(tmp != null && tmp.head != null){
			prevVertex = curVertex;
			curVertex = new Vertex(tmp.head);
			if(tmp.head instanceof IRcommand_Allocate_Func){
				// list of all the function in the program
				func_list = new VertexList(curVertex, func_list);
			} else {
				if(prevVertex != null) prevVertex.addNext(curVertex);
				curVertex.addPrev(prevVertex);
				// if the command is of type jump we should add edge to the label
				if(tmp.head instanceof IRcommand_Jump_Label){
					Vertex labelVertex = null;
					if(label_list != null){
						labelVertex = label_list.getVertexWithLabelName(((IRcommand_Jump_Label)tmp.head).label_name);
					}
					if(labelVertex != null){
						labelVertex.addPrev(curVertex);
						curVertex.addNext(labelVertex);
					} else {
						jump_list = new VertexList(curVertex,jump_list);
					}
					curVertex = null;
				// if the command is of type conditional jump add edge to the label
				// without setting current to null
				} else if(tmp.head instanceof IRcommand_Jump_If_Eq_To_Zero) {
					Vertex labelVertex = null;
					if(label_list != null)
						labelVertex = label_list.getVertexWithLabelName(((IRcommand_Jump_If_Eq_To_Zero)tmp.head).label_name);
					if(labelVertex != null){
						labelVertex.addPrev(curVertex);
						curVertex.addNext(labelVertex);
					} else {
						jump_list = new VertexList(curVertex,jump_list);
					}
				// if the command is return we should invalidate current vertex
				} else if(tmp.head instanceof IRcommand_FuncReturn){
					return_list = new VertexList(curVertex,return_list);
					curVertex = null;
				// if the command is label we set a pointer the the right jump command
				}else if(tmp.head instanceof IRcommand_Label){
					Vertex returnVertex;
					if(jump_list != null)
						 returnVertex = jump_list.getVertexWithLabelName(((IRcommand_Label)tmp.head).label_name);
					else returnVertex = null;
					if(returnVertex != null){
						returnVertex.addNext(curVertex);
						curVertex.addPrev(returnVertex);
					} else {
						label_list = new VertexList(curVertex,label_list);
					}
				}
			}
			tmp = tmp.tail;
		}
		for(VertexList e=return_list;e!=null;e=e.tail){
			if(e.head != null){
				// running liveness on each return command
				e.head.liveness();
			}
		}
		for(VertexList e=func_list;e!=null;e=e.tail){
			if(e.head != null){
				e.head.buildDependancyGraph();
			}
		}
		REG_ALLOC.getInstance().allocate_registers();
	}
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static IR instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected IR() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static IR getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new IR();
		}
		return instance;
	}
}
