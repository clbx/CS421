
public class Operand {
	DataType type;	    // Data type (size) of operand
	int 	size;				// Size of data in bytes
	AddrMode mode;	    // Addressing mode � imm, reg, idx, etc
	int 	value;		    // Value for immediate mode or offset
	String 	label;		    // Label referenced
	String	reg;		    // Register
	String	idx;		    // Index Register
	String	base;	    	// Base Register

	public Operand() {
	}

	public Operand(ExpNode sym) {
		if (sym==null) {
			System.out.println("Error - Null symbol table reference, could this be an undefined identifier?");
			return;
		}
		switch (sym.operation) {
		case 'r':
			//Look the variable name up in symbol table
			type = DataType.inttype;
			size = 4;
			label = sym.operand;
			mode = AddrMode.abs;
			//if (sym.allocation==Allocation.methodAllocation) {
			//	mode = AddrMode.idx;
			//	value = -sym.displacement;
			//	idx = "%rbp";
			//}
			break;
		case 'c':
			type = DataType.inttype;
			size = 4;
			mode = AddrMode.imm;
			value = Integer.parseInt(sym.operand);
			break;
		default:
			System.out.println("Illegal use of identifier in operand");
		}
	}
	
	public Operand(String label) {
		type = DataType.none;
		mode = AddrMode.abs;
		this.label = label;
	}

	public Operand(int value) {
		type = DataType.inttype;
		mode = AddrMode.imm;
		this.value = value;
		size = 4;
	}
	
	public Operand(int r, int size) {
		type = DataType.none;
		mode = AddrMode.reg;
		value = r;		// Save the register number in case we need it as an int
		this.size = size;
		reg = "%r"+r;
		switch (size) {
		case 8: 
			break;
		case 4: reg+="d";
			break;
		case 2: reg+="w";
			break;
		case 1: reg+="b";
			break;
		default: System.out.println("Your compiler writer is crap!");		
		}
		if (r==7) reg="%rbp";
		if (r==6) reg="%rsp";
	}
}
