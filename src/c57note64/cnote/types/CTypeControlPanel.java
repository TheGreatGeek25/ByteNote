package c57note64.cnote.types;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.types.table.CTypeTableModel;
import c57note64.cnote.types.typeeditor.CTypeEditor;

@SuppressWarnings("serial")
public class CTypeControlPanel extends JPanel {
	
	public JButton deleteButton;
	public JButton addButton;
	public JButton editButton;
	
	public CTypeControlPanel() {
		super(true);
		
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS ) );
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		
		addButton = new JButton( new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				C57note64Main.isSaved = false;
				new CTypeEditor(( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager, "Edit type");
				
			}
		});
		//TODO
		addButton.setText("Add...");
		add(addButton);
		add(Box.createVerticalGlue());
		
		editButton = new JButton( new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				C57note64Main.isSaved = false;
				String type = (String) ( (CTypeManagerPanel) getParent()).data[( (CTypeManagerPanel) getParent()).mainTable.getSelectedRow()][0];
				new CTypeEditor(( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager, "Edit type", type);
				
			}
		});
		editButton.setEnabled(false);
		editButton.setText("Edit...");
		add(editButton);
		add(Box.createVerticalGlue());
		
		deleteButton = new JButton(  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				C57note64Main.isSaved = false;
				( (CTypeManagerPanel) getParent()).deleteType(( (CTypeManagerPanel) getParent()).mainTable.getSelectedRow());
				( (CTypeManagerPanel) getParent()).mainTable.setModel( new CTypeTableModel(( (CTypeManagerPanel) getParent()).data, ( (CTypeManagerPanel) getParent()).columnNames) );
			}
		} );
		deleteButton.setEnabled(false);
		deleteButton.setText("Delete");
		add(deleteButton);
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		
	}
	
	public void c57run() {
//		System.out.println(( (CTypeManagerPanel) getParent()).mainTable.getSelectedRow());
		if(( (CTypeManagerPanel) getParent()).mainTable.getSelectedRow() != -1 ) {
			deleteButton.setEnabled(true);
			editButton.setEnabled(true);
		} else {
			deleteButton.setEnabled(false);
			editButton.setEnabled(false);
		}

	}
}
