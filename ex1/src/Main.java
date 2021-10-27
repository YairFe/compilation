   
import java.io.*;
import java.io.PrintWriter;
import java.lang.Math;
import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			file_reader = new FileReader(inputFilename);
			file_writer = new PrintWriter(outputFilename);
			l = new Lexer(file_reader);
			s = l.next_token();

			while (s.sym != TokenNames.EOF)
			{
				if (s.sym == TokenNames.NUMBER)
					if((Integer) s.value < 0 || (Integer) s.value >= (1<<16))
						throw new Exception("not a Number");
				
				printToken(l,s,file_writer);
				
				s = l.next_token();
			}
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
			writeErrorToFile(outputFilename);
		}
	}

	public static void writeErrorToFile(String fileName){
		try {
			PrintWriter file_writer;
			file_writer = new PrintWriter(fileName);
			file_writer.print("ERROR");
			file_writer.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	public static void printToken(Lexer l, Symbol s, PrintWriter file_writer) throws Exception{
		if (s.value != null){
			file_writer.print(TokenNames.translateToken(s.sym));
			file_writer.print("(" + s.value + ")");
			file_writer.print("[" + l.getLine() + "," + l.getTokenStartPosition() + "]");
			System.out.print(TokenNames.translateToken(s.sym));
			System.out.print("(" + s.value + ")");
			System.out.println("[" + l.getLine() + "," + l.getTokenStartPosition() + "]");
		} else {
			file_writer.print(TokenNames.translateToken(s.sym));
			file_writer.print("[" + l.getLine() + "," + l.getTokenStartPosition() + "]");
			System.out.print(TokenNames.translateToken(s.sym));
			System.out.println("[" + l.getLine() + "," + l.getTokenStartPosition() + "]");
		}
		file_writer.print("\n");
	}

}

