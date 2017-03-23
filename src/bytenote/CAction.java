package bytenote;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import bytenote.note.Note;
import bytenote.note.editor.NoteEditPanel;
import bytenote.note.types.NoteTypeManagerPanel;
import bytenote.notefiles.NoteFileFilter;
import bytenote.notefiles.NoteFileReader;
import bytenote.notefiles.NoteFileWriter;
import bytenote.notefiles.oldio.CFileWriter;
import bytenote.update.UpdateHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CAction implements EventHandler<ActionEvent> {
		
		private String action;
		
		public CAction(String action) {
			this.action = action;
		}

		@Override
		public void handle(ActionEvent event) {
			switch (action) {
			case "saveAction":
				try {
					File noteFile = new File(ByteNoteMain.filePath);
					noteFile = NoteFileFilter.requestFormatUpdate(noteFile);
					ByteNoteMain.filePath = noteFile.getAbsolutePath();
					ByteNoteMain.savedData = NoteData.getCurrentData();
					NoteFileWriter.writeToFile(noteFile);
					System.out.println("File saved to "+ByteNoteMain.filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "newNoteAction":
				NoteEditPanel cnep = new NoteEditPanel( new Note(0, "Note Text", "(default)", JFXMain.root.todoPanel) );
				JFXMain.root.infoPanel.noteEditor = cnep;
//				C57View.showView(JFXMain.mainStage, new BorderPane(new CustomColorDialog(JFXMain.mainStage)), "Hello", 500, 500);
				JFXMain.showView(JFXMain.mainStage, cnep, "New Note", 300, 300);
				break;
			case "deleteNoteAction":
				JFXMain.root.infoPanel.note.delete();
				break;
			case "manageTypeAction":
				NoteTypeManagerPanel ctmp = new NoteTypeManagerPanel();
				JFXMain.root.controlPanel.typeManager = ctmp;
				JFXMain.showView(JFXMain.mainStage, ctmp, "Manage types", 300, 300);
				break;
			case "deselectAction":
				JFXMain.root.infoPanel.setNote(null);
				break;
			case "newFileAction":
				File inputFile = null;
				inputFile = JFXMain.openFileView(JFXMain.mainStage, "save");
				if(inputFile != null) {
//					try {
//								inputFile = NoteFileFilter.requestFormatUpdate(inputFile);
//					} catch (IOException e1) {}
					ByteNoteMain.filePath = inputFile.getAbsolutePath();
					try {
						NoteFileReader.loadDataFromFile(inputFile, NoteData.getBlankNoteData());
						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
					} catch (URISyntaxException | ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				
				break;
			case "openAction":
				try {
					File inputFile1 = null;
					inputFile1 = JFXMain.openFileView(JFXMain.mainStage, "open");
					if(inputFile1 != null) {
						inputFile1 = NoteFileFilter.requestFormatUpdate(inputFile1);
						ByteNoteMain.filePath = inputFile1.getAbsolutePath();
						NoteFileReader.loadDataFromFile(inputFile1, NoteData.getBlankNoteData());
						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
					}
				} catch (URISyntaxException | IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case "saveAsAction":
				File inputFile2 = null;
				inputFile2 = JFXMain.openFileView(JFXMain.mainStage, "save");
				try {
//					inputFile2 = NoteFileFilter.requestFormatUpdate(inputFile2);
					ByteNoteMain.filePath = inputFile2.getAbsolutePath();
					ByteNoteMain.savedData = NoteData.getCurrentData();
					NoteFileWriter.writeToFile(inputFile2);
					CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
				} catch (URISyntaxException | IOException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
//					e.printStackTrace();
				}
				break;
			case "checkForUpdatesAction":
				//TODO check for updates
//				System.out.println("checkForUpdates");
				System.out.println(JFXMain.confirmExit());
				File byteNoteDir = new File(ClassLoader.getSystemClassLoader().getResource("bytenote").getFile());
				for (File file : UpdateHandler.getConfigFiles(byteNoteDir)) {
					file.toPath().relativize(byteNoteDir.toPath());
				}
				break;
			default:
				break;
			}
		}
		
	}