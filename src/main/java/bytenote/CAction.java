package bytenote;

import bytenote.note.Note;
import bytenote.note.editor.NoteEditPanel;
import bytenote.note.types.NoteTypeManagerPanel;
import bytenote.notefiles.NoteFileFilter;
import bytenote.notefiles.NoteFileReader;
import bytenote.notefiles.NoteFileWriter;
import bytenote.update.UpdateChecker;
import bytenote.update.UpdatePane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class CAction implements EventHandler<ActionEvent> {

	private String action;
	private JFXMain jfxMain;

	public CAction(String action, JFXMain jfxMain) {
		this.action = action;
		this.jfxMain = jfxMain;
	}

	@Override
	public void handle(ActionEvent event) {
		switch (action) {
		case "saveAction":
			try {
				File noteFile = new File(ByteNoteMain.filePath);
				noteFile = NoteFileFilter.requestFormatUpdate(noteFile, jfxMain);
				if(noteFile != null) {
					ByteNoteMain.filePath = noteFile.getAbsolutePath();
					ByteNoteMain.savedData = NoteData.getCurrentData(jfxMain);
					NoteFileWriter.writeToFile(noteFile, jfxMain);
					System.out.println("File saved to "+ByteNoteMain.filePath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "newNoteAction":
			NoteEditPanel cnep = new NoteEditPanel( new Note(jfxMain, 0, "Note Text", "(default)", jfxMain.getRoot().todoPanel), jfxMain);
			jfxMain.getRoot().infoPanel.noteEditor = cnep;
			//				C57View.showView(JFXMain.mainStage, new BorderPane(new CustomColorDialog(JFXMain.mainStage)), "Hello", 500, 500);
			JFXMain.showView(jfxMain.getMainStage(), cnep, "New Note", 300, 300);
			break;
		case "deleteNoteAction":
			jfxMain.getRoot().infoPanel.note.delete();
			break;
		case "manageTypeAction":
			NoteTypeManagerPanel ctmp = new NoteTypeManagerPanel(jfxMain);
			jfxMain.getRoot().controlPanel.typeManager = ctmp;
			JFXMain.showView(jfxMain.getMainStage(), ctmp, "Manage types", 300, 300);
			break;
		case "deselectAction":
			jfxMain.getRoot().infoPanel.setNote(null);
			break;
		case "newFileAction":
			if(jfxMain.confirmExit("continue")) {
				File inputFile = null;
				inputFile = JFXMain.openFileView(jfxMain.getMainStage(), "save");
				if(inputFile != null) {
					//					try {
					//								inputFile = NoteFileFilter.requestFormatUpdate(inputFile);
					//					} catch (IOException e1) {}
					ByteNoteMain.filePath = inputFile.getAbsolutePath();
					try {
						NoteFileReader.loadDataFromFile(inputFile, NoteData.getBlankNoteData(), jfxMain);
						//						CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
						NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case "openAction":
			if(jfxMain.confirmExit("continue")) {
				try {
					File inputFile1;
					inputFile1 = JFXMain.openFileView(jfxMain.getMainStage(), "open");
					if(inputFile1 != null) {
						inputFile1 = NoteFileFilter.requestFormatUpdate(inputFile1, jfxMain);
						if(inputFile1 != null) {
							ByteNoteMain.filePath = inputFile1.getAbsolutePath();
							NoteFileReader.loadDataFromFile(inputFile1, NoteData.getBlankNoteData(), jfxMain);
							NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case "saveAsAction":
			File inputFile2;
			inputFile2 = JFXMain.openFileView(jfxMain.getMainStage(), "save");
			try {
				//					inputFile2 = NoteFileFilter.requestFormatUpdate(inputFile2);
				ByteNoteMain.filePath = inputFile2.getAbsolutePath();
				ByteNoteMain.savedData = NoteData.getCurrentData(jfxMain);
				NoteFileWriter.writeToFile(inputFile2, jfxMain);
				NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
				//					CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {}
			break;
		case "checkForUpdatesAction":
			try {
				boolean updateAvailable = UpdateChecker.check(ByteNoteMain.updateSite);
				if(updateAvailable) {
					UpdatePane up = new UpdatePane(ByteNoteMain.updateSite, jfxMain);
					Stage s = JFXMain.showView(jfxMain.getMainStage(), up, "Update", 600, 700);
					s.setWidth(600);
					s.setHeight(700);
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.initOwner(jfxMain.getMainStage());
					alert.setHeaderText("No updates available.");
					alert.showAndWait();
				}

			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initOwner(jfxMain.getMainStage());
				alert.setContentText("Error code: ucf010 \nPlease check your internet connection and try again.");
				alert.setHeaderText("Failed to check for updates.");
				alert.showAndWait();
			}

			break;
		case "exit":
			if(jfxMain.confirmExit()) {
				jfxMain.getMainStage().hide();
			}
			break;
		case "showReleaseNotes":
			try {
				Scanner sc = new Scanner(ByteNoteMain.class.getResourceAsStream("/bytenote/releaseNotes.html"));
				sc.useDelimiter("\\A");
				WebView wv = new WebView();
				String html = sc.next();
				BufferedImage image = ImageIO.read(ByteNoteMain.class.getResourceAsStream("/bytenote/logo256.png"));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				String dataURI = "data:image/png;base64,"+Base64.getMimeEncoder().encodeToString(baos.toByteArray());
				wv.getEngine().loadContent(html.replace("logo.png", dataURI));
				BorderPane pane = new BorderPane(wv);
				Button exit = new Button("Exit");
				exit.setOnAction((ActionEvent exitEvent) -> exit.getScene().getWindow().hide());
				pane.setBottom(exit);
				exit.prefWidthProperty().bind(pane.widthProperty());
				exit.setPrefHeight(20);
				JFXMain.showView(jfxMain.getMainStage(), pane, "Release Notes", 600, 700);
				sc.close();
			} catch(IOException e) {
				
			}
			break;
		default:
			break;
		}
	}

}