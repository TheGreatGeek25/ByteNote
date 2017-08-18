package bytenote;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

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
			if(JFXMain.confirmExit("continue")) {
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
			}
			break;
		case "openAction":
			if(JFXMain.confirmExit("continue")) {
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
			try {
				boolean updateAvailable = UpdateChecker.check(ByteNoteMain.updateSite);
				if(updateAvailable) {
					UpdatePane up = new UpdatePane(ByteNoteMain.updateSite);
					Stage s = JFXMain.showView(JFXMain.mainStage, up, "Update", 600, 700);
					s.setWidth(600);
					s.setHeight(700);
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.initOwner(JFXMain.mainStage);
					alert.setHeaderText("No updates available.");
					alert.showAndWait();
				}

			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initOwner(JFXMain.mainStage);
				alert.setContentText("Error code: ucf010 \nPlease check your internet connection and try again.");
				alert.setHeaderText("Failed to check for updates.");
				alert.showAndWait();
			}

			break;
		case "exit":
			JFXMain.mainStage.hide();
			break;
		case "showReleaseNotes":
			try {
				Scanner sc = new Scanner(ByteNoteMain.class.getResourceAsStream("releaseNotes.html"));
				sc.useDelimiter("\\A");
				WebView wv = new WebView();
				String html = sc.next();
				BufferedImage image = ImageIO.read(ByteNoteMain.class.getResourceAsStream("logo256.png"));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				String dataURI = "data:image/png;base64,"+DatatypeConverter.printBase64Binary(baos.toByteArray());
				wv.getEngine().loadContent(html.replace("logo.png", dataURI));
				BorderPane pane = new BorderPane(wv);
				Button exit = new Button("Exit");
				exit.setOnAction((ActionEvent exitEvent) -> {
					exit.getScene().getWindow().hide();
				});
				pane.setBottom(exit);
				exit.prefWidthProperty().bind(pane.widthProperty());
				exit.setPrefHeight(20);
				JFXMain.showView(JFXMain.mainStage, pane, "Release Notes", 600, 700);
				sc.close();
			} catch(IOException e) {
				
			}
			break;
		default:
			break;
		}
	}

}