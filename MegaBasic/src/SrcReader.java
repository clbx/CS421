import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SrcReader {
  String filename;
  File infile;
  Scanner src;
  char ch = ' ';
  String line = "";
  int pos = 0;
  public static final char eof = (char) 4;
  public static final char eol = '\n';

  public SrcReader(String name) throws FileNotFoundException {
    filename = name;
    infile = new File(filename);
    src = new Scanner(infile);
  }

  public char nextch() {
    if (pos >= line.length()) {
      if (src.hasNextLine()) {
        line = src.nextLine() + eol;
        System.out.println("// " + line);
        pos = 0;
        //System.out.println("EOL");
        //return nextch();
      } else {
        System.out.println("EOF");
        return eof;
      }
    }
    ch = line.charAt(pos++);
    return ch;
  }

}
