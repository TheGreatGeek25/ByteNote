package c57note64.cnote.editor;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.CNote;

@SuppressWarnings("serial")
public class CNoteEdit extends JDialog {
	
	public CNoteEdit(JFrame owner, String title, CNote editNote) {
		super(owner, title);
		
		setLocationRelativeTo(null);
		setMinimumSize( new Dimension(300, 300));
		setResizable(true);
		addWindowListener( new CWindowListener() );		
		
		( (CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.noteEditor = this;
		
		setContentPane( new CNoteEditPanel(this, editNote) );
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		setVisible(true);
		setEnabled(true);
	}

	public void c57run() {
		revalidate();
		repaint();
		((CNoteEditPanel) getContentPane()).c57run();
	}
	
	public class CWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			( (CMainPanel) C57note64Main.c57main.getContentPane()).infoPanel.noteEditor = null;
			super.windowClosing(e);
		}
	}
}
