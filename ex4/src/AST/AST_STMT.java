package AST;
import TYPES.*;

public abstract class AST_STMT extends AST_Node
{
	public AST_STMT(int line){
		super(line);
	}
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}

	public TYPE SemantMe(){
        return new TYPE_ERROR(line);
    }
}
