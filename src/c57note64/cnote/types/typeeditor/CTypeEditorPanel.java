package c57note64.cnote.types.typeeditor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import c57note64.C57note64Main;
import c57note64.CMainPanel;
import c57note64.cnote.types.CNoteTypes;
import c57note64.cnote.types.CTypeManagerPanel;

@SuppressWarnings("serial")
public class CTypeEditorPanel extends JPanel {
	
	public String typeStr;
	public Color typeColor;
	
	public JLabel typeStrLabel;
	public JLabel typeColorLabel;
	
	public JColorChooser cc;
	public JTextField typeStrEdit;
	public JButton doneButton;
	
	protected CTypeEditor parent;
	
	public CTypeEditorPanel(CTypeEditor cTypeEditor) {
		super(true);
		this.parent = cTypeEditor;
		
//		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		setLayout( new GridBagLayout() );
		GridBagConstraints gbc = new GridBagConstraints();
		
		typeStr = "Enter type name here";
		typeColor = new Color(0, 0, 255);
		
		
		
		typeStrEdit = new JTextField(typeStr, 15);
		gbc.gridy = 1;
		add(typeStrEdit, gbc);
		
		cc = new JColorChooser();
		gbc.gridy = 3;
		add(cc, gbc);
		
		doneButton = new JButton( new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveType();
				CTypeEditorPanel.this.parent.dispatchEvent(new WindowEvent(CTypeEditorPanel.this.parent, WindowEvent.WINDOW_CLOSING));
			}
		});
		doneButton.setText("Done");
		gbc.gridy = 4;
		add(doneButton, gbc);
		
		setEnabled(true);
		setVisible(true);
	}
	
	protected void saveType() {
		// TODO Auto-generated method stub
		System.out.println("Save type");
		if(CNoteTypes.typeMap.containsKey(typeStr)) {
			for (int i = 0; i < ( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel.notes.size(); i++) {
				if(( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel.notes.get(i).type.equals(typeStr)) {
					( (CMainPanel) C57note64Main.c57main.getContentPane()).todoPanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			for (int i = 0; i < ( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel.notes.size(); i++) {
				if(( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel.notes.get(i).type.equals(typeStr)) {
					( (CMainPanel) C57note64Main.c57main.getContentPane()).doingPanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			for (int i = 0; i < ( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel.notes.size(); i++) {
				if(( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel.notes.get(i).type.equals(typeStr)) {
					( (CMainPanel) C57note64Main.c57main.getContentPane()).donePanel.notes.get(i).type = typeStrEdit.getText();
				}
			}
			CNoteTypes.typeMap.remove(typeStr);
		}
		CTypeManagerPanel manager = (CTypeManagerPanel) ( (CMainPanel) C57note64Main.c57main.getContentPane()).controlPanel.typeManager.getContentPane();
		CNoteTypes.addToMap(typeStrEdit.getText(), cc.getColor());
		manager.loadData();
	}

	public CTypeEditorPanel(CTypeEditor cTypeEditor, String type) {
		this(cTypeEditor);
		typeStr = type;
		typeColor = CNoteTypes.typeMap.get(type);
		typeStrEdit.setText(typeStr);
		cc.setColor(typeColor);
	}

	public void c57run() {
		
	}

}
