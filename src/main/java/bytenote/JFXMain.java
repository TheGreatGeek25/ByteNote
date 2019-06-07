package bytenote;

import bytenote.notefiles.NoteFileFilter;
import bytenote.notefiles.NoteFileReader;
import bytenote.notefiles.NoteFileWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class JFXMain extends Application {

	private MainPanel root;

	private Stage mainStage;

	public ByteNote byteNote;

	@Override
	public void start(Stage primaryStage) {
		try {

			byteNote = new ByteNote();

			mainStage = primaryStage;
			
			primaryStage.setTitle(ByteNoteMain.name);
			primaryStage.getIcons().add( new Image(JFXMain.class.getResourceAsStream("/bytenote/logo32.png")) );
			primaryStage.setResizable(true);
			primaryStage.setMinHeight(ByteNoteMain.minHeight);
			primaryStage.setMinWidth(ByteNoteMain.minWidth);
			if(!primaryStage.isFullScreen()) {
				primaryStage.setHeight(ByteNoteMain.prefHeight);
				primaryStage.setWidth(ByteNoteMain.prefWidth);
			}
			primaryStage.setOnCloseRequest((WindowEvent event) -> {
					if(!confirmExit()) {
						event.consume();
					}
			});
						
			root = new MainPanel(this);
			
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.setScene(scene);
			
			
			primaryStage.show();

			File notefile = null;
			if(!ByteNoteMain.filePath.equals("")) {
				notefile = new File(ByteNoteMain.filePath);
			}
			/*while(!ByteNoteMain.isFileValid(notefile)) {
				System.err.println("File at \""+notefile.getAbsolutePath()+"\" is invalid.");
				notefile = openFileView(mainStage, "open");
				notefile = NoteFileFilter.requestFormatUpdate(notefile);
			}*/

			NoteData.getBlankNoteData().load(this);
			ByteNoteMain.savedData = NoteData.getCurrentData(this);
			if(notefile != null) {
				NoteFileReader.loadDataFromFile(notefile, NoteData.getBlankNoteData(), this);
				ByteNoteMain.filePath = notefile.getAbsolutePath();
				NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
			}

			root._run = root.new _runService();
			root._run.setRestartOnFailure(true);	
			root._run.start();
		} catch(IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	public Stage getMainStage() {
		return mainStage;
	}

	public static File openFileView(Stage parent, String openORsave) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(NoteFileFilter.cnoteFilter);
		fc.getExtensionFilters().add(NoteFileFilter.byntFilter);
		fc.setSelectedExtensionFilter(NoteFileFilter.byntFilter);
		switch (openORsave) {
			case "open":
				return fc.showOpenDialog(parent);
			case "save":
				return fc.showSaveDialog(parent);
			default:
				System.err.println("openORsave must be \"open\" or \"save\"");
				break;
		}
		return null;
	}
	
	public static Stage showView(Stage parent, Pane paneToShow, String title, int minWidth, int minHeight) {
		Stage stage = new Stage();
		stage.initOwner(parent);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.getIcons().addAll(parent.getIcons().toArray( new Image[] {} ) );
		stage.setTitle(title);
		stage.setResizable(true);
		stage.setMinHeight(minHeight);
		stage.setMinWidth(minWidth);
		stage.setScene( new Scene(paneToShow) );
		stage.show();
		return stage;
	}

	public boolean confirmExit() {
		return confirmExit("exit");
	}
	
	public boolean confirmExit(String replaceExit) {
		if(!ByteNoteMain.isSaved) {
			Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to "+replaceExit+"? Unsaved changes will be lost.");
			a.initOwner(mainStage);
			a.initModality(Modality.WINDOW_MODAL);
			ButtonType bt = a.showAndWait().get();
			return bt == ButtonType.OK;
		}
		return true;
	}

	public MainPanel getRoot() {
		return root;
	}
	
}
