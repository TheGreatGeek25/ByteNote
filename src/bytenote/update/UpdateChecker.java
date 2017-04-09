package bytenote.update;

import java.io.IOException;
import java.net.URL;

import bytenote.ByteNoteMain;

public class UpdateChecker {
	
	public static boolean check(URL updateSite) throws IOException {
		URL url = new URL(updateSite.toString()+"/versionName");
		return compareVersions(ByteNoteMain.version, UpdateHandler.getStringFromURL(url).replaceAll("\n", ""));
	}
	
	public static boolean isJRECompatible(URL updateSite) throws IOException {
		URL url = new URL(updateSite.toString()+"/requiredJREVersion");
		return UpdateHandler.getStringFromURL(url).replaceAll("\n", "").equals(getJavaVersion());
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
		return Integer.parseInt(v1Split[0]) > Integer.parseInt(v0Split[0]) ||
				(Integer.parseInt(v1Split[0]) == Integer.parseInt(v0Split[0]) && Integer.parseInt(v1Split[1]) > Integer.parseInt(v0Split[1])) ||
				((Integer.parseInt(v1Split[0]) == Integer.parseInt(v0Split[0]) && (Integer.parseInt(v1Split[1]) == Integer.parseInt(v0Split[1]))) && Integer.parseInt(v1Split[2]) > Integer.parseInt(v0Split[2]));
			
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
