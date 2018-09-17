public class MegaBasic {

  public static void main(String[] args) {
    SrcReader src = new SrcReader("test.txt");
    Lexan lex = new Lexan(src);
    Parser parser = new Parser(lex);
    char ch;
    Token sy;
  }

}
