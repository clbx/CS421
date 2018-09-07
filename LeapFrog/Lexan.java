public class Lexan{

  private SrcReader src;
  private char ch = ' ';
  private Token[] iniToken = new Token[128];
  private int inum = 0;
  private String name = "";

  public Lexan(SrcReader src){
    this.src = src;
    initialize();
  }

  public int getInum(){
    return inum;
  }

  public void initialize(){
    int i;
    for(i = 0; i < iniToken.length; i++)
      iniToken[i] = Token.nullToken;
    for(i = '0'; i <= '9'; i++)
      iniToken[i] = Token.Number;
    for(i = 'A'; i <='Z'; i++)
      iniToken[i] = Token.Ident;
    for(i = 'a'; i <='z'; i++)
      iniToken[i] = Token.Ident;
    iniToken['='] = Token.Equal;
    iniToken['+'] = Token.Plus;
    iniToken['-'] = Token.Minus;
    iniToken['*'] = Token.Mult;
    iniToken['/'] = Token.Divd;
    iniToken['('] = Token.Lparan;
    iniToken[')'] = Token.Rparan;
  }

  public Token next(){
    Token sy = Token.nullToken;
    while(ch==' ') ch = src.nextch();
    sy = iniToken[ch];

    switch(sy){
      case Number:
      inum = 0;
        while(ch >= '0' && ch <= '9'){
          inum = inum*10+ch-'0';
          ch = src.nextch();
        }
        break;
      case Ident:
        name = "";
        while(Character.isLetter(ch) || Character.isDigit(ch)){
          name+=ch;
          ch = src.nextch();
        }
        break;
      default:
    }

    ch=src.nextch();
    return sy;
  }
}
