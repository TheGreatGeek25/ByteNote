package bytenote.notefiles;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import bytenote.notefiles.bynt.BYNTReader;
import bytenote.notefiles.oldio.CFileReader;

public class NoteFileReader {
	public static void loadDataFromFile(File file) throws ClassNotFoundException, IOException {
		Scanner scan = new Scanner(file);
		String line = scan.nextLine();
		if(file.getName().endsWith(".bynt")) {
			BYNTReader.readBYNTFile(file);
		} else {
			if(line.startsWith("BYNT")) {
				BYNTReader.readBYNTFile(file);
			} else {
				CFileReader.getNoteFileReader(file).noteFileMain(file);
			}
		}
		scan.close();
	}
}
