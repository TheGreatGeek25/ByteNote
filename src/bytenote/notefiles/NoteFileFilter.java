package bytenote.notefiles;

import javafx.stage.FileChooser.ExtensionFilter;


public class NoteFileFilter {
	/*@Override
	public boolean accept(File f) {
		return f.getName().endsWith(".cnote")||f.isDirectory();
	}
	@Override
	public String getDescription() {
		return ".cnote";
	}*/

	public static final ExtensionFilter cnoteFilter = new ExtensionFilter("Note Files (obsolete)", "*.cnote");
	public static final ExtensionFilter byntFilter = new ExtensionFilter("Note Files", "*.bynt");
}
