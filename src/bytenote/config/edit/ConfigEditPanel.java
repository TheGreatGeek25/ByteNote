package bytenote.config.edit;

import bytenote.config.Config;
import javafx.scene.layout.GridPane;

public class ConfigEditPanel extends GridPane {
	
	private Config editConfig;
	
	public ConfigEditPanel(Config edit) {
		super();
		setVgap(42);
		
		editConfig = edit;
		
	}	
	
	public boolean saveEdits() {
		return false;
	}
	
}
