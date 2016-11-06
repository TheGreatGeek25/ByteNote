package c57note64.cnotefiles;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CNoteFileFilter extends FileFilter {
	@Override
	public boolean accept(File f) {
		return f.getName().endsWith(".cnote")||f.isDirectory();
	}
	@Override
	public String getDescription() {
		return ".cnote";
	}

}
