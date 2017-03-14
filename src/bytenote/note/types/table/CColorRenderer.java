package bytenote.note.types.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class CColorRenderer extends JLabel implements TableCellRenderer {

	public CColorRenderer() {
		setOpaque(true);
	}
	
	@Override 
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		// TODO Auto-generated method stub
		Color color = (Color)value;
		setBackground(color);
		if(isSelected) {
			setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground()));
		} else {
			setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground()));
		}
		try{
			float[] colorHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
			setToolTipText("<html>RGB: "+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+"<br/>"
					+ "HSB: "+colorHSB[0]+", "+colorHSB[1]+", "+colorHSB[2]+"</html>");
		} catch(Exception e) {
			
		}
		return this;
	}
	
}
