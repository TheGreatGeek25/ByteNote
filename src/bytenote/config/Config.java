package bytenote.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bytenote.note.types.NoteTypes;
import bytenote.note.types.NoteTypes.NoteType;

public class Config {
	
	public String bytenoteVersion;
	
	public ArrayList<NoteType> noteTypes;
	
	public void apply() {
		Map<String, NoteType> noteTypeMap = new HashMap<String, NoteType>();
		for(NoteType notetype: noteTypes) {
			noteTypeMap.put(notetype.getName(), notetype);
		}
		NoteTypes.setTypeMap(noteTypeMap);
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
