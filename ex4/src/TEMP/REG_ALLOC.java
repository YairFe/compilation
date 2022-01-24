package TEMP;
import java.util.*;

public class REG_ALLOC {
	private TEMP_LIST all_temporaries = new TEMP_LIST(null,null); // a list of all created temporaries in sorted order (graph nodes)
	
	private final String[] colors = {"$t0", "$t1", "$t2", "$t3", "$t4", 
							         "$t5", "$t6", "$t7", "$t8", "$t9"}; // a list of register names to "color" the graph with
	
	public void addTemp(TEMP t) {
		this.all_temporaries.add(t);
	}
	
	public void allocate_registers() {
		Stack<TEMP> stack = new Stack<TEMP>();
		while(this.all_temporaries.length > 0){
			for(TEMP_LIST e=this.all_temporaries;e!=null;e=e.next){
				if(e.value.numOfNeighbors() < 10){
					stack.push(e.value);
					e.value.disconnectFromNeighbors();
					e.remove(e.value);
					break;
				}
			}
		}
		while(!stack.isEmpty()){
			TEMP t = stack.pop();
			Boolean hasColorNeighbor;
			for(int i=0;i<colors.length;i++){
				hasColorNeighbor = false;
				for(TEMP_LIST e=t.neighbors;e.value!=null;e=e.next){
					if(colors[i].equals(e.value.color)){
						hasColorNeighbor = true;
						break;
					}
				}
				if(!hasColorNeighbor){
					t.color = colors[i];
					break;
				}
			}
		}
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static REG_ALLOC instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected REG_ALLOC() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static REG_ALLOC getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new REG_ALLOC();
		}
		return instance;
	}
}
