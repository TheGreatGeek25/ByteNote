package bytenote.notefiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;
import bytenote.NoteData;
import bytenote.notefiles.bynt.BYNTWriter;
import bytenote.notefiles.oldio.CFileReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;


public class NoteFileFilter {

	public static File requestFormatUpdate(File notefile) throws IOException, URISyntaxException {
		if(notefile != null && isOldFileType(notefile)) {
			Alert rename = new Alert(AlertType.CONFIRMATION);
			rename.initOwner(JFXMain.mainStage);
			rename.initModality(Modality.WINDOW_MODAL);
			rename.setContentText("You are using an obsolete file type. Would you like to update to the \".bynt\" file type?");
			ButtonType result = rename.showAndWait().get();
			if(result == ButtonType.OK) {
				File newFile = new File(notefile.getParentFile(), notefile.getName().replaceAll(".cnote", ".bynt"));
//				Files.move(inputFile1.toPath(), newFile.toPath());
				notefile.renameTo(newFile);
				CFileReader.getNoteFileReader(newFile).noteFileMain(newFile);
				BYNTWriter.writeDataToFile(NoteData.getCurrentData(), newFile);
				rename = new Alert(AlertType.INFORMATION);
				rename.initOwner(JFXMain.mainStage);
				rename.initModality(Modality.WINDOW_MODAL);
				rename.setContentText("\""+notefile.getName()+"\" has been updated to: \""+newFile.getName()+"\"");
				rename.show();
				ByteNoteMain.filePath = newFile.getAbsolutePath();
				NoteFileWriter.writeConfigFile("lastOpenedPath.txt", ByteNoteMain.filePath.getBytes());
//				CFileWriter.writePathFile( new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI()) );
				return newFile;
			}
		}
		return notefile;
	}
	
	public static boolean isOldFileType(File file) throws FileNotFoundException {
		if(file.getName().endsWith(".bynt")) {
			return false;
		} else {
			if(file.exists()) {
				Scanner scan = new Scanner(file);
				scan.useDelimiter("\n");
				String line = scan.next();
				scan.close();
				return !line.startsWith("BYNT");
			} else {
				return true;
			}
		}
	}
	
	public static final ExtensionFilter cnoteFilter = new ExtensionFilter("Note Files (obsolete)", "*.cnote");
	public static final ExtensionFilter byntFilter = new ExtensionFilter("Note Files", "*.bynt");
}
