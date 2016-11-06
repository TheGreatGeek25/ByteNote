package c57note64;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import c57note64.cnote.CNote;
import c57note64.cnote.editor.CNoteEdit;
import c57note64.cnote.types.CTypeManager;
import c57note64.cnotefiles.CFileReader;
import c57note64.cnotefiles.CFileWriter;
import c57note64.cnotefiles.CNoteFileFilter;

@SuppressWarnings("serial")
public class CAction extends AbstractAction {
		
		private String action;
		
		public CAction(String action) {
			this.action = action;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (action) {
			case "saveAction":
				try {
					File cnoteFile = new File(C57note64Main.filePath);
					CFileWriter.saveNoteFile(cnoteFile);
					C57note64Main.isSaved = true;
					System.out.println("File saved to "+C57note64Main.filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "newNoteAction":
				C57note64Main.isSaved = false;
				( (CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.noteEditor = new CNoteEdit(C57note64Main.c57main, "New note", new CNote(0, "Note text", "Note type", ( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel));
				break;
			case "deleteNoteAction":
				( (CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.note.delete();
				break;
			case "manageTypeAction":
				new CTypeManager(C57note64Main.c57main, "Manage types");
				System.out.println();
				break;
			case "deselectAction":
				((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.setNote(null);
				break;
			case "newFileAction":
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Select file");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				CNoteFileFilter cnff = new CNoteFileFilter();
				jfc.addChoosableFileFilter(cnff);
				jfc.setFileFilter(cnff);
				int n = jfc.showSaveDialog(C57note64Main.c57main);
				if(n == JFileChooser.APPROVE_OPTION) {
					File inputFile = jfc.getSelectedFile();
					if(!inputFile.getName().endsWith(".cnote") && jfc.getFileFilter() == cnff) {
						inputFile = new File(inputFile+".cnote");
					}
					C57note64Main.filePath = inputFile.getAbsolutePath();
					try {
						CFileWriter.makeDefaultNoteFile(inputFile);
						CFileReader.getNoteFileReader(inputFile).noteFileMain(inputFile);
						CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				break;
			case "openAction":
				JFileChooser jfc1 = new JFileChooser();
				jfc1.setDialogTitle("Select file");
				jfc1.setFileSelectionMode(JFileChooser.FILES_ONLY);
				CNoteFileFilter cnff1 = new CNoteFileFilter();
				jfc1.addChoosableFileFilter(cnff1);
				jfc1.setFileFilter(cnff1);
				int n1 = jfc1.showOpenDialog(C57note64Main.c57main);
				if(n1 == JFileChooser.APPROVE_OPTION) {
					File inputFile = jfc1.getSelectedFile();
					C57note64Main.filePath = inputFile.getAbsolutePath();
					try {
						CFileReader.getNoteFileReader(inputFile).noteFileMain(inputFile);
						CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				break;
			case "saveAsAction":
				JFileChooser jfc2 = new JFileChooser();
				jfc2.setDialogTitle("Select file");
				jfc2.setFileSelectionMode(JFileChooser.FILES_ONLY);
				CNoteFileFilter cnff2 = new CNoteFileFilter();
				jfc2.addChoosableFileFilter(cnff2);
				jfc2.setFileFilter(cnff2);
				int n2 = jfc2.showSaveDialog(C57note64Main.c57main);
				if(n2 == JFileChooser.APPROVE_OPTION) {
					File inputFile = jfc2.getSelectedFile();
					if(!inputFile.getName().endsWith(".cnote") && jfc2.getFileFilter() == cnff2) {
						inputFile = new File(inputFile+".cnote");
					}
					C57note64Main.filePath = inputFile.getAbsolutePath();
					try {
						CFileWriter.makeDefaultNoteFile(inputFile);
						CFileWriter.saveNoteFile(inputFile);
						C57note64Main.isSaved = true;
						CFileWriter.writePathFile( new File(C57note64Main.class.getResource("lastOpenedPath.txt").toURI()) );
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
			}
		}
		
	}