package bytenote.note;

import java.io.Serializable;

import bytenote.JFXMain;
import bytenote.note.types.NoteTypes;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;

public class Note extends Label implements Serializable {
	private static final long serialVersionUID = -5075470174637277651L;

	public static int cNotePrefHeight = 100;
	
	public int priority;
	public String noteText;
	public String type;
	public transient NotePanel panel;
	
	public Note(int priority, String text, String type) {
		super();
		this.priority = priority;
		this.noteText = text;
		this.type = type;
		setBackground( new Background( new BackgroundFill(NoteTypes.typeMap.get(type), null, null) ) );
		setText(this.noteText);
//		setTextFill(Color.BLACK);
//		setOpaque(true);
//		addMouseListener( new CMouseListener() );
//		addEventFilter(MouseEvent.MOUSE_CLICKED, new CMouseListener() );
		setOnMousePressed( new CMouseListener() );
		setWrapText(true);
	}
	
	public Note(int priority, String text, String type, NotePanel panel) {
		this(priority, text, type);
		this.panel = panel;
	}
	
	public Note copy(boolean includePanel) {
		if(includePanel) {
			return new Note(priority, noteText, type, panel);
		} else {
			return new Note(priority, noteText, type);
		}
	}
	
	public void c57run() {
		if(getOnMousePressed() == null) {
			setOnMousePressed( new CMouseListener() );
		}
		Parent parent = getParent();
		panel = (NotePanel) parent;
		for (int i = 0; i < parent.getChildrenUnmodifiable().size(); i++) {
			if(parent.getChildrenUnmodifiable().get(i) == this ) {
				this.priority = i;
			}
		}
		setText(noteText);
		setBackground( new Background( new BackgroundFill(NoteTypes.typeMap.get(type), null, null/*new Insets(30)*/) ) );
		setSelectedBorder(getSelected());
		
	}
	
	public void setSelectedBorder(boolean selected) {
		if(selected) {
			setBorder(new Border(new BorderStroke(NoteTypes.getPaintFromType(type, true), BorderStrokeStyle.SOLID, null, new BorderWidths(4)) ) );
//			setBorder(BorderFactory.createLineBorder( new Color(255-getBackground().getRed(), 255-getBackground().getGreen(), 255-getBackground().getBlue()) , 4));
		} else {
			setBorder(Border.EMPTY);
		}
	}
	
	public boolean getSelected() {
		if(JFXMain.root.infoPanel.note != null) {
			return JFXMain.root.infoPanel.note.equals(this);
		}
		return false;
	}
	
	public void setPanel(NotePanel newPanel) {
		delete();
		this.panel = newPanel;
		newPanel.addNote(this);
	}
	
	public void delete() {
		NotePanel panel = (NotePanel) this.getParent();
		if(panel != null) {
			panel.remove(this);
			panel.notes.remove(this);
		}
		JFXMain.root.infoPanel.setNote(null);
	}
	
	public class CMouseListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			JFXMain.root.infoPanel.setNote(Note.this);
		}
	}
	
	
}
