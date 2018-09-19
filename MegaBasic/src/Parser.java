
public class Parser {
	Lexan lex;
	Token tok;
	
	public Parser(Lexan lex) {
		this.lex = lex;
		tok = lex.next();
	}
	
	public void program() {
		System.out.println("Program "+tok);
		while (tok==Token.Print || tok==Token.Ident) {
			stmt();
		}
		expect(Token.eof);
	}
	
	public void stmt() {
		System.out.println("Statement "+tok);
		switch (tok) {
		case Print:
			printStmt();
			break;
		case Ident:
			assignment();
			break;
		default:
			error("Illegal beginning of a statement");	
		}
		expect(Token.eol);
	}
	
	public void printStmt() {
		expect(Token.Print);
		expression();
	}
	
	public void assignment() {
		varRef();
		expect(Token.Equal);
		expression();
	}
	
	public void expression() {
		term();
		while (tok==Token.Plus || tok==Token.Minus) {
			tok = lex.next();
			term();
		}
	}

	public void term() {
		factor();
		while (tok==Token.Mult || tok==Token.Divd) {
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
    System.out.println("Expect: " + sy + " Found: " + tok);
		if (tok == sy) {
			tok = lex.next();
		} else 
			error("Expected "+sy+", not found");
	}

	public void error(String errmsg) {
		System.out.println("Error! "+errmsg);
	}

}
