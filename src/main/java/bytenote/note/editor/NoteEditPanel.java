package bytenote.note.editor;

import bytenote.JFXMain;
import bytenote.note.Note;
import bytenote.note.NotePanel;
import bytenote.note.types.NoteTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class NoteEditPanel extends GridPane {
	
	public TextField noteTextField;
	public ComboBox<Object> noteTypeBox;
	public ComboBox<Object> notePanelBox;
	public Spinner<Object> notePrioritySpinner;
	
	public Label textFieldLabel;
	public Label typeBoxLabel;
	public Label panelBoxLabel;
	public Label prioritySpinnerLabel;
	
	public Note editNote;
	private JFXMain jfxMain;
	private String notePanel;
	
	public Button cancelButton;
	public Button doneButton;
		
	public NoteEditPanel(Note editNote, JFXMain jfxMain) {
		super();
		this.editNote = editNote;
		this.jfxMain = jfxMain;

		setBackground( new Background( new BackgroundFill(Color.rgb(200, 188, 255), null, null) ) );
		
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction( (javafx.event.ActionEvent event) -> {
				NoteEditPanel.this.getScene().getWindow().hide();
		});
		add(cancelButton, 0, 0);
		
		textFieldLabel = new Label("Text");
		add(textFieldLabel, 0, 1);
		
//		noteTextField = new TextField(this.editNote.noteText, 15);
		noteTextField = new TextField(this.editNote.noteText);
		add(noteTextField, 0, 2);
		
		typeBoxLabel = new Label("Type");
		add(typeBoxLabel, 0, 3);
		
		ObservableList<Object> types = FXCollections.observableArrayList(NoteTypes.getTypeMap().keySet().toArray());
		noteTypeBox = new ComboBox<>(types);
		noteTypeBox.setEditable(false);
		noteTypeBox.setValue(this.editNote.type);
		add(noteTypeBox, 0, 4);
		
		panelBoxLabel = new Label("Note section");
		add(panelBoxLabel, 0, 5);
		
		ObservableList<Object> panels = FXCollections.observableArrayList("To do", "Doing", "Done");
		notePanelBox = new ComboBox<>(panels);
		notePanelBox.setEditable(false);
		if(this.editNote.panel == jfxMain.getRoot().todoPanel) {
			notePanelBox.setValue(panels.get(0));
			notePanel = (String) panels.get(0);
		} else if(this.editNote.panel == jfxMain.getRoot().doingPanel) {
			notePanelBox.setValue(panels.get(1));
			notePanel = (String) panels.get(1);
		} else if(this.editNote.panel == jfxMain.getRoot().donePanel) {
			notePanelBox.setValue(panels.get(2));
			notePanel = (String) panels.get(2);
		}
		add(notePanelBox, 0, 6);
		
		prioritySpinnerLabel = new Label("Priority");
		add(prioritySpinnerLabel, 0, 7);
		
		notePrioritySpinner = new Spinner<>(0, this.editNote.panel.notes.size(), this.editNote.priority);
		add(notePrioritySpinner, 0, 8);
		
		doneButton = new Button("Done");
		doneButton.setOnAction(event -> saveNote());
		add(doneButton, 0, 9);
		
		setVisible(true);
	}
	

	private void saveNote() {
		NotePanel notePanel = (NotePanel) this.editNote.getParent();
		
		//remove note from notePanel
		if(notePanel != null) {
			notePanel.remove(this.editNote);
			notePanel.notes.remove(this.editNote);
		}
		
		//update note data
		Note noteToSave = this.editNote.copy(false);
		noteToSave.noteText = noteTextField.getText();
		noteToSave.type = (String) noteTypeBox.getValue();
		noteToSave.priority = (int) notePrioritySpinner.getValue();
		
		//add note to notePanel
		switch (notePanelBox.getValue().toString()) {
		case "To do":
			noteToSave.setPanel(jfxMain.getRoot().todoPanel);
			break;
		case "Doing":
			noteToSave.setPanel(jfxMain.getRoot().doingPanel);
			break;
		case "Done":
			noteToSave.setPanel(jfxMain.getRoot().donePanel);
			break;
		}
		//close edit dialog
		this.getScene().getWindow().hide();
		
		
	}
	
	
	public void _run() {
		if(!notePanel.equals(notePanelBox.getValue())) {
			switch ((String)notePanelBox.getValue()) {
			case "To do":
				getChildren().remove(notePrioritySpinner);
				notePrioritySpinner = new Spinner<>(0, jfxMain.getRoot().todoPanel.notes.size(), Math.min(this.editNote.priority, jfxMain.getRoot().todoPanel.notes.size()));
				add(notePrioritySpinner, 0, 8);
				notePanel = "To do";
				break;
			case "Doing":
				getChildren().remove(notePrioritySpinner);
				notePrioritySpinner = new Spinner<>(0, jfxMain.getRoot().doingPanel.notes.size(), Math.min(this.editNote.priority, jfxMain.getRoot().doingPanel.notes.size()));
				add(notePrioritySpinner, 0, 8);
				notePanel = "Doing";
				break;
			case "Done":
				getChildren().remove(notePrioritySpinner);
				notePrioritySpinner = new Spinner<>(0, jfxMain.getRoot().donePanel.notes.size(), Math.min(this.editNote.priority, jfxMain.getRoot().donePanel.notes.size()));
				add(notePrioritySpinner, 0, 8);
				notePanel = "Done";
				break;
			}
		}
		
	}
	
}
