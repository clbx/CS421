
public class Instruction {
	String	label = "";	
	Opcode	opcode = Opcode.none;
	int size = 0;
	Operand	src = null;
	Operand	dst = null;
	String	comment = "";
	Instruction next = null;
	
	public Instruction() {
		// Empty constructor to create an empty instruction
	}
	
	public Instruction(Opcode opcode) {
		this.opcode = opcode;
	}
}
