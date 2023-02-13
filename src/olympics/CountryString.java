package olympics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CountryString extends JPanel{
	private String name;
	private int x, y;
	public CountryString(String name, int x, int y) {
		this.name  = name;
		this.x = x;
		this.y = y;
		setBounds(x, y, 100, 20);
	}
	
	@Override
	public void paint(Graphics g) {
		drawString((Graphics2D)g);
	}
	
	public void drawString(Graphics2D g) {
		g.drawString(name, x, y);
	}
}
