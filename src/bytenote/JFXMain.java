package bytenote;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import bytenote.notefiles.NoteFileFilter;
import bytenote.notefiles.NoteFileReader;
import bytenote.notefiles.oldio.CFileWriter;
import javafx.application.Application;
import javafx.event.EventHandler;
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

public class JFXMain extends Application {
	
	public static MainPanel root;
	
	public static Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			
			primaryStage.setTitle(ByteNoteMain.name);
			primaryStage.getIcons().add( new Image(this.getClass().getResourceAsStream("logo32.png")) );
			primaryStage.setResizable(true);
			primaryStage.setMinHeight(ByteNoteMain.minHeight);
			primaryStage.setMinWidth(ByteNoteMain.minWidth);
			primaryStage.setHeight(ByteNoteMain.prefHeight);
			primaryStage.setWidth(ByteNoteMain.prefWidth);
			primaryStage.setOnCloseRequest( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if(!confirmExit()) {
						event.consume();
					}
				}
			});
						
			root = new MainPanel();
			
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.setScene(scene);
			
			
			primaryStage.show();
			
			File notefile = new File(ByteNoteMain.filePath);
			while(!ByteNoteMain.isFileValid(notefile)) {
				System.err.println("File at \""+notefile.getAbsolutePath()+"\" is invalid.");
				notefile = openFileView(mainStage, "open");
				notefile = NoteFileFilter.requestFormatUpdate(notefile);
			}
			ByteNoteMain.filePath = notefile.getAbsolutePath();
			NoteFileReader.loadDataFromFile(notefile, NoteData.getBlankNoteData());
			ByteNoteMain.savedData = NoteData.getCurrentData();
			CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toExternalForm()) );
			
			root.c57run = root.new C57runService();
			root.c57run.setRestartOnFailure(true);	
			root.c57run.start();
		} catch(URISyntaxException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public static File openFileView(Stage parent, String openORsave) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(NoteFileFilter.cnoteFilter);
		fc.getExtensionFilters().add(NoteFileFilter.byntFilter);
		fc.setSelectedExtensionFilter(NoteFileFilter.byntFilter);
		if(openORsave.equals("open")) {
			return fc.showOpenDialog(parent);
		} else if (openORsave.equals("save")) {
			return fc.showSaveDialog(parent);
		} else {
			System.err.println("openORsave must be \"open\" or \"save\"");
		}
		return null;
	}
	
	public static void showView(Stage parent, Pane paneToShow, String title, int minWidth, int minHeight) {
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
	}

	public static boolean confirmExit() {
		if(!ByteNoteMain.isSaved) {
			Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit? Unsaved changes will be lost.");
			a.initOwner(mainStage);
			a.initModality(Modality.WINDOW_MODAL);
			ButtonType bt = a.showAndWait().get();
			return bt == ButtonType.OK;
		}
		return true;
	}
	
}
