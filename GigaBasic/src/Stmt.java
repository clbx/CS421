
public class Stmt {
	Stmt next;
	Stmt child;
	
	ExpNode expression;
	String type;

	public void traverse(int level) {
		indent(level);
		System.out.println(type);
		if(child != null) {
			child.traverse(level+1);			
		}
		if(next != null) {
			next.traverse(level);
		}
		
	}
	
	
	
	
	public void indent(int n) {
		for(;n>0;n--) {
			System.out.print("   ");
		}
	}
	
	
}
