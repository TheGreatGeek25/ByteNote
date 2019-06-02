package bytenote.note.types.table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class CColor {
	private final SimpleStringProperty typeName;
	private final ObjectProperty<Color> typeColor;
	
	public CColor(String name, Color color) {
		typeName = new SimpleStringProperty(name);
		typeColor = new SimpleObjectProperty<Color>(color);
	}
	
	public String getTypeName() {
		return typeName.get();
	}
	
	public void setTypeName(String name) {
		typeName.set(name);
	}
	
	public StringProperty getTypeNameProperty() {
		return typeName;
	}
	
	public Color getTypeColor() {
		return typeColor.get();
	}
	
	public void setTypeColor(Color color) {
		typeColor.set(color);
	}
	
	public ObjectProperty<Color> getTypeColorProperty() {
		return typeColor;
	}
	
}
