package AST;
import TYPES.*;

public abstract class AST_VAR extends AST_Node
{
    // label name of the variable
    public String name;
    // index of the variable for the offset calculation
    public int index;
    // global local or param
    public String scope_type; 

    public AST_VAR(int line){
        super(line);
    }
    public TYPE SemantMe(){
        return new TYPE_ERROR(line);
    }
}
