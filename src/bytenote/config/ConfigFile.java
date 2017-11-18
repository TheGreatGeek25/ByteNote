package bytenote.config;

import java.io.File;

public class ConfigFile extends File {

	private static final long serialVersionUID = -5858752517557176569L;
	
	public static final String CONFIG = "config.xml";
	
	public ConfigFile() {
		super(Config.getURIInConfigDir(CONFIG));
	}
	
	public Config readData() {
		Config config = new Config();
		
		
		return null;
	}
	
	public void writeData(Config data) {
		
		
		
	}
	
	

}
