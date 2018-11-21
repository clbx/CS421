import java.util.HashMap;

public class SymbolTable extends HashMap<String, SymbolTable.Entry>{
	int location=0;
	
	public void add(String name, String type) {
		Entry entry = new Entry(type);
		put(name,entry);
	}
	
	// public Entry get(String name);
	// public boolean containsKey(String name);
		
	public class Entry {
		//String name;
		String dataType;
		int address;
		int size;
		
		public Entry(String type) {
			address = location;
			dataType = type;
			if (type.equals("int")) {
				location += 4;
				size = 4;
			}
			else {
				location += 8;
				size = 8;
			}
		}
		
		public String toString() {
			String text="";
			text += " Type: "+dataType+ "  Addr: "+address;
			return text;
		}
	}
}
