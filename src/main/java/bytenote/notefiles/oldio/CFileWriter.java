package bytenote.notefiles.oldio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;
import bytenote.NoteData;
import bytenote.note.Note;
import bytenote.note.types.NoteTypes;

public class CFileWriter {
	
	public static final String defaultNoteFileContents = ByteNoteMain.syntaxVersion+"\n"
			+ "notes\n"
			+ "todo\n"
			+ "doing\n"
			+ "done\n"
			+ "settings\n"
			+ "types\n"
			+ "(default)255,255,150";
	
	@Deprecated
	public static void writePathFile(File file) {
		try {
			file.createNewFile();
//			System.out.println("Saving note file path at: "+file.getAbsolutePath());
			Files.write(Paths.get(file.getAbsolutePath()), ByteNoteMain.filePath.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	public static void makeDefaultNoteFile(File file) {
		try {
			if(file.createNewFile()) {
				System.out.println("Creating note file at: "+file.getAbsolutePath());
				Files.write(Paths.get(file.getAbsolutePath()), defaultNoteFileContents.getBytes(), StandardOpenOption.WRITE);
			}
		} catch (IOException e) {
			e.printStackTrace();
//			System.exit(0);
			
		}
	}
	
	/**
	 * @param saveFile
	 * @deprecated Use {@link #saveNoteFile(File, NoteData)} instead.
	 */
	@Deprecated
	public static void saveNoteFile(File saveFile, JFXMain jfxMain) {
		String writeString = ByteNoteMain.syntaxVersion+"\n"
				+ "notes\n"
				+ "todo\n";
		/*
		 * Save todo notes
		 */
		ArrayList<Note> todoNoteList = jfxMain.getRoot().todoPanel.notes;
		for (int i = 0; i < todoNoteList.size(); i++) {
			writeString += "note\n"
					+ "noteText"+todoNoteList.get(i).noteText+"\n"
					+ "priority"+todoNoteList.get(i).priority+"\n"
					+ "type"+todoNoteList.get(i).type+"\n";
			if(i != todoNoteList.size() - 1) {
				writeString += "";
			}
		}
		
		/*
		 * Save doing notes
		 */
		writeString += "doing\n";
		ArrayList<Note> doingNoteList = jfxMain.getRoot().doingPanel.notes;
		for (int i = 0; i < doingNoteList.size(); i++) {
			writeString += "note\n"
					+ "noteText"+doingNoteList.get(i).noteText+"\n"
					+ "priority"+doingNoteList.get(i).priority+"\n"
					+ "type"+doingNoteList.get(i).type+"\n";
			if(i != doingNoteList.size() - 1) {
				writeString += "";
			}
		}
		/*
		 * Save done notes
		 */
		writeString += "done\n";
		ArrayList<Note> doneNoteList = jfxMain.getRoot().donePanel.notes;
		for (int i = 0; i < doneNoteList.size(); i++) {
			writeString += "note\n"
					+ "noteText"+doneNoteList.get(i).noteText+"\n"
					+ "priority"+doneNoteList.get(i).priority+"\n"
					+ "type"+doneNoteList.get(i).type+"\n";
			if(i != doneNoteList.size() - 1) {
				writeString += "";
			}
		}
		/*
		 * Save settings
		 */
		writeString += "settings\n"
				+ "types\n";
		for (int i = 0; i < NoteTypes.getTypeMap().keySet().toArray().length; i++) {
			writeString += NoteTypes.getTypeMap().keySet().toArray()[i]+"";
			String color;
			//red
			color = ( (int) Math.floor(NoteTypes.getColor(NoteTypes.getTypeMap().keySet().toArray(new String[0])[i]).getRed()*255))+",";
			//green
			color += ( (int) Math.floor(NoteTypes.getColor(NoteTypes.getTypeMap().keySet().toArray(new String[0])[i]).getGreen()*255))+",";
			//blue
			color += ( (int) Math.floor(NoteTypes.getColor(NoteTypes.getTypeMap().keySet().toArray(new String[0])[i]).getBlue()*255));
			writeString += color+"\n";
		}
		
		
		/*
		 * Write to file
		 */
		
		try {
			Files.write(Paths.get(saveFile.getAbsolutePath()), writeString.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void saveNoteFile(File file, NoteData data) {
		try {
			Files.write(Paths.get(file.getAbsolutePath()), data.toOldFormat().getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
