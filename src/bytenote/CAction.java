package bytenote;

import java.io.File;
import java.net.URISyntaxException;

import bytenote.cnote.CNote;
import bytenote.cnote.editor.CNoteEditPanel;
import bytenote.cnote.types.CTypeManagerPanel;
import bytenote.cnotefiles.CFileReader;
import bytenote.cnotefiles.CFileWriter;
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
				CNoteEditPanel cnep = new CNoteEditPanel( new CNote(0, "Note Text", "(default)", JFXMain.root.todoPanel) );
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
								CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("lastOpenedPath.txt").toURI()) );
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
				File inputFile1 = null;
				inputFile1 = JFXMain.openFileView(JFXMain.mainStage, "open");
				if(inputFile1 != null) {
					ByteNoteMain.filePath = inputFile1.getAbsolutePath();
					try {
						CFileReader.getNoteFileReader(inputFile1).noteFileMain(inputFile1);
						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("lastOpenedPath.txt").toURI()) );
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				
				break;
			case "saveAsAction":
				File inputFile2 = null;
				inputFile2 = JFXMain.openFileView(JFXMain.mainStage, "save");
				ByteNoteMain.filePath = inputFile2.getAbsolutePath();
				try {
					CFileWriter.makeDefaultNoteFile(inputFile2);
					CFileWriter.saveNoteFile(inputFile2);
					ByteNoteMain.isSaved = true;
					CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("lastOpenedPath.txt").toURI()) );
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				break;
			case "checkForUpdatesAction":
				//TODO check for updates
				System.out.println("checkForUpdates");
				break;
			default:
				break;
			}
		}
		
	}