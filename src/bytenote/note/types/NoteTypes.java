package bytenote.note.types;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

public class NoteTypes {
	private static HashMap<String, Color> typeMap = new HashMap<String, Color>();
	private static Color defaultColor = Color.rgb(255, 255, 127);
	
	public static Color getColor(String name) {
		return typeMap.get(name);
	}
	
	public static void addToMap(String type, Color color) {
		typeMap.put(type, color);
	}
	
	public static void setMap(Map<String, Color> newMap) {
		typeMap.clear();
		for(String s: newMap.keySet().toArray(new String[0])) {
			addToMap(s, newMap.get(s));
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
		addToMap("(default)", defaultColor);
	}

	public static HashMap<String, Color> getTypeMap() {
		return typeMap;
	}
}
