package c57note64.cnote.types;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class CNoteTypes {
	public static Map<String, Color> typeMap = new HashMap<String, Color>();
	public static void addToMap(String type, Color color) {
		typeMap.put(type, color);
	}
	public static void setMap(Map<String, Color> newMap) {
		typeMap.clear();
		for (int i = 0; i < newMap.keySet().toArray().length; i++) {
			addToMap((String) newMap.keySet().toArray()[i], newMap.get(newMap.keySet().toArray()[i]));
		}
	}
}
