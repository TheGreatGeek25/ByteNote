package bytenote.note.types;

import bytenote.JFXMain;
import bytenote.note.types.typeeditor.NoteTypeEditorPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NoteTypeControlPanel extends VBox {
	
	public Button deleteButton;
	public Button addButton;
	public Button editButton;
	public Button exitButton;
	
	public NoteTypeControlPanel() {
		super();
		
		setAlignment(Pos.CENTER);
		setSpacing(20);
		
		addButton = new Button("Add...");
		addButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				( (NoteTypeManagerPanel) getParent()).typeEditor = new NoteTypeEditorPanel();
				JFXMain.showView((Stage) NoteTypeControlPanel.this.getScene().getWindow(), ( (NoteTypeManagerPanel) getParent()).typeEditor, "Edit type", 300, 300);
			}
		});
		getChildren().add(addButton);
		
		editButton = new Button("Edit...");
		editButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String type = (String) ( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem().getTypeName();
				( (NoteTypeManagerPanel) getParent()).typeEditor = new NoteTypeEditorPanel(type);
				JFXMain.showView((Stage) NoteTypeControlPanel.this.getScene().getWindow(), ( (NoteTypeManagerPanel) getParent()).typeEditor, "Edit type", 300, 300);
			}
		});
		editButton.setDisable(true);
		getChildren().add(editButton);

		deleteButton = new Button("Delete");
		deleteButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				( (NoteTypeManagerPanel) getParent()).deleteType(( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem());
			}
		});
		deleteButton.setDisable(true);
		getChildren().add(deleteButton);
		
		exitButton = new Button("Exit");
		exitButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				NoteTypeControlPanel.this.getScene().getWindow().hide();
			}
		});
		getChildren().add(exitButton);
		
	}
	
	public void _run() {
		if(!( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().isEmpty() && !( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem().getTypeName().equals("(default)")) {
			deleteButton.setDisable(false);
			editButton.setDisable(false);
		} else {
			deleteButton.setDisable(true);
			editButton.setDisable(true);
		}

	}
}
