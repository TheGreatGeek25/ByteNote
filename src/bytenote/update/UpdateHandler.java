package bytenote.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;

public class UpdateHandler {
	public static enum UpdateType {
		JAR, WIN32BIT
	}
	// installation info
	public static UpdateType getUpdateType() throws URISyntaxException, IOException {
		File parentFile = new File(ByteNoteMain.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
		File infoFile = new File(parentFile, "installationInfo");
		if(infoFile.exists()) {
			String info = getStringFromURL(infoFile.toURI().toURL()).replaceAll("\n", "");
			if(info.equals("win32bit")) {
				return UpdateType.WIN32BIT;
			}
		}
		return UpdateType.JAR;
	}
	
	public static String getStringFromURL(URL url) throws IOException {
		URLConnection connect = url.openConnection();
		connect.setConnectTimeout(2000);
		InputStream in = connect.getInputStream();
		String out = "";
		while(in.available() > 0) {
			out += new String( new byte[] {(byte) in.read()});
		}
		return out;
	}
	
	public static void update(URL updateSite, UpdateType type) {
		if(type == UpdateType.WIN32BIT) {
			try {
				URL installerWeb = new URL(updateSite.toString()+"/windows32bitUpdate.exe");
				URLConnection connect = installerWeb.openConnection();
				connect.setConnectTimeout(2000);
				InputStream in = connect.getInputStream();
				File thisFile = new File(ByteNoteMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
				File outputFile = new File(thisFile.getParentFile(), "update.exe");
				FileOutputStream out = new FileOutputStream(outputFile);
				while(in.available() > 0) {
					out.write(in.read());
				}
				out.close();
				in.close();
				
				File tempDir = Files.createTempDirectory("ByteNoteUpdateConfig").toFile();
				File byteNoteDir = new File(ClassLoader.getSystemClassLoader().getResource("bytenote").getFile());
				for (File file : getConfigFiles(byteNoteDir)) {
					Path path = byteNoteDir.toPath().relativize(file.toPath());
//					new File(tempDir.getAbsolutePath()+"/"+path.toString().;
					Files.copy(file.toPath(), tempDir.toPath().resolve(path.toString()));
				}
				
				//TODO add update GUI
				
				if(JFXMain.confirmExit()) {
					ProcessBuilder pb = new ProcessBuilder("TIMEOUT 8 & \""+outputFile.getAbsolutePath()+"\"");
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
			if(file.getAbsolutePath().endsWith("config"+File.separatorChar+file.getName())) {
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
