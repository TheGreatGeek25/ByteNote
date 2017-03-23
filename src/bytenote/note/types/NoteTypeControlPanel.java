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
	
	public NoteTypeControlPanel() {
		super();
		
		setAlignment(Pos.CENTER);
		setSpacing(20);
		
//		add(Box.createVerticalGlue());
//		add(Box.createVerticalGlue());
		
		addButton = new Button("Add...");
		addButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				( (NoteTypeManagerPanel) getParent()).typeEditor = new NoteTypeEditorPanel();
				JFXMain.showView((Stage) NoteTypeControlPanel.this.getScene().getWindow(), ( (NoteTypeManagerPanel) getParent()).typeEditor, "Edit type", 300, 300);
			}
		});
		getChildren().add(addButton);
//		add(Box.createVerticalGlue());
		
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
//		add(Box.createVerticalGlue());
		
		/*deleteButton = new JButton(  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				C57note64Main.isSaved = false;
				( (NoteTypeManagerPanel) getParent()).deleteType(( (NoteTypeManagerPanel) getParent()).mainTable.getSelectedRow());
				( (NoteTypeManagerPanel) getParent()).mainTable.setModel( new NoteTypeTableModel(( (NoteTypeManagerPanel) getParent()).data, ( (NoteTypeManagerPanel) getParent()).columnNames) );
			}
		} );*/
		deleteButton = new Button("Delete");
		deleteButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				( (NoteTypeManagerPanel) getParent()).deleteType(( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem());
//				( (NoteTypeManagerPanel) getParent()).mainTable.setModel( new NoteTypeTableModel(( (NoteTypeManagerPanel) getParent()).data, ( (NoteTypeManagerPanel) getParent()).columnNames) );
			}
		});
		deleteButton.setDisable(true);
		getChildren().add(deleteButton);
//		add(Box.createVerticalGlue());
//		add(Box.createVerticalGlue());
		
	}
	
	public void c57run() {
//		System.out.println(( (NoteTypeManagerPanel) getParent()).mainTable.getSelectedRow());
		if(!( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().isEmpty() && !( (NoteTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem().getTypeName().equals("(default)")) {
			deleteButton.setDisable(false);
			editButton.setDisable(false);
		} else {
			deleteButton.setDisable(true);
			editButton.setDisable(true);
		}

	}
}
