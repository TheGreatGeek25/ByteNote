package bytenote.cnote.types;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
	public static Paint getPaintFromType(String type, boolean invert) {
		addToMap("(default)", Color.rgb(255, 255, 150));
		Color color = typeMap.get(type);
		if(invert) {
			color = color.invert();
		}
		String colorString = color.toString();
//		String colorString = "rgba("+Math.round(color.getRed())+","+Math.round(color.getGreen())+","+Math.round(color.getBlue())+","+color.getOpacity()+")";
		return Paint.valueOf(colorString);
	}
}
