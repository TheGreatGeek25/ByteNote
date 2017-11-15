package bytenote.note.types;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

public class NoteTypes {
	protected static HashMap<String, NoteType> typeMap = new HashMap<String, NoteType>();
	protected static Color defaultColor = Color.rgb(255, 255, 127);
	
	public static Color getColor(String name) {
		return typeMap.get(name).getColor();
	}
	
	@Deprecated
	public static void addToMap(String type, Color color) {
		addToMap(type,color,true);
	}
	
	public static void addToMap(String type, Color color, boolean isLocal) {
		if(!(isLocal && typeMap.containsKey(type))) {
			typeMap.put(type, new NoteType(type, color, isLocal) );
		}
	}
	
	@Deprecated
	public static void setMap(Map<String, Color> newMap) {
		typeMap.clear();
		for (int i = 0; i < newMap.keySet().toArray().length; i++) {
			addToMap((String) newMap.keySet().toArray()[i], newMap.get(newMap.keySet().toArray()[i]));
		}
		addDefaultColor();
	}
	
	public static void setTypeMap(Map<String, NoteType> newMap) {
		typeMap.clear();
		for (int i = 0; i < newMap.keySet().toArray().length; i++) {
			addToMap((String) newMap.keySet().toArray()[i], newMap.get(newMap.keySet().toArray()[i]).getColor(), newMap.get(newMap.keySet().toArray()[i]).isLocal());
		}
		addDefaultColor();
	}
	
	public static void setDefault(Color c) {
		defaultColor = c;
	}
	
	public static Color getDefault() {
		return defaultColor;
	}
	
	public static void addDefaultColor() {
		addToMap("(default)", defaultColor, false);
	}

	public static HashMap<String, NoteType> getTypeMap() {
		return typeMap;
	}
	
	public static class NoteType {
		
		public String name;
		public Color color;
		public boolean isLocal;
		
		public String getName() {
			return name;
		}

		public Color getColor() {
			return color;
		}

		public boolean isLocal() {
			return isLocal;
		}

		public NoteType(String name, Color color, boolean isLocal) {
			this.name = name;
			this.color = color;
			this.isLocal = isLocal;
		}
		
		public NoteType(String name, Color color) {
			this(name,color,true);
		}
		
	}
}
