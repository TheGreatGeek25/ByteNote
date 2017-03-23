package bytenote.notefiles;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import bytenote.NoteData;
import bytenote.notefiles.bynt.BYNTReader;
import bytenote.notefiles.oldio.CFileReader;
import bytenote.notefiles.oldio.CFileWriter;

public class NoteFileReader {
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
					CFileReader.getNoteFileReader(file).noteFileMain(file);
				}
				scan.close();
			} else {
				CFileWriter.makeDefaultNoteFile(file);
				CFileReader.getNoteFileReader(file).noteFileMain(file);
			}
		}
		
	}
}
