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
			if(file.exists()) {
				Scanner scan = new Scanner(file);
				scan.useDelimiter("\n");
				String line = scan.next();
				if(line.startsWith("BYNT")) {
					BYNTWriter.writeDataToFile(NoteData.getCurrentData(), file);
				} else {
					CFileWriter.makeDefaultNoteFile(file);
					CFileWriter.saveNoteFile(file, NoteData.getCurrentData());
				}
				scan.close();
			} else {
				CFileWriter.makeDefaultNoteFile(file);
				CFileWriter.saveNoteFile(file, NoteData.getCurrentData());
			}
		}
	}
	
	public static void writeToFile(File file, NoteData data) throws IOException {
		if(file.getName().endsWith(".bynt")) {
			BYNTWriter.writeDataToFile(data, file);
		} else {
			if(file.exists()) {
				Scanner scan = new Scanner(file);
				scan.useDelimiter("\n");
				String line = scan.next();
				if(line.startsWith("BYNT")) {
					BYNTWriter.writeDataToFile(data, file);
				} else {
					CFileWriter.makeDefaultNoteFile(file);
					CFileWriter.saveNoteFile(file, data);
				}
				scan.close();
			} else {
				CFileWriter.makeDefaultNoteFile(file);
				CFileWriter.saveNoteFile(file, data);
			}
		}
	}
}
