package AST; import TYPES.*;


public abstract class AST_EXP extends AST_Node
{
	AST_EXP(int line){
		super(line);
	}

	public TYPE SemantMe() { return null; }
}

