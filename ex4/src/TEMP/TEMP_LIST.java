package TEMP;

public class TEMP_LIST {
	public TEMP value;
	public TEMP_LIST next;
	public int length = 0;
	
	public TEMP_LIST (TEMP value, TEMP_LIST next) {
		this.value = value;
		this.next = next;
		if(value != null) this.length = 1;
		if(next != null) this.length += next.length;
	}
	
	public boolean equals(TEMP_LIST other){
		// assume sorted lists
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
		
		for(TEMP_LIST e=this;e!=null;e=e.next){
			// null value mean empty list
			if(e.value == null) return;
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
				// element removed - decrease length
				this.length -= 1;
				return;
			}
			prevValue = e;
		}
		
		// element not found
		return;
	}
	
	public void add(TEMP other){
		if(other == null)
			return;
		
		TEMP_LIST prevValue = null;
		
		for(TEMP_LIST e=this;e!=null;e=e.next){
			if(e.value == null){
				e.value = other;
				e.next = new TEMP_LIST(null,null);
				length += 1;
				return;
			} else if(e.value.getSerialNumber() == other.getSerialNumber())
				// element already present
				return;
			else if(e.value.getSerialNumber() > other.getSerialNumber()){
				if(prevValue == null){
					e.next = new TEMP_LIST(e.value,e.next);
					e.value = other;
				} else{
					prevValue.next = new TEMP_LIST(other,e);
				}
				// element added - increase length
				this.length += 1;
				return;
			}
			prevValue = e;
		}
		// might have to remove that row
		prevValue.next = new TEMP_LIST(other,null);
	}

	public void union(TEMP_LIST other){
		for(TEMP_LIST e=other; e!= null;e=e.next){
			this.add(e.value);
		}
	}

	public TEMP_LIST clone(){
		TEMP_LIST tmp = null;
		if(this.next != null)
			tmp = this.next.clone();
		return new TEMP_LIST(this.value,tmp);
	}
}
