package bytenote.notefiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import bytenote.notefiles.oldio.CFileReader;

public class NoteFileReader {
	public static void loadDataFromFile(File file) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		String line = scan.nextLine();
		if(line.startsWith("BYNT")) {
			
		} else {
			CFileReader.getNoteFileReader(file).noteFileMain(file);
		}
		scan.close();
	}
}
