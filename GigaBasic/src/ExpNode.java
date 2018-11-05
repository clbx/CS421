
public class ExpNode {
	char operation;
	String operand;
	ExpNode left=null, right=null;
	
	public void traverse() {
		if (left!=null) left.traverse();
		if (right!=null) right.traverse();
		System.out.print(this+" ");
	}
	public String toString() {
		if (operation=='r' || operation=='c') return operand;
		return ""+operation;
	}
}
