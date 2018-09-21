
public class Parser {
  final boolean debug = true;
  Lexan lex;
  Token tok;

  public Parser(Lexan lex) {
    this.lex = lex;
    tok = lex.next();
  }

  public void program() {
    if (debug) System.out.println("Program " + tok);
    while (tok == Token.Print || tok == Token.Ident) {
      stmt();
    }
    expect(Token.eof);
  }

  public void stmt() {
    if (debug) System.out.println("Statement " + tok);
    switch (tok) {
      case Print:
        System.out.println("IS PRINT");
        printStmt();
        break;
      case Ident:
        System.out.println("IS ASSIGNMENT");
        assignment();
        break;
      case Literal:
        printStmt();
        break;
      default:
        error("Illegal beginning of a statement");
    }
    expect(Token.eol);
  }

  public void printStmt() {
    tok = lex.next();
    if (tok == Token.Literal) {
      System.out.println("Is literal");
      literal();
    } else {
      System.out.println("Is expression");
      expression();
    }

  }

  public void literal() {
    expect(Token.Literal);
    //tok = lex.next();
  }

  public void assignment() {
    varRef();
    expect(Token.Equal);
    expression();
  }

  public void expression() {
    term();
    while (tok == Token.Plus || tok == Token.Minus) {
      tok = lex.next();
      term();
    }
  }

  public void term() {
    factor();
    while (tok == Token.Mult || tok == Token.Divd) {
      tok = lex.next();
      factor();
    }
  }

  public void factor() {
    switch (tok) {
      case Integer:
        tok = lex.next();
        break;
      case Ident:
        varRef();
        break;
      case Lparen:
        tok = lex.next();
        expression();
        expect(Token.Rparen);
        break;
      default:
        error("Illegal token in an expression");
    }
  }

  public void varRef() {
    expect(Token.Ident);
  }

  public void expect(Token sy) {
    if (debug) System.out.println("Expect " + sy + "  Found " + tok);
    if (tok == sy) {
      tok = lex.next();
    } else
      error("Expected " + sy + ", not found");
  }

  public void error(String errmsg) {
    System.out.println("Error! " + errmsg);
  }

}
