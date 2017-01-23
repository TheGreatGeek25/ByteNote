package c57note64;

import c57note64.cnote.CNote;
import c57note64.cnote.editor.CNoteEditPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CInfoPanel extends HBox {
	
	public static final int defaultHeight = 32;
	
	public Label noteText;
	public Label noteType;
	public Label notePriority;
	
	public CNote note;
	
	public Button editNote;
	
	public CNoteEditPanel noteEditor;
	
	public CInfoPanel() {
		super();
		setPrefSize(JFXMain.mainStage.getWidth(), defaultHeight);
		setBackground( new Background( new BackgroundFill(Color.web("rgb(0,255,255)"), null, null) ) );
		
		noteText = new Label("Note text: No note selected");
		getChildren().add(noteText);
		
		notePriority = new Label("Note priority: No note selected");
		getChildren().add(notePriority);
		
		noteType = new Label("Note type: No note selected");
		getChildren().add(noteType);
		
		setSpacing(20);
		setAlignment(Pos.CENTER);
		
		
		editNote = new Button("Edit");
		editNote.setDisable(true);
		/*editNote.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});*/
		editNote.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				C57note64Main.isSaved = false;
				CInfoPanel.this.noteEditor = new CNoteEditPanel(note);
				JFXMain.showView(JFXMain.mainStage, CInfoPanel.this.noteEditor, "Edit note", 300, 300);
//				CInfoPanel.this.noteEditor = new CNoteEdit(C57note64Main_OLD.c57main, "Edit note", note);
			}
		});
		getChildren().add(editNote);
		
		
		setDisabled(true);
		setVisible(true);
		
	}
	
	public void setNote(CNote note) {
		this.note = note;
		if(note != null) {
			this.noteText.setText("Note text: "+note.noteText);
			this.noteType.setText("Note type: "+note.type);
			this.notePriority.setText("Note priority: "+Integer.toString(note.priority));
			this.editNote.setDisable(false);
		} else {
			this.noteText.setText("Note text: No note selected");
			this.noteType.setText("Note type: No note selected");
			this.notePriority.setText("Note priority: No note selected");
			this.editNote.setDisable(true);
		}
		
		
	}
	
	public void c57run() {
		if(noteEditor != null) {
			if(noteEditor.getScene().getWindow().isShowing()) {
				noteEditor.c57run();
			} else {
				noteEditor = null;
			}
		}
		
	}
	
}
