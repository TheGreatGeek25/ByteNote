package c57note64.cnotefiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.CNote;
import c57note64.cnote.types.CNoteTypes;

public class CFileWriter {
	
	public static final String defaultNoteFileContents = C57note64Main.syntaxVersion+"\n"
			+ "notes\n"
			+ "todo\n"
			+ "doing\n"
			+ "done\n"
			+ "settings\n"
			+ "types\n"
			+ "(default)255,255,150";
	
	public static void writePathFile(File file) {
		try {
			file.createNewFile();
//			System.out.println("Saving note file path at: "+file.getAbsolutePath());
			Files.write(Paths.get(file.getAbsolutePath()), C57note64Main.filePath.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
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
	
	public static void saveNoteFile(File saveFile) {
		String writeString = C57note64Main.syntaxVersion+"\n"
				+ "notes\n"
				+ "todo\n";
		/**
		 * Save todo notes
		 */
		ArrayList<CNote> todoNoteList = ( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel.notes;
		for (int i = 0; i < todoNoteList.size(); i++) {
			writeString += "note\n"
					+ "noteText"+todoNoteList.get(i).noteText+"\n"
					+ "priority"+todoNoteList.get(i).priority+"\n"
					+ "type"+todoNoteList.get(i).type+"\n";
			if(i != todoNoteList.size() - 1) {
				writeString += "";
			}
		}
		
		/**
		 * Save doing notes
		 */
		writeString += "doing\n";
		ArrayList<CNote> doingNoteList = ( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel.notes;
		for (int i = 0; i < doingNoteList.size(); i++) {
			writeString += "note\n"
					+ "noteText"+doingNoteList.get(i).noteText+"\n"
					+ "priority"+doingNoteList.get(i).priority+"\n"
					+ "type"+doingNoteList.get(i).type+"\n";
			if(i != doingNoteList.size() - 1) {
				writeString += "";
			}
		}
		/**
		 * Save done notes
		 */
		writeString += "done\n";
		ArrayList<CNote> doneNoteList = ( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel.notes;
		for (int i = 0; i < doneNoteList.size(); i++) {
			writeString += "note\n"
					+ "noteText"+doneNoteList.get(i).noteText+"\n"
					+ "priority"+doneNoteList.get(i).priority+"\n"
					+ "type"+doneNoteList.get(i).type+"\n";
			if(i != doneNoteList.size() - 1) {
				writeString += "";
			}
		}
		/**
		 * Save settings
		 */
		writeString += "settings\n"
				+ "types\n";
		for (int i = 0; i < CNoteTypes.typeMap.keySet().toArray().length; i++) {
			writeString += CNoteTypes.typeMap.keySet().toArray()[i]+"";
			String color;
			//red
			color = CNoteTypes.typeMap.get(CNoteTypes.typeMap.keySet().toArray()[i]).getRed()+",";
			//green
			color += CNoteTypes.typeMap.get(CNoteTypes.typeMap.keySet().toArray()[i]).getGreen()+",";
			//blue
			color += CNoteTypes.typeMap.get(CNoteTypes.typeMap.keySet().toArray()[i]).getBlue();
			writeString += color+"\n";
		}
		
		
		/**
		 * Write to file
		 */
		
		try {
			Files.write(Paths.get(saveFile.getAbsolutePath()), writeString.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
