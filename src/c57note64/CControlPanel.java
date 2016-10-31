package c57note64;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import c57note64.cnote.types.CTypeManager;

@SuppressWarnings("serial")
public class CControlPanel extends JPanel {
	
	public JMenuBar menuBar;
	
	public JMenu file;
	public JMenuItem save;
	public JMenu notes;
	public JMenuItem newNote, deleteNote, manageTypes;
	public JMenu selection;
	public JMenuItem deselect;
	
	public CTypeManager typeManager;
	
	public CControlPanel() {
		super(true);
		setName("ControlPanel");
		setPreferredSize( new Dimension(C57note64Main.c57main.getSize().width, 32) );
		setBackground( new Color(0, 255, 255) );
		
		menuBar = new JMenuBar();
		
		file = new JMenu("File");
		menuBar.add(file);
		
		save = new JMenuItem( new CAction("saveAction") );
		save.setText("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control pressed S"));
		file.add(save);
		
		notes = new JMenu("Notes");
		menuBar.add(notes);
		
		newNote = new JMenuItem( new CAction("newNoteAction") );
		newNote.setText("New note...");
		notes.add(newNote);
		
		deleteNote = new JMenuItem( new CAction("deleteNoteAction") );
		deleteNote.setText("Delete note");
		deleteNote.setAccelerator(KeyStroke.getKeyStroke("pressed DELETE"));
		deleteNote.setEnabled(false);
		notes.add(deleteNote);
				
		notes.addSeparator();
	
		manageTypes = new JMenuItem( new CAction("manageTypeAction") );
		manageTypes.setText("Manage types...");
		notes.add(manageTypes);
		
		selection = new JMenu("Selection");
		menuBar.add(selection);
		
		deselect = new JMenuItem( new CAction("deselectAction") );
		deselect.setText("Deselect");
		deselect.setAccelerator(KeyStroke.getKeyStroke("control shift pressed A"));
		selection.add(deselect);
		
		add(menuBar);
		
		setEnabled(true);
		setVisible(true);
		
	}
	
	public void c57run() {
		revalidate();
		repaint();
		if(typeManager != null) {
			typeManager.c57run();
			
		}
		if(( (CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.note != null) {
			deleteNote.setEnabled(true);
		} else {
			deleteNote.setEnabled(false);
		}
		
	}
	
}
