import java.io.FileNotFoundException;

public class LeapFrog {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		SrcReader src = new SrcReader("test.txt");
		Lexan lex = new Lexan(src);
		char ch;
		Token sy;
		/*
		ch = src.nextch();

		while (ch!= SrcReader.eof) {
			System.out.print(ch);
			ch = src.nextch();
		}
		System.out.println("End-of-file");
		*/
		sy = lex.next();
		while (sy!=Token.eof) {
			System.out.print(sy);
			if (sy==Token.Integer) System.out.print(": "+lex.getInum());
			if (sy==Token.Real) System.out.print(": "+lex.getRnum());
			if (sy==Token.Ident) System.out.print(": "+lex.getIdent());
			System.out.println();
			sy = lex.next();
		}
	}

}
