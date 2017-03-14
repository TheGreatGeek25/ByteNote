package bytenote.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;

public class UpdateHandler {
	static enum UpdateType {
		JAR, WIN32BIT
	}
	
	public static void update(URL updateSite, UpdateType type) {
		if(type == UpdateType.WIN32BIT) {
			try {
				URL installerWeb = new URL(updateSite.toString()+"/windows32bitUpdate.exe");
				InputStream in = installerWeb.openStream();
				File thisFile = new File(ByteNoteMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
				File outputFile = new File(thisFile.getParentFile(), "update.exe");
				FileOutputStream out = new FileOutputStream(outputFile);
				while(in.available() > 0) {
					out.write(in.read());
				}
				out.close();
				in.close();
				
				//TODO add update GUI
				
				if(JFXMain.confirmExit()) {
					ProcessBuilder pb = new ProcessBuilder("TIMEOUT 8 & "+outputFile.getAbsolutePath());
					pb.start();
					System.exit(0);
				}
				
			} catch (IOException | URISyntaxException e) {
//				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<File> getConfigFiles(File file) {
		ArrayList<File> fileList = new ArrayList<>();
		if(file.isFile()) {
			if(file.getAbsolutePath().endsWith("config/"+file.getName())) {
				fileList.add(file);
			}
		} else if(file.isDirectory()) {
			for (File file1 : file.listFiles()) {
				fileList.addAll(getConfigFiles(file1));
			}
		}
		return fileList;
	}
	
}
