package bytenote.cnote;

import bytenote.JFXMain;
import bytenote.cnote.types.CNoteTypes;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;

public class CNote extends Label {
	
	public static int cNotePrefHeight = 100;
	
	public int priority;
	public String noteText;
	public String type;
	public CNotePanel panel;
	
	public CNote(int priority, String text, String type) {
		super();
		this.priority = priority;
		this.noteText = text;
		this.type = type;
		setBackground( new Background( new BackgroundFill(CNoteTypes.typeMap.get(type), null, null) ) );
		setText(this.noteText);
//		setTextFill(Color.BLACK);
//		setOpaque(true);
//		addMouseListener( new CMouseListener() );
		addEventFilter(MouseEvent.MOUSE_CLICKED, new CMouseListener() );
				
	}
	
	public CNote(int priority, String text, String type, CNotePanel panel) {
		this(priority, text, type);
		this.panel = panel;
	}
	
	public void c57run() {
//		revalidate();
//		repaint();
		Parent parent = getParent();
		panel = (CNotePanel) parent;
		for (int i = 0; i < parent.getChildrenUnmodifiable().size(); i++) {
			if(parent.getChildrenUnmodifiable().get(i).equals(this)) {
				this.priority = i;
			}
		}
		
		setText(noteText);
		setBackground( new Background( new BackgroundFill(CNoteTypes.typeMap.get(type), null, null/*new Insets(30)*/) ) );
//		setSelectedBorder(getSelected());
		
	}
	
	public void setSelectedBorder(boolean selected) {
		if(selected) {
			setBorder(new Border(new BorderStroke(CNoteTypes.getPaintFromType(type, true), BorderStrokeStyle.SOLID, null, null) ) );
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
	
	public void setPanel(CNotePanel newPanel) {
		delete();
		this.panel = newPanel;
		newPanel.addNote(this);
	}
	
	public void delete() {
		CNotePanel panel = (CNotePanel) this.getParent();
		if(panel != null) {
			panel.remove(this);
			panel.notes.remove(this);
		}
		JFXMain.root.infoPanel.setNote(null);
	}
	
	public class CMouseListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			JFXMain.root.infoPanel.setNote(CNote.this);
		}
	}
	
	
}
