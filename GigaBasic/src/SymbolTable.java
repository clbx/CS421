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
		DataType dataType;
		int address;
		int size;
		
		public Entry(String type) {
			address = location;
			switch (type) {
			case "int":
				dataType = DataType.inttype;
				location += 4;
				size = 4;
				break;
			case "real": 
				dataType = DataType.realtype;
				location += 8;
				size = 8;
				break;
			case "bool":
				dataType = DataType.booleantype;
				location += 4;
				size = 1;
				break;
			}
		}
		
		public String toString() {
			String text="";
			text += " Type: "+dataType+ "  Addr: "+address;
			return text;
		}
	}
}
