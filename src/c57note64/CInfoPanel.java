package c57note64;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import c57note64.cnote.CNote;
import c57note64.cnote.editor.CNoteEdit;

@SuppressWarnings("serial")
public class CInfoPanel extends JPanel {
	
	public JLabel noteText;
	public JLabel noteType;
	public JLabel notePriority;
	
	public CNote note = new CNote(0, "777", "typeR");
	
	public JButton editNote;
	
	public CNoteEdit noteEditor;
	
	public CInfoPanel() {
		super(true);
		setName("InfoPanel");
		setPreferredSize( new Dimension(C57note64Main.c57main.getSize().width, 32) );
		setBackground( new Color(0, 255, 255) );
		
		noteText = new JLabel("Note text: No note selected");
		add(noteText);
		
		notePriority = new JLabel("Note priority: No note selected");
		add(notePriority);
		
		noteType = new JLabel("Note type: No note selected");
		add(noteType);
		
		
		
		
		editNote = new JButton("Edit");
		editNote.setEnabled(false);
		editNote.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				C57note64Main.isSaved = false;
				CInfoPanel.this.noteEditor = new CNoteEdit(C57note64Main.c57main, "Edit note", note);
			}
		});
		add(editNote);
		
		
		setEnabled(true);
		setVisible(true);
		
	}
	
	public void setNote(CNote note) {
		this.note = note;
		if(note != null) {
			this.noteText.setText("Note text: "+note.noteText);
			this.noteType.setText("Note type: "+note.type);
			this.notePriority.setText("Note priority: "+Integer.toString(note.priority));
			this.editNote.setEnabled(true);
		} else {
			this.noteText.setText("Note text: No note selected");
			this.noteType.setText("Note type: No note selected");
			this.notePriority.setText("Note priority: No note selected");
			this.editNote.setEnabled(false);
		}
		
		
	}
	
	public void c57run() {
		revalidate();
		repaint();
		if(noteEditor != null) {
			noteEditor.c57run();
		}
		
	}
	
}
