package bytenote.note.types;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

public class NoteTypes {
	protected static HashMap<String, Color> typeMap = new HashMap<String, Color>();
	protected static Color defaultColor = Color.rgb(255, 255, 150);
	
	public static Color getColor(String name) {
		return typeMap.get(name);
	}
	
	public static void addToMap(String type, Color color) {
		typeMap.put(type, color);
	}
	
	public static void setMap(Map<String, Color> newMap) {
		typeMap.clear();
		for (int i = 0; i < newMap.keySet().toArray().length; i++) {
			addToMap((String) newMap.keySet().toArray()[i], newMap.get(newMap.keySet().toArray()[i]));
		}
	}
	
	public static void setDefault(Color c) {
		defaultColor = c;
	}
	
	public static Color getDefault() {
		return defaultColor;
	}
	
	public static void addDefaultColor() {
		addToMap("(default)", defaultColor);
	}

	public static HashMap<String, Color> getTypeMap() {
		return typeMap;
	}
}
