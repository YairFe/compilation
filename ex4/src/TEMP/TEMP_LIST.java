package TEMP;

public class TEMP_LIST {

	public TEMP_LIST prev;
	public TEMP value;
	public TEMP_LIST next;
	
	public TEMP_LIST (TEMP value, TEMP_LIST next) {
		this.value = value;
		this.next = next;
		this.prev =  null;
		if(this.next != null) { this.next.prev = this; }
	}
}
