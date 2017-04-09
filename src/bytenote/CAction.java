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
import bytenote.update.UpdateChecker;
import bytenote.update.UpdateHandler;
import bytenote.update.UpdateHandler.UpdateType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;

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
//						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
						NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
					} catch (ClassNotFoundException | IOException e) {
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
						NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
//						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
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
					NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
//					CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {}
				break;
			case "checkForUpdatesAction":
				//TODO check for updates
				try {
					boolean updateAvailable = UpdateChecker.check(ByteNoteMain.updateSite);
					boolean updateCompat = false;
					if(updateAvailable) {
						if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
							updateCompat = UpdateChecker.isJRECompatible(ByteNoteMain.updateSite);
						} else if (UpdateHandler.getUpdateType() == UpdateType.WIN32BIT) {
							updateCompat = true;
						}
					}
					if(updateAvailable) {
						if(updateCompat) {
							WebView wv = new WebView();
							BorderPane wvbp = new BorderPane(wv);
							JFXMain.showView(JFXMain.mainStage, wvbp, "Update", 400, 600);
						} else {
							
						}
					}
					
					
				} catch (IOException e) {
//					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.initOwner(JFXMain.mainStage);
					alert.setContentText("Error code: ucf010 \nPlease check your internet connection and try again.");
					alert.setHeaderText("Failed to check for updates.");
					alert.showAndWait();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
				break;
			default:
				break;
			}
		}
		
	}