public interface TokenNames {
  /* terminals */
  public static final int EOF = 0;
  public static final int PLUS = 1;
  public static final int MINUS = 2;
  public static final int TIMES = 3;
  public static final int DIVIDE = 4;
  public static final int LPAREN = 5;
  public static final int RPAREN = 6;
  public static final int LBRACE = 7;
  public static final int RBRACE = 8;
  public static final int LBRACK = 9;
  public static final int RBRACK = 10;
  public static final int NUMBER = 11;
  public static final int ID = 12;
  public static final int ASSIGN = 13;
  public static final int STRING = 14;
  public static final int COMMA = 15;
  public static final int DOT = 16;
  public static final int SEMICOLON = 17;
  public static final int EQ = 18;
  public static final int LT = 19;
  public static final int GT = 20;
  public static final int TYPE_INT = 21;
  public static final int TYPE_STRING = 22;
  public static final int CLASS = 23;
  public static final int EXTENDS = 24;
  public static final int NIL = 25;
  public static final int RETURN = 26;
  public static final int ARRAY = 27;
  public static final int NEW = 28;
  public static final int WHILE = 29;
  public static final int IF = 30;
  

  public static String translateToken(int token){
    switch(token){
      case 0:
        return "EOF";
      case 1:
        return "PLUS";
      case 2:
        return "MINUS";
      case 3:
        return "TIMES";
      case 4:
        return "DIVIDE";
      case 5:
        return "LPRAEN";
      case 6:
        return "RPAREN";
      case 7:
        return "LBRACE";
      case 8:
        return "RBRACE";
      case 9:
        return "LBRACK";
      case 10:
        return "RBRACK";
      case 11:  
        return "NUMBER";
      case 12:
        return "ID";
      case 13:
        return "ASSIGN";
      case 14:
        return "STRING";
      case 15:
        return "COMMA";
      case 16:
        return "DOT";  
      case 17:
        return "SEMICOLON";
      case 18:
        return "EQ";
      case 19:
        return "LT";
      case 20:
        return "GT";
	    case 21:
		    return "TYPE_INT";
	    case 22:
		    return "TYPE_STRING";
	    case 23:
		    return "CLASS";
	    case 24:
		    return "EXTENDS";
	    case 25:
		    return "NIL";
	    case 26:
		    return "RETURN";
	    case 27:
		    return "ARRAY";
	    case 28:
		    return "NEW";
	    case 29:
		    return "WHILE";
	    case 30:
		    return "IF";
      default:
        return "ERROR";
    }
  }
}
