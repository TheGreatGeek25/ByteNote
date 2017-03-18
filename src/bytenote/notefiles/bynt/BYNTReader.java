package bytenote.notefiles.bynt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import bytenote.NoteData;

public class BYNTReader {
	public static void readBYNTFile(File file) throws IOException, ClassNotFoundException {
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
				throw new IOException("\""+version+"\" is not a recognized version.");
		}
		ois.close();
	}
}
