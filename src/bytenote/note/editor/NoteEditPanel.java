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
	
	public Button doneButton;
		
	public NoteEditPanel(Note editNote) {
		super();
		this.editNote = editNote;
				
//		setBackground( new Color(200, 188, 255) );
		setBackground( new Background( new BackgroundFill(Color.web("rgb(200,188,255)"), null, null) ) );
		
		textFieldLabel = new Label("Text");
		add(textFieldLabel, 0, 1);
		
//		noteTextField = new TextField(this.editNote.noteText, 15);
		noteTextField = new TextField(this.editNote.noteText);
		add(noteTextField, 0, 2);
		
		typeBoxLabel = new Label("Type");
		add(typeBoxLabel, 0, 3);
		
		ObservableList<Object> types = FXCollections.observableArrayList(NoteTypes.typeMap.keySet().toArray());
		noteTypeBox = new ComboBox<>(types);
		noteTypeBox.setEditable(false);
		noteTypeBox.setValue(this.editNote.type);
		add(noteTypeBox, 0, 4);
		
		panelBoxLabel = new Label("Note section");
		add(panelBoxLabel, 0, 5);
		
		ObservableList<Object> panels = FXCollections.observableArrayList("To do", "Doing", "Done");
		notePanelBox = new ComboBox<Object>(panels);
		notePanelBox.setEditable(false);
		if(this.editNote.panel == JFXMain.root.todoPanel) {
			notePanelBox.setValue(panels.get(0));
		} else if(this.editNote.panel == JFXMain.root.doingPanel) {
			notePanelBox.setValue(panels.get(1));
		} else if(this.editNote.panel == JFXMain.root.donePanel) {
			notePanelBox.setValue(panels.get(2));
		}
		add(notePanelBox, 0, 6);
		
		prioritySpinnerLabel = new Label("Priority");
		add(prioritySpinnerLabel, 0, 7);
		
		notePrioritySpinner = new Spinner<>(0, this.editNote.panel.notes.size(), this.editNote.priority);
		add(notePrioritySpinner, 0, 8);
		
		doneButton = new Button("Done");
		doneButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				saveNote();
			}
		});
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
		this.editNote.noteText = noteTextField.getText();
		this.editNote.type = (String) noteTypeBox.getValue();
		this.editNote.priority = (int) notePrioritySpinner.getValue();
		//add note to notePanel
//		notePanel.addNote(this.editNote);

		switch (notePanelBox.getValue().toString()) {
		case "To do":
			this.editNote.setPanel(JFXMain.root.todoPanel);
			break;
		case "Doing":
			this.editNote.setPanel(JFXMain.root.doingPanel);
			break;
		case "Done":
			this.editNote.setPanel(JFXMain.root.donePanel);
			break;
		}
		//close edit dialog
		this.getScene().getWindow().hide();
		
		
//		this.parent.dispatchEvent(new WindowEvent(this.parent, WindowEvent.WINDOW_CLOSING));
//		((MainPanel) C57note64Main.c57main.getContentPane()).infoPanel.noteEditor = null;
	}
	
	public void c57run() {
		switch ((String)notePanelBox.getValue()) {
		case "To do":
			getChildren().remove(notePrioritySpinner);
			notePrioritySpinner = new Spinner<>(0, JFXMain.root.todoPanel.notes.size(), Math.min(this.editNote.priority, JFXMain.root.todoPanel.notes.size()));
			add(notePrioritySpinner, 0, 8);
			break;
		case "Doing":
			getChildren().remove(notePrioritySpinner);
			notePrioritySpinner = new Spinner<>(0, JFXMain.root.doingPanel.notes.size(), Math.min(this.editNote.priority, JFXMain.root.doingPanel.notes.size()));
			add(notePrioritySpinner, 0, 8);
			break;
		case "Done":
			getChildren().remove(notePrioritySpinner);
			notePrioritySpinner = new Spinner<>(0, JFXMain.root.donePanel.notes.size(), Math.min(this.editNote.priority, JFXMain.root.donePanel.notes.size()));
			add(notePrioritySpinner, 0, 8);
			break;
		}
	}
	
}
