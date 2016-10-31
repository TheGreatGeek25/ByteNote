package c57note64.cnote;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.types.CNoteTypes;

@SuppressWarnings("serial")
public class CNote extends JLabel {
	
	public int priority;
	public String noteText;
	public String type;
	public CNotePanel panel;
	
	public CNote(int priority, String text, String type) {
		super();
		this.priority = priority;
		this.noteText = text;
		this.type = type;
		setBackground(CNoteTypes.typeMap.get(type));
		setText(this.noteText);
		setOpaque(true);
		setPreferredSize( new Dimension(10, 10) );
		addMouseListener( new CMouseListener() );
				
	}
	
	public CNote(int priority, String text, String type, CNotePanel panel) {
		this(priority, text, type);
		this.panel = panel;
	}
	
	public void c57run() {
		revalidate();
		repaint();
		Container parent = getParent();
		panel = (CNotePanel) parent;
		for (int i = 0; i < parent.getComponentCount(); i++) {
			if(parent.getComponent(i).equals(this)) {
				this.priority = i;
			}
		}
		
		setText(noteText);
		setBackground(CNoteTypes.typeMap.get(type));
		setSelectedBorder(getSelected());
		
	}
	
	public void setSelectedBorder(boolean selected) {
		if(selected) {
			setBorder(BorderFactory.createLineBorder( new Color(255-getBackground().getRed(), 255-getBackground().getGreen(), 255-getBackground().getBlue()) , 4));
		} else {
			setBorder(BorderFactory.createEmptyBorder());
		}
	}
	
	public boolean getSelected() {
		if(((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.note != null) {
			return ((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.note.equals(this);
		}
		return false;
	}
	
	public void setPanel(CNotePanel newPanel) {
		delete();
		newPanel.addNote(this);
	}
	
	public void delete() {
		CNotePanel panel = (CNotePanel) this.getParent();
		if(panel != null) {
			panel.remove(this);
			panel.notes.remove(this);
		}
		((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.setNote(null);
	}
	
	public class CMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			((CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.setNote(CNote.this);
		}
	}
	
	
}
