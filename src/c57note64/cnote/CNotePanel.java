package c57note64.cnote;

import java.util.ArrayList;

import javax.swing.JButton;

import c57note64.CInfoPanel;
import c57note64.JFXMain;
import c57note64.cnote.types.CNoteTypes;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class CNotePanel extends GridPane {
	
	public static double paneToWin = 1.0/3.0;
	
	public ArrayList<CNote> notes = new ArrayList<CNote>();
	public int minShownIndex = 0;
	
	public JButton pageDown;
	public JButton pageUp;
	
	public Integer columns = 2;
	
	public CNotePanel() {
		super();
		
		ObservableList<ColumnConstraints> c = getColumnConstraints();
		c.clear();
		for (int i=0; i<columns; i++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHalignment(HPos.CENTER);
			cc.setHgrow(Priority.ALWAYS);
//			cc.setMinWidth(50*columns/*-(columns-1*15)*/);
			c.add(cc);
		}
		
		setPrefSize((JFXMain.mainStage.getWidth()*paneToWin)-JFXMain.mainStage.getWidth()/42, Math.max(JFXMain.mainStage.getHeight()-CInfoPanel.defaultHeight*2, getChildren().size()*15));
		setHgap(15);
		setVgap(15);
		
//		setMaximumSize( new Dimension((int) (C57note64Main.c57main.getSize().width*paneToWin), C57note64Main.c57main.getSize().height) );
		setBorder( new Border( new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(8) ) ) );
		
		setVisible(true);
				
//		setEnabled(true);
//		setOpaque(true);
		
		CNoteTypes.addToMap("type1", Color.BLUE);
		
		for (int i = 0; i < 16; i++) {
			addNote( new CNote(i, "text"+i, "type1") );
		}
		
		refreshLayout();
		
	}
	
	public void refreshLayout() {
		ObservableList<Node> children = getChildren();
		for (int i = 0; i < children.size(); i++) {
			GridPane.setConstraints(children.get(i), i%columns, i/columns);
			( (CNote) children.get(i) ).setPrefSize(this.getWidth()/2-15*3, this.getHeight()/Math.floor(children.size()/2)-15*(Math.floor(children.size()/2)) );
		}
	}
	
	public void c57run() {
		refreshLayout();
		for (int i = 0; i < getChildren().size(); i++) {
			if(getChildren().get(i) instanceof CNote) {
				((CNote) getChildren().get(i)).c57run();
			}
		}
		setPrefSize((JFXMain.root.getWidth()*paneToWin)-JFXMain.root.getWidth()/42, Math.max(JFXMain.root.getHeight()-CInfoPanel.defaultHeight*2, getChildren().size()*15));
//		setLayout( new GridLayout(/*15+*/getChildren().size()/2, 2, 15, 15) );
		
//		repaint();
	}
	
	public void addNote(CNote note) {
		if(getChildren().size() >= note.priority && note.priority >= 0) {
			getChildren().add(note.priority, note);
			notes.add(note);
		} else if(note.priority < 0) {
//			System.err.println("Priority invalid. Setting priority to 0");
			note.priority = 0;
			addNote(note);
		} else {
//			System.err.println("Priority too low. Setting priority to "+getComponentCount());
			note.priority = getChildren().size();
			addNote(note);
		}
		
		
	}
	
	public int getMinUnusedPriority() {
		int i = 0;
		while(true) {
			if(getNoteWithPriority(i) == null) {
				return i;
			}
			i++;
		}
	}
		
	public CNote getNoteWithPriority(int priority) {
		for (int i = 0; i < notes.size(); i++) {
			if (notes.get(i).priority == priority) {
				return notes.get(i);
			}
		}
//		System.err.println("No note with priority: "+priority);
		return null;
	}

	public void remove(CNote cNote) {
		this.getChildren().remove(cNote);
	}
	
}
