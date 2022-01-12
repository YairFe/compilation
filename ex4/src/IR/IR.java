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
		IRcommandList tmp = new IRcommandList(head,tail);
		VertexList label_list = null;
		VertexList return_list = null;
		VertexList func_list = null;
		// vertex for looping through the graph
		Vertex prevVertex = null;
		Vertex curVertex = null;
		// looping through the ir command from start to end
		while(tmp != null && tmp.head != null){
			prevVertex = curVertex;
			curVertex = new Vertex(tmp.head);
			if(tmp.head instanceof IRcommand_Allocate_Func){
				func_list = new VertexList(curVertex, func_list);
			} else {
				if(prevVertex != null) prevVertex.addNext(curVertex);
				curVertex.addPrev(prevVertex);
				if(tmp.head instanceof IRcommand_Jump_Label){
					Vertex labelVertex = label_list.getVertexWithLabelName(((IRcommand_Jump_Label)tmp.head).label_name);
					if(labelVertex != null){
						labelVertex.addPrev(curVertex);
						curVertex.addNext(labelVertex);
					} else {
						return_list = new VertexList(curVertex,return_list);
					}
					curVertex = null;
				} else if(tmp.head instanceof IRcommand_Jump_If_Eq_To_Zero) {
					Vertex labelVertex = label_list.getVertexWithLabelName(((IRcommand_Jump_If_Eq_To_Zero)tmp.head).label_name);
					if(labelVertex != null){
						labelVertex.addPrev(curVertex);
						curVertex.addNext(labelVertex);
					} else {
						return_list = new VertexList(curVertex,return_list);
					}
				} else if(tmp.head instanceof IRcommand_FuncReturn){
					curVertex = null;	
				}else if(tmp.head instanceof IRcommand_Label){
					Vertex returnVertex = return_list.getVertexWithLabelName(((IRcommand_Label)tmp.head).label_name);
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
		for(VertexList e=func_list;e!= null;e=e.tail){
			if(e.head != null){
				// running liveness on each function graph
				e.head.liveness();
				e.head.buildColoredGraph();
			}
		}
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
