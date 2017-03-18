package bytenote.notefiles;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import bytenote.NoteData;
import bytenote.notefiles.bynt.BYNTWriter;
import bytenote.notefiles.oldio.CFileWriter;

public class NoteFileWriter {
	public static void writeToFile(File file) throws IOException {
		if(file.getName().endsWith(".bynt")) {
			BYNTWriter.writeDataToFile(NoteData.getCurrentData(), file);
		} else {
			Scanner scan = new Scanner(file);
			String line = scan.nextLine();
			if(line.startsWith("BYNT")) {
				BYNTWriter.writeDataToFile(NoteData.getCurrentData(), file);
			} else {
				CFileWriter.makeDefaultNoteFile(file);
				CFileWriter.saveNoteFile(file);
			}
			scan.close();
		}
	}
}
