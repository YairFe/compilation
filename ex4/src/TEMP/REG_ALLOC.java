package TEMP;

public class REG_ALLOC {
	private TEMP_LIST all_temporaries = null; // a list of all created temporaries in sorted order
	
	private TEMP_LIST stack = null; // will be used as a stack for the graph coloring algorithm
					// NOTE: adding and removing temporaries from stack should be done
					// manually and not with add()/remove(), because add() and remove()
					// maintain a sorted order in the list.
	
	private final String[] colors = {"$t0", "$t1", "$t2", "$t3", "$t4", 
					 "$t5", "$t6", "$t7", "$t8", "$t9"}; // a list of register names to "color" the graph with
	
	public void addTemp(TEMP t) {
		if(this.all_temporaries == null) this.all_temporaries = new TEMP_LIST(t, null);
		else this.all_temporaries.add(t);
	}
	
	public void allocate_registers() {
		// implement the graph coloring algorithm here
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
