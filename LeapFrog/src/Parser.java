public class Parser{

  Token tok;
  Lexan lex;

  public Parser(Lexan lex){
    this.lex = lex;
  }

  public void trump(){
    expect(Token.Trump);
    expect(Token.Lbrack);
    while(tok == Token.Tweet || tok == Token.Integer ||
      tok == Token.Ident || tok == Token.Lparen ||
      tok == Token.Question || tok == Token.eol){

      statement();

    }
    expect(Token.Rbrack);
    expect(Token.Impeached);
  }

  public void expect(Token sy){
    if(tok == sy){
      tok = lex.next();
    }else{
      error("Expected "+ sy + ", not found");
    }
  }

  public void tweetStmt(){
    expect(Token.Tweet);
    expression();
  }

  public void expression(){
    simpleExp();
    while(tok == Token.Lss || tok == Token.Gtr || tok == Token.Eql){
      expression();
    }
  }

  public void simpleExp(){
    term();
    while(tok == Token.Plus || tok == Token.Minus){
      term();
    }

  }

  public void term(){
    factor();
    while(tok == Token.Mult || tok == Token.Divd){
      term();
    }
  }

  public void factor(){
    switch(tok){
      case Integer:
        expect(Token.Integer);
        break;
      case Ident:
        expect(Token.Ident);
        break;
      case Lparen:
        expect(Token.Lparen);
        expression();
        expect(Token.Rparen);
        break;
    }
  }

  public void error(String errmsg){
    System.out.println("Error! " + errmsg);
  }

  public void statement(){
    switch(tok){
      case Tweet:
        tweetStmt();
        break;
      case Integer:
      case Lparen:
      case Ident:
        expression();
        break;
      case Question:
      case eol:
        tok = lex.next();
    }
  }

}
