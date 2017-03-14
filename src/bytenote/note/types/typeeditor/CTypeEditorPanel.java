package bytenote.note.types.typeeditor;

import bytenote.JFXMain;
import bytenote.note.types.NoteTypes;
import bytenote.note.types.CTypeManagerPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CTypeEditorPanel extends GridPane {
	
	public String typeStr;
	public Color typeColor;
	
	public Label typeStrLabel;
	public Label typeColorLabel;
	
	public ColorPicker cp;
	public TextField typeStrEdit;
	public Button doneButton;
		
	public CTypeEditorPanel() {
		super();
		
		
		typeStr = "Enter type name here";
		typeColor = Color.BLUE;
		
		
		typeStrEdit = new TextField(typeStr);
		add(typeStrEdit, 0, 0);
		
		cp = new ColorPicker(typeColor);
		add(cp, 2, 0);
		
		doneButton = new Button("Done");
		doneButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveType();
				CTypeEditorPanel.this.getScene().getWindow().hide();
			}

		});
		add(doneButton, 1, 1);
		
		setVisible(true);
	}
	
	protected void saveType() {
//		System.out.println("Save type");
		if(NoteTypes.typeMap.containsKey(typeStr)) {
			for (int i = 0; i < JFXMain.root.todoPanel.notes.size(); i++) {
				if(JFXMain.root.todoPanel.notes.get(i).type.equals(typeStr)) {
					JFXMain.root.todoPanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			for (int i = 0; i < JFXMain.root.doingPanel.notes.size(); i++) {
				if(JFXMain.root.doingPanel.notes.get(i).type.equals(typeStr)) {
					JFXMain.root.doingPanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			for (int i = 0; i < JFXMain.root.donePanel.notes.size(); i++) {
				if(JFXMain.root.donePanel.notes.get(i).type.equals(typeStr)) {
					JFXMain.root.donePanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			NoteTypes.typeMap.remove(typeStr);
		}
		CTypeManagerPanel manager = JFXMain.root.controlPanel.typeManager;
		NoteTypes.addToMap(typeStrEdit.getText(), cp.getValue());
		manager.types = manager.loadData();
		manager.mainTable.setItems(manager.types);
	}

	public CTypeEditorPanel(String type) {
		this();
		typeStr = type;
		typeColor = NoteTypes.typeMap.get(type);
		typeStrEdit.setText(typeStr);
		cp.setValue(typeColor);;
	}

	public void c57run() {
		
	}

}
