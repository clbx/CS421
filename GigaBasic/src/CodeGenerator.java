
public class CodeGenerator {

	private static Instruction head = null, tail = null;
	private static int labelCount=1;
	private static boolean[] registers = new boolean[16];
	private final static int maxreg=4;
	private final static int reglimit=1;
	private static int regcount=maxreg;

	public static void walk(Stmt tree) {
		System.out.println("\n\nWalking the Tree\n");
		freeRegisters();
		//System.out.println("Class "+tree.name);
		//printSymbolTable(tree.name,tree.symtable);
		//Stmt m = tree.child;
		blockCode(tree.child);		
		/*		System.out.println(" Block of Statements");
		while (m!=null) {
			freeRegisters();
			System.out.println("    Method: "+m.name);
			printSymbolTable(m.name,m.symtable);
			insert(genLabel(m.name));
			Operand rbp = new Operand(7,4);
			Operand rsp = new Operand(6,4);
			Operand c16 = new Operand(16);
			insert(genCode(Opcode.push,rbp));		// Save the rbp register
			insert(genCode(Opcode.mov,rsp,rbp));	// Make the rbp register point to the current top of the stack
			insert(genCode(Opcode.sub,c16,rsp));	// Allocate 16 bytes on the stack for local variables
			blockCode(m.body);
			insert(genCode(Opcode.mov,rbp,rsp));	// Restore the stack pointer
			insert(genCode(Opcode.pop,rbp));		// Restore the rbp register
			insert(genCode(Opcode.ret));

			m = m.next;
		}
		 */
		System.out.println("+++Walk Complete+++\n\n");
		printInstructionStream(head);
	}

	private static void blockCode(Stmt stmt) {
		while (stmt!=null) {
			switch (stmt.type) {
			case "assignment": 
				expressionCode(stmt);
				break;
			case "while":
				whileCode(stmt);
				break;
			case "if":
				ifCode(stmt);
				break;
			default:
				System.out.println("     Unimplemented stmt - "+stmt.type);
			}
			stmt = stmt.next;
		}
	}
		
	private static void whileCode(Stmt t) {
		System.out.println("While statement");
		printExpressionTree(t.expression,2);

//		Instruction topLabel = genLabel("WhileLoop");
		Instruction topLabel = genLabel();
		Operand topref = new Operand(topLabel.label);
//		Instruction botLabel = genLabel("WhileEnd");
		Instruction botLabel = genLabel();
		Operand botref = new Operand(botLabel.label);
//		Instruction testLabel =  genLabel("WhileTest");
		Instruction testLabel =  genLabel();
		Operand testref = new Operand(testLabel.label);
		Operand condition;

		insert(genCode(Opcode.jmp,testref));
		insert(topLabel);
		blockCode(t.child);
		insert(testLabel);
		condition = mathCode(t.expression);
		insert(genCode(Opcode.test,condition,condition));
		insert(genCode(Opcode.jnz, topref));
		insert(botLabel);
	}

	private static void ifCode(Stmt t) {
		System.out.println("If statement");

	}

	private static void expressionCode(Stmt t) {
		ExpNode root = t.expression;
		System.out.println("Expression statement");
		// DISPLAY THE EXPRESSION TREE
		//printExpressionTree(root,1);

		// GENERATE THE CODE
		mathCode(root);
		insert(new Instruction());	// Insert a blank line between statements
		freeRegisters();
	}

	private static Operand mathCode(ExpNode root) {
		Operand src, dst;
		Opcode opcode;
		if (root==null) {
			System.out.println("*mathCode* - entered with null reference");
			return null;
		}
		System.out.print("*mathCode* - "+root.operation+" ");
		switch (root.operation) {
		case 'r':		// Reference to a variable
			src = new Operand(root);
			System.out.println(root.operand);
			return src;
		case 'c':		// Constant
			src = new Operand(root);
			System.out.println(root.operand);
			return src;
		}
		dst = mathCode(root.left);
		src = mathCode(root.right);
		opcode = makeOpcode(root.operation);
		System.out.println("   Operation: "+opcode+"  "+src.value+"  "+dst.label);
		switch (opcode) {
		case mov:
			if (src.mode!=AddrMode.reg) {
				Operand reg = getRegister(dst.size);	// Get a register
				System.out.println("get a reg"+reg.reg);
				if (src.mode==AddrMode.stack)
					insert(genCode(Opcode.pop,reg));
				else
					insert(genCode(Opcode.mov,src,reg));						
				insert(genCode(Opcode.mov,reg,dst));
			}
			else {
				insert(genCode(Opcode.mov,src,dst));
			}
			break;
		default:
			if (dst.mode==AddrMode.imm &&src.mode==AddrMode.imm) {
				switch (opcode) {
				case add:
					dst.value += src.value;
					break;
				case sub:
					dst.value -= src.value;
					break;
				default:

				}
				return dst;
			}
			if (dst.mode!=AddrMode.reg) {
				Operand reg = getRegister(dst.size);
				if (dst.mode==AddrMode.stack)
					insert(genCode(Opcode.pop, reg));
				else
					insert(genCode(Opcode.mov,dst,reg));
				dst = reg;
			}
			if (src.mode==AddrMode.stack) {
				Operand reg = getRegister(src.size);
				insert(genCode(Opcode.pop,reg));
				src = reg;
			}
			switch(opcode) {
			case sete: case setne: case setl: case setg:
			case setle: case setge:
				insert(genCode(Opcode.cmp,src,dst));
				insert(genCode(opcode,dst));
				break;
			default:
				insert(genCode(opcode,src,dst));
			}
			freeRegister(src);

			if (regcount<reglimit && dst.mode==AddrMode.reg) {			// If the results is in a register, free it up by 
				Operand stack =  new Operand(); 	// pushing the results onto the stack
				stack.mode = AddrMode.stack;			// This is only necessary if we run out of registers
				stack.size = dst.size;
				stack.type = dst.type;
				insert(genCode(Opcode.push,dst));
				freeRegister(dst);
				dst = stack;
			}

			break;
		}

		return dst;
		/*
		case method:
			dst = new Operand(root.sym);
			insert(genCode(Opcode.call,dst));
			src = new Operand(1,4);
			return src;
		 */

	}

	private static Opcode makeOpcode(char operator) {
		Opcode opcode;
		switch (operator) {
		case '+':  	return Opcode.add;
		case '-':   return Opcode.sub;
		case '*':   return Opcode.mul;
		case '/':   return Opcode.div;
		//case '=': 	return Opcode.sete;
		case '!':  return Opcode.setne;
		case '<':   return Opcode.setl;
		case '>':   return Opcode.setg;
		case '=':   return Opcode.mov;
		default: 	return Opcode.none;
		}
	}

	private static void printExpressionTree(ExpNode root, int level) {
		int i;
		if (root==null) return;
		for (i=0; i<level; i++) System.out.print("   ");
		System.out.println(root.operation);
		printExpressionTree(root.left, level+1);
		printExpressionTree(root.right, level+1);
	}

	private static Instruction genCode(Opcode opcode) {
		Instruction code = new Instruction(opcode);
		return code;
	}

	private static Instruction genCode(Opcode opcode, Operand src) {
		Instruction code = new Instruction(opcode);
		code.src = src;
		return code;
	}

	private static Instruction genCode(Opcode opcode, Operand src, Operand dst) {
		Instruction code = new Instruction(opcode);
		code.size = src.size;
		code.src = src;
		code.dst = dst;
		return code;
	}

	private static Instruction genLabel() {
		Instruction label = new Instruction(Opcode.none);
		labelCount++;
		label.label = ".L"+labelCount;
		return label;
	}

	private static Instruction genLabel(String name) {
		Instruction label = new Instruction(Opcode.none);
		label.label = name;
		return label;
	}



	private static void insert(Instruction code) {
		if (code==null) return;
		if (head==null)
			head = code;
		else
			tail.next = code;
		tail = code;
		/*
		System.out.print("	"+code.opcode+"	");
		if (code.src!=null) {
			System.out.print(code.src.mode);
		}
		if (code.dst!=null)
			System.out.print(", "+code.src.mode);
		System.out.println();
		 */
	}

	private static void printInstructionStream(Instruction head) {
		while (head!=null) {
			if (head.label!="")
				System.out.print(head.label+":  ");
			else
				System.out.print("	");
			if (head.opcode != Opcode.none) {
				System.out.print("	"+head.opcode);
				switch (head.size) {
				case 1: System.out.print("b");
						break;
				case 2:	System.out.print("w");
						break;
				case 4: System.out.print("l");
				  		break;
				case 8:	System.out.print("q");
						break;
				}
				System.out.print("	");
			}
			if (head.src!=null) {
				printOperand(head.src);
			}
			if (head.dst!=null) {
				System.out.print(", ");
				printOperand(head.dst);
			}
			System.out.println();

			head = head.next;
		}
	}



	private static void printOperand(Operand operand) {
		if (operand==null) return;
		switch (operand.mode) {
		case imm:
			System.out.print("$"+operand.value);
			break;
		case reg:
			System.out.print(operand.reg);
			break;
		case abs:
			System.out.print(operand.label);
			break;
		case idx:
			System.out.print(operand.value+"("+operand.idx+")");
			break;
		case stack:
			System.out.print("?stack?");
			break;
		default:
			System.out.print("noMode");	
		}
		return;
	}

	private static Operand getRegister(int size) {
		Operand reg=null;
		int r;
		if (regcount>0) {
			regcount--;
			System.out.println("Get Reg");
			for (r=8; r<registers.length; r++) 
				if (registers[r]) {
					System.out.println("Reg#"+r);
					registers[r]=false;		// Mark the register as "in use"
					reg = new Operand(r,size);
					return reg;
				}
		}
		reg = new Operand();
		reg.mode = AddrMode.stack;	// All registers are busy, use the stack
		return reg;
	}
	
	private static Operand getRegister(DataType type) {
		Operand reg=null;
		switch (type) {
			case inttype:
				reg = getRegister(4);
				reg.type = type;
				break;
			case realtype:
				reg = getRegister(8);
				reg.type = type;
				break;
			default:
				reg = getRegister(4);
				reg.type = type;
		}
		return reg;
	}
	private static void freeRegisters() {
		for (int i=0; i<registers.length; i++)
			registers[i] = true;		//  Free the register, mark it as available
		registers[6] = false; 			//	Make the stack pointer not available
		registers[7] = false;			//  Make the base pointer register not available
		regcount=maxreg;
	}

	private static void freeRegister(Operand reg) {
		if (reg.mode != AddrMode.reg) return;
		registers[reg.value] = true;	//  Free the register, mark it as available
		regcount++;
	}
}
