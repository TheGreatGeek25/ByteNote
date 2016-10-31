package c57note64;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import c57note64.cnote.types.CNoteTypes;
import c57note64.cnotefiles.CFileReader;
import c57note64.cnotefiles.CFileWriter;

@SuppressWarnings("serial")
public class C57note64Main extends JFrame {
	
	public static boolean isSaved = true;
	
	public static C57note64Main c57main;
	public static final String version = "v1.0";
	public static final String[] supportedSyntaxVersions = {"v1.0"};
	
	public static Dimension preferredSize = new Dimension(700, 700);
	public static Dimension minSize = new Dimension(400, 400);
	
	public C57note64Main() {
		c57main = this;
		setName("C57note64");
		setTitle("C57note64");
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
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new C57note64Main();
				try {
					File thisFile = new File(C57note64Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					File cnoteFile = new File(thisFile.getParentFile().getAbsolutePath()+File.separator+"notes.cnote");
					CFileWriter.makeDefaultNoteFile(cnoteFile);
					CFileReader.getNoteFileReader(cnoteFile).noteFileMain(cnoteFile);
				} catch (Exception e1) {
					e1.printStackTrace();
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
	
	public void c57run() {
		((CMainPanel) getContentPane()).c57run();
		if(isSaved) {
			setTitle("C57note64");
		} else {
			setTitle("C57note64 *UNSAVED*");
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
