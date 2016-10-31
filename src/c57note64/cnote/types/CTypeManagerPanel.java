package c57note64.cnote.types;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import c57note64.cnote.types.table.CColorRenderer;
import c57note64.cnote.types.table.CTypeTableModel;
import c57note64.cnote.types.typeeditor.CTypeEditor;

@SuppressWarnings("serial")
public class CTypeManagerPanel extends JPanel {
	
	public JScrollPane mainScrollPane;
	public CTypeControlPanel controlPanel;
	public JTable mainTable;
	
	public CTypeEditor typeEditor;
	
	public final String[] columnNames = {"Type name", "Color"};
	public Object[][] data;

	public CTypeManagerPanel(CTypeManager cTypeManager) {
		super(true);
		setLayout( new BorderLayout() );
		
		
		mainTable = new JTable();
		loadData();
//		mainTable.setPreferredSize(new Dimension(cTypeManager.getWidth()-17, cTypeManager.getHeight()) );
		mainTable.setFillsViewportHeight(true);
		mainTable.setDefaultRenderer(Color.class, new CColorRenderer() );
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		mainScrollPane = new JScrollPane(mainTable);
//		mainScrollPane.setPreferredSize(new Dimension(cTypeManager.getWidth()-17, cTypeManager.getHeight()) );
		add(mainScrollPane, BorderLayout.CENTER);
		
		controlPanel = new CTypeControlPanel();
		controlPanel.setBackground( new Color(255, 0, 0) );
		controlPanel.setPreferredSize( new Dimension(100, 200) ); 
		add(controlPanel, BorderLayout.LINE_END);

	}

	public void c57run() {
		revalidate();
		repaint();
		saveData();
		controlPanel.c57run();
	}
	
	public void loadData() {
		data = new Object[CNoteTypes.typeMap.keySet().size()][columnNames.length];
		for (int i = 0; i < CNoteTypes.typeMap.keySet().toArray().length; i++) {
			data[i][0] = CNoteTypes.typeMap.keySet().toArray()[i];
			data[i][1] = CNoteTypes.typeMap.get(CNoteTypes.typeMap.keySet().toArray()[i]);
		}
		mainTable.setModel( new CTypeTableModel(data, columnNames) );
	}
	
	public void deleteType(int row) {
		ArrayList<Object[]> asList = new ArrayList<Object[]>(Arrays.asList(data));
		asList.remove(row);
		Object[][] newData = new Object[asList.size()][data[0].length];
		asList.toArray(newData);
		data = newData.clone();
		
	}
	
	public void saveData() {
		CNoteTypes.typeMap.clear();
		for (int i = 0; i < data.length; i++) {
			CNoteTypes.addToMap((String) data[i][0], (Color) data[i][1]);
			
		}
	}

}
