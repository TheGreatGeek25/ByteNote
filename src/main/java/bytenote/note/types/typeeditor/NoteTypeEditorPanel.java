package bytenote.note.types.typeeditor;

import bytenote.JFXMain;
import bytenote.note.types.NoteTypeManagerPanel;
import bytenote.note.types.NoteTypes;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class NoteTypeEditorPanel extends GridPane {
	
	public String typeStr;
	public Color typeColor;
	
	public Button exitButton;
	
	public Label typeStrLabel;
	public Label typeColorLabel;
	
	public ColorPicker cp;
	public TextField typeStrEdit;
	public Button doneButton;
	private JFXMain jfxMain;

	public NoteTypeEditorPanel(JFXMain jfxMain) {
		super();
		this.jfxMain = jfxMain;
		setAlignment(Pos.CENTER);
		setVgap(42);
		
		
		typeStr = "Enter type name here";
		typeColor = Color.BLUE;
		
		exitButton = new Button("Cancel");
		exitButton.setOnAction( (ActionEvent event) -> {
				NoteTypeEditorPanel.this.getScene().getWindow().hide();
		});
		add(exitButton, 0, 0);
		setHalignment(exitButton, HPos.CENTER);
		
		typeStrEdit = new TextField();
		typeStrEdit.setPromptText(typeStr);
		typeStrEdit.focusTraversableProperty().set(false);
		add(typeStrEdit, 0, 1);
		setHalignment(typeStrEdit, HPos.CENTER);
		
		cp = new ColorPicker(typeColor);
		add(cp, 0, 2);
		setHalignment(cp, HPos.CENTER);
		
		doneButton = new Button("Done");
		doneButton.setOnAction( (ActionEvent event) -> {
				saveType();
				NoteTypeEditorPanel.this.getScene().getWindow().hide();
		});
		add(doneButton, 0, 3);
		setHalignment(doneButton, HPos.CENTER);
		
		setVisible(true);
	}
	
	protected void saveType() {
//		System.out.println("Save type");
		if(NoteTypes.getTypeMap().containsKey(typeStr)) {
			for (int i = 0; i < jfxMain.getRoot().todoPanel.notes.size(); i++) {
				if(jfxMain.getRoot().todoPanel.notes.get(i).type.equals(typeStr)) {
					jfxMain.getRoot().todoPanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			for (int i = 0; i < jfxMain.getRoot().doingPanel.notes.size(); i++) {
				if(jfxMain.getRoot().doingPanel.notes.get(i).type.equals(typeStr)) {
					jfxMain.getRoot().doingPanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			for (int i = 0; i < jfxMain.getRoot().donePanel.notes.size(); i++) {
				if(jfxMain.getRoot().donePanel.notes.get(i).type.equals(typeStr)) {
					jfxMain.getRoot().donePanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			NoteTypes.getTypeMap().remove(typeStr);
		}
		NoteTypeManagerPanel manager = jfxMain.getRoot().controlPanel.typeManager;
		NoteTypes.addToMap(typeStrEdit.getText(), cp.getValue());
		manager.types = manager.loadData();
		manager.mainTable.setItems(manager.types);
	}

	public NoteTypeEditorPanel(String type, JFXMain jfxMain) {
		this(jfxMain);
		typeStr = type;
		typeColor = NoteTypes.getColor(type);
		typeStrEdit.setText(typeStr);
		cp.setValue(typeColor);
	}

	public void _run() {
		
	}

}
