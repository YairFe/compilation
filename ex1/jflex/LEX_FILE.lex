/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/***********************/
/* MACRO DECLARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
OtherWhiteSpace = [ \t\f]
Letters = [a-zA-Z]
Other = .
Digits = [0-9]
WhiteSpace		= {LineTerminator} | {OtherWhitespace}
CommChars 		= "{" | "}" | "[" | "]" | "?" | "!" | "/" | "*" | "-" | "+" | "." | ";" 
INTEGER			= 0 | [1-9][0-9]*

/* NOTE: currently assuming that an identifier can start with an uppercase letter.
   If not, should be rewritten to: ID = ([a-z]+)({Digits} | {Letters})* */
ID				= ({Letters}+)({Digits} | {Letters})*

STRING 			= \" ( {Letters}* ) \"
LineComm		= "//"( {Letters} | {Digits} | {OtherWhiteSpace} | {CommChars} ) * ( {LineTerminator} )
BlockComm		= "/*"( ( {Letters} | {Digits} | {CommChars} | {WhiteSpace} )* )"*/"
CLASS_KEY = "class"
EXTENDS_KEY = "extends" 
NIL_KEY = "nil" 
RETURN_KEY = "return" 
ARRAY_KEY = "array" 
NEW_KEY = "new" 
WHILE_KEY = "while" 
IF_KEY = "if"
INT_KEY = "int"
STRING_KEY = "string"
ASSIGN = ":="

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

"+"					{ return symbol(TokenNames.PLUS);}
"-"					{ return symbol(TokenNames.MINUS);}
"*"					{ return symbol(TokenNames.TIMES);}
"/"					{ return symbol(TokenNames.DIVIDE);}
"("					{ return symbol(TokenNames.LPAREN);}
")"					{ return symbol(TokenNames.RPAREN);}
"["					{ return symbol(TokenNames.RBRACK);}
"]"					{ return symbol(TokenNames.LBRACK);}
"{"					{ return symbol(TokenNames.RBRACE);}
"}"					{ return symbol(TokenNames.LBRACE);}
"."					{ return symbol(TokenNames.DOT);}
","					{ return symbol(TokenNames.COMMA);}
";"					{ return symbol(TokenNames.SEMICOLON);}
"<"					{ return symbol(TokenNames.LT);}
">"					{ return symbol(TokenNames.GT);}
{ASSIGN}				{ return symbol(TokenNames.ASSIGN);}
"="					{ return symbol(TokenNames.EQ);}
{INTEGER}				{ return symbol(TokenNames.NUMBER, new Integer(yytext()));}
{CLASS_KEY}				{ return symbol(TokenNames.CLASS);}
{EXTENDS_KEY}			        { return symbol(TokenNames.EXTENDS);}
{NIL_KEY}				{ return symbol(TokenNames.NIL);}
{RETURN_KEY}				{ return symbol(TokenNames.RETURN);}
{ARRAY_KEY}				{ return symbol(TokenNames.ARRAY);}
{NEW_KEY}				{ return symbol(TokenNames.NEW);}
{WHILE_KEY}				{ return symbol(TokenNames.WHILE);}
{IF_KEY}				{ return symbol(TokenNames.IF);}
{INT_KEY}				{ return symbol(TokenNames.TYPE_INT);}
{STRING_KEY}				{ return symbol(TokenNames.TYPE_STRING);}
{ID}					{ return symbol(TokenNames.ID,     new String( yytext()));} 
{STRING}				{ return symbol(TokenNames.STRING, new String(yytext()));}
{WhiteSpace}				{ /* just skip what was found, do nothing */ }
/*
	NOTE: currently assuming that comments should be ignored.
	If comments should be part of the output, need to update
	the next two cases.
*/
{LineComm}				{ /* just skip what was found, do nothing */ }
{BlockComm}				{ /* just skip what was found, do nothing */ }
<<EOF>>					{ return symbol(TokenNames.EOF);}
{Other}					{ /* lexical error */
					  throw new Exception("Lexical error");}
}
