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
	public TEMP_LIST neighbors = null;
	public String color = null;
	
	public TEMP(int serial)
	{
		this.serial = serial;
	}
	
	public int getSerialNumber()
	{
		return serial;
	}
	
	public void addNeighbor(TEMP n) { 
		if(this.neighbors == null) this.neighbors = new TEMP_LIST(n, null);
		else this.neighbors.add(n);
	}
	
	public void removeNeighbor(TEMP n) { 
		if(this.neighbors != null) this.neighbors.remove(n);
	}
	
	public String toString() {
		if(this.color == null) return String.format("Temp_%d", this.serial);
		return this.color;
	}
}
