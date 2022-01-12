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
	// assume sorted lists
	public boolean equals(TEMP_LIST other){
		if(other == null)
			return false;
		// there is always one instance of every TEMP
		// so we can compare the pointers
		if(this.value != other.value ){
			return false;
		}
		if(this.next == null){
			if(other.next == null) return true;
			else return false;
		}
		return this.next.equals(other.next);
	}
	public void remove(TEMP other){
		if(other == null)
			return;
		TEMP_LIST prevValue = null;
		for(TEMP_LIST e=this;e!= null;e=e.next){
			if(e.value.getSerialNumber() == other.getSerialNumber()){
				if(prevValue == null){
					if(e.next == null){
						// prev and next are null so empty list
						e.value = null;
					} else{
						e.value = e.next.value;
						e.next = e.next.next;
					}
				} else{
					prevValue.next = e.next;
				}
				return;
			}
			prevValue = e;
		}
		return;
	}
	public void add(TEMP other){
		if(other == null)
			return;
		if(this.next == null){
			this.next = new TEMP_LIST(other,this.next);
			return;
		}
		TEMP_LIST prevValue = null;
		for(TEMP_LIST e=this;e!=null;e=e.next){
			if(e.value.getSerialNumber() == other.getSerialNumber())
				return;
			else if(e.value.getSerialNumber() > other.getSerialNumber()){
				if(prevValue == null){
					e.next = new TEMP_LIST(e.value,e.next);
					e.value = other;
				} else{
					prevValue.next = new TEMP_LIST(other,e);
				}
				return;
			}
			prevValue = e;
		}
		prevValue.next = new TEMP_LIST(other,null);
	}
	public TEMP_LIST clone(){
		TEMP_LIST tmp = null;
		if(this.next != null)
			tmp = this.next.clone();
		return new TEMP_LIST(this.value,tmp);
	}
}
