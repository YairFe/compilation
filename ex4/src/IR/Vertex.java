/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.util.*;
/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class Vertex
{
	public IRcommand command;
	public VertexList next;
    public VertexList prev;
    public TEMP_LIST input;
    public TEMP_LIST output;

	public Vertex(IRcommand command)
	{
        
        this.next = null;
        this.prev = null;
		this.command = command;
        if(command instanceof IRcommand_FuncReturn && ((IRcommand_FuncReturn)command).value != null){
            this.input = new TEMP_LIST(((IRcommand_FuncReturn)command).value,null);
        }else{
            this.input = null;
        }
        this.output = null;
	}

    public void addNext(Vertex other){
        if(other != null)
            this.next = new VertexList(other,next);
    }
	public void addPrev(Vertex other){
        if(other != null)
            this.prev = new VertexList(other,prev);
    }
    public void liveness(){
        if(this.next != null && this.next.head != null){
            // start liveness from the end
            this.next.head.liveness();
        }
        TEMP_LIST IRLiveTemp = command.getLiveTemp(this.input);
        // if after liveness there isn't any change dont do anything
        if((IRLiveTemp == null && this.output == null) || 
            (IRLiveTemp != null && IRLiveTemp.equals(this.output))){
            return;
        }
        this.output = IRLiveTemp;
        // first update every input
        for(VertexList e=this.next;e!=null;e=e.tail){
            e.head.input = IRLiveTemp;
        }
        // then run liveness for every node
        for(VertexList e=this.next;e!=null;e=e.tail){
            e.head.liveness();
        }
        
    }

    public void buildColoredGraph(){
        
    }
}
