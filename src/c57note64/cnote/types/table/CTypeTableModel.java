package c57note64.cnote.types.table;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class CTypeTableModel extends AbstractTableModel {
	
	public Object[][] data;
	public String[] columnNames;
	
	public CTypeTableModel(Object[][] data, String[] columnnames) {
		this.data = data;
		this.columnNames = columnnames;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return getValueAt(0, columnIndex).getClass();
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
}
