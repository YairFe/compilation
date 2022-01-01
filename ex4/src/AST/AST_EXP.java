package AST; import TYPES.*; import TEMP.*; import IR.*; import TEMP.*; import IR.*;


public abstract class AST_EXP extends AST_Node
{
	AST_EXP(int line){
		super(line);
	}

	public TYPE SemantMe() { return null; }
	public TEMP IRme() { return null; }
}

