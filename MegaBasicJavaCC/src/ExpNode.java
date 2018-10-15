/**
 * 
 * @author buxtonc
 *
 */


public class ExpNode {
	
	/**
	 * Can be:
	 * c if it's a constant
	 * or an operator (+,-,=,etc.)
	 */
	char operator;   //Holds operators when they're needed
	String operand;  //Holds operands when they're needed
	
	ExpNode left = null;
	ExpNode right = null;
	
	/**
	 * traverses the tree.
	 * Call on root node
	 */
	public void traverse() {
		if(left != null) 
			left.traverse();
		if(right != null) 
			right.traverse();
		System.out.print(this+" ");
	}
	
	/**
	 * @return String returns the operator of it was an operator, returns an operand if it was an operand
	 */
	public String toString() {
		if(operator == 'c')
			return operand;
		return " "+operator;
	}
}
