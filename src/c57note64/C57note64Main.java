package c57note64;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import c57note64.cnote.types.CNoteTypes;
import c57note64.cnotefiles.CFileReader;
import c57note64.cnotefiles.CFileWriter;
import c57note64.cnotefiles.CNoteFileFilter;

@SuppressWarnings("serial")
public class C57note64Main extends JFrame {
	
	public static boolean isSaved = true;
	
	public static C57note64Main c57main;
	public static final String version = "v1.1.1";
	public static final String syntaxVersion = "v1.0";
	
	public static String filePath;
	
	public static Dimension preferredSize = new Dimension(700, 700);
	public static Dimension minSize = new Dimension(400, 400);
	
	public C57note64Main() {
		c57main = this;
		setName("C57note64");
		setTitle("C57note64");
		setIconImage( new ImageIcon(C57note64Main.class.getResource("logo32.png")).getImage() );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setPreferredSize(preferredSize);
		setSize(preferredSize);
		setResizable(true);
		setLocationRelativeTo(null);
		setMinimumSize(minSize);
		setEnabled(true);
		setVisible(true);
		
		setPane( new CMainPanel() );
	}
	
	public static void main(String[] args) {
		System.out.println("Starting C57note64"+version);
		
		if(args.length == 1) {
			try {
				filePath = args[0];
				new File(filePath).toURI();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Invalid file");
				System.out.println("Launching file selection GUI");
				filePath = "";
//				filePath = getFileToOpen().getAbsolutePath();
				/*JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Select file");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				CNoteFileFilter cnff = new CNoteFileFilter();
				jfc.addChoosableFileFilter(cnff);
				jfc.setFileFilter(cnff);
				JFrame tempFrame = new JFrame();
				tempFrame.setIconImage( new ImageIcon(C57note64Main.class.getResource("logo32.png")).getImage() );
				tempFrame.setEnabled(false);
				int n = jfc.showOpenDialog(tempFrame);
				if(n == JFileChooser.APPROVE_OPTION) {
					File inputFile = jfc.getSelectedFile();
					if(!inputFile.getName().endsWith(".cnote") && jfc.getFileFilter() == cnff) {
						inputFile = new File(inputFile+".cnote");
					}
					filePath = inputFile.getAbsolutePath();
				} else if(n == JFileChooser.CANCEL_OPTION) {
					System.exit(0);
				}
				tempFrame.dispose();*/
				
			}
		} else {
			try {
				File pathFile = new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI());
				filePath = CFileReader.readNoteFile(pathFile).replace("\n", "");
				new File(filePath).toURI();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Invalid file");
				System.out.println("Launching file selection GUI");
				filePath = "";
//				filePath = getFileToOpen().getAbsolutePath();
				/*JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Select file");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				CNoteFileFilter cnff = new CNoteFileFilter();
				jfc.addChoosableFileFilter(cnff);
				jfc.setFileFilter(cnff);
				JFrame tempFrame = new JFrame();
				tempFrame.setIconImage( new ImageIcon(C57note64Main.class.getResource("logo32.png")).getImage() );
				tempFrame.setEnabled(false);
				int n = jfc.showOpenDialog(tempFrame);
				if(n == JFileChooser.APPROVE_OPTION) {
					File inputFile = jfc.getSelectedFile();
					if(!inputFile.getName().endsWith(".cnote") && jfc.getFileFilter() == cnff) {
						inputFile = new File(inputFile+".cnote");
					}
					filePath = inputFile.getAbsolutePath();
				} else if(n == JFileChooser.CANCEL_OPTION) {
					System.exit(0);
				}
				tempFrame.dispose();*/
				
			}
			
		}
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new C57note64Main();
				try {
					/*File thisFile = new File(C57note64Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					File cnoteFile = new File(thisFile.getParentFile().getAbsolutePath()+File.separator+"notes.cnote");*/
					File cnotefile = new File(filePath);
					while(!isFileValid(cnotefile)) {
						filePath = getFileToOpen(c57main).getAbsolutePath();
						cnotefile = new File(filePath);
					}
					CFileWriter.makeDefaultNoteFile(cnotefile);
					CFileReader.getNoteFileReader(cnotefile).noteFileMain(cnotefile);
					CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
					System.err.println("File at \""+cnotefile.getAbsolutePath()+"\" is invalid.");
					
				} catch (Exception e1) {
//					e1.printStackTrace();
				}
				Timer t = new Timer(10, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						C57note64Main.c57main.c57run();
					}
				});
				t.start();
			}
		});
	}
	
	public static File getFileToOpen(JFrame dialogParentFrame) {
		File outFile = null;
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Select file");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		CNoteFileFilter cnff = new CNoteFileFilter();
		jfc.addChoosableFileFilter(cnff);
		jfc.setFileFilter(cnff);
//		JFrame tempFrame = new JFrame();
//		tempFrame.setIconImage( new ImageIcon(C57note64Main.class.getResource("logo32.png")).getImage() );
//		tempFrame.setEnabled(false);
		int n = jfc.showOpenDialog(dialogParentFrame);
		if(n == JFileChooser.APPROVE_OPTION) {
			outFile = jfc.getSelectedFile();
			if(!outFile.getName().endsWith(".cnote") && jfc.getFileFilter() == cnff) {
				outFile = new File(outFile+".cnote");
			}
		} else if(n == JFileChooser.CANCEL_OPTION) {
//			tempFrame.dispose();
			System.exit(0);
		}
//		tempFrame.dispose();
		return outFile;
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
	
	public void c57run() {
		((CMainPanel) getContentPane()).c57run();
		if(isSaved) {
			setTitle("C57note64   "+new File(filePath).getAbsolutePath());
		} else {
//			setTitle("C57note64 *UNSAVED*");
			setTitle("C57note64   *"+new File(filePath).getAbsolutePath()+"*");
		}
		CNoteTypes.addToMap("(default)", new Color(255, 255, 150) );
	}
	
	public void setPane(CMainPanel screen) {
		
		remove(getContentPane());
		setContentPane(screen);
		revalidate();
		System.out.println("Content Pane set to "+getContentPane().getName());
	}
	
}
