package bytenote;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import bytenote.notefiles.NoteFileReader;

public class ByteNoteMain {
		
	public static boolean isSaved = true;
	
	public static final String name = "ByteNote";
	public static final String version = "v2.0.0-alpha";
	public static final String syntaxVersion = "v1.0";
	public static final String BYNTVersion = "BYNTv1.0";
	
	public static final double minWidth = 400;
	public static final double minHeight = 400;
	
	public static final double prefWidth = 700;
	public static final double prefHeight = 700;

	public static URL updateSite;
	private static String updateSiteString = "file:C:/Users/MCH/Documents/Charlie's Stuff/ByteNote/ByteNote/test/updateSite"/*"https://thegreatgeek25.github.io/ByteNote/update"*/;
	
	public static NoteData savedData;
	
	public static String filePath;
	
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		System.out.println("Starting "+name+version);
		updateSite = new URL(updateSiteString);	
		
		if(args.length == 1) {
			try {
				filePath = args[0];
				new File(filePath).toURI();
			} catch (Exception e) {
//				e.printStackTrace();
				System.err.println("Invalid file");
				System.out.println("Launching file selection GUI");
				filePath = "";
			}
		} else {
			try {
				filePath = NoteFileReader.readConfigFile("lastOpenedPath.txt").replaceAll("\n", "");
				new File(filePath).toURI();
			} catch (Exception e) {
//				e.printStackTrace();
				System.err.println("Invalid file");
				System.out.println("Launching file selection GUI");
				filePath = "";
				
			}
			
		}
		
		try {
			Class.forName("javafx.application.Application", false, ByteNoteMain.class.getClassLoader());
			javafx.application.Application.launch(JFXMain.class, args);
		} catch (ClassNotFoundException e) {
			JavaFXNotFoundFrame.open();
		}
	}
	
	public static boolean isFileValid(File file) {
		try{
			if(file.createNewFile()) {
				if(!file.delete()) {
					System.err.println("ERROR!!");
				}
			}
			return true;
		} catch(Exception e) {
//			e.printStackTrace();
			return false;
		}
	}
	
}
