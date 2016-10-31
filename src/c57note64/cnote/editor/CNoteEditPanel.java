package c57note64.cnote.editor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.CNote;
import c57note64.cnote.CNotePanel;
import c57note64.cnote.types.CNoteTypes;

@SuppressWarnings("serial")
public class CNoteEditPanel extends JPanel {
	
	public JTextField noteTextField;
	public JComboBox<Object> noteTypeBox;
	public JComboBox<Object> notePanelBox;
	public JSpinner notePrioritySpinner;
	
	public JLabel textFieldLabel;
	public JLabel typeBoxLabel;
	public JLabel panelBoxLabel;
	public JLabel prioritySpinnerLabel;
	
	public CNote editNote;
	
	public JButton doneButton;
	
	public CNoteEdit parent;
	
	public CNoteEditPanel(CNoteEdit parent, CNote editNote) {
		super(true);
		this.editNote = editNote;
		this.parent = parent;
		
		setName("CNoteEditPanel");
		
		setBackground( new Color(200, 188, 255) );
		setLayout( new GridBagLayout() );
		GridBagConstraints gbc = new GridBagConstraints();
		
		textFieldLabel = new JLabel("Text");
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 1;
		gbc.gridx = 0;
		add(textFieldLabel, gbc);
		
		noteTextField = new JTextField(this.editNote.noteText, 15);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 2;
		gbc.gridx = 0;
		add(noteTextField, gbc);
		
		typeBoxLabel = new JLabel("Type");
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 3;
		gbc.gridx = 0;
		add(typeBoxLabel, gbc);
		
		Object[] types = CNoteTypes.typeMap.keySet().toArray();
		noteTypeBox = new JComboBox<Object>(types);
		noteTypeBox.setEditable(false);
		noteTypeBox.setSelectedItem(this.editNote.type);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 4;
		gbc.gridx = 0;
		add(noteTypeBox, gbc);
		
		panelBoxLabel = new JLabel("Note section");
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 5;
		gbc.gridx = 0;
		add(panelBoxLabel, gbc);
		
		Object[] panels = {"To do", "Doing", "Done"};
		notePanelBox = new JComboBox<Object>(panels);
		notePanelBox.setEditable(false);
		if(this.editNote.panel == ( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel) {
			notePanelBox.setSelectedItem(panels[0]);
		} else if(this.editNote.panel == ( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel) {
			notePanelBox.setSelectedItem(panels[1]);
		} else if(this.editNote.panel == ( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel) {
			notePanelBox.setSelectedItem(panels[2]);
		}
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 6;
		gbc.gridx = 0;
		add(notePanelBox, gbc);
		
		prioritySpinnerLabel = new JLabel("Priority");
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 7;
		gbc.gridx = 0;
		add(prioritySpinnerLabel, gbc);
		
		notePrioritySpinner = new JSpinner( new SpinnerNumberModel(0, 0, ( (CNotePanel) this.editNote.panel).notes.size(), 1) );
		notePrioritySpinner.setValue(this.editNote.priority);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 8;
		gbc.gridx = 0;
		add(notePrioritySpinner, gbc);
		
		doneButton = new JButton("Done");
		doneButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveNote();
				
			}
		});
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.gridy = 9;
		gbc.gridx = 0;
		add(doneButton, gbc);
		
		setVisible(true);
		setEnabled(true);
	}
	

	private void saveNote() {
		CNotePanel notePanel = (CNotePanel) this.editNote.getParent();
		//remove note from notePanel
		if(notePanel != null) {
			notePanel.remove(this.editNote);
			notePanel.notes.remove(this.editNote);
		}
		//update note data
		this.editNote.noteText = noteTextField.getText();
		this.editNote.type = (String) noteTypeBox.getSelectedItem();
		this.editNote.priority = (int) notePrioritySpinner.getValue();
		//add note to notePanel
//		notePanel.addNote(this.editNote);

		switch (notePanelBox.getSelectedItem().toString()) {
		case "To do":
			this.editNote.setPanel(( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel);
			break;
		case "Doing":
			this.editNote.setPanel(( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel);
			break;
		case "Done":
			this.editNote.setPanel(( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel);
			break;
		}
		//close edit dialog
//		Window cNoteEditDialog = ((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.noteEditor;
//		cNoteEditDialog.dispatchEvent(new WindowEvent(cNoteEditDialog, WindowEvent.WINDOW_CLOSING));
		this.parent.dispatchEvent(new WindowEvent(this.parent, WindowEvent.WINDOW_CLOSING));
		((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.noteEditor = null;
	}
	
	public void c57run() {
		revalidate();
		repaint();
		switch ((String)notePanelBox.getSelectedItem()) {
		case "To do":
			( (SpinnerNumberModel) notePrioritySpinner.getModel()).setMaximum(( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel.notes.size());
			break;
		case "Doing":
			( (SpinnerNumberModel) notePrioritySpinner.getModel()).setMaximum(( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel.notes.size());
			break;
		case "Done":
			( (SpinnerNumberModel) notePrioritySpinner.getModel()).setMaximum(( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel.notes.size());
			break;
		}
	}
	
}
