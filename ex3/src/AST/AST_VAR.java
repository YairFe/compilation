package AST;
import TYPES.*;

public abstract class AST_VAR extends AST_Node
{
    public AST_VAR(int line){
        super(line);
    }
    public TYPE SemantMe(){
        return new TYPE_ERROR(line);
    }
}
