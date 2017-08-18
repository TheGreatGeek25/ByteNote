package bytenote.notefiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import bytenote.NoteData;
import bytenote.notefiles.bynt.BYNTReader;
import bytenote.notefiles.oldio.CFileReader;
import bytenote.notefiles.oldio.CFileWriter;
import bytenote.notefiles.oldio.CFileReader.Reader;

public class NoteFileReader {
	
	/**
	 * @param path
	 * @return a {@code FileInputStream} associated with the file at {@code System.getProperty("user.home")+".bytenote/"+path}
	 * @throws FileNotFoundException
	 */
	public static FileInputStream getConfigFileStream(String path) throws FileNotFoundException {
		return new FileInputStream( new File(System.getProperty("user.home")+"/.bytenote/"+path) );
	}
	
	/**
	 * @param path
	 * @throws FileNotFoundException
	 */
	public static String readConfigFile(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(getConfigFileStream(path));
		sc.useDelimiter("\\A");
		String out = sc.next();
		sc.close();
		return out;
	}
	
	public static void loadDataFromFile(File file, NoteData loadOnFail) throws ClassNotFoundException, IOException {
		if(file.getName().endsWith(".bynt")) {
			BYNTReader.readBYNTFile(file, loadOnFail);
		} else {
			if(file.exists()) {
				Scanner scan = new Scanner(file);
				scan.useDelimiter("\n");
				String line = scan.next();
				if(line.startsWith("BYNT")) {
					BYNTReader.readBYNTFile(file, loadOnFail);
				} else {
					CFileWriter.makeDefaultNoteFile(file);
					Reader reader = CFileReader.getNoteFileReader(file);
					if(reader != null) {
						reader.noteFileMain(file);
					}
				}
				scan.close();
			} else {
				CFileWriter.makeDefaultNoteFile(file);
				Reader reader = CFileReader.getNoteFileReader(file);
				if(reader != null) {
					reader.noteFileMain(file);
				}
			}
		}
		
	}
}
