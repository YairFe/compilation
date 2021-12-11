package TYPES;

public class TYPE_ERROR extends TYPE
{
    int line;
	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	public TYPE_ERROR(int line) {
        this.line = line;
        this.name = "ERROR";
    }

	/*************/
	/* isError() */
	/*************/
	public boolean isError(){ return true;}
}
