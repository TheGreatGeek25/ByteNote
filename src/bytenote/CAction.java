package bytenote;

import java.io.File;
import java.net.URISyntaxException;

import bytenote.note.Note;
import bytenote.note.editor.NoteEditPanel;
import bytenote.note.types.CTypeManagerPanel;
import bytenote.notefiles.CFileReader;
import bytenote.notefiles.CFileWriter;
import bytenote.update.UpdateHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
					File cnoteFile = new File(ByteNoteMain.filePath);
					CFileWriter.saveNoteFile(cnoteFile);
					ByteNoteMain.isSaved = true;
					System.out.println("File saved to "+ByteNoteMain.filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "newNoteAction":
				ByteNoteMain.isSaved = false;
				NoteEditPanel cnep = new NoteEditPanel( new Note(0, "Note Text", "(default)", JFXMain.root.todoPanel) );
				JFXMain.root.infoPanel.noteEditor = cnep;
//				C57View.showView(JFXMain.mainStage, new BorderPane(new CustomColorDialog(JFXMain.mainStage)), "Hello", 500, 500);
				JFXMain.showView(JFXMain.mainStage, cnep, "testing", 300, 300);
				break;
			case "deleteNoteAction":
				JFXMain.root.infoPanel.note.delete();
				break;
			case "manageTypeAction":
				CTypeManagerPanel ctmp = new CTypeManagerPanel();
				JFXMain.root.controlPanel.typeManager = ctmp;
				JFXMain.showView(JFXMain.mainStage, ctmp, "Manage types", 300, 300);
				break;
			case "deselectAction":
				JFXMain.root.infoPanel.setNote(null);
				break;
			case "newFileAction":
				Thread t = new Thread( new Runnable() {
					@Override
					public void run() {
						File inputFile = null;
						inputFile = JFXMain.openFileView(JFXMain.mainStage, "save");
						if(inputFile != null) {
							ByteNoteMain.filePath = inputFile.getAbsolutePath();
							try {
								CFileWriter.makeDefaultNoteFile(inputFile);
								CFileReader.getNoteFileReader(inputFile).noteFileMain(inputFile);
								CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
							} catch (URISyntaxException e) {
								e.printStackTrace();
							}
						}
					}
				});
				t.setDaemon(true);
				t.start();
				
				
				break;
			case "openAction":
				try {
					File inputFile1 = null;
					inputFile1 = JFXMain.openFileView(JFXMain.mainStage, "open");
					if(inputFile1 != null) {
						if(inputFile1.getAbsolutePath().endsWith(".cnote")) {
							Alert rename = new Alert(AlertType.CONFIRMATION);
							rename.initOwner(JFXMain.mainStage);
							rename.initModality(Modality.WINDOW_MODAL);
							rename.setContentText("The \".cnote\" file type is obsolete. Would you like to update to the \".bynt\" file type?");
							ButtonType result = rename.showAndWait().get();
							if(result == ButtonType.OK) {
								File newFile = new File(inputFile1.getParentFile(), inputFile1.getName().replaceAll(".cnote", ".bynt"));
//								Files.move(inputFile1.toPath(), newFile.toPath());
								inputFile1.renameTo(newFile);
								rename = new Alert(AlertType.INFORMATION);
								rename.initOwner(JFXMain.mainStage);
								rename.initModality(Modality.WINDOW_MODAL);
								rename.setContentText("\""+inputFile1.getName()+"\" has been renamed to: \""+newFile.getName()+"\"");
								rename.show();
								inputFile1 = newFile;
							}
						}
						ByteNoteMain.filePath = inputFile1.getAbsolutePath();
						CFileReader.getNoteFileReader(inputFile1).noteFileMain(inputFile1);
						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				break;
			case "saveAsAction":
				File inputFile2 = null;
				inputFile2 = JFXMain.openFileView(JFXMain.mainStage, "save");
				try {
					ByteNoteMain.filePath = inputFile2.getAbsolutePath();
					CFileWriter.makeDefaultNoteFile(inputFile2);
					CFileWriter.saveNoteFile(inputFile2);
					ByteNoteMain.isSaved = true;
					CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
				} catch (URISyntaxException e) {
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