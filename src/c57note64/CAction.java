package c57note64;

import java.io.File;
import java.net.URISyntaxException;

import c57note64.cnote.CNote;
import c57note64.cnote.editor.CNoteEditPanel;
import c57note64.cnote.types.CTypeManagerPanel;
import c57note64.cnotefiles.CFileReader;
import c57note64.cnotefiles.CFileWriter;
import c57note64.cnotefiles.CTreeFileChooserPane.CFileChooserType;
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
					File cnoteFile = new File(C57note64Main.filePath);
					CFileWriter.saveNoteFile(cnoteFile);
					C57note64Main.isSaved = true;
					System.out.println("File saved to "+C57note64Main.filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "newNoteAction":
				C57note64Main.isSaved = false;
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
						try {
							inputFile = JFXMain.openFileView(JFXMain.mainStage, CFileChooserType.SAVE);
						} catch (URISyntaxException e1) {}
						if(inputFile != null) {
							C57note64Main.filePath = inputFile.getAbsolutePath();
							try {
								CFileWriter.makeDefaultNoteFile(inputFile);
								CFileReader.getNoteFileReader(inputFile).noteFileMain(inputFile);
								CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
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
				try {
					inputFile1 = C57View.openFileView(JFXMain.mainStage, CFileChooserType.OPEN);
				} catch (URISyntaxException e1) {}
				if(inputFile1 != null) {
					C57note64Main.filePath = inputFile1.getAbsolutePath();
					try {
						CFileReader.getNoteFileReader(inputFile1).noteFileMain(inputFile1);
						CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				
				break;
			case "saveAsAction":
				File inputFile2 = null;
				try {
					inputFile2 = C57View.openFileView(JFXMain.mainStage, CFileChooserType.SAVE);
				} catch (URISyntaxException e1) {}
				C57note64Main.filePath = inputFile2.getAbsolutePath();
				try {
					CFileWriter.makeDefaultNoteFile(inputFile2);
					CFileWriter.saveNoteFile(inputFile2);
					C57note64Main.isSaved = true;
					CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		
	}