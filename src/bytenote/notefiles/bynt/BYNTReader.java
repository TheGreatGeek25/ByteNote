package bytenote.notefiles.bynt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;
import bytenote.NoteData;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

public class BYNTReader {
	public static <D extends NoteData> void readBYNTFile(File file, D loadOnFail) throws IOException, ClassNotFoundException {
		if(file.exists()) {
			FileInputStream input = new FileInputStream(file);
			String version = "";
			String c = new String(new byte[] {(byte) input.read()});
			while(!c.equals("\n")) {
				version += c;
				c = new String(new byte[] {(byte) input.read()});
			}

			ObjectInputStream ois = new ObjectInputStream(input);
			switch(version) {
			case "BYNTv1.0":
				((NoteData.V1_0) ois.readObject()).load();
				break;
			default:
				ois.close();
				Alert alert = new Alert(AlertType.ERROR);
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initOwner(JFXMain.mainStage);
				alert.setContentText("Error code: io0000 \nBYNT file version \""+version+"\" is not compatible with "+ByteNoteMain.name+" "+ByteNoteMain.version);
				alert.setHeaderText("Failed to load file.");
				alert.showAndWait();
			}
			ois.close();
		} else {
			loadOnFail.load();
		}
	}
}
