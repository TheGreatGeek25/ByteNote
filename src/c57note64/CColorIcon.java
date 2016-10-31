package c57note64;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class CColorIcon implements Icon {
	
	public int width;
	public int height;
	public Color color;
	
	public CColorIcon(Color c, int width, int height) {
		this.width = width;
		this.height = height;
		this.color = c;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {

	    g.setColor(color);
	    g.fillRect(x,y, width,height); 
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

}
