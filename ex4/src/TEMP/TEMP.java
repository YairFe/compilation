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
	public TEMP_LIST neighbors = new TEMP_LIST(null,null); // neighbor list for the interference graph - will be updated during liveness analysis
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
		return this.neighbors.length;
	}
	
	public void addNeighbor(TEMP n) { 
		this.neighbors.add(n);
	}
	
	public void removeNeighbor(TEMP n) { 
		// remove a node from the neighbor list
		this.neighbors.remove(n);
	}
	public void disconnectFromNeighbors(){
		for(TEMP_LIST e=this.neighbors;e.value!=null;e=e.next){
			e.value.removeNeighbor(this);
		}
	}
	public String toString() {
		// return a "Temp_n" string if a register was not assigned yet.
		// If there is an asigned register, return the register.
		if(this.color == null) return String.format("Temp_%d", this.serial);
		return this.color;
	}
}
