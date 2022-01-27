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
    public boolean visitedVertex = false;

	public Vertex(IRcommand command)
	{
        
        this.next = null;
        this.prev = null;
		this.command = command;
        if(command instanceof IRcommand_FuncReturn && ((IRcommand_FuncReturn)command).value != null){
            this.input = new TEMP_LIST(((IRcommand_FuncReturn)command).value,new TEMP_LIST(null,null));
        }else{
            // value is null when the list is empty
            this.input = new TEMP_LIST(null,null);
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
        if(this.next != null){
            if(this.next.head.output != null)
                this.input = this.next.head.output.clone();
            for(VertexList e=this.next.tail;e!=null;e=e.tail){
                this.input.union(e.head.output);
            }
        }
        TEMP_LIST IRLiveTemp = command.getLiveTemp(this.input);
        // if after liveness there isn't any change dont do anything
        if(IRLiveTemp.equals(this.output)){
            return;
        }
        this.output = IRLiveTemp;
        // then run liveness for every neighbor
        for(VertexList e=this.prev;e!=null;e=e.tail){
            e.head.liveness();
        }
    }

    public void buildDependancyGraph(){
        if(!this.visitedVertex){
            System.out.format("command: %s\n",this.command.getClass());
            this.input.printList();
            System.out.println();
            for(TEMP_LIST e=this.input;e.value!=null;e=e.next){
                for(TEMP_LIST f=e.next;f.value!=null;f=f.next){
                    f.value.addNeighbor(e.value);
                    e.value.addNeighbor(f.value);
                } 
            }
            this.visitedVertex = true;
            // go to every neighbor and add his TEMPS
            for(VertexList j=this.next;j!=null;j=j.tail){
                if(j.head != null)
                    j.head.buildDependancyGraph();
            }
        }
    }
}
