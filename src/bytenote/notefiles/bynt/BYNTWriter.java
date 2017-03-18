package bytenote.notefiles.bynt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import bytenote.ByteNoteMain;
import bytenote.NoteData;

public class BYNTWriter {
	public static <D extends NoteData> void writeDataToFile(D data, File file) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		out.write((ByteNoteMain.BYNTSyntaxVersion+"\n").getBytes());
		
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(data);
		
		out.close();
	}
}
