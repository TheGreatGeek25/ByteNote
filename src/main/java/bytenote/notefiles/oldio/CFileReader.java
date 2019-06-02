package bytenote.notefiles.oldio;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;
import bytenote.note.Note;
import bytenote.note.types.NoteTypes;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

public class CFileReader {
	
	public static class v1_0 extends Reader {
		
		@Override
		public void readNoteFileString(String string, JFXMain jfxMain) {
			String[] fileStrArray = string.split("")[1].split("");
			Map<String, String> mainMap = new HashMap<String, String>();
			for (int i = 0; i < fileStrArray.length; i++) {
				if(i+1<fileStrArray.length) {
					mainMap.put(fileStrArray[i].replace("\n", ""), fileStrArray[i+1]);
				}
				i++;
			}
			setSettingsFromString(mainMap.get("settings"));
			setNotesFromString(mainMap.get("notes"), jfxMain);
		}

		@Override
		public void noteFileMain(File file, JFXMain jfxMain) {
			String fileStr = readFile(file);
//			fileStr = fileStr.replace("\n", "");
			String[] fileStrArray = fileStr.split("")[1].split("");
			Map<String, String> mainMap = new HashMap<String, String>();
			for (int i = 0; i < fileStrArray.length; i++) {
				if(i+1<fileStrArray.length) {
					mainMap.put(fileStrArray[i].replace("\n", ""), fileStrArray[i+1]);
				}
				i++;
			}
			setSettingsFromString(mainMap.get("settings"));
			setNotesFromString(mainMap.get("notes"), jfxMain);
		}
	
		/*public static String readNoteFile(File file) {
			Scanner scanner = null;
			String fileString = "";
			try {
				scanner = new Scanner(file);
				fileString = scanner.useDelimiter("\\A").next();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(scanner != null) {
					scanner.close();
				}
			}
			return fileString;
		}*/

		@Override
		public void setSettingsFromString(String settings) {
			String[] settingsArray = settings.split("");
			Map<String, String> settingsMap = new HashMap<String, String>();
			for (int i = 0; i < settingsArray.length; i++) {
				if(i+1 < settingsArray.length) {
					settingsMap.put(settingsArray[i].replace("\n", ""), settingsArray[i+1]);
				}
				i++;
			}
		
			String[] typeArray = settingsMap.get("types").split("");
			Map<String, Color> typeMap = new HashMap<String, Color>();
			for (int i = 0; i < typeArray.length; i++) {
				String typeName = typeArray[i].replace("\n", "");
				Color typeColor;
				if(i+1<typeArray.length) {
					if(typeArray[i+1].split(",").length == 3) {
//						typeColor = new Color(Integer.parseInt(typeArray[i+1].split(",")[0]), Integer.parseInt(typeArray[i+1].split(",")[1]), Integer.parseInt(typeArray[i+1].split(",")[2]));
						typeColor = Color.web("rgb("+typeArray[i+1].split(",")[0]+","+typeArray[i+1].split(",")[1]+","+typeArray[i+1].split(",")[2]+")");
						typeMap.put(typeName, typeColor);
					}
				}
			}
			NoteTypes.setMap(typeMap);

		
		}

		@Override
		public void setNotesFromString(String notes, JFXMain jfxMain) {
			String[] noteSectionsArray = notes.split("");
			Map<String, String> mainNoteMap = new HashMap<String, String>();
			for (int i = 0; i < noteSectionsArray.length; i++) {
				if (i+1 < noteSectionsArray.length) {
					mainNoteMap.put(noteSectionsArray[i].replace("\n", ""), noteSectionsArray[i+1]);
				}
			}
			String[] todoStrArray = mainNoteMap.get("todo").split("");
			String[] doingStrArray = mainNoteMap.get("doing").split("");
			String[] doneStrArray = mainNoteMap.get("done").split("");
		
			/*
			 * Add notes to todo section
			 */
			jfxMain.getRoot().todoPanel.notes.clear();
			jfxMain.getRoot().todoPanel.getChildren().remove(0, jfxMain.getRoot().todoPanel.getChildren().size());
			for (int i = 1; i < todoStrArray.length; i++) {
				Note todoNote;
//				try {
					if(todoStrArray[i].split("")[0].replace("\n", "").equals("note")) {
						i++;
					}
				/*} catch(ArrayIndexOutOfBoundsException e) {
					i++;
				}*/
				String[] todoNoteDataArray = todoStrArray[i].split("");
				Map<String, String> todoNoteData = new HashMap<String, String>();
			
				for (int j = 0; j < todoNoteDataArray.length; j++) {
				
					if(j+1 < todoNoteDataArray.length) {
						todoNoteData.put(todoNoteDataArray[j].replace("\n", ""), todoNoteDataArray[j+1]);
					}
					j++;
				}
			
			
				todoNote = new Note(jfxMain, Integer.parseInt(todoNoteData.get("priority")), todoNoteData.get("noteText"), todoNoteData.get("type"));
				jfxMain.getRoot().todoPanel.addNote(todoNote);
			}

			/*
			 * Add notes to doing section
			 */
			jfxMain.getRoot().doingPanel.notes.clear();
			jfxMain.getRoot().doingPanel.getChildren().remove(0, jfxMain.getRoot().doingPanel.getChildren().size());
			for (int i = 1; i < doingStrArray.length; i++) {
				Note doingNote;
				String[] doingNoteDataArray = doingStrArray[i].split("");
				Map<String, String> doingNoteData = new HashMap<String, String>();
			
				for (int j = 0; j < doingNoteDataArray.length; j++) {
				
					if(j+1 < doingNoteDataArray.length) {
						doingNoteData.put(doingNoteDataArray[j].replace("\n", ""), doingNoteDataArray[j+1]);
					}
					j++;
				}
			
			
				doingNote = new Note(jfxMain, Integer.parseInt(doingNoteData.get("priority")), doingNoteData.get("noteText"), doingNoteData.get("type"));
				jfxMain.getRoot().doingPanel.addNote(doingNote);
				i++;
			}

			/*
		 	* Add notes to done section
		 	*/
			jfxMain.getRoot().donePanel.notes.clear();
			jfxMain.getRoot().donePanel.getChildren().remove(0, jfxMain.getRoot().donePanel.getChildren().size());
			for (int i = 1; i < doneStrArray.length; i++) {
				Note doneNote;
				String[] doneNoteDataArray = doneStrArray[i].split("");
				Map<String, String> doneNoteData = new HashMap<String, String>();
			
				for (int j = 0; j < doneNoteDataArray.length; j++) {
				
					if(j+1 < doneNoteDataArray.length) {
						doneNoteData.put(doneNoteDataArray[j].replace("\n", ""), doneNoteDataArray[j+1]);
					}
					j++;
				}
			
			
				doneNote = new Note(jfxMain, Integer.parseInt(doneNoteData.get("priority")), doneNoteData.get("noteText"), doneNoteData.get("type"));
				jfxMain.getRoot().donePanel.addNote(doneNote);
				i++;
			}
		
		}
	}

	public static Reader getNoteFileReader(File noteFile, JFXMain jfxMain) {
		String fileStr = readFile(noteFile);
		String[] fileStrArray = fileStr.split("");
		String fileVersionStr = fileStrArray[0].replace("\n", "");
		switch (fileVersionStr) {
		case "v1.0":
			return new v1_0();
		default:
			Alert alert = new Alert(AlertType.ERROR);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.initOwner(jfxMain.getMainStage());
			alert.setContentText("Error code: io0001 \nNote file syntax version \""+fileVersionStr+"\" is not compatible with "+ByteNoteMain.name+" "+ByteNoteMain.version);
			alert.setHeaderText("Failed to load file.");
			alert.showAndWait();
			return null;
		}
		
	}

	public static String readFile(File file) {
		String fileString = "";
		try (Scanner scanner = new Scanner(file)) {
			fileString = scanner.useDelimiter("\\A").next();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return fileString;
	}
	
	public static abstract class Reader {
		public abstract void readNoteFileString(String string, JFXMain jfxMain);
		public abstract void noteFileMain(File noteFile, JFXMain jfxMain);
//		public abstract String readNoteFile(File file);
		public abstract void setSettingsFromString(String settings);
		public abstract void setNotesFromString(String notes, JFXMain jfxMain);
	}
	
}
