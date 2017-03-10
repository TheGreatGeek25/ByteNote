package bytenote;

import java.io.File;
import java.net.URISyntaxException;

import bytenote.cnotefiles.CFileReader;
import bytenote.cnotefiles.CFileWriter;
import bytenote.cnotefiles.CNoteFileFilter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JFXMain extends Application {
	
	public static CMainPanel root;
	
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
						
			root = new CMainPanel();
			
			Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.setScene(scene);
			
			
			primaryStage.show();
			
			File cnotefile = new File(ByteNoteMain.filePath);
			while(!ByteNoteMain.isFileValid(cnotefile)) {
				System.err.println("File at \""+cnotefile.getAbsolutePath()+"\" is invalid.");
				cnotefile = openFileView(mainStage, "open");
				ByteNoteMain.filePath = cnotefile.getAbsolutePath();
			}
			CFileWriter.makeDefaultNoteFile(cnotefile);
			CFileReader.getNoteFileReader(cnotefile).noteFileMain(cnotefile);
			CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("lastOpenedPath.txt").toURI()) );
			
			root.c57run = root.new C57runService();
			root.c57run.setRestartOnFailure(true);	
			root.c57run.start();
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}

	}
	
	public static File openFileView(Stage parent, String openORsave) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(CNoteFileFilter.cnoteFilter);
		fc.setSelectedExtensionFilter(CNoteFileFilter.cnoteFilter);
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
	
}
