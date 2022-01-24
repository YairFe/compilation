package TEMP;

public class REG_ALLOC {
	private TEMP_LIST all_temporaries = null; // a list of all created temporaries in sorted order
	
	private TEMP_LIST stack = null; // will be used as a stack for the graph coloring algorithm
	
	private final String[] colors = {"$t0", "$t1", "$t2", "$t3", "$t4", 
							         "$t5", "$t6", "$t7", "$t8", "$t9"}; // a list of register names to "color" the graph with
	
	public void addTemp(TEMP t) {
		if(this.all_temporaries == null) this.all_temporaries = new TEMP_LIST(t, null);
		else this.all_temporaries.add(t);
	}
	
	private void pushToStack(TEMP t) {
		// remove t from the neighbor list of every neighbor of t
		TEMP_LIST n = t.neighbors;
		while(n != null) {
			n.value.removeNeighbor(t);
			n = n.next;
		}
		
		// insert t into the stack
		stack.stack_push(t);
	}
	
	private void popFromStack() {
		// pop a value (t) from the top of the stack
		TEMP t = stack.stack_pop();
		int i;
		
		// add t into the neighbor lists of all its neighbors
		TEMP_LIST n = t.neighbors;
		while(n != null) {
			n.value.addNeighbor(t);
			n = n.next;
		}
		
		// find a color for t that is not shared by any of its neighbors
		for(i = 0; (i < 10)&&(t.color == null); i++) {
			n = t.neighbors;
			
			while((n != null)&&(n.value.color.equals(colors[i]))) n = n.next;
			
			if(n == null) t.color = colors[i]; // none of t's neighbors are assigned to colors[i]
		}
	}
	
	public void allocate_registers() {
		// Current implementation: the most basic version of the algorithm.
		// We do not coalesce MOVs and we assume that the interference graph is 10-colorable (no spills required)
		TEMP t;
		TEMP_LIST temps = all_temporaries;
		int i;
		
		// loop until all the temporaries are in the stack
		while(stack.length < all_temporaries.length) {
			// check the number of edges for the current node
			 t = temps.value;
			 i = t.numOfNeighbors();
			 
			 // if the node has less than 10 edges, push to stack
			 if(i < 10) pushToStack(t);
			 
			 // look at another node
			 if(temps.next != null) temps = temps.next;
			 else temps = all_temporaries;
		}
		
		// once all the temporaries are in the stack, pop them out and assign colors
		while(stack.length > 0) {
			popFromStack();
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
