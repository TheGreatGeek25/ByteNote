package bytenote;

import java.io.File;

import bytenote.cnotefiles.CFileReader;

public class ByteNoteMain {
		
	public static boolean isSaved = true;
	
	public static final String name = "ByteNote";
	public static final String version = "v2.0.0-alpha";
	public static final String syntaxVersion = "v1.0";
	
	public static final double minWidth = 400;
	public static final double minHeight = 400;
	
	public static final double prefWidth = 700;
	public static final double prefHeight = 700;
	
	
	public static String filePath;
	
	
	public static void main(String[] args) {
		
		System.out.println("Starting "+name+version);
		
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
				File pathFile = new File(ByteNoteMain.class.getResource("config/lastOpenedPath.txt").toURI());
				filePath = CFileReader.readFile(pathFile).replace("\n", "");
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
