/***********/
/* PACKAGE */
/***********/
package TEMP;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class TEMP
{
	private int serial=0;
	public TEMP_LIST neighbors = null; // neighbor list for the interference graph - will be updated during liveness analysis
	public String color = null; // will contain the register assigned to this temporary variable
	
	public TEMP(int serial)
	{
		this.serial = serial;
	}
	
	public int getSerialNumber()
	{
		return serial;
	}
	
	public int numOfNeighbors() {
		// get current number of interfering temporaries
		if(this.neighbors == null) return 0;
		return this.neighbors.length;
	}
	
	public void addNeighbor(TEMP n) { 
		// add a node to the neighbor list
		if(this.neighbors == null) this.neighbors = new TEMP_LIST(n, null);
		else this.neighbors.add(n);
	}
	
	public void removeNeighbor(TEMP n) { 
		// remove a node from the neighbor list
		if(this.neighbors != null) this.neighbors.remove(n);
	}
	
	public String toString() {
		// return a "Temp_n" string if a register was not assigned yet.
		// If there is an asigned register, return the register.
		if(this.color == null) return String.format("Temp_%d", this.serial);
		return this.color;
	}
}
