import java.io.*;

public class LeapFrog{
  public static void main(String[] args) throws FileNotFoundException{
      SrcReader src = new SrcReader("text.txt");
      Lexan lex = new Lexan(src);
      char ch;
      Token sy;
      /*
      ch = src.nextch()
      while(ch != src.eof){
        System.out.print(ch);
        ch = src.nextch();
      }
      System.out.println("End of File");
      */
      sy = lex.next();
      while(sy != Token.eof){
        System.out.println(sy);
        sy = lex.next();
      }
  }
}
