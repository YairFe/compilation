package TYPES;

public class TYPE_ARRAY extends TYPE
{
    public TYPE array_type;

    public TYPE_ARRAY(String name, TYPE type){
        this.name = name;
        this.array_type = type;
    }
	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return true;}
}
