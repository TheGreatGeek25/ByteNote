package c57note64.cnote.types.typeeditor;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.types.CTypeManagerPanel;

@SuppressWarnings("serial")
public class CTypeEditor extends JDialog {
	public static final int NEW_TYPE = 0;
	public static final int EDIT_TYPE = 1;
	
	public CTypeEditor(Window owner, String title) {
		super(owner, title);
		
		setLocationRelativeTo(null);
		setMinimumSize( new Dimension(650, 400));
		setSize(650, 400);
		setResizable(true);
		addWindowListener( new CWindowListener() );		
		
		setContentPane( new CTypeEditorPanel(this) );
		setModalityType(ModalityType.APPLICATION_MODAL);
		( (CTypeManagerPanel) ( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager.getContentPane()).typeEditor = this;
		setVisible(true);
		setEnabled(true);
		
	}
	
	public CTypeEditor(Window owner, String title, String type) {
		super(owner, title);
		
		setLocationRelativeTo(null);
		setMinimumSize( new Dimension(650, 400));
		setSize(650, 400);
		setResizable(true);
		addWindowListener( new CWindowListener() );		
		
		setContentPane( new CTypeEditorPanel(this, type) );
		setModalityType(ModalityType.APPLICATION_MODAL);
		( (CTypeManagerPanel) ( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager.getContentPane()).typeEditor = this;
		setVisible(true);
		setEnabled(true);
	}

	public void c57run() {
		((CTypeEditorPanel) getContentPane()).c57run();
		
	}
	
	public class CWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			( (CTypeManagerPanel) ( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager.getContentPane()).typeEditor = null;
			super.windowClosing(e);
		}
	}
	
}
