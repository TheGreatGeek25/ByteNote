package c57note64;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import c57note64.cnote.CNote;
import c57note64.cnote.editor.CNoteEdit;
import c57note64.cnote.types.CTypeManager;
import c57note64.cnotefiles.CFileWriter;

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
					File thisFile = new File(C57note64Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					File cnoteFile = new File(thisFile.getParentFile().getAbsolutePath()+File.separator+"notes.cnote");
					CFileWriter.saveNoteFile(cnoteFile);
					C57note64Main.isSaved = true;
					System.out.println("File saved.");
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
			default:
				break;
			}
		}
		
	}