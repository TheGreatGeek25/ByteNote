package bytenote.cnotefiles;

import javafx.stage.FileChooser.ExtensionFilter;

public class CNoteFileFilter/* extends FileFilter*/ {
	/*@Override
	public boolean accept(File f) {
		return f.getName().endsWith(".cnote")||f.isDirectory();
	}
	@Override
	public String getDescription() {
		return ".cnote";
	}*/

	public static final ExtensionFilter cnoteFilter = new ExtensionFilter("Note Files", "*.cnote");
	
}
