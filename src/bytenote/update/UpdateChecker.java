package bytenote.update;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import bytenote.ByteNoteMain;
import bytenote.notefiles.oldio.CFileReader;

public class UpdateChecker {
	
	public static URL getUpdateURL(URL updateSite) {
		String latestVersion = CFileReader.readFile(new File(updateSite.toString()+"/latestVersion"));
		if(!compareVersions(ByteNoteMain.version, latestVersion)) {
			return null;
		} else {
			try {
				return new URL(updateSite.toString()+latestVersion);
			} catch (MalformedURLException e) {
				return null;
			}
		}
		
	}
	
	/**
	 * 
	 * @param v0
	 * @param v1
	 * @return Returns true if v1 is newer than v0. Returns false otherwise.
	 */
	public static boolean compareVersions(String v0, String v1) {
		String[] v0Split;
		String[] v1Split;
		if(v0.contains("-")) {
			v0Split = new String[] {v0.split(".")[0], v0.split(".")[1], v0.split(".")[2].split("-")[0], v0.split(".")[2].split("-")[1]};
		} else {
			v0Split = v0.split(".");
		}
		
		if(v1.contains("-")) {
			v1Split = new String[] {v1.split(".")[0], v1.split(".")[1], v1.split(".")[2].split("-")[0], v1.split(".")[2].split("-")[1]};	
		} else {
			v1Split = v1.split(".");
		}
		if(Integer.parseInt(v1Split[0]) > Integer.parseInt(v0Split[0]) ||
				Integer.parseInt(v1Split[1]) > Integer.parseInt(v0Split[1]) ||
				Integer.parseInt(v1Split[2]) > Integer.parseInt(v0Split[2])) {
			return true;
		}
		
		return false;
	}
	
	public static String getJavaVersion() {
		String version = System.getProperty("java.specification.version");
		return version;
	}
	
	public static String getJavaHome() {
		String home = System.getProperty("java.home");
		return home;
	}
	
}
