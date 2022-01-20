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

public class VertexList
{
	public Vertex head;
    public VertexList tail;
	
	public VertexList(Vertex head, VertexList tail)
	{
		this.head = head;
        this.tail = tail;
	}
    /* return the vertex in the graph with the povided label */
    public Vertex getVertexWithLabelName(String name){
        VertexList curVertex = new VertexList(head,tail);
        while(curVertex != null && curVertex.head != null){
            if(curVertex.head.command instanceof IRcommand_Label &&
            ((IRcommand_Label) curVertex.head.command).label_name.equals(name)){
                return curVertex.head;
            } else if(curVertex.head.command instanceof IRcommand_Jump_Label &&
            ((IRcommand_Jump_Label) curVertex.head.command).label_name.equals(name)){
                return curVertex.head;
            } else if(curVertex.head.command instanceof IRcommand_Jump_If_Eq_To_Zero &&
            ((IRcommand_Jump_If_Eq_To_Zero) curVertex.head.command).label_name.equals(name)){
                return curVertex.head;
            }
            curVertex = curVertex.tail;
        }
        return null;
    }
	
}