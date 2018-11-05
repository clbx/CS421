
public class SymbolTable {
	Entry[] table = new Entry[1000];
	int last = 0;
	int location=0;
	
	public void add(String name, String type) {
		table[last] = new Entry();
		table[last].name = name;
		table[last].address = location;
		table[last].dataType = type;
		last++;
		if (last>table.length)
			System.out.println("Error!  Symbol table overflow!!");
		if (type.equals("int")) 
			location += 4;
		else
			location += 8;
	}
	
	private class Entry {
		String name;
		String dataType;
		int address;
		int size;
	}
}
