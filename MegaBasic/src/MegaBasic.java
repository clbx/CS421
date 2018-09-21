import java.io.FileNotFoundException;

public class MegaBasic {

	public static void main(String[] args) throws FileNotFoundException {
		SrcReader src = new SrcReader("test.mb");
		Lexan lex = new Lexan(src);
		Parser parser = new Parser(lex);
		char ch;
		Token sy;

		System.out.println("Parsing Beginning");
		parser.program();
		System.out.println("Parsing Complete");
	}

}
