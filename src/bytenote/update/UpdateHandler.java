package bytenote.update;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import bytenote.ByteNoteMain;

public class UpdateHandler {
	
	public static UpdatePane up;
	
	public static enum UpdateType {
		JAR, WIN32BIT
	}
	
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
		int inInt = in.read();
		while(inInt != -1) {
			out += new String( new byte[] {(byte) inInt});
			inInt = in.read();
		}
		return out;
	}
	
	public static void _run() {
		if(up != null) {
			up._run();
		}
	}
	
}
