package bytenote.cnotefiles;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bytenote.JFXMain;
import bytenote.cnote.CNote;
import bytenote.cnote.types.CNoteTypes;
import javafx.scene.paint.Color;

public class CFileReader {
	
	public static class v1_0 extends Reader {
		
		@Override
		public void readNoteFileString(String string) {
			String fileStr = string;
			String[] fileStrArray = fileStr.split("")[1].split("");
			Map<String, String> mainMap = new HashMap<String, String>();
			for (int i = 0; i < fileStrArray.length; i++) {
				if(i+1<fileStrArray.length) {
					mainMap.put(fileStrArray[i].replace("\n", ""), fileStrArray[i+1]);
				}
				i++;
			}
			setSettingsFromString(mainMap.get("settings"));
			setNotesFromString(mainMap.get("notes"));
		}
		
		public void noteFileMain(File file) {
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
			setNotesFromString(mainMap.get("notes"));
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
			CNoteTypes.setMap(typeMap);

		
		}
	
		public void setNotesFromString(String notes) {
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
		
			/**
			 * Add notes to todo section
			 */
			JFXMain.root.todoPanel.notes.clear();
			JFXMain.root.todoPanel.getChildren().remove(0, JFXMain.root.todoPanel.getChildren().size());
			for (int i = 1; i < todoStrArray.length; i++) {
				CNote todoNote;
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
			
			
				todoNote = new CNote(Integer.parseInt(todoNoteData.get("priority")), todoNoteData.get("noteText"), todoNoteData.get("type"));
				JFXMain.root.todoPanel.addNote(todoNote);
			}
			/**
			 * Add notes to doing section
			 */
			JFXMain.root.doingPanel.notes.clear();
			JFXMain.root.doingPanel.getChildren().remove(0, JFXMain.root.doingPanel.getChildren().size());
			for (int i = 1; i < doingStrArray.length; i++) {
				CNote doingNote;
				String[] doingNoteDataArray = doingStrArray[i].split("");
				Map<String, String> doingNoteData = new HashMap<String, String>();
			
				for (int j = 0; j < doingNoteDataArray.length; j++) {
				
					if(j+1 < doingNoteDataArray.length) {
						doingNoteData.put(doingNoteDataArray[j].replace("\n", ""), doingNoteDataArray[j+1]);
					}
					j++;
				}
			
			
				doingNote = new CNote(Integer.parseInt(doingNoteData.get("priority")), doingNoteData.get("noteText"), doingNoteData.get("type"));
				JFXMain.root.doingPanel.addNote(doingNote);
				i++;
			}
			/**
		 	* Add notes to done section
		 	*/
			JFXMain.root.donePanel.notes.clear();
			JFXMain.root.donePanel.getChildren().remove(0, JFXMain.root.donePanel.getChildren().size());
			for (int i = 1; i < doneStrArray.length; i++) {
				CNote doneNote;
				String[] doneNoteDataArray = doneStrArray[i].split("");
				Map<String, String> doneNoteData = new HashMap<String, String>();
			
				for (int j = 0; j < doneNoteDataArray.length; j++) {
				
					if(j+1 < doneNoteDataArray.length) {
						doneNoteData.put(doneNoteDataArray[j].replace("\n", ""), doneNoteDataArray[j+1]);
					}
					j++;
				}
			
			
				doneNote = new CNote(Integer.parseInt(doneNoteData.get("priority")), doneNoteData.get("noteText"), doneNoteData.get("type"));
				JFXMain.root.donePanel.addNote(doneNote);
				i++;
			}
		
		}
	}
	
	public static Reader getNoteFileReader(File noteFile) {
		String fileStr = readFile(noteFile);
		String[] fileStrArray = fileStr.split("");
		String fileVersionStr = fileStrArray[0].replace("\n", "");
		switch (fileVersionStr) {
		case "v1.0":
			return new v1_0();
		default:
			throw new IllegalArgumentException(fileVersionStr+" is not a compatible file syntax version!");
		}
		
	}

	public static String readFile(File file) {
		Scanner scanner = null;
		String fileString = "";
		try {
			scanner = new Scanner(file);
			fileString = scanner.useDelimiter("\\A").next();
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			if(scanner != null) {
				scanner.close();
			}
		}
		return fileString;
	}
	
	public static abstract class Reader {
		public abstract void readNoteFileString(String string);
		public abstract void noteFileMain(File noteFile);
//		public abstract String readNoteFile(File file);
		public abstract void setSettingsFromString(String settings);
		public abstract void setNotesFromString(String notes);
	}
	
}
