import java.io.*;
import java.util.*;

public class SrcReader{
  String filename;
  File infile;
  Scanner src;
  char ch = ' ';
  String line = "";
  int pos=0;
  public static final char eof = (char)-1;
  public static final char eol = '\n';


  public SrcReader(String name) throws FileNotFoundException{
    filename = name;
    infile = new File(filename);
    src = new Scanner(infile);

  }

  public char nextch(){
    if(pos >= line.length()){
      if(src.hasNextLine()){
        line = src.nextLine();
        pos = 0;
        return eol;
      }
      else
        return eof;
    }
    ch = line.charAt(pos++);
    return ch;
  }
}
