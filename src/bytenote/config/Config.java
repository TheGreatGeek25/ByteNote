package bytenote.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

public class Config {
	
	public void apply() {
		
	}
	
	

	/**
	 * @param path
	 * @return a {@code FileInputStream} associated with the file at {@code System.getProperty("user.home")+".bytenote/"+path}
	 * @throws FileNotFoundException
	 */
	public static FileInputStream getConfigFileStream(String path) throws FileNotFoundException {
		return new FileInputStream(getURIInConfigDir(path).toString()); 
	}
	
	
	/**
	 * @param path
	 * @return The URI of the file at {@code System.getProperty("user.home")+".bytenote/"+path}
	 */
	public static URI getURIInConfigDir(String path) {
		return new File(System.getProperty("user.home")+"/.bytenote/"+path).toURI();
	}

	/**
	 * @param path
	 * @throws FileNotFoundException
	 */
	public static String readFileInConfigDir(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(Config.getConfigFileStream(path));
		sc.useDelimiter("\\A");
		String out = sc.next();
		sc.close();
		return out;
	}
	
}
