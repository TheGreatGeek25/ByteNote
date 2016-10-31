package c57note64.cnote.types;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import c57note64.C57note64Main;
import c57note64.CMainPanel;

@SuppressWarnings("serial")
public class CTypeManager extends JDialog {

	public CTypeManager(Window owner, String title) {
		super(owner, title);
		
		setLocationRelativeTo(null);
		setMinimumSize( new Dimension(300, 300));
		setSize(400, 400);
		setResizable(true);
		addWindowListener( new CWindowListener() );		
		
		setContentPane( new CTypeManagerPanel(this) );
		setModalityType(ModalityType.APPLICATION_MODAL);
		( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager = this;
		setVisible(true);
		setEnabled(true);
		
	}
	
	
	
	public void c57run() {
		revalidate();
		repaint();
		((CTypeManagerPanel) getContentPane()).c57run();
		
	}
	
	public class CWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager = null;
			super.windowClosing(e);
		}
	}
	
}
