package c57note64.cnote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import c57note64.C57note64Main;

@SuppressWarnings("serial")
public class CNotePanel extends JPanel {
	
	public static double paneToWin = 1.0/3.0;
	
	public ArrayList<CNote> notes = new ArrayList<CNote>();
	public int minShownIndex = 0;
	
	public JButton pageDown;
	public JButton pageUp;
	
	public CNotePanel() {
		setName("NotePanel");
		setDoubleBuffered(true);
		setLayout( new GridLayout(0, 2, 15, 15) );
		setPreferredSize( new Dimension((int) (C57note64Main.c57main.getSize().width*paneToWin)-C57note64Main.c57main.getSize().width/14, C57note64Main.c57main.getSize().height+getComponentCount()*15) );
//		setMaximumSize( new Dimension((int) (C57note64Main.c57main.getSize().width*paneToWin), C57note64Main.c57main.getSize().height) );
		setBorder( BorderFactory.createLineBorder( new Color(0, 0, 0) , 5) );
		
		setVisible(true);
		setEnabled(true);
		setOpaque(true);
		
		/*for (int i = 0; i < 7; i++) {
			addNote( new CNote(i, "text", "type1") );
		}*/
		
	}
	
	public void c57run() {
		for (int i = 0; i < getComponents().length; i++) {
			if(getComponents()[i] instanceof CNote) {
				((CNote) getComponents()[i]).c57run();
			}
		}
		setPreferredSize( new Dimension((int) (C57note64Main.c57main.getSize().width*paneToWin)-C57note64Main.c57main.getSize().width/14, C57note64Main.c57main.getSize().height+getComponentCount()*15) );
//		setLayout( new GridLayout(/*15+*/getComponentCount()/2, 2, 15, 15) );
		repaint();
	}
	
	public void addNote(CNote note) {
		if(getComponentCount() >= note.priority && note.priority >= 0) {
			add(note, note.priority);
			notes.add(note);
		} else if(note.priority < 0) {
//			System.err.println("Priority invalid. Setting priority to 0");
			note.priority = 0;
			addNote(note);
		} else {
//			System.err.println("Priority too low. Setting priority to "+getComponentCount());
			note.priority = getComponentCount();
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
	
}
