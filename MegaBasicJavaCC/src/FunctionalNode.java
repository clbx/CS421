/**
 * 
 * @author buxtonc
 *
 */


public class FunctionalNode {
	
	/**
	 * Can be:
	 * c if it's a constant
	 * or an operator (+,-,=,etc.)
	 */
	
	ExpNode child = null;
	FunctionalNode next = null;
	
	/**
	 * traverses the tree.
	 * Call on root node
	 */
	public void execute() {
		if(child != null) {
			child.traverse();
		}
		if(next != null) {
			next.execute();
		}
	}
}
