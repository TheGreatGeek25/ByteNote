package bytenote;

import bytenote.note.Note;
import bytenote.note.editor.NoteEditPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class InfoPanel extends HBox {
	
	public static final int defaultHeight = 32;
	
	private Label noteText;
	private Label noteType;
	private Label notePriority;
	private String noteTextStr;
	private String noteTypeStr;
	
	public Note note;
	
	private Button editNote;
	
	public NoteEditPanel noteEditor;

	private final JFXMain jfxMain;
	private final MainPanel mainPanel;
	
	public InfoPanel(JFXMain jfxMain, MainPanel mainPanel) {
		super();
		this.jfxMain = jfxMain;
		this.mainPanel = mainPanel;
		setPrefSize(jfxMain.getMainStage().getWidth(), defaultHeight);
		setBackground( new Background( new BackgroundFill(Color.web("rgb(0,255,255)"), null, null) ) );
		
		noteText = new Label("Note text: No note selected");
		noteTextStr = noteText.getText();
		noteText.setTooltip( new Tooltip(noteTextStr) );
		getChildren().add(noteText);
		
		notePriority = new Label("Note priority: No note selected");
		getChildren().add(notePriority);
		
		noteType = new Label("Note type: No note selected");
		noteTypeStr = noteType.getText();
		noteType.setTooltip( new Tooltip(noteTypeStr) );
		getChildren().add(noteType);
		
		setSpacing(20);
		setAlignment(Pos.CENTER);
		
		
		editNote = new Button("Edit");
		editNote.setDisable(true);
		editNote.setOnAction( (ActionEvent event) -> {
				InfoPanel.this.noteEditor = new NoteEditPanel(note);
				JFXMain.showView(jfxMain.getMainStage(), InfoPanel.this.noteEditor, "Edit note", 300, 300);
		});
		getChildren().add(editNote);
		
		
		setDisabled(true);
		setVisible(true);
		
	}
	
	public void setNote(Note note) {
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
	
	public void _run() {
		if(noteEditor != null) {
			if(noteEditor.getScene().getWindow().isShowing()) {
				noteEditor._run();
			} else {
				noteEditor = null;
			}
		}
		if(!noteTextStr.equals(noteText.getText())) {
			noteTextStr = noteText.getText();
			noteText.setTooltip( new Tooltip(noteTextStr) );
		}
		if(!noteTypeStr.equals(noteType.getText())) {
			noteTypeStr = noteType.getText();
			noteType.setTooltip( new Tooltip(noteTypeStr) );
		}

	}
	
}
