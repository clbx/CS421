
public class Stmt {
	String type;
	
	ExpNode expression;
	Stmt child;
	Stmt child_else;
	Stmt next;
	
	public void traverse(int level) {
		indent(level);
		System.out.print(type+": ");
		if (expression!=null) expression.traverse();
		System.out.println();
		if (child!=null) {
			child.traverse(level+1);
		}
		if (next!=null) {
			next.traverse(level);
		}
	}
	
	public void indent(int n) {
		for (;n>0; n--)
			System.out.print("   ");
	}
}
