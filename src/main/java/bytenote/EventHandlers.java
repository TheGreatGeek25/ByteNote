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

public class EventHandlers {

    public final EventHandler<ActionEvent> SAVE_EVENT;
    public final EventHandler<ActionEvent> NEW_NOTE_EVENT;
    public final EventHandler<ActionEvent> DELETE_NOTE_EVENT;
    public final EventHandler<ActionEvent> MANAGE_TYPE_EVENT;
    public final EventHandler<ActionEvent> DESELECT_EVENT;
    public final EventHandler<ActionEvent> NEW_FILE_EVENT;
    public final EventHandler<ActionEvent> OPEN_EVENT;
    public final EventHandler<ActionEvent> SAVE_AS_EVENT;
    public final EventHandler<ActionEvent> CHECK_FOR_UPDATES_EVENT;
    public final EventHandler<ActionEvent> EXIT_EVENT;
    public final EventHandler<ActionEvent> SHOW_RELEASE_NOTES_EVENT;


    public EventHandlers(JFXMain jfxMain) {
        SAVE_EVENT = event -> {
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
        };

        NEW_NOTE_EVENT = event -> {
            NoteEditPanel noteEditPanel = new NoteEditPanel( new Note(jfxMain, 0, "Note Text", "(default)", jfxMain.getRoot().todoPanel), jfxMain);
            jfxMain.getRoot().infoPanel.noteEditor = noteEditPanel;
            JFXMain.showView(jfxMain.getMainStage(), noteEditPanel, "New Note", 300, 300);
        };

        DELETE_NOTE_EVENT = event -> jfxMain.getRoot().infoPanel.note.delete();

        MANAGE_TYPE_EVENT = event -> {
            NoteTypeManagerPanel managerPanel = new NoteTypeManagerPanel(jfxMain);
            jfxMain.getRoot().controlPanel.typeManager = managerPanel;
            JFXMain.showView(jfxMain.getMainStage(), managerPanel, "Manage types", 300, 300);
        };

        DESELECT_EVENT = event -> jfxMain.getRoot().infoPanel.setNote(null);

        NEW_FILE_EVENT = event -> {
            if(jfxMain.confirmExit("continue")) {
                File inputFile = JFXMain.openFileView(jfxMain.getMainStage(), "save");
                if(inputFile != null) {
                    //					try {
                    //								inputFile = NoteFileFilter.requestFormatUpdate(inputFile);
                    //					} catch (IOException e1) {}
                    ByteNoteMain.filePath = inputFile.getAbsolutePath();
                    try {
                        NoteFileReader.loadDataFromFile(inputFile, NoteData.getBlankNoteData(), jfxMain);
                        NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        OPEN_EVENT = event -> {
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
        };

        SAVE_AS_EVENT = event -> {
            File inputFile = JFXMain.openFileView(jfxMain.getMainStage(), "save");
            try {
                //					inputFile = NoteFileFilter.requestFormatUpdate(inputFile);
                ByteNoteMain.filePath = inputFile.getAbsolutePath();
                ByteNoteMain.savedData = NoteData.getCurrentData(jfxMain);
                NoteFileWriter.writeToFile(inputFile, jfxMain);
                NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
                //					CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {}
        };

        CHECK_FOR_UPDATES_EVENT = event -> {
            try {
                if(UpdateChecker.check(ByteNoteMain.updateSite)) {
                    UpdatePane up = new UpdatePane(ByteNoteMain.updateSite, jfxMain);
                    Stage s = JFXMain.showView(jfxMain.getMainStage(), up, "Update", 600, 700);
                    s.setWidth(600);
                    s.setHeight(700);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(jfxMain.getMainStage());
                    alert.setHeaderText("No updates available.");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(jfxMain.getMainStage());
                alert.setContentText("Error code: ucf010 \nPlease check your internet connection and try again.");
                alert.setHeaderText("Failed to check for updates.");
                alert.showAndWait();
            }
        };

        EXIT_EVENT = event -> {
            if(jfxMain.confirmExit()) {
                jfxMain.getMainStage().hide();
            }
        };

        SHOW_RELEASE_NOTES_EVENT = event -> {
            try {
                Scanner sc = new Scanner(ByteNoteMain.class.getResourceAsStream("/bytenote/releaseNotes.html"));
                sc.useDelimiter("\\A");
                WebView wv = new WebView();
                String html = sc.next();
                BufferedImage image = ImageIO.read(ByteNoteMain.class.getResourceAsStream("/bytenote/logo256.png"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                String dataURI = "data:image/png;base64,"+ Base64.getMimeEncoder().encodeToString(baos.toByteArray());
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
        };
    }

}
