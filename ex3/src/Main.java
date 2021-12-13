   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import TYPES.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l ;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];


		/********************************/
		/* [1] Initialize a file reader */
		/********************************/
		try{
			file_reader = new FileReader(inputFilename);
		} catch (Exception e){
			e.printStackTrace();
			return;
		}
		/******************************/
		/* [3] Initialize a new lexer */
		/******************************/
		l = new Lexer(file_reader);
		
		/*******************************/
		/* [4] Initialize a new parser */
		/*******************************/
		p = new Parser(l);

		try
		{
			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_PROGRAM) p.parse().value;

			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();
			/*************************/
			/* [7] check for errors  */
			/*************************/
			TYPE t = AST.SemantMe();
			if(t.isError()){
				System.out.format("ERROR(%d)\n",((TYPE_ERROR) t).line);
				String msg = String.format("ERROR(%d)",((TYPE_ERROR) t).line);
				writeToFile(outputFilename,msg);
				return;
			}

			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();
			writeToFile(outputFilename,"OK");
			System.out.println("OK");
    	}
		catch (IOException e){
			writeToFile(outputFilename,"ERROR");
		}     
		catch (Exception e)
		{	
			e.printStackTrace();
			String msg = String.format("ERROR(%d)",l.getLine());
			writeToFile(outputFilename,msg);
		}
	}


	public static void writeToFile(String fileName, String msg){
		try {
			PrintWriter file_writer;
			file_writer = new PrintWriter(fileName);
			file_writer.print(msg);
			file_writer.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
}


